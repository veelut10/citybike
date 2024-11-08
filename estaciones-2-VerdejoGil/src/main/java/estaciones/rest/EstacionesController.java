package estaciones.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.rest.modelo.BicicletaDTO;
import estaciones.rest.modelo.EstacionDTO;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@RestController
@RequestMapping("/estaciones")
public class EstacionesController {
	private IServicioEstaciones servicio;

	@Autowired
	public EstacionesController(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}
	
	// No funciona ninguno por el JSON (lo de -d{})
	// curl -i -X POST -H "Content-Type: application/json" -d '{"id": 1, "nombre": "Estacion", "fechaAlta": "2024-04-12", "numPuestos": 20, "direccion": "Calle", "longitud": 73.935242, "latitud": 40.730610, "idBicicletas": [1, 2, 3] }' http://localhost:8080/estaciones
	// curl -X POST -H "Content-Type: application/json" -d '{"nombre": "Nombre de la estación", "numPuestos": 10, "direccion": "Dirección de la estación", "longitud": 0.0, "latitud": 0.0 }' http://localhost:8080/estaciones
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void> altaEstacion(
			@RequestBody EstacionDTO estacion) throws RepositorioException{
		String id = this.servicio.altaEstacion(estacion.getNombre(), estacion.getNumPuestos(), estacion.getDireccion(), estacion.getLongitud(), estacion.getLatitud());
		// Construye la URL completa del nuevo recurso
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(nuevaURL).build();

	}
	
	@PostMapping(value = "/{idEstacion}/bicicleta", consumes = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void> altaBicicleta(
			@PathVariable String idEstacion,
			@RequestBody BicicletaDTO bicicleta) throws RepositorioException, EntidadNoEncontrada{
		String idBici = this.servicio.altaBicicleta(bicicleta.getModelo(), idEstacion);
		// Construye la URL completa del nuevo recurso
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEstacion}/bicicleta").buildAndExpand(idBici).toUri();
		return ResponseEntity.created(nuevaURL).build();

	}
	
	@PutMapping(value = "/bajaBicicleta")
	public ResponseEntity<Void> bajaBicicleta(
			@PathVariable String id,
			@PathVariable String idBicicleta) throws RepositorioException, EntidadNoEncontrada{
		servicio.bajaBicicleta(idBicicleta, "");
		// Construye la URL completa del nuevo recurso
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/bicicleta").buildAndExpand(idBicicleta).toUri();
		return ResponseEntity.created(nuevaURL).build();

	}
	
	@GetMapping("/{idEstacion}/bicicletas")
	public Page<BicicletaDTO> getListadoBicicletasEnEstacion(
			@RequestParam int page,
			@RequestParam int size,
			@PathVariable String idEstacion) throws Exception {
		
		Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());

		return this.servicio.getListadoBicicletasEnEstacion(paginacion, idEstacion).map((bicicleta) -> {
    		BicicletaDTO bicicletaDTO = fromBicicletaToDTO(bicicleta);
        	return bicicletaDTO;
    	});
	}

	@GetMapping("/{idEstacion}")
	public EstacionDTO getEstacionById(@PathVariable String idEstacion) throws Exception {
		Estacion estacion = servicio.getEstacion(idEstacion);
		EstacionDTO estacionDTO = fromEstacionToDTO(estacion);
		return estacionDTO;
	}
	
	@GetMapping
	public Page<EstacionDTO> getAllEstacionesPaginado(
			@RequestParam int page,
			@RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());
		
		return this.servicio.getListadoEstaciones(paginacion).map((estacion) -> {
			EstacionDTO estacionDTO = fromEstacionToDTO(estacion);	
			return estacionDTO;
		});
	}
	
	private EstacionDTO fromEstacionToDTO(Estacion estacion) {
    	EstacionDTO estacionDTO = new EstacionDTO();
		estacionDTO.setId(estacion.getId());
		estacionDTO.setNombre(estacion.getNombre());
		estacionDTO.setFechaAlta(estacion.getFechaAlta());
		estacionDTO.setNumPuestos(estacion.getNumPuestos());
		estacionDTO.setDireccion(estacion.getDireccion());
		estacionDTO.setLongitud(estacion.getLongitud());
		estacionDTO.setLatitud(estacion.getLatitud());
		
		List<String> idBicicletas = new ArrayList<String>();
		for(Bicicleta b : estacion.getBicicletas()) 
			idBicicletas.add(b.getId());
		
		estacionDTO.setIdBicicletas(idBicicletas);
		return estacionDTO;
    }
    
    private BicicletaDTO fromBicicletaToDTO(Bicicleta bicicleta) {
    	BicicletaDTO bicicletaDTO = new BicicletaDTO();
    	bicicletaDTO.setId(bicicleta.getId());
    	bicicletaDTO.setModelo(bicicleta.getModelo());
    	bicicletaDTO.setFechaAlta(bicicleta.getFechaAlta());
    	bicicletaDTO.setFechaBaja(bicicleta.getFechaBaja());
    	bicicletaDTO.setMotivoBaja(bicicleta.getMotivoBaja());
    	bicicletaDTO.setIdEstacion(bicicleta.getEstacion().getId());
    	bicicletaDTO.setDisponible(bicicleta.isDisponible());
    	return bicicletaDTO;
    }

}
