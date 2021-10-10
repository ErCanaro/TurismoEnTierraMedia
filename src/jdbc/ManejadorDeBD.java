package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class ManejadorDeBD {
	Connection coneccion = null;
	
	public ManejadorDeBD() {
		
	}
	
	
	/* Realiza una coneccion de base de datos referida en el parametro "url"
	 * parametro: url
	 * y devuelve un objeto Connection con la coneccion solicitada, o null si algo salio mal
	 */
	public Connection conectarBD(String url) {
		try {
		
			this.coneccion = null;
			this.coneccion = DriverManager.getConnection(url);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectar la BD:   " + e.getMessage().toString() );
		}
		return this.coneccion;

	}
	
	
	
	/* Ejecuta una consulta a una base de datos pasada por parametro
	 * parametro: unaConeccion (un objeto coneccion)
	 * parametro: unaSentenciaSQL
	 * retorna: un objeto ResultSer con el resultado de la consulta, o null si algo salio mal
	 */	
	public ResultSet ejecutarConsultaSQL (Connection unaConeccion, String unaSentenciaSQL) {
		ResultSet resultado = null;
		try {
			
			resultado = unaConeccion.createStatement().executeQuery(unaSentenciaSQL);
			
			//Statement statement = unaConeccion.createStatement();
			//resultado = statement.executeQuery(unaSentenciaSQL);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,  e.getMessage().toString() );
			return resultado;

		}
		return resultado;
	}
	
	
	
	/* Realiza una Desconeccion de base de datos referida en el parametro 
	 * parametro: objeto tipo Connection
	 * desuleve TRUE si se cerro correctamente , o FALSE si no se pudo cerrar por algun motivo
	 */
	public boolean desconectarBD(Connection unaConeccion) {
			try {
				this.coneccion.close();
				return true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se pudo desconectar la base de datos: " + e.getMessage().toString());
				return false;
			}
	}
	
}
