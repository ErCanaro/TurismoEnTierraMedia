package tierraMedia;


public class Posicion {
	private double x;
	private double y;
	
	public Posicion (double x, double y) {
		this.x = x;
		this.y = y;		
	}
	
	public double distanciaAotraPosicion(Posicion pos2) {
		double distEjeX = pos2.x - this.x;
		double distEjeY = pos2.y - this.y;
		return Math.sqrt( Math.pow(distEjeX, 2) + Math.pow(distEjeY, 2));
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return this.x + "," + this.y;
	}
}
