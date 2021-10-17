package tierraMedia;

import java.util.ArrayList;

public class PromocionAxB extends Producto {

	public PromocionAxB(String nombre, TipoAtraccion tipo, Atraccion a1, Atraccion a2, Atraccion a3) {
		super(nombre, tipo, 0, 0);
		super.setCosto(a1.getCosto() + a2.getCosto());
		super.setDuracion(a1.getDuracion() + a2.getDuracion() + a3.getDuracion());
		super.setPromo(true);
		this.atraccionesIncluidas.add(a1);
		this.atraccionesIncluidas.add(a2);
		this.atraccionesIncluidas.add(a3);
	}

//	public boolean verificarTipoDeAtraccion(Atraccion a1, Atraccion a2, Atraccion a3) {
//		return this.getTipo() == a1.getTipo() && this.getTipo() == a2.getTipo() && this.getTipo() == a3.getTipo();
//	}


	public PromocionAxB(String nombre, TipoAtraccion tipoAtraccion, ArrayList<Atraccion> atraccionesIncluidas) {
		super(nombre, tipoAtraccion, 0, 0);
		super.setCosto(atraccionesIncluidas.get(0).getCosto() + atraccionesIncluidas.get(1).getCosto());
		super.setDuracion(atraccionesIncluidas.get(0).getDuracion() + atraccionesIncluidas.get(1).getDuracion() + atraccionesIncluidas.get(1).getDuracion());
		super.setPromo(true);
		this.atraccionesIncluidas= atraccionesIncluidas;
	}

	@Override
	public String toString() {
		return "-->[Promocion: " + super.getTipo() + "]" + "'" + super.getNombre() + "', Costo: " + super.getCosto()
				+ " Duracion: " + super.getDuracion() + ".\n" + "    -Atracciones Incluidas: ["
				+ atraccionesIncluidas.get(0).getNombre() + ", " + atraccionesIncluidas.get(1).getNombre() + ", "
				+ atraccionesIncluidas.get(2).getNombre() + "]";
	}

	@Override
	public void mostrarPorPantalla() {
		System.out.printf("%-22s%-14s%-8d%-10.1f%-7s", getNombre(), getTipo(), this.getCosto(), getDuracion(), getCupo());
		System.out.println(
				"[" + getAtraccionesIncluidas().get(0).getNombre() + ", " + getAtraccionesIncluidas().get(1).getNombre()
						+ ", " + getAtraccionesIncluidas().get(2).getNombre() + "]");
	}

}
