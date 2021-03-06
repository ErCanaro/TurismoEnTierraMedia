package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import jdbc.ProveedorDeConeccion;
import tierraMedia.Atraccion;
import tierraMedia.Posicion;
import tierraMedia.Producto;
import tierraMedia.TipoAtraccion;
import tierraMedia.Usuario;
import tierraMedia.TipoAtraccion;

public class UsuarioDAOImpl implements UsuarioDAO {

	public int insertar(Usuario usuario) {
		
		try {
			String sql = "INSERT INTO USUARIOS (NOMBRE, DINERO, TIEMPO, POSX, POSY, ID_TIPO_ATRACCION) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			//TIPO DE LA BD (TEXTO nombre, TEXTO TipoAtraccion, INTEGER presupuesto, REAL tiempoDisponible)
			statement.setString(1, usuario.getNombre());
			statement.setInt(2, usuario.getPresupuesto());
			statement.setDouble(3, usuario.getTiempoDisponible());
			statement.setDouble(4, usuario.getPosX());
			statement.setDouble(5, usuario.getPosY());
			statement.setLong(6, obtegerIdTipoAtraccion(usuario));

			int rows = statement.executeUpdate();

			statement.close();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
	}


	private long obtegerIdTipoAtraccion(Usuario usuario) {
		long id = 0;
		TipoAtraccion preferencia =  usuario.getPreferencia();
		switch(preferencia) {
			case AVENTURA:    id = 1; 	break;
			case PAISAJES:    id = 2; 	break;
			case DEGUSTACION: id = 3; 	break;
		}
		return id;
	}


	public int actualizar(Usuario usuario) {
		
		try {
			String sql = "UPDATE USUARIOS SET Nombre = ?, dinero = ?, tiempo = ?, PosX = ?, PosY = ?, id_tipo_atraccion = ? WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			statement.setInt(2, usuario.getPresupuesto());
			statement.setDouble(3, usuario.getTiempoDisponible());
			statement.setDouble(4, usuario.getPosX()); 
			statement.setDouble(5, usuario.getPosY()); 
			statement.setLong(6, obtegerIdTipoAtraccion(usuario)); 
			statement.setLong(7, usuario.getIdUsuario());
			int rows = statement.executeUpdate();

			statement.close();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
		
	}

	public int borrar(Usuario usuario) {
		
		try {
			String sql = "DELETE FROM USUARIOS WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, usuario.getIdUsuario());
			int rows = statement.executeUpdate();

			statement.close();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
	}

	public Usuario buscarPorIdUsuario(long idUsuario) {
		try {
			String sql = "SELECT Usuarios.id_usuario, Usuarios.nombre, Usuarios.dinero, " 
					+ "Usuarios.tiempo, Usuarios.PosX, Usuarios.PosY, Tipos_atraccion.Descripcion_tipo_atraccion "
					+ "FROM Usuarios INNER JOIN Tipos_atraccion "
					+ "ON Usuarios.id_tipo_atraccion = Tipos_atraccion.id_tipo_atraccion "
					+ "WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, idUsuario);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				user = toUsuario(resultados);
			}

			statement.close();
			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
		
	}

	public int contarTodos() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM USUARIOS";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			int total = resultados.getInt("TOTAL");
			
			statement.close();
			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
	}
	
	

	public ArrayList<Usuario> buscarTodos() {
		try {
			String sql = "SELECT Usuarios.id_usuario, Usuarios.nombre, Usuarios.dinero, " 
					+ "Usuarios.tiempo, Usuarios.PosX, Usuarios.PosY, Tipos_atraccion.Descripcion_tipo_atraccion "
					+ "FROM Usuarios INNER JOIN Tipos_atraccion "
					+ "ON Usuarios.id_tipo_atraccion = Tipos_atraccion.id_tipo_atraccion";

			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultados.next()) {
				usuarios.add(toUsuario(resultados));
			}

			statement.close();
			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	

	private Usuario toUsuario(ResultSet resultados) throws SQLException {
		// COLUMNAS DE LA BD:  USUARIO  NOMBRE  DINERO  TIEMPO  POSX  POSY  idPREFERENCIA
		Long idUsuario = resultados.getLong(1);
		String nombre = resultados.getString(2);
		int dinero = resultados.getInt(3);
		double tiempo = resultados.getInt(4);
		double posX = resultados.getInt(5);
		double posY = resultados.getInt(6);
		tierraMedia.TipoAtraccion preferencia =  TipoAtraccion.valueOf(resultados.getString(7).toUpperCase());
		Usuario usuario = new Usuario(idUsuario, nombre , dinero, tiempo, new Posicion(posX, posY), preferencia);
		
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();
		itinerarioDAO.itinerarioeHistorialDelUsuario(usuario);
		
		return usuario;
	}
	
	
	
	
}
