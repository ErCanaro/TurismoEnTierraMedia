package dao;

public class DAOFactory {

	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImpl();
	}
	
	public static AtraccionDAO getAtraccionDAO() {
		return new AtraccionDAOImpl();
	}

	
	public static PromocionDAOImpl getPromocionDAO() {
		return new PromocionDAOImpl();
	}
	
	/*public static ItinerarioDAOImpl getItinerarioDAO() {
		return new PromocionDAOImpl();
	}
	*/
	
	
}