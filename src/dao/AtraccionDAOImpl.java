package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ProveedorDeConeccion;
import tierraMedia.Atraccion;
import tierraMedia.Posicion;
import tierraMedia.TipoAtraccion;
import tierraMedia.Usuario;

public class AtraccionDAOImpl implements AtraccionDAO {

	@Override
	public ArrayList<Atraccion> buscarTodos() {
		try {
			String sql = "SELECT Atracciones.Id_Atracciones, Atracciones.Nombre, Atracciones.Cupo,"
					+ " Atracciones.Precio, Atracciones.Duracion, Atracciones.PosX, Atracciones.PosY,"
					+ " Tipos_atraccion.Descripcion_tipo_atraccion "
					+ " FROM Atracciones INNER JOIN Tipos_atraccion "  
					+ " ON Atracciones.id_tipo_atraccion = Tipos_atraccion.id_tipo_atraccion";

			Connection conn = DriverManager.getConnection("jdbc:Sqlite:TierraMediaBD.db");
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
			while (resultados.next()) {
				atracciones.add(toAtraccion(resultados));
			}
		
			return atracciones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}
	
	
	private Atraccion toAtraccion(ResultSet resultados) throws SQLException {
		
		// COLUMNAS DE LA BD:  ATRACCIONES  ID  NOMBRE  CUPO  PRECIO  DURACION POSX POSY TIPO 
		Long idAtraccion = resultados.getLong(1);
		String nombre = resultados.getString(2);
		int cupo = resultados.getInt(3);
		double costo = resultados.getDouble(4);
		double duracion = resultados.getInt(5);
		double posX = resultados.getInt(6);
		double posY = resultados.getInt(7);
		tierraMedia.TipoAtraccion tipo =  TipoAtraccion.valueOf(resultados.getString(8).toUpperCase());
		
		return new Atraccion(idAtraccion, nombre, tipo,  costo,  duracion, cupo, new Posicion(posX, posY));
	
		
	}

	@Override
	public Atraccion buscarPorIdAtraccion(Long IdAtraccion) {
		try {
			String sql = "SELECT Atracciones.Id_Atracciones, Atracciones.Nombre, Atracciones.Cupo,"
					+ " Atracciones.Precio, Atracciones.Duracion, Atracciones.PosX, Atracciones.PosY,"
					+ " Tipos_atraccion.Descripcion_tipo_atraccion "
					+ " FROM Atracciones INNER JOIN Tipos_atraccion "  
					+ " ON Atracciones.id_tipo_atraccion = Tipos_atraccion.id_tipo_atraccion "
					+ " WHERE Atracciones.Id_Atracciones = ?";
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, IdAtraccion);
			ResultSet resultados = statement.executeQuery();

			Atraccion atraccion = null;

			if (resultados.next()) {
				atraccion = toAtraccion(resultados);
			}

			return atraccion;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	
	@Override
	public int actualizar(Atraccion atraccion) {
		 // Columnas: Id_Atracciones, Nombre, Cupo, Precio, Duracion, PosX, PosY, id_tipo_atraccion
		try {
			String sql = "UPDATE atracciones SET Nombre = ?, cupo = ?, precio = ?, duracion = ?  WHERE id_atracciones = ?";
			Connection conn = ProveedorDeConeccion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, atraccion.getNombre());
			statement.setInt(2, atraccion.getCupo());
			statement.setDouble(3, atraccion.getCosto());
			statement.setDouble(4, atraccion.getDuracion());
			//statement.setDouble(5, atraccion.getPosX()); 
			//statement.setDouble(6, atraccion.getPosY()); //pos y
			//statement.setString(6, usuario.getPreferencia().toString()); 
			statement.setLong(5, atraccion.getIdAtraccion());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int actualizarCupo(Atraccion atraccion) {
		 // Columnas: Id_Atracciones, Nombre, Cupo, Precio, Duracion, PosX, PosY, id_tipo_atraccion
		try {
			String sql = "UPDATE atracciones SET cupo = ? WHERE id_atracciones = ?";
			Connection conn = ProveedorDeConeccion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, atraccion.getCupo());
			statement.setLong(2, atraccion.getIdAtraccion());
			
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	
	@Override
	public int contarTodos() {
		return 0;
	}

	@Override
	public int insertar(Atraccion t) {
		return 0;
	}


	@Override
	public int borrar(Atraccion t) {
		return 0;
	}

	


}
