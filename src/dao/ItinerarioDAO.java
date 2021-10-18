package dao;

import tierraMedia.Atraccion;
import tierraMedia.Itinerario;

public interface ItinerarioDAO extends GenericDAO<Itinerario> {
	
	public abstract Itinerario buscarPorIdItinerario(Long IdItinerario);
	public abstract int agregarItemItinerario(Atraccion atraccion);
	public abstract long obtenerIDItinerarioPorIdUsuario(Long idUsuario);
	public abstract int insertarItemItinerario(long idItinerario, long idAtraccion);

}
