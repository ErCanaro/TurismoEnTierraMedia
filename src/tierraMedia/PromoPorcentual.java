package tierraMedia;

import java.util.ArrayList;

public class PromoPorcentual extends Producto {
	private long idPromocion;
	private int porcentajeDcto;

	public PromoPorcentual(String nombre, TipoAtraccion tipo, Atraccion a1, Atraccion a2, int porcentajeDcto) {
		super(nombre, tipo, 0, 0);
		super.setCosto((a1.getCosto() + a2.getCosto()) * (100 - porcentajeDcto) / 100);// Revisar que calcule bien
		super.setDuracion(a1.getDuracion() + a2.getDuracion());
		super.setPromo(true);
		this.atraccionesIncluidas.add(a1);
		this.atraccionesIncluidas.add(a2);
	}

	public PromoPorcentual(long idPromocion, String nombre, TipoAtraccion tipoAtraccion, ArrayList<Atraccion> atraccionesIncluidas,	int descuentoPorcentual, int costo, double duracion) {
		super(idPromocion, nombre, tipoAtraccion, costo, duracion);
		//super.setCosto((atraccionesIncluidas.get(0).getCosto() + atraccionesIncluidas.get(1).getCosto()) * (100 - porcentajeDcto) / 100);// Revisar que calcule bien
		//super.setDuracion(atraccionesIncluidas.get(0).getDuracion() + atraccionesIncluidas.get(1).getDuracion());
		super.setPromo(true);
		this.atraccionesIncluidas= atraccionesIncluidas;
		this.idPromocion = idPromocion;
		
	}

	public int getPorcentajeDcto() {
		return this.porcentajeDcto;
	}

	@Override
	public String toString() {
		return "-->[Promocion: " + super.getTipo() + "]" + "'" + super.getNombre() + "', Costo: " + super.getCosto()
				+ " Duracion: " + super.getDuracion() + "\n     -Atracciones Incluidas: ["
				+ atraccionesIncluidas.get(0).getNombre() + ", " + atraccionesIncluidas.get(1).getNombre() + "]";
	}

	@Override
	public void mostrarPorPantalla() {
		System.out.printf("%-22s%-14s%-8d%-10.1f%-7s", getNombre(), getTipo(), this.getCosto(), getDuracion(), getCupo());
		System.out.println("[" + getAtraccionesIncluidas().get(0).getNombre() + ", "
				+ getAtraccionesIncluidas().get(1).getNombre() + "]");
	}
}
