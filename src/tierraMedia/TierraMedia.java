package tierraMedia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.PromocionDAO;
import dao.UsuarioDAO;

public class TierraMedia {

	private ArrayList<Atraccion> atraccionesDB = new ArrayList<Atraccion>();
	private ArrayList<Producto> promocionesDB = new ArrayList<Producto>();
	private ArrayList<Producto> productosDB = new ArrayList<Producto>();
	private ArrayList<Usuario> usuariosDB = new ArrayList<Usuario>();
	static Scanner sc = new Scanner(System.in);

	public ArrayList<Atraccion> getAtraccionesDB() {
		return this.atraccionesDB;
	}

	public ArrayList<Producto> getProductosDB() {
		return this.productosDB;
	}

	public ArrayList<Producto> getPromocionesDB() {
		return this.promocionesDB;
	}

	public ArrayList<Usuario> getUsuariosDB() {
		return this.usuariosDB;
	}

	public void cargarDatos() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		Archivo archivo = new Archivo();
		archivo.crearAtraccionesDesdeArchivo();
		archivo.crearPromocionesDesdeArchivos();
		//archivo.crearUsuariosDesdeArchivo();

		this.atraccionesDB = atraccionDAO.buscarTodos();   // archivo.getAtraccionesDB();
		this.promocionesDB = promocionDAO.buscarTodos();   // archivo.getPromocionesDB();         //promocionDAO.buscarTodos();   //
		this.usuariosDB = usuarioDAO.buscarTodos();        // archivo.getUsuariosDB();

		this.productosDB.addAll(this.atraccionesDB);
		this.productosDB.addAll(this.promocionesDB);
	}

	/**
	 * Este método recorre la colección de productos y devuelve una colección
	 * ordenada filtrando según las preferencias del usuario, si es atraccion o
	 * promoción, y de acuerdo al costo del producto.
	 * 
	 * @param usuario
	 * @return ArrayList<Producto> ordenada.
	 */
	public ArrayList<Producto> ordenarSugerencia(Usuario usuario) {
		ArrayList<Producto> sugerencia = new ArrayList<Producto>();
		ArrayList<Producto> noPreferidas = new ArrayList<Producto>();

		for (Producto prod : this.productosDB) {
			int aux = 0;
			for (Atraccion atr : prod.getAtraccionesIncluidas()) {
				if (usuario.getHistorialDeAtracciones().contains(atr)) {
					aux++;
				}
			}
			if (prod.getCosto() <= usuario.getPresupuesto() && prod.getDuracion() <= usuario.getTiempoDisponible()
					&& prod.getCupo() > 0 && aux == 0) {
				if (prod.getTipo() == usuario.getPreferencia()) {
					sugerencia.add(prod);
				} else {
					noPreferidas.add(prod);
				}
			}
		}

		Comparator<Producto> ordenarSugerencia = Comparator.comparing(Producto::esPromo)
				.thenComparing(Producto::getCosto).thenComparing(Producto::getDuracion)
				.thenComparing(Producto::getNombre);
		Collections.sort(sugerencia, ordenarSugerencia.reversed());
		Collections.sort(noPreferidas, ordenarSugerencia.reversed());

		sugerencia.addAll(noPreferidas);
		return sugerencia;
	}

	/**
	 * Método utilizado para interactuar por consola. Muestra una lista de usuarios
	 * de la cual se selecciona uno y devuelve una lista de productos (atracciones y
	 * promociones) que el usuario es capaz de adquirir acorde a sus presupuesto y
	 * tiempo.
	 */
	public void crearOferta() {
		Usuario usr = seleccionarUsuario();

		mostrarOfertasPosibles(usr);

		usr.itinerarioToString();
	}

	/**
	 * Muestra por consola únicamente la lista de Productos que el usuario escogido
	 * puede adquirir. Este método es parte del método crearOferta();
	 * 
	 * @param usr
	 */
	public void mostrarOfertasPosibles(Usuario usr) {
		do {
			System.out.println("-----------------------------------------------------------------------");
			System.out.println("Por favor, " + usr.getNombre() + " escoja una oferta");
			System.out.println("Aún dispone de " + usr.getPresupuesto() + " Monedas y de " + usr.getTiempoDisponible()
					+ " horas.");
			System.out.println("-----------------------------------------------------------------------");
			System.out.printf("%-6s%-22s%-14s%-8s%-10s%-7s%-10s\n", "Nro", "NOMBRE", "TIPO", "COSTO", "DURACION",
					"CUPO", "INCLUYE");
			for (int i = 0; i < ordenarSugerencia(usr).size(); i++) {
				System.out.printf("%-6s", i + 1 + "> ");
				ordenarSugerencia(usr).get(i).mostrarPorPantalla();

			}
			System.out.println("-----------------------------------------------------------------------");
			System.out.print("[0] Si no desea adquirir más ofertas ingrese 0(cero)");
			;

			int eleccion1 = sc.nextInt();

			if (eleccion1 == 0) {
				break;
			} else if (eleccion1 > 0 && eleccion1 <= ordenarSugerencia(usr).size()) {
				usr.aceptarSugerencia(ordenarSugerencia(usr).get(eleccion1 - 1));

			}

		} while (ordenarSugerencia(usr).size() > 0);
	}

	/**
	 * Método para seleccionar por consola un usuario de una lista.
	 * 
	 * @return
	 */
	public Usuario seleccionarUsuario() {
		System.out.println("Por favor seleccione un usuario para hacerle una oferta: ");
		System.out.printf("%-6s%-15s%-15s%-15s%-10s", "Nro", "NOMBRE", "PREFERENCIA", "PRESUPUESTO",
				"TIEMPO DISPONIBLE\n");
		for (int i = 0; i < usuariosDB.size(); i++) {
			System.out.printf("%-6s%-15s%-15s%-15d%-10s%n", i + 1 + " - ", this.usuariosDB.get(i).getNombre(),
					this.usuariosDB.get(i).getPreferencia(), this.usuariosDB.get(i).getPresupuesto(),
					this.usuariosDB.get(i).getTiempoDisponible());
		}

		System.out.print("\nIngrese el número correspondiente al usuario escogido: ");
		int usuario = 0;
		int usrFlag = 0;
		Usuario usr = null;

		do {
			usuario = validarIngresoDeDatosNumericos();

			if (usuario > 0 && usuario <= this.usuariosDB.size()) {
				usr = this.usuariosDB.get(usuario - 1);
				usrFlag = 1;
			} else {
				System.out.println("La opción escogida no existe");
				sc.nextLine();
				usrFlag = 0;
			}
		} while (usrFlag != 1);

		return usr;
	}

	/**
	 * Método para validar el uso de datos al seleccionar opciones desde la consola.
	 * Muestra error si se ingresan datos incorrectos o fuera de rango.
	 * 
	 * @return
	 */
	public int validarIngresoDeDatosNumericos() {
		int aux = 0;
		int eleccion = 0;
		do {
			try {
				eleccion = sc.nextInt();
				if ((int) eleccion >= 0) {
					aux = 1;
				}
			} catch (InputMismatchException ime) {
				System.err.println("El valor ingresado debe ser un número entero");
				sc.nextLine();
				aux = 0;
			}
		} while (aux != 1);

		return eleccion;
	}

	/**
	 * Realiza sugerencias a cada uno de los usuarios de la colección de usuarios,
	 * quienes deberán decidir si las aceptan o no.
	 */
	public void ofrecerATodosLosUsuarios() {
		for (Usuario user : this.usuariosDB) {
			mostrarOfertasPosibles(user);
			System.out.println("NO HAY MAS OFERTAS DISPONIBLES PARA " + user.getNombre().toUpperCase());
			System.out.println("*********************************************************************\n");
		}
	}

	/**
	 * Formatea e imprime el Itinerario para el usuario escogido.
	 * 
	 * @param user
	 */
	public void imprimirItinerarioEnArchivo(Usuario user) {
		try {
			PrintWriter salida = new PrintWriter(new FileWriter(user.getNombre() + "Itinerario.txt"));

			int costoTotal = 0;
			double duracionTotal = 0;
			for (Producto prod : user.getItinerario()) {
				costoTotal += prod.getCosto();
				duracionTotal += prod.getDuracion();
			}

			salida.println("-----------------------------------------------------------------------------");
			salida.println("*******************************SU ITINERARIO*********************************");
			salida.println("------------------------ ------------------------- --------------------------");
			salida.printf("%-12s%-14s%-14s%-13s\n", "Nombre", user.getNombre(), "Preferencia", user.getPreferencia());
			salida.printf("%-12s%-14s%-14s%-13.1f\n", "Presupuesto", user.getPresupuesto(), "Tiempo Disp.",
					user.getTiempoDisponible());
			salida.println("-----------------------------------------------------------------------------\n");

			salida.println("<<<<<<<<<<<<<<<<<<<<<<<<< ---Productos Adquiridos--- >>>>>>>>>>>>>>>>>>>>>>>>\n");
			for (int i = 0; i < user.getItinerario().size(); i++) {
				salida.println(i + 1 + "- " + user.getItinerario().get(i) + "\n");

			}

			salida.println("------------------------ ************************* --------------------------");
			salida.println("_____________________________________________________________________________");
			salida.printf("%-12s%-15s%-15d%-17s%-18.1f\n", " ", "COSTO TOTAL= ", costoTotal, "TIEMPO ESTIMADO",
					duracionTotal);
			salida.println("_____________________________________________________________________________\n");
			salida.printf("%-28s%-24s\n", " ", "GRACIAS POR SU VISITA");
			salida.println("------------------------ ************************* --------------------------");

			salida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imprime los Itinerarios de cada uno de los usuarios de la colección.
	 */
	public void imprimirTodosLosItinerarios() {
		for (Usuario user : this.usuariosDB) {
			imprimirItinerarioEnArchivo(user);
		}
	}
	
	
	public Usuario obtenerUsuarioPorID(long IdUsuario) {
		Usuario usuario = null;
		for(Usuario usr : usuariosDB) {
			if(usr.getIdUsuario() == IdUsuario) {
				usuario = usr;
				break;
			}
		}
		return usuario;
	}
	
	
	

	public static void main(String[] args) {
		TierraMedia tm = new TierraMedia();
		tm.cargarDatos();

		int opcion = 0;

		System.out.println("---------- BIENVENIDOS A TIERRA MEDIA -------------");
		System.out.println("---------------------------------------------------");
		do {
			System.out.println("Que acción desea realizar?\n");
			System.out.println("1 - Ofrecer Sugerencias a Usuario");
			System.out.println("2 - Ofrecer Sugerencias a todos los usuarios");
			System.out.println("3 - Imprimir Itinerarios de todos los usuarios");
			System.out.println("4 - Salir\n");
			System.out.println("Ingrese el número correspondiente");
			int opcionFlag = 0;
			do {
				opcion = tm.validarIngresoDeDatosNumericos();
				if (opcion >= 1 && opcion <= 4) {
					opcionFlag = 1;
				} else {
					System.out.println("La opcion ingresada no existe!!");
					opcionFlag = 0;
				}

			} while (opcionFlag != 1);

			switch (opcion) {
			case 1:
				tm.crearOferta();
				break;
			case 2:
				tm.ofrecerATodosLosUsuarios();
				break;
			case 3:
				tm.imprimirTodosLosItinerarios();
				break;
			case 4:
				System.out.println("Programa Terminado!");
				System.exit(0);
				break;
			default:
				System.out.println("Opcion no disponible");
				break;
			}
		} while (opcion != 4);
	}

}