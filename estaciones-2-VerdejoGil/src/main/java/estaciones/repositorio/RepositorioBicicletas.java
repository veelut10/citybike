package estaciones.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;

@NoRepositoryBean
public interface RepositorioBicicletas
	extends PagingAndSortingRepository<Bicicleta, String> {
	
	Page<Bicicleta> findByEstacion(Pageable pageable, Estacion estacion);
	
	Page<Bicicleta> findByEstacionAndIsDisponible(Pageable pageable, Estacion estacion, boolean isDisponible);
}
