package dao;


import tierraMedia.Producto;


public interface ItinerarioDAO extends GenericDAO<Producto> {
	
	//public abstract Producto buscarItemItinerarioPorIdProducto(long IdProducto);
	//public abstract int agregarItemItinerario(long  IdUsuario, Producto producto);
	//public abstract long obtenerIDItinerarioPorIdUsuario(long IdUsuario);
	public int insertarItemItinerario(long  IdUsuario, long IdProducto, String tipo);

}
