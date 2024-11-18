package estaciones.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import repositorio.RepositorioException;
import repositorio.EntidadNoEncontrada;

public interface IServicioEstaciones {

	String altaEstacion(String nombre, int numPuestos, String direccion, double longitud, double latitud) throws RepositorioException;
	
	String altaBicicleta(String modeloBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	void bajaBicicleta(String idBicicleta, String motivoBaja) throws RepositorioException, EntidadNoEncontrada;
	
	Page<Bicicleta>  getListadoBicicletasEnEstacion(Pageable pageable, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	Page<Estacion> getListadoEstaciones(Pageable pageable) throws RepositorioException, EntidadNoEncontrada;
	
	Estacion getEstacion(String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	Page<Bicicleta> getListadoBicicletasDisponiblesEnEstacion(Pageable pageable, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	void estacionarBicicleta(String idBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	Bicicleta getBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada;
}
