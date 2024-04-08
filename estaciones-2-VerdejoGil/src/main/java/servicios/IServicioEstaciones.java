package servicios;

import java.util.List;

import modelo.Bicicleta;
import modelo.Estacion;
import modelo.SitioTuristico;
import repositorios.RepositorioException;
import repositorios.EntidadNoEncontrada;

public interface IServicioEstaciones {

	String crearEstacion(String nombre, int numPuestos, String direccion, double longitud, double latitud) throws RepositorioException;

	List<SitioTuristico> obtenerSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada;
	
	void establecerSitiosTuristicos(String id, List<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada;
	
	Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	String altaBicicleta(String modeloBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	void estacionarBicicleta(String idBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
    
	void estacionarBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada;
    
	void retirarBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada;
    
	void bajaBicicleta(String idBicicleta, String motivoBaja) throws RepositorioException, EntidadNoEncontrada;
    
	List<Bicicleta> bicicletasEstacionadasCerca(double longitud, double latitud) throws RepositorioException;
    
	List<Estacion> estacionesConMasSitiosTuristicosCerca() throws RepositorioException;

}
