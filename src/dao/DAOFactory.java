package dao;

public class DAOFactory {

	public static UsuarioDAO getUserDAO() {
		return new UsuarioDAOImpl();
	}

}