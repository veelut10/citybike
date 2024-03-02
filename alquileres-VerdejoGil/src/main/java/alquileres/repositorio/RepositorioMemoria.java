package alquileres.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alquileres.modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioMemoria implements RepositorioUsuario{
	
private Map<String, Usuario> usuarios = new HashMap<>();
	
	@Override
	public String add(Usuario entity) throws RepositorioException {
		String id = entity.getId();
		this.usuarios.put(id, entity);		
		
		return id;
	}

	@Override
	public void update(Usuario entity) throws RepositorioException, EntidadNoEncontrada {
		if (! this.usuarios.containsKey(entity.getId()))
			throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
		
		this.usuarios.put(entity.getId(), entity);
		
	}

	@Override
	public void delete(Usuario entity) throws RepositorioException, EntidadNoEncontrada {
		if (! this.usuarios.containsKey(entity.getId()))
			throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
		
		this.usuarios.remove(entity.getId());
		
	}

	@Override
	public Usuario getById(String id) throws RepositorioException, EntidadNoEncontrada {
		if (! this.usuarios.containsKey(id))
			throw new EntidadNoEncontrada(id + " no existe en el repositorio");
		
		return usuarios.get(id);
	}

	@Override
	public List<Usuario> getAll() throws RepositorioException {
		return new ArrayList<Usuario>(usuarios.values());
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		return new ArrayList<String> (usuarios.keySet());
	}
}
