package estaciones.servicio;

import java.time.LocalDate;
import java.util.Optional;

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
	public String altaEstacion (String nombre, int numPuestos, String direccion, double longitud, double latitud) throws RepositorioException{
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
			
		Estacion estacion = new Estacion(nombre, numPuestos, direccion, longitud, latitud);
		String idEstacion = repositorioEstaciones.save(estacion).getId();
		return idEstacion;
	}
	
    @Override
    public String altaBicicleta(String modeloBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
    	if (modeloBicicleta == null || modeloBicicleta.isEmpty())
			throw new IllegalArgumentException("modeloBicicleta: no debe ser nulo ni vacio");
		
    	if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");
		
    	Optional<Estacion> e = repositorioEstaciones.findById(idEstacion);
    	
    	if(e.isPresent()) {
    		Estacion estacion = (Estacion) e.get();
    		
    		if(estacion.hasHueco()) {
    			Bicicleta bicicleta = new Bicicleta(modeloBicicleta, LocalDate.now(), estacion);
    			String idBici = repositorioBicicletas.save(bicicleta).getId();
    			bicicleta = repositorioBicicletas.findById(idBici).get();
    			estacion.addBici(bicicleta);
    			repositorioEstaciones.save(estacion);
    			return idBici;
    		}
    		else
    			return null;
    	}
    	else
    		throw new EntidadNoEncontrada("Estacion no encontrada con id: " + idEstacion);
    }
    
    @Override
    public void bajaBicicleta(String idBicicleta, String motivoBaja) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null || idBicicleta.isEmpty()) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
        }

        if (motivoBaja == null || motivoBaja.isEmpty()) {
            throw new IllegalArgumentException("motivoBaja: no debe ser nulo ni vacío");
        }
        
        Optional<Bicicleta> b = repositorioBicicletas.findById(idBicicleta);
        
        if(b.isPresent()) {
        	Bicicleta bicicleta = (Bicicleta) b.get();
        	
        	// Si la bicicleta estaba en una estación, también actualizar la estación
            if (bicicleta.getEstacion() != null) {
                Estacion estacion = repositorioEstaciones.findById(bicicleta.getEstacion().getId()).get();
                estacion.removeBici(bicicleta);
                repositorioEstaciones.save(estacion);
            }
        	
        	// Actualizar la información de la bicicleta
            bicicleta.darBaja(motivoBaja);
            
            // Actualizar bicicleta en el repositorio
            repositorioBicicletas.save(bicicleta);
        }
        else
        	throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);
    }
    
    @Override
    public Page<Bicicleta> getListadoBicicletasEnEstacion(Pageable pageable, String idEstacion) throws RepositorioException, EntidadNoEncontrada{
    	if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");
    	
    	Optional<Estacion> e = repositorioEstaciones.findById(idEstacion);
    	
    	if(e.isPresent()) {
    		Estacion estacion = e.get();
    		return repositorioBicicletas.findByEstacion(pageable, estacion);
    	}
    	else
    		throw new EntidadNoEncontrada("Estacion no encontrada con id: " + idEstacion);
    }
    
    
    //---------------------------------------------------------------------------------------------------------------------
    
    
    //Funciones usuarios

	@Override
	public Page<Estacion> getListadoEstaciones(Pageable pageable) throws RepositorioException, EntidadNoEncontrada{
		return repositorioEstaciones.findAll(pageable);
	}
	
	
	@Override
	public Estacion getEstacion(String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Optional<Estacion> e = repositorioEstaciones.findById(idEstacion);
		
		if(e.isPresent()) {
			Estacion estacion = e.get();
			return estacion;
		}
		else
    		throw new EntidadNoEncontrada("Estacion no encontrada con id: " + idEstacion);
	}

	@Override
	public Page<Bicicleta> getListadoBicicletasDisponiblesEnEstacion(Pageable pageable, String idEstacion) throws RepositorioException, EntidadNoEncontrada{
		Optional<Estacion> e = repositorioEstaciones.findById(idEstacion);
	
		if(e.isPresent()) {
			Estacion estacion = e.get();
			return repositorioBicicletas.findByEstacionAndIsDisponible(pageable, estacion, true);
		}
		else
			throw new EntidadNoEncontrada("Estacion no encontrada con id: " + idEstacion);
	}
    
    @Override
    public void estacionarBicicleta(String idBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null || idBicicleta.isEmpty()) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
        }

        if (idEstacion == null || idEstacion.isEmpty()) {
            throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");
        }
        
        Optional<Estacion> e = repositorioEstaciones.findById(idEstacion);
        Optional<Bicicleta> b = repositorioBicicletas.findById(idBicicleta);
        
        if(e.isPresent() && b.isPresent()) {
    		Estacion estacion = (Estacion) e.get();
    		Bicicleta bicicleta = (Bicicleta) b.get();
    		
    		if(estacion.hasHueco()) {
    			// Si la bicicleta estaba en una estación, también actualizar esa
                if (bicicleta.getEstacion() != null) {
                    Estacion estacionAntigua = repositorioEstaciones.findById(bicicleta.getEstacion().getId()).get();
                    estacionAntigua.removeBici(bicicleta);
                    repositorioEstaciones.save(estacionAntigua);
                }
    			estacion.addBici(bicicleta);
    			bicicleta.setEstacion(estacion);
    			repositorioEstaciones.save(estacion);
    	        repositorioBicicletas.save(bicicleta);
    		}
    	}
    	else
    		throw new EntidadNoEncontrada("Estacion o Bicicleta no encontrada con id: " + idEstacion + " o " + idBicicleta);
    }

	@Override
	public Bicicleta getBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada {
		Optional<Bicicleta> b = repositorioBicicletas.findById(idBicicleta);
		
		if(b.isPresent()) {
			Bicicleta bicicleta = b.get();
			return bicicleta;
		}
		else
    		throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);	
	}
}