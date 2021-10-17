package dao;

import tierraMedia.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {

	public abstract Usuario buscarPorIdUsuario(String username);

}