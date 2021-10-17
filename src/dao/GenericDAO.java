package dao;

import java.util.ArrayList;
import java.util.List;

import tierraMedia.Atraccion;
import tierraMedia.Usuario;

public interface GenericDAO<T> {

	public ArrayList<T> buscarTodos();
	public int contarTodos();
	public int insertar(T t);
	public int actualizar(T t);
	public int borrar(T t);
}