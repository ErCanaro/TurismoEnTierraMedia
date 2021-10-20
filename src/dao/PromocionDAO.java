package dao;

import tierraMedia.Producto;

public interface PromocionDAO extends GenericDAO <Producto>{
	
	public abstract Producto buscarPorIdPromocion(Long id);

}
