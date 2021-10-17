package dao;

import tierraMedia.Atraccion;


public interface AtraccionDAO extends GenericDAO<Atraccion>{

	public abstract Atraccion buscarPorIdAtraccion(Long IdAtraccion);
	public abstract int actualizarCupo(Atraccion atraccion);
}
