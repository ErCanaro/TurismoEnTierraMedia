package tierraMedia;

public class Atraccion extends Producto {
	private int cupo;

	public Atraccion(String nombre, TipoAtraccion tipo, int costo, double duracion, int cupo) {
		super(nombre, tipo, costo, duracion);
		this.cupo = cupo;
		this.atraccionesIncluidas.add(this);
	}



	@Override
	public int getCupo() {
		return this.cupo;
	}
	public void restarCupo() {
		this.cupo -= 1;
	}

	@Override
	public String imprimirEnArchivo() {
		return this.getNombre() + "," + this.getTipo() + "," + this.getCosto() + "," + this.getDuracion() + ","
				+ this.cupo;
	}

	@Override
	public void mostrarPorPantalla() {
		System.out.printf("%-22s%-14s%-8d%-10.1f%-7d", getNombre(), getTipo(), this.getCosto(), getDuracion(),
				getCupo());
		System.out.println("[-]");

	}

}
