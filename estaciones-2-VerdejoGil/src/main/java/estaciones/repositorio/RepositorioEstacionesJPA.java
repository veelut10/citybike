package estaciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estaciones.modelo.Estacion;

@Repository
public interface RepositorioEstacionesJPA 
	extends RepositorioEstaciones,
			JpaRepository<Estacion, String>{

}
