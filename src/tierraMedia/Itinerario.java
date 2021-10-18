package tierraMedia;


import java.util.ArrayList;

public class Itinerario {
	private long IdItinerario;
	private long IdUsuario;
	private int costo;
	private double tiempoNecesario;
	private ArrayList<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

	public Itinerario ( long IdUsuario, int costo, double tiempoNecesario) {
		this.IdItinerario = 0;
		this.IdUsuario = IdUsuario;
		this.costo = costo;
		this.tiempoNecesario = tiempoNecesario;

	}
	public Itinerario (long IdItinerario, long IdUsuario, int costo, double tiempoNecesario, ArrayList<Atraccion> atraccionesIncluidas) {
		this.IdItinerario = IdItinerario;
		this.IdUsuario = IdUsuario;
		this.costo = costo;
		this.tiempoNecesario = tiempoNecesario;
		this.atraccionesIncluidas = atraccionesIncluidas;
	}


	public Itinerario(long  IdUsuario) {
		this.IdUsuario =  IdUsuario;
		this.costo = 0;
		this.tiempoNecesario = 0;
	}
	
	public void agregarAtraccionAlItinerario (Atraccion atraccion) {
		atraccionesIncluidas.add(atraccion);
	}




	// ------------------------------- GETTERS Y SETTERS -------------------------------------

	public long getIdItinerario() {
		return this.IdItinerario;
	}
	
	
	public void setIdItinerario(long idItinerario) {
		this.IdItinerario = idItinerario;
	}


	public int getCosto() {
		return this.costo;
	}


	public void setCosto(int costo) {
		this.costo = costo;
	}


	public double getTiempoNecesario() {
		return this.tiempoNecesario;
	}


	public void setTiempoNecesario(double tiempoNecesario) {
		this.tiempoNecesario = tiempoNecesario;
	}


	public ArrayList<Atraccion> getAtraccionesIncluidas() {
		return this.atraccionesIncluidas;
	}


	public void setAtraccionesIncluidas(ArrayList<Atraccion> atraccionesIncluidas) {
		this.atraccionesIncluidas = atraccionesIncluidas;
	}
	
}
