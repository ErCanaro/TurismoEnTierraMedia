package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tierraMedia.Atraccion;
import tierraMedia.Producto;
import tierraMedia.PromoPorcentual;
import tierraMedia.PromocionAbsoluta;
import tierraMedia.PromocionAxB;
import tierraMedia.TipoAtraccion;

public class PromocionDAOImpl implements PromocionDAO {

	@Override
	public ArrayList<Producto> buscarTodos() {
		try {
			String sql = "select id_promocion, nombre, precio, descuento, tipo_atraccion, id_tipo_promocion from Promociones";
			Connection conn = DriverManager.getConnection("jdbc:Sqlite:TierraMediaBD.db");
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();
			

			ArrayList<Producto> producto = new ArrayList<Producto>();
			while (resultados.next()) {
				
				producto.add(toProducto(resultados));
			}
		
			return producto;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	
	private Producto toProducto(ResultSet resultados) throws SQLException {
		// COLUMNAS DEL RESULTSET:  ID_promoCIONES  NOMBRE  PRECIO  DESCUENTO   TIPO_ATRACCION   ID_TIPO_PROMOCION
		Long   idPromocion         = resultados.getLong(1);
		String nombre              = resultados.getString(2);
		int    costoAbsoluto       = resultados.getInt(3);
		int    descuentoPorcentual = resultados.getInt(4);
		TipoAtraccion tipoAtraccion =  TipoAtraccion.valueOf(resultados.getString(5));
		int    tipoPromo           = resultados.getInt(6);
	
		ArrayList<Atraccion> atraccionesIncluidas = atraccionesDeLaPromocion(resultados);

		Producto producto = null;
		if (tipoPromo == 1) {
			producto = new PromocionAxB(idPromocion, nombre, tipoAtraccion, atraccionesIncluidas);
		} else if (tipoPromo == 2) {
			producto = new PromoPorcentual(idPromocion, nombre, tipoAtraccion, atraccionesIncluidas, descuentoPorcentual);
		} else if (tipoPromo == 3) {
			producto = new PromocionAbsoluta(idPromocion, nombre, tipoAtraccion, atraccionesIncluidas, costoAbsoluto);
		}
		
		return producto;
	}
	
	
	private ArrayList<Atraccion> atraccionesDeLaPromocion(ResultSet resultados) throws SQLException {
		try {
			String sql = "select AtraccionesDeLaPromocion.ID_Atraccon "
					+ "from AtraccionesDeLaPromocion "
					+ "where AtraccionesDeLaPromocion.ID_Promocion = ?";
			Connection conn = DriverManager.getConnection("jdbc:Sqlite:TierraMediaBD.db");
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, resultados.getLong(1));
			ResultSet result = statement.executeQuery();

			AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
			
			ArrayList<Atraccion> producto = new ArrayList<Atraccion>();
			while (result.next()) {
				producto.add(atraccionDAO.buscarPorIdAtraccion(result.getLong(1)));
			}
		
			return producto;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
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


	@Override
	public Producto buscarPorIdAtraccion(Long id) {
		return null;
	}


	@Override
	public int contarTodos() {
		return 0;
	}

	
	
}
