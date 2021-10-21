package tierraMedia;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Producto {
	private long IdProducto;
	private String nombre;
	private TipoAtraccion tipo;
	private int costo;
	private double duracion;
	protected boolean esPromo;
	protected ArrayList<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

	public Producto(String nombre, TipoAtraccion tipo, int costo, double duracion) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.costo = costo;
		this.duracion = duracion;
	}


	public Producto(long id, String nombre, TipoAtraccion tipo, double costo, double duracion) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.costo = (int)costo;
		this.duracion = duracion;
		this.IdProducto = id;
	}




	public long getIdProducto() {
		return IdProducto;
	}

	public ArrayList<Atraccion> getAtraccionesIncluidas() {
		return atraccionesIncluidas;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoAtraccion getTipo() {
		return tipo;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public int getCupo() {
		int cupo1 = Integer.MAX_VALUE;
		for (Atraccion atr : atraccionesIncluidas) {
			if (atr.getCupo() < cupo1) {
				cupo1 = atr.getCupo();
			}
		}
		return cupo1;
	}

	public boolean esPromo() {
		return esPromo;
	}

	public void setPromo(boolean isPromo) {
		this.esPromo = isPromo;
	}

	public String imprimirEnArchivo() {
		return this.nombre + "," + this.tipo + "," + this.costo + "," + this.duracion;
	}

	public abstract void mostrarPorPantalla();

	@Override
	public String toString() {
		return "-->Nombre: " + nombre + ", Tipo: " + tipo + ", Costo: " + costo + ", Duracion: " + duracion;
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
		Producto other = (Producto) obj;
		return Objects.equals(nombre, other.nombre);
	}

}
