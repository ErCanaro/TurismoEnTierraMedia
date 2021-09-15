package tierraMedia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Archivo {

	FileReader fr = null;
	BufferedReader br = null;
	FileWriter fw = null;
	private ArrayList<Atraccion> atraccionesDB = new ArrayList<Atraccion>();
	private ArrayList<Producto> promocionesDB = new ArrayList<Producto>();
	private ArrayList<Usuario> usuariosDB = new ArrayList<Usuario>();

	protected ArrayList<Atraccion> getAtraccionesDB() {
		return atraccionesDB;
	}

	protected ArrayList<Producto> getPromocionesDB() {
		return promocionesDB;
	}

	protected ArrayList<Usuario> getUsuariosDB() {
		return usuariosDB;
	}

	public void crearUsuariosDesdeArchivo() {
		try {
			fr = new FileReader("usuarios.txt");
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(",");
				String nombre = datos[0];
				TipoAtraccion tipo = TipoAtraccion.valueOf(datos[1]);
				int presupuesto = Integer.parseInt(datos[2]);
				double tiempoDisponible = Double.parseDouble(datos[3]);

				Usuario user = new Usuario(nombre, tipo, presupuesto, tiempoDisponible);
				if (!usuariosDB.contains(user)) {
					usuariosDB.add(user);
				}
			}
			fr.close();
		} catch (IOException e) {
			System.err.print("No se encuentra el archivo");
		}

	}

	public void crearAtraccionesDesdeArchivo() {
		try {
			fr = new FileReader("atracciones.txt");
			br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(",");
				String nombre = datos[0];
				TipoAtraccion tipo = TipoAtraccion.valueOf(datos[1]);
				int costo = Integer.parseInt(datos[2]);
				double duracion = Double.parseDouble(datos[3]);
				int cupo = Integer.parseInt(datos[4]);

				Atraccion atr = new Atraccion(nombre, tipo, costo, duracion, cupo);
				if (!atraccionesDB.contains(atr)) {
					atraccionesDB.add(atr);
				}
			}
			fr.close();
		} catch (IOException e) {
			System.err.print("No se encuentra el archivo");
		}

	}

	public void crearPromocionesDesdeArchivos() {
		// Aca se podría implementar una verificación para el tipo de promo
		try {
			try {
				fr = new FileReader("promociones.txt");
				br = new BufferedReader(fr);
				String linea;
				while ((linea = br.readLine()) != null) {
					String[] datos = linea.split(",");
					String nombre = datos[0];
					TipoAtraccion tipo = TipoAtraccion.valueOf(datos[1]);

					Atraccion a1 = null;
					Atraccion a2 = null;
					Atraccion a3 = null;
					for (Atraccion atr : atraccionesDB) {
						if (atr.getNombre().equals(datos[2])) {
							a1 = atr;
						} else if (atr.getNombre().equals(datos[3])) {
							a2 = atr;
						} else if (atr.getNombre().equals(datos[4])) {
							a3 = atr;
						}
					}

					int descuentoPorcentual = Integer.parseInt(datos[5]);
					int costoAbsoluto = Integer.parseInt(datos[6]);
					int tipoPromo = Integer.parseInt(datos[7]);

					if (tipoPromo == 1) {
						PromocionAxB promoAxB = new PromocionAxB(nombre, tipo, a1, a2, a3);
						promocionesDB.add(promoAxB);
					} else if (tipoPromo == 2) {
						PromoPorcentual promoPorcent = new PromoPorcentual(nombre, tipo, a1, a2, descuentoPorcentual);
						promocionesDB.add(promoPorcent);
					} else if (tipoPromo == 3) {
						PromocionAbsoluta promoAbsol = new PromocionAbsoluta(nombre, tipo, a1, a2, costoAbsoluto);
						promocionesDB.add(promoAbsol);
					}
				}
				fr.close();

			} catch (NumberFormatException nfe) {
				System.err.print("El tipo de dato no coincide");
			}
		} catch (IOException e) {
			System.err.print("No se encuentra el archivo");
		}
	}

	public void imprimirAtraccionesEnArchivo() {
		try {
			PrintWriter salida = new PrintWriter(new FileWriter("atraccionesDB3.txt"));

			for (int i = 0; i < atraccionesDB.size(); i++) {
				salida.println(atraccionesDB.get(i).imprimirEnArchivo());
			}
			salida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void imprimirPromocionesEnArchivo() {
		try {
			PrintWriter salida = new PrintWriter(new FileWriter("promocionesDB3.txt"));

			for (Producto promo : promocionesDB) {
				salida.println(promo.imprimirEnArchivo());
			}
			salida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
