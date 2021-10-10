package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProveedorDeConeccion {

	private static String url = "jdbc:Sqlite:TierraMediaBD.db";
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {

		if (connection == null) {
			connection = DriverManager.getConnection(url);			
		}

		return connection;
	}

}
