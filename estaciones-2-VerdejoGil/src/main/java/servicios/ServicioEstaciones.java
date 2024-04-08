package servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import modelo.Bicicleta;
import modelo.Estacion;
import modelo.Historico;
import modelo.ResumenSitioTuristico;
import modelo.SitioTuristico;
import repositorios.FactoriaRepositorios;
import repositorios.Repositorio;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import repositorios.RepositorioMemoria;

public class ServicioEstaciones implements IServicioEstaciones{
	
	private Repositorio<Estacion, String> repositorioEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
	private Repositorio<Historico, String> repositorioHistoricos = FactoriaRepositorios.getRepositorio(Historico.class);
	private Repositorio<Bicicleta, String> repositorioBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	
    
	
	private SitiosTuristicosGeoNames sitiosTuristicosGeonames = new SitiosTuristicosGeoNames();
	
	
	
	@Override
	public String crearEstacion (String nombre, int numPuestos, String direccion, double longitud, double latitud) throws RepositorioException{
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
		String idEstacion = repositorioEstaciones.add(estacion);
		return idEstacion;
	}
	
	@Override
	public List<SitioTuristico> obtenerSitiosTuristicos(String idEstacion) throws RepositorioException, EntidadNoEncontrada{
		if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		ArrayList<SitioTuristico> sitios = new ArrayList<SitioTuristico>();
		Estacion estacion = this.getEstacion(idEstacion);
		List<ResumenSitioTuristico> resumenes = sitiosTuristicosGeonames.getResumenesSitiosInteres(estacion.getLongitud(), estacion.getLatitud());
		for(ResumenSitioTuristico resumen : resumenes) {
			SitioTuristico s = sitiosTuristicosGeonames.getSitioInteres(resumen.getId());
			if(s != null)
				sitios.add(s);
		}
		return sitios;
	}

	@Override
	public void establecerSitiosTuristicos(String id, List<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada{
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		if (sitiosTuristicos == null)
			throw new IllegalArgumentException("sitios turisticos: no debe ser nulo");
		
		Estacion estacion = repositorioEstaciones.getById(id);
		estacion.setSitiosTuristicos(sitiosTuristicos);
		repositorioEstaciones.update(estacion);
	}

	@Override
	public Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada{
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		return repositorioEstaciones.getById(id);
	}
	
	

    // Métodos a implementar
    @Override
    public String altaBicicleta(String modeloBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
    	if (modeloBicicleta == null || modeloBicicleta.isEmpty())
			throw new IllegalArgumentException("modeloBicicleta: no debe ser nulo ni vacio");
		
    	if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");
		
        //Crear bicicleta y añadir bicicleta al repositorioBicicletas
    	Bicicleta bicicleta = new Bicicleta(modeloBicicleta, LocalDate.now(), idEstacion);
        String idBicicleta = repositorioBicicletas.add(bicicleta);
        
        //Añadir bicicleta a la estacion y actualizar repositorioEstaciones
        Estacion estacion = repositorioEstaciones.getById(idEstacion);
        Set<String> bicis = estacion.getBicicletas();
        bicis.add(bicicleta.getId());
        estacion.setBicicletas(bicis);
        repositorioEstaciones.update(estacion);
        return idBicicleta;
    }
    
    @Override
    public void estacionarBicicleta(String idBicicleta, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null || idBicicleta.isEmpty()) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacío");
        }

        if (idEstacion == null || idEstacion.isEmpty()) {
            throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacío");
        }

        try {
            Bicicleta bicicleta = repositorioBicicletas.getById(idBicicleta);
            if (bicicleta == null) {
                throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);
            }

            Estacion estacion = repositorioEstaciones.getById(idEstacion);
            if (estacion == null) {
                throw new EntidadNoEncontrada("Estación no encontrada con id: " + idEstacion);
            }

            Historico historico = new Historico(idBicicleta, estacion, LocalDate.now());
            repositorioHistoricos.add(historico);

            bicicleta.setEstacion(idEstacion);
            bicicleta.setHistorico(historico.getId());
            repositorioBicicletas.update(bicicleta);
            
            Set<String> bicisIds = estacion.getBicicletas();
            bicisIds.add(idBicicleta);
            List<Historico> historicos = estacion.getHistorico();
            historicos.add(historico);

            estacion.setBicicletas(bicisIds);
            estacion.setHistorico(historicos);
            
            repositorioEstaciones.update(estacion);

        } catch (RepositorioException e) {
            throw new RepositorioException("Error al acceder a los repositorios: " + e.getMessage(), e);
        } catch (EntidadNoEncontrada e) {
            throw e;
        } catch (Exception e) {
            throw new RepositorioException("Error inesperado al estacionar bicicleta: " + e.getMessage(), e);
        }
    }

    
    @Override
    public void estacionarBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null || idBicicleta.isEmpty()) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacío");
        }

        try {
            // Obtener la bicicleta
            Bicicleta bicicleta = repositorioBicicletas.getById(idBicicleta);
            if (bicicleta == null) {
                throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);
            }

            // Buscar una estación con puestos libres
            Estacion estacion = repositorioEstaciones.getAll().stream()
													            		.filter(est -> est.getBicicletas().size() < est.getNumPuestos())
													                    .findFirst()
													                    .orElse(null);;
            if (estacion == null) {
                throw new RepositorioException("No se encontraron estaciones con puestos libres");
            }

            // Crear historico
            Historico historico = new Historico(idBicicleta, estacion, LocalDate.now());
            repositorioHistoricos.add(historico);

            // Actualizar bicicleta
            bicicleta.setEstacion(estacion.getId());
            bicicleta.setHistorico(historico.getId());
            repositorioBicicletas.update(bicicleta);
            
            // Actualizar estación
            estacion.getBicicletas().add(idBicicleta);
            estacion.getHistorico().add(historico);
            repositorioEstaciones.update(estacion);

        } catch (RepositorioException e) {
            throw new RepositorioException("Error al acceder a los repositorios: " + e.getMessage(), e);
        } catch (EntidadNoEncontrada e) {
            throw e;
        } catch (Exception e) {
            throw new RepositorioException("Error inesperado al estacionar bicicleta: " + e.getMessage(), e);
        }
    }


    @Override
    public void retirarBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null || idBicicleta.isEmpty()) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacío");
        }

        try {
            // Obtener la bicicleta y verificar su existencia
            Bicicleta bicicleta = repositorioBicicletas.getById(idBicicleta);
            if (bicicleta == null) {
                throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);
            }

            // Obtener la estación y el histórico asociados a la bicicleta
            String estacionID = bicicleta.getEstacion();
            String historicoID = bicicleta.getHistorico();
            Estacion estacion = repositorioEstaciones.getById(estacionID);
            Historico historico = repositorioHistoricos.getById(historicoID);

            // Actualizar la bicicleta
            bicicleta.setEstacion(null);
            bicicleta.setHistorico(null);
            repositorioBicicletas.update(bicicleta);

            // Actualizar la estación
            estacion.removeBici(bicicleta);
            repositorioEstaciones.update(estacion);

            // Actualizar el histórico
            historico.setFechaFin(LocalDate.now());
            repositorioHistoricos.update(historico);

        } catch (RepositorioException e) {
            throw new RepositorioException("Error al acceder a los repositorios: " + e.getMessage(), e);
        } catch (EntidadNoEncontrada e) {
            throw e;
        } catch (Exception e) {
            throw new RepositorioException("Error inesperado al retirar bicicleta: " + e.getMessage(), e);
        }
    }

	
    @Override
    public void bajaBicicleta(String idBicicleta, String motivoBaja) throws RepositorioException, EntidadNoEncontrada {
        if (idBicicleta == null || idBicicleta.isEmpty()) {
            throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacío");
        }

        if (motivoBaja == null || motivoBaja.isEmpty()) {
            throw new IllegalArgumentException("motivoBaja: no debe ser nulo ni vacío");
        }

        try {
            // Obtener la bicicleta y verificar su existencia
            Bicicleta bicicleta = repositorioBicicletas.getById(idBicicleta);
            if (bicicleta == null) {
                throw new EntidadNoEncontrada("Bicicleta no encontrada con id: " + idBicicleta);
            }

            // Actualizar la información de la bicicleta
            bicicleta.setMotivoBaja(motivoBaja);
            bicicleta.setFechaBaja(LocalDate.now());
            bicicleta.setEstacion(null); // Retirar la bicicleta de cualquier estación

            // Actualizar bicicleta en el repositorio
            repositorioBicicletas.update(bicicleta);

            // Si la bicicleta estaba en una estación, también actualizar la estación
            if (bicicleta.getEstacion() != null) {
                Estacion estacion = repositorioEstaciones.getById(bicicleta.getEstacion());
                if (estacion != null) {
                    estacion.removeBici(bicicleta);
                    repositorioEstaciones.update(estacion);
                }
            }

        } catch (RepositorioException e) {
            throw new RepositorioException("Error al acceder a los repositorios: " + e.getMessage(), e);
        } catch (EntidadNoEncontrada e) {
            throw e;
        } catch (Exception e) {
            throw new RepositorioException("Error inesperado al dar de baja la bicicleta: " + e.getMessage(), e);
        }
    }


	@Override
	public List<Bicicleta> bicicletasEstacionadasCerca(double longitud, double latitud) throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Estacion> estacionesConMasSitiosTuristicosCerca() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

    
    
    
}