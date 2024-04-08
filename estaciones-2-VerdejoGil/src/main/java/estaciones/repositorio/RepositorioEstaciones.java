package estaciones.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import modelo.Estacion;

@NoRepositoryBean
public interface RepositorioEstaciones
	extends CrudRepository<Estacion, String> {
}

