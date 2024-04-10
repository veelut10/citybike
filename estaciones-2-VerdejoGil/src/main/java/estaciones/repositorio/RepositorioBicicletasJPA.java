package estaciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estaciones.modelo.Bicicleta;

@Repository
public interface RepositorioBicicletasJPA
	extends RepositorioBicicletas,
			JpaRepository<Bicicleta, Integer>{

}
