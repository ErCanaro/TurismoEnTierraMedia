package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Before;
import org.junit.Test;

import jdbc.ProveedorDeConeccion;
import tierraMedia.Posicion;
import tierraMedia.TipoAtraccion;
import tierraMedia.Usuario;


public class TestDAO {


	private Connection conn = null;
	
	
	
	public void inicializarBD() {
			try {
			conn = ProveedorDeConeccion.getConnection();
			PreparedStatement statement = null;
			
			String sql1 = "DROP TABLE IF EXISTS usuarios"; 
			String sql2 = "DROP TABLE IF EXISTS atracciones";
			String sql3 = "DROP TABLE IF EXISTS item_itinerario";
			String sql4 = "CREATE TABLE 'Usuarios' ( 'ID_Usuario' INTEGER NOT NULL UNIQUE, 'Nombre' TEXT NOT NULL, 'Dinero' REAL NOT NULL, 'Tiempo' REAL NOT NULL, 'PosX' REAL NOT NULL, 'PosY' REAL NOT NULL, 'id_tipo_atraccion' INTEGER NOT NULL, PRIMARY KEY('ID_Usuario' AUTOINCREMENT), FOREIGN KEY('id_tipo_atraccion') REFERENCES 'Tipos_atraccion' )";
			String sql5 = "CREATE TABLE 'Atracciones' ( 'Id_Atracciones' INTEGER NOT NULL UNIQUE, 'Nombre' TEXT, 'Cupo' INTEGER, 'Precio' REAL, 'Duracion' REAL, 'PosX' REAL, 'PosY' REAL, 'id_tipo_atraccion' INTEGER, PRIMARY KEY('Id_Atracciones' AUTOINCREMENT), FOREIGN KEY('id_tipo_atraccion') REFERENCES 'Tipos_atraccion' )";
			String sql6 = "CREATE TABLE 'Item_Itinerario' ( 'id_itinerario' INTEGER NOT NULL UNIQUE, 'id_usuario' INTEGER, 'id_producto' INTEGER, 'tipo_producto' TEXT, FOREIGN KEY('id_usuario') REFERENCES 'Usuarios'('ID_Usuario'), PRIMARY KEY('id_itinerario' AUTOINCREMENT) )";
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test
	public void testABMdeUsuarios() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		inicializarBD();
		
		//---------- INSERTA USURIOS Y CONTROLAR LA CANTIDAD DE USUARIOS INGRESADOS ------------------
		long id = 0; 
		//Long idUsuario, String nombre, int dinero, double tiempo, Posicion posicion, TipoAtraccion preferencia
		Usuario usuario1 = new Usuario((long)1, "Lucas", 50, 20, new Posicion(1,2), TipoAtraccion.AVENTURA);
		Usuario usuario2 = new Usuario((long)2, "Diego", 30, 21, new Posicion(1,2), TipoAtraccion.PAISAJES);
		Usuario usuario3 = new Usuario((long)3, "Juan" , 23, 40, new Posicion(1,2), TipoAtraccion.DEGUSTACION);
		usuarioDAO.insertar(usuario1);
		usuarioDAO.insertar(usuario2);
		usuarioDAO.insertar(usuario3);

		int aux = usuarioDAO.buscarTodos().size();
		assertEquals( 3, aux);
		
		//---------- RECUPERAR UN USUARIO POR ID ------------------
		long idUsuario = 3; 
		Usuario usuarioRecuperado = usuarioDAO.buscarPorIdUsuario(idUsuario);
		assertEquals(usuario3, usuarioRecuperado);
		
		
		//-------- MODIFICA AL USUARIO RECIUEN INGRESADO EL DINERO LO PONE EN 1----------------
		usuarioRecuperado.setPresupuesto(100);
		usuarioDAO.actualizar(usuarioRecuperado);
		
		Usuario usuarioModificado = usuarioDAO.buscarPorIdUsuario(usuarioRecuperado.getIdUsuario());
		
		assertEquals(usuarioRecuperado, usuarioModificado);
		assertEquals(100, usuarioModificado.getPresupuesto());
		
		
		//--------- BORRA EL USUARIO INSERTADO ------------------
		usuarioDAO.borrar(usuarioModificado);
		assertEquals(2, usuarioDAO.buscarTodos().size());

	}

	
	

}
