package alquileres.repositorio;

import java.util.List;

import alquileres.modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public interface RepositorioUsuario extends Repositorio<Usuario, String>{
	
	@Override
	public String add(Usuario entity) throws RepositorioException;

	@Override
	public void update(Usuario entity) throws RepositorioException, EntidadNoEncontrada;

	@Override
	public void delete(Usuario entity) throws RepositorioException, EntidadNoEncontrada;

	@Override
	public Usuario getById(String id) throws RepositorioException, EntidadNoEncontrada;

	@Override
	public List<Usuario> getAll() throws RepositorioException;

	@Override
	public List<String> getIds() throws RepositorioException;

}
