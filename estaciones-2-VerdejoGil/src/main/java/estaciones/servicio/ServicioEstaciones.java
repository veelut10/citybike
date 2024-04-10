package estaciones.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.repositorio.RepositorioBicicletas;
import estaciones.repositorio.RepositorioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Service
public class ServicioEstaciones implements IServicioEstaciones{

	private RepositorioEstaciones repositorioEstaciones;
	private RepositorioBicicletas repositorioBicicletas;
	
	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repositorioEstaciones, RepositorioBicicletas repositorioBicicletas) {
		this.repositorioEstaciones = repositorioEstaciones;
		this.repositorioBicicletas = repositorioBicicletas;
	}
	
	//Funciones del gestor
	
	@Override
	public Integer altaEstacion (String nombre, int numPuestos, String direccion, double longitud, double latitud) throws RepositorioException{
		// Control de integridad de los datos
		
			if (nombre == null || nombre.isEmpty())
				throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
			
			if (numPuestos <= 0)
				throw new IllegalArgumentException("numero puestos: no puede ser 0 o menos ni nulo");
			
			if (direccion == null || direccion.isEmpty())
				throw new IllegalArgumentException("direccion: no debe ser nula ni vacia");
			
			if (longitud < -180 || longitud > 180)
				throw new IllegalArgumentException("longitud: no debe ser menor que -180 ni mayor que 180");
			
			if (latitud < -90 || latitud > 90)
				throw new IllegalArgumentException("latitud: no debe ser menor que -90 ni mayor que 90");
			
		Estacion estacion = new Estacion(nombre, numPuestos, direccion, longitud, latitud, LocalDate.now());
		Integer idEstacion = repositorioEstaciones.save(estacion).getId();
		return idEstacion;
	}
	
	// Métodos a implementar
    @Override
    public Integer altaBicicleta(String modeloBicicleta, Integer idEstacion) throws RepositorioException, EntidadNoEncontrada {
    	if (modeloBicicleta == null || modeloBicicleta.isEmpty())
			throw new IllegalArgumentException("modeloBicicleta: no debe ser nulo ni vacio");
		
    	if (idEstacion == null)
			throw new IllegalArgumentException("idEstacion: no debe ser nulo");
		
    	Optional e = repositorioEstaciones.findById(idEstacion);
    	
    	if(e.isPresent()) {
    		Estacion estacion = (Estacion) e.get();
    		
    		if(estacion.hasHueco()) {
    			Bicicleta bicicleta = new Bicicleta(modeloBicicleta, LocalDate.now(), estacion);
    			estacion.addBici(bicicleta);
    			repositorioEstaciones.save(estacion);
    			Integer idBici = repositorioBicicletas.save(bicicleta).getId();
    			return idBici;
    		}
    		else
    			return null;
    	}
    	else
    		throw new EntidadNoEncontrada("Estacion no encontrada con id: " + idEstacion);
    }
    
    @Override
    public void bajaBicicleta(Integer idBicicleta, String motivoBaja) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo");
        }

        if (motivoBaja == null || motivoBaja.isEmpty()) {
            throw new IllegalArgumentException("motivoBaja: no debe ser nulo ni vacío");
        }
        
        Optional b = repositorioBicicletas.findById(idBicicleta);
        
        if(b.isPresent()) {
        	Bicicleta bicicleta = (Bicicleta) b.get();
        	
        	// Si la bicicleta estaba en una estación, también actualizar la estación
            if (bicicleta.getEstacion() != null) {
                Estacion estacion = repositorioEstaciones.findById(bicicleta.getEstacion().getId()).get();
                estacion.removeBici(bicicleta);
                repositorioEstaciones.save(estacion);
            }
        	
        	// Actualizar la información de la bicicleta
            bicicleta.setMotivoBaja(motivoBaja);
            bicicleta.setFechaBaja(LocalDate.now());
            bicicleta.setEstacion(null);
            bicicleta.setDisponible(false);
            
            // Actualizar bicicleta en el repositorio
            repositorioBicicletas.save(bicicleta);
        }
        else
        	throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);
    }
    
    @Override
    public List<Bicicleta> getListadoBicicletasEnEstacion(Integer idEstacion) throws RepositorioException, EntidadNoEncontrada{
    	if (idEstacion == null)
			throw new IllegalArgumentException("idEstacion: no debe ser nulo");
    	
    	Optional e = repositorioEstaciones.findById(idEstacion);
    	
    	if(e.isPresent()) {
    		Estacion estacion = (Estacion) e.get();
    		return estacion.getBicicletas();	
    	}
    	else
    		throw new EntidadNoEncontrada("Estacion no encontrada con id: " + idEstacion);
    }
    
    
    //---------------------------------------------------------------------------------------------------------------------
    
    
    //Funciones usuarios

	@Override
	public Page<Estacion> getListadoEstaciones(Pageable pageable) throws RepositorioException, EntidadNoEncontrada{
		
		
		return repositorioEstaciones.findAll(pageable).map((estacion) -> {
			return estacion;
		});
	}
	
	
	@Override
	public Estacion getEstacion(Integer idEstacion) throws RepositorioException, EntidadNoEncontrada {
		return repositorioEstaciones.findById(idEstacion).get();
	}

	@Override
	public List<Bicicleta> getListadoBicicletasDisponiblesEnEstacion(Integer idEstacion)
			throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}
    
    @Override
    public void estacionarBicicleta(Integer idBicicleta, Integer idEstacion) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo");
        }

        if (idEstacion == null) {
            throw new IllegalArgumentException("idEstacion: no debe ser nulo");
        }
        
        Optional e = repositorioEstaciones.findById(idEstacion);
        Optional b = repositorioBicicletas.findById(idBicicleta);
        
        if(e.isPresent() && b.isPresent()) {
    		Estacion estacion = (Estacion) e.get();
    		Bicicleta bicicleta = (Bicicleta) b.get();
    		
    		if(estacion.hasHueco()) {
    			estacion.addBici(bicicleta);
    			bicicleta.setEstacion(estacion);
    			repositorioEstaciones.save(estacion);
    	        repositorioBicicletas.save(bicicleta);
    		}
    	}
    	else
    		throw new EntidadNoEncontrada("Estacion o Bicicleta no encontrada con id: " + idEstacion + " o " + idBicicleta);
    }
}