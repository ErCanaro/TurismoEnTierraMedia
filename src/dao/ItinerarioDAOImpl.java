package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ProveedorDeConeccion;
import tierraMedia.Atraccion;
import tierraMedia.Itinerario;
import tierraMedia.Producto;
import tierraMedia.Usuario;

public class ItinerarioDAOImpl implements ItinerarioDAO {

	
	public long obtenerIdItinerarioPorIdUsuario(Long idUsuario) {
		long id = 0;
		try {
			String sql = "SELECT id_itinerario  FROM itinerarios WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, idUsuario);
			ResultSet resultados = statement.executeQuery();

			if (resultados.next()) {
				id = resultados.getLong(1);
			}
			return id;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	

	
	public int insertarItemItinerario(long  IdUsuario, long IdProducto, String tipo) {
		try {
			String sql = "INSERT INTO Item_Itinerario (id_usuario, id_producto, tipo_producto ) VALUES (?, ?, ?)";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, IdUsuario);
			statement.setLong(2, IdProducto);
			statement.setString(3, tipo);

			int rows = statement.executeUpdate();
			return rows;
		
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	

	@Override
	public ArrayList<Producto> itinerarioDelUsuarioPorId(Long idUsuario) {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		ArrayList<Producto> productos = new ArrayList<Producto>();
		try {
			String sql = "SELECT * FROM Item_Itinerario WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, idUsuario);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				if (resultados.getString(3) == "atraccion") {
					productos.add(atraccionDAO.buscarPorIdAtraccion(resultados.getLong(2)));
				}else {
					productos.add(promocionDAO.buscarPorIdPromocion(resultados.getLong(2)));
				}
			}
			return productos;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	
	
	public Usuario itinerarioeHistorialDelUsuario(Usuario usuario) {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		try {
			String sql = "SELECT * FROM Item_Itinerario WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1,usuario.getIdUsuario());
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				if (resultados.getString(3) == "atraccion") {
					usuario.getItinerario().add(atraccionDAO.buscarPorIdAtraccion(resultados.getLong(2)));
				}else {
					usuario.getItinerario().add(promocionDAO.buscarPorIdPromocion(resultados.getLong(2)));
				}
				usuario.getHistorialDeAtracciones().addAll(usuario.atraccionesDelItinerario());
			}
			return usuario;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}


	@Override
	public ArrayList<Producto> buscarTodos() {
		return null;
	}

	@Override
	public int contarTodos() {
		return 0;
	}

	@Override
	public int insertar(Producto t) {
		return 0;
	}

	@Override
	public int actualizar(Producto t) {
		return 0;
	}

	@Override
	public int borrar(Producto t) {
		return 0;
	}




}
