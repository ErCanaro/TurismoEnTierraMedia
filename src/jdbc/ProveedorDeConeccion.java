package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.MissingDataException;

public class ProveedorDeConeccion {

	private static String url = "jdbc:Sqlite:TierraMediaBD.db";
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {

		if (connection == null) {
			connection = DriverManager.getConnection(url);			
		}

		return connection;
	}

	public static void closeConnection() {
		try {
		connection.close();
		}catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	
	public static void insertarDatosDePruebaEnLaBD() {
		
		try {
		
		String sql1 = "DROP TABLE IF EXISTS 'AtraccionesDeLaPromocion'";
		String sql2 = "CREATE TABLE IF NOT EXISTS 'AtraccionesDeLaPromocion' ('ID_Promocion' INTEGER NOT NULL,  'ID_Atraccon' INTEGER NOT NULL, PRIMARY KEY('ID_Promocion','ID_Atraccon'))";

		String sql3 = "DROP TABLE IF EXISTS 'Usuarios'";
		String sql4 = "CREATE TABLE IF NOT EXISTS 'Usuarios' ('ID_Usuario' INTEGER NOT NULL UNIQUE, 'Nombre' TEXT NOT NULL, 'Dinero' REAL NOT NULL, 'Tiempo' REAL NOT NULL, 'PosX' REAL NOT NULL,'PosY' REAL NOT NULL,  'id_tipo_atraccion' INTEGER NOT NULL, PRIMARY KEY('ID_Usuario' AUTOINCREMENT), FOREIGN KEY('id_tipo_atraccion') REFERENCES 'Tipos_atraccion')";

		String sql5 = "DROP TABLE IF EXISTS 'Tipos_atraccion'";
		String sql6 = "CREATE TABLE IF NOT EXISTS 'Tipos_atraccion' ( 'id_tipo_atraccion' INTEGER NOT NULL UNIQUE, 'descripcion_tipo_atraccion' TEXT NOT NULL, PRIMARY KEY('id_tipo_atraccion' AUTOINCREMENT))";

		String sql7 = "DROP TABLE IF EXISTS 'Tipos_promociones'";
		String sql8 = "CREATE TABLE IF NOT EXISTS 'Tipos_promociones' ('id_tipo_promocion' INTEGER NOT NULL UNIQUE,  'descripciontipo_tipo_promocion' INTEGER, PRIMARY KEY('id_tipo_promocion' AUTOINCREMENT))";

		String sql9 = "DROP TABLE IF EXISTS 'Atracciones'";
		String sql10 = "CREATE TABLE IF NOT EXISTS 'Atracciones' ('Id_Atracciones' INTEGER NOT NULL UNIQUE, 'Nombre' TEXT, 'Cupo' INTEGER, 'Precio' REAL, 'Duracion' REAL, 'PosX' REAL, 'PosY' REAL, 'id_tipo_atraccion' INTEGER, PRIMARY KEY('Id_Atracciones' AUTOINCREMENT), FOREIGN KEY('id_tipo_atraccion') REFERENCES 'Tipos_atraccion')";

		String sql11 = "DROP TABLE IF EXISTS 'Item_Itinerario'";
		String sql12 = "CREATE TABLE IF NOT EXISTS 'Item_Itinerario' ( 'id_itinerario' INTEGER NOT NULL UNIQUE, 'id_usuario' INTEGER, 'id_producto' INTEGER, 'tipo_producto' TEXT, PRIMARY KEY('id_itinerario' AUTOINCREMENT), FOREIGN KEY('id_usuario') REFERENCES 'Usuarios'('ID_Usuario'))";

		String sql13 = "DROP TABLE IF EXISTS 'Promociones'";
		String sql14 = "CREATE TABLE IF NOT EXISTS 'Promociones' ('ID_Promocion' INTEGER NOT NULL UNIQUE, 'Nombre' TEXT NOT NULL, 'Precio' REAL NOT NULL, 'Descuento' REAL NOT NULL, 'tipo_atraccion' TEXT, 'id_tipo_promocion' INTEGER NOT NULL, PRIMARY KEY('ID_Promocion' AUTOINCREMENT), FOREIGN KEY('id_tipo_promocion') REFERENCES 'Tipos_promociones', FOREIGN KEY('tipo_atraccion') REFERENCES 'Tipos_atraccion'('descripcion_tipo_atraccion'))";

		String sql15 = "INSERT INTO 'AtraccionesDeLaPromocion' ('ID_Promocion','ID_Atraccon') VALUES (1,1), (1,4), (2,3), (2,6), (3,2), (3,4), (3,6)";

		String sql16 = "INSERT INTO 'Usuarios' ('ID_Usuario','Nombre','Dinero','Tiempo','PosX','PosY','id_tipo_atraccion') VALUES (1,'EowyN',90.0,90.0,1.0,2.0,1),  (2,'Gandalf',100.0,100.0,1.0,2.0,2), (3,'Sam',80.0,80.0,1.0,3.0,3), (4,'Galadriel',10.0,8.0,2.0,4.0,2), (5,'Jose',40.0,15.0,1.0,3.0,1), (6,'Maria',25.0,10.0,5.0,1.0,2), (7,'Marta',20.0,34.0,1.0,1.0,1)";

		String sql17 = "INSERT INTO 'Tipos_atraccion' ('id_tipo_atraccion','descripcion_tipo_atraccion') VALUES (1,'AVENTURA'), (2,'PAISAJES'), (3,'DEGUSTACION')";

		String sql18 = "INSERT INTO 'Tipos_promociones' ('id_tipo_promocion','descripciontipo_tipo_promocion') VALUES (1,'Promocion AxB'), (2,'Promocion porcentual'), (3,'Promocion absoluta')";

		String sql19 = "INSERT INTO 'Atracciones' ('Id_Atracciones', 'Nombre', 'Cupo', 'Precio', 'Duracion', 'PosX', 'PosY', 'id_tipo_atraccion') VALUES (1,'Moria',46,10.0,2.0,1.0,2.0,1), (2,'Minas Tirith',45,5.0,2.0,1.0,2.0,2), (3,'La comarca',141,3.0,6.5,1.0,2.0,3), (4,'Mordor',96,25.0,3.0,1.0,2.0,1), (5,'Abismo de Helm',39,5.0,2.0,1.0,2.0,2), (6,'Lothlorien',27,35.0,1.0,1.0,2.0,3), (7,'Eredor',27,12.0,3.0,1.0,2.0,2), (8,'Bosque negro',60,3.0,3.0,1.0,2.0,1)";

//		String sql20 = "INSERT INTO 'Item_Itinerario' ('id_itinerario','id_usuario','id_producto','tipo_producto') VALUES (15,1,1,'1'), (16,1,2,'1')";

		String sql21 = "INSERT INTO 'Promociones' ('ID_Promocion', 'Nombre', 'Precio', 'Descuento', 'tipo_atraccion', 'id_tipo_promocion') VALUES (1,'Pack aventura',22.0,20.0,'AVENTURA',2), (2,'Pack paisajes',36.0,0.0,'PAISAJES',3), (3,'Pack degustación',10.0,0.0,'DEGUSTACION',1)";
		
		
			Connection conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = null;
			statement = conn.prepareStatement(sql1);
			statement.execute();
			statement = conn.prepareStatement(sql2);
			statement.execute();
			statement = conn.prepareStatement(sql3);
			statement.execute();
			statement = conn.prepareStatement(sql4);
			statement.execute();
			statement = conn.prepareStatement(sql5);
			statement.execute();
			statement = conn.prepareStatement(sql6);
			statement.execute();
			statement = conn.prepareStatement(sql7);
			statement.execute();
			statement = conn.prepareStatement(sql8);
			statement.execute();
			statement = conn.prepareStatement(sql9);
			statement.execute();
			statement = conn.prepareStatement(sql10);
			statement.execute();
			statement = conn.prepareStatement(sql11);
			statement.execute();
			statement = conn.prepareStatement(sql12);
			statement.execute();
			statement = conn.prepareStatement(sql13);
			statement.execute();
			statement = conn.prepareStatement(sql14);
			statement.execute();
			statement = conn.prepareStatement(sql15);
			statement.execute();
			statement = conn.prepareStatement(sql16);
			statement.execute();
			statement = conn.prepareStatement(sql17);
			statement.execute();
			statement = conn.prepareStatement(sql18);
			statement.execute();
			statement = conn.prepareStatement(sql19);
			statement.execute();
//			statement = conn.prepareStatement(sql20);
//			statement.execute();
			statement = conn.prepareStatement(sql21);
			statement.execute();
			statement.close();
			
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
//	public static void main(String[] args) {
////		ProveedorDeConeccion pdc = new ProveedorDeConeccion();
//		insertarDatosDePruebaEnLaBD();
//		System.out.println("DB cargada");
//	}
}
