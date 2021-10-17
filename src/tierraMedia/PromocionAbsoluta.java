package tierraMedia;

import java.util.ArrayList;

public class PromocionAbsoluta extends Producto {

	public PromocionAbsoluta(String nombre, TipoAtraccion tipo, Atraccion a1, Atraccion a2, int costo) {
		super(nombre, tipo, costo, 0);
		super.setDuracion(a1.getDuracion() + a2.getDuracion());
		super.setPromo(true);
		this.atraccionesIncluidas.add(a1);
		this.atraccionesIncluidas.add(a2);
	}


	public PromocionAbsoluta(String nombre, TipoAtraccion tipoAtraccion, ArrayList<Atraccion> atraccionesIncluidas,
			int costo) {
		super(nombre, tipoAtraccion, costo, 0);
		super.setDuracion(atraccionesIncluidas.get(0).getDuracion() + atraccionesIncluidas.get(1).getDuracion());
		super.setPromo(true);
		this.atraccionesIncluidas= atraccionesIncluidas;
	}


	@Override
	public String toString() {
		return "-->[Promocion: " + super.getTipo() + "]" + "'" + super.getNombre() + "', Costo: " + super.getCosto()
				+ ", Duracion: " + super.getDuracion() + "\n     -Atracciones Incluidas: ["
				+ atraccionesIncluidas.get(0).getNombre() + ", " + atraccionesIncluidas.get(1).getNombre() + "]";
	}

	@Override
	public void mostrarPorPantalla() {
		System.out.printf("%-22s%-14s%-8d%-10.1f%-7s", getNombre(), getTipo(), this.getCosto(), getDuracion(), getCupo());
		System.out.println("[" + getAtraccionesIncluidas().get(0).getNombre() + ", "
				+ getAtraccionesIncluidas().get(1).getNombre() + "]");
	}

}
