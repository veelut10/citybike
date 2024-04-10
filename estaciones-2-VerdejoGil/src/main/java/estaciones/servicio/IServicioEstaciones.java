package estaciones.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import repositorio.RepositorioException;
import repositorio.EntidadNoEncontrada;

public interface IServicioEstaciones {

	Integer altaEstacion(String nombre, int numPuestos, String direccion, double longitud, double latitud) throws RepositorioException;
	
	Integer altaBicicleta(String modeloBicicleta, Integer idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	void bajaBicicleta(Integer idBicicleta, String motivoBaja) throws RepositorioException, EntidadNoEncontrada;
	
	List<Bicicleta> getListadoBicicletasEnEstacion(Integer idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	Page<Estacion> getListadoEstaciones(Pageable pageable) throws RepositorioException, EntidadNoEncontrada;
	
	Estacion getEstacion(Integer idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	List<Bicicleta> getListadoBicicletasDisponiblesEnEstacion(Integer idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	void estacionarBicicleta(Integer idBicicleta, Integer idEstacion) throws RepositorioException, EntidadNoEncontrada;
}
