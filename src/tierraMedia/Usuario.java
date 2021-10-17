package tierraMedia;

import java.util.ArrayList;
import java.util.Objects;

import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.UsuarioDAO;

public class Usuario {
	private Long IdUsuario;
	private String nombre;
	private int presupuesto;
	private double tiempoDisponible;
	private TipoAtraccion preferencia;
	private Posicion posicion;
	private ArrayList<Atraccion> historialDeAtracciones = new ArrayList<Atraccion>(); // Cambio visibilidad ver si rompe
	private ArrayList<Producto> itinerario = new ArrayList<Producto>();


	public Usuario(String nombre, TipoAtraccion preferencia, int presupuesto, double tiempoDisponible) {
		this.nombre = nombre;
		this.preferencia = preferencia;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
	}

	public Usuario(Long idUsuario, String nombre, int dinero, double tiempo, Posicion posicion, TipoAtraccion preferencia) {
		this.IdUsuario = idUsuario;
		this.nombre = nombre;
		this.preferencia = preferencia;
		this.presupuesto = dinero;
		this.tiempoDisponible = tiempo;
		this.posicion = posicion;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public ArrayList<Atraccion> getHistorialDeAtracciones() {
		return this.historialDeAtracciones;
	}

	public String getNombre() {
		return this.nombre;
	}

	public TipoAtraccion getPreferencia() {
		return this.preferencia;
	}

	public int getPresupuesto() {
		return this.presupuesto;
	}

	public double getTiempoDisponible() {
		return this.tiempoDisponible;
	}

	public ArrayList<Atraccion> getHistorialDeProductos() {
		return this.historialDeAtracciones;
	}

	public ArrayList<Producto> getItinerario() {
		return this.itinerario;
	}

	public void aceptarSugerencia(Producto sugerencia) {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO(); 
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		
		if (this.presupuesto >= sugerencia.getCosto() && this.tiempoDisponible >= sugerencia.getDuracion()
				&& sugerencia.getCupo() > 0 && !this.historialDeAtracciones.contains(sugerencia)) {
			this.itinerario.add(sugerencia);
			this.historialDeAtracciones.addAll(sugerencia.getAtraccionesIncluidas());
			this.tiempoDisponible -= sugerencia.getDuracion();
			this.presupuesto -= sugerencia.getCosto();
			//System.out.println("aceptar sugerincia ---- cantidad de atraciones incluidas: "+ sugerencia.getAtraccionesIncluidas().size());
			for (Atraccion atr : sugerencia.getAtraccionesIncluidas()) {
				atr.restarCupo();
				atraccionDAO.actualizarCupo(atr);
				//System.out.println("Id_Atraccion: " + atr.getIdAtraccion());
				//System.out.println(atr);
			}
			
		usuarioDAO.actualizar(this);
			
		}

	}

	public void itinerarioToString() {
		int costoTotal = 0;
		double duracionTotal = 0;
		for (Producto prod : this.itinerario) {
			costoTotal += prod.getCosto();
			duracionTotal += prod.getDuracion();
		}

		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("*******************************SU ITINERARIO*********************************");
		System.out.println("------------------------ ------------------------- --------------------------");
		System.out.printf("%-12s%-14s%-14s%-13s\n", "Nombre", this.getNombre(), "Preferencia", this.getPreferencia());
		System.out.printf("%-12s%-14d%-14s%-13.1f\n", "Presupuesto", this.getPresupuesto(), "Tiempo Disp.",
				this.getTiempoDisponible());
		System.out.println("-----------------------------------------------------------------------------\n");

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< ---Productos Adquiridos--- >>>>>>>>>>>>>>>>>>>>>>>>\n");
		for (int i = 0; i < this.itinerario.size(); i++) {
			System.out.println(i + 1 + "- " + itinerario.get(i) + "\n");

		}

		System.out.println("------------------------ ************************* --------------------------");
		System.out.println("_____________________________________________________________________________");
		System.out.printf("%-12s%-15s%-15d%-17s%-18.1f\n", " ", "COSTO TOTAL= ", costoTotal, "TIEMPO ESTIMADO",
				duracionTotal);
		System.out.println("_____________________________________________________________________________\n");
		System.out.printf("%-28s%-24s\n", " ", "GRACIAS POR SU VISITA");
		System.out.println("------------------------ ************************* --------------------------");

	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + ", Preferencia: " + preferencia + ", Presupuesto: " + presupuesto
				+ ", Tiempo Disponible: " + tiempoDisponible;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nombre, other.nombre);
	}

	public Long getIdUsuario() {
		return this.IdUsuario;
	}

	public double getPosX() {
		return this.posicion.getX();
	}

	public double getPosY() {
		return this.posicion.getY();
	}

}
