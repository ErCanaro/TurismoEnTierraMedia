package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import jdbc.ManejadorDeBD;
import tierraMedia.Posicion;
import tierraMedia.TipoAtraccion;
import tierraMedia.Usuario;

public class UsuarioDAOTest {

	
	@Test
	public void testABMdeUsuarios() {
		
		System.out.println("------------   CUENTA EL TOTAL DE USUARIOS  ----------------");
		UsuarioDAO usuarioDAO = DAOFactory.getUserDAO();
		System.out.println(usuarioDAO.findAll());
		System.out.println(usuarioDAO.countAll());

		
		
		
		String idBusca = "4"; 
		System.out.println("------------ BUSCA EL USUARIO " + idBusca + " que existe Y OTRO QUE NO EXISTE ----------------");
		System.out.println("USUARIO: " + usuarioDAO.findByIdUsuario("4"));
		System.out.println("USUARIO: " + usuarioDAO.findByIdUsuario("78"));


		
		System.out.println("---------- INSERTA UN USURIO ------------------");
		long id = 8; 
		String nombre = "Pepe";
		int dinero = 30;
		double tiempo = 30.0;
		Posicion pos = new Posicion(1,2);
		TipoAtraccion preferencia = TipoAtraccion.AVENTURA;
		Usuario unUsuario = new Usuario(id, nombre, dinero, tiempo, pos, preferencia);
		usuarioDAO.insert(unUsuario);
		System.out.println(unUsuario);

		
		
		System.out.println("-------- MODIFICA AL USUARIO RECIUEN INGRESADO EL DINERO LO PONE EN 1----------------");
		System.out.println(unUsuario.getPresupuesto());
		unUsuario.setPresupuesto(1);
		usuarioDAO.update(unUsuario);
		System.out.println(unUsuario);
		
		
		System.out.println("-------- CUENTA LA NUEVA CANTIDAD DE USUARIOS LUEGO DEL INSERT --------------------");
		System.out.println(usuarioDAO.findAll());
		System.out.println(usuarioDAO.countAll());

		
		
		System.out.println("--------- BORRA EL USUARIO INSERTADO -------------------");
		usuarioDAO.delete(unUsuario);
		System.out.println(usuarioDAO.findAll());
		System.out.println(usuarioDAO.countAll());
	}


}
