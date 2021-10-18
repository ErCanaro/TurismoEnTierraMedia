package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ProveedorDeConeccion;
import tierraMedia.Atraccion;
import tierraMedia.Itinerario;
import tierraMedia.Usuario;

public class ItinerarioDAOImpl implements ItinerarioDAO {


	@Override
	public ArrayList<Itinerario> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int contarTodos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertar(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int actualizar(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int borrar(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public Itinerario buscarPorIdItinerario(Long IdItinerario) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int agregarItemItinerario(Atraccion atraccion) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long obtenerIDItinerarioPorIdUsuario(Long idUsuario) {
		long id = 0;
		try {
			String sql = "SELECT id_itinerario  FROM itinerarios WHERE id_usuario = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, idUsuario);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				id = resultados.getLong(1);
			}
			return id;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
	}
	
	private Usuario toItinerario(ResultSet resultados) throws SQLException {

		return null;
	}

	@Override
	public int insertarItemItinerario(long idItinerario, long idAtraccion) {
		try {
			String sql = "INSERT INTO AtraccionesDelItinerario (ID_itinerario, ID_Atraccion) VALUES (?, ?)";
			Connection conn = ProveedorDeConeccion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, idItinerario);
			statement.setLong(2, idAtraccion);

			int rows = statement.executeUpdate();
			return rows;
		
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
