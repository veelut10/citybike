package estaciones.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.rest.modelo.BicicletaDTO;
import estaciones.rest.modelo.EstacionDTO;
import estaciones.rest.modelo.MotivoBajaDTO;
import estaciones.servicio.IServicioEstaciones;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

//ESPECIFICACIÓN OpenAPI E INTERFAZ Swagger UI
//http://localhost:8081/estaciones para obtener el token
//http://localhost:8081/v3/api-docs
//http://localhost:8081/swagger-ui.html
@RestController
@RequestMapping("/estaciones")
@Tag(name = "Estaciones", description = "Aplicacion de estaciones")
public class EstacionesController {
	private IServicioEstaciones servicio;
	
	@Autowired
	private PagedResourcesAssembler<BicicletaDTO> pagedResourcesAssemblerBicicletaDTO;
	
	@Autowired
	private PagedResourcesAssembler<EstacionDTO> pagedResourcesAssemblerEstacionDTO;

	@Autowired
	public EstacionesController(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}
	
	//http://localhost:8081/estaciones + pasar JSON de una EstacionDTO en el body
	@Operation(summary = "Dar de alta una Estacion", description ="Se usa un DTO de Estacion para crear la Estacion")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAuthority('gestor')")
	public ResponseEntity<Void> altaEstacion(@Valid @RequestBody EstacionDTO estacion) throws RepositorioException{
		String idEstacion = this.servicio.altaEstacion(estacion.getNombre(), estacion.getPuestosTotales(), estacion.getDireccion(), estacion.getLongitud(), estacion.getLatitud());
		// Construye la URL completa del nuevo recurso
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEstacion}").buildAndExpand(idEstacion).toUri();
		return ResponseEntity.created(nuevaURL).build();

	}
	
	//http://localhost:8081/estaciones/bicicletas + pasar JSON de una BicicletaDTO en el body con id de la Estacion
	@Operation(summary = "Dar de alta una Bicicleta en una Estacion", description ="Se usa un DTO de Bicicleta en el cual esta indicado el id de la Estacion")
	@PostMapping(value = "/bicicletas", consumes = { MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAuthority('gestor')")
	public ResponseEntity<Void> altaBicicleta(@Valid @RequestBody BicicletaDTO bicicleta) throws RepositorioException, EntidadNoEncontrada{
		String idBicicleta = this.servicio.altaBicicleta(bicicleta.getModelo(), bicicleta.getIdEstacion());
		// Construye la URL completa del nuevo recurso
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEstacion}/bicicletas").buildAndExpand(idBicicleta).toUri();
		return ResponseEntity.created(nuevaURL).build();

	}
	
	//http://localhost:8081/estaciones/bicicletas/{idBicicleta}/baja} + pasar JSON con el motivo de baja en el body
	@Operation(summary = "Dar de baja una Bicicleta", description ="Se indica la Bicicleta que se quiere dar de baja mediante su id y se le pasa el Motivo de Baja por un DTO")
	@PatchMapping(value = "/bicicletas/{idBicicleta}/baja")
	@PreAuthorize("hasAuthority('gestor')")
	public ResponseEntity<Void> bajaBicicleta(
			@PathVariable String idBicicleta, 
			@Valid @RequestBody MotivoBajaDTO motivoBaja) throws RepositorioException, EntidadNoEncontrada{
		servicio.bajaBicicleta(idBicicleta, motivoBaja.getMotivoBaja());
		return ResponseEntity.noContent().build();
	}
	
	//http://localhost:8081/estaciones/{idEstacion}/bicicletas?page=0&size=2
	@Operation(summary = "Obtener el listado de Bicicletas en una Estacion", description ="Se indica la Estacion mediante su id y el numero y tamaño de pagina")
	@GetMapping("/{idEstacion}/bicicletas")
	@PreAuthorize("hasAuthority('gestor')")
	public PagedModel<EntityModel<BicicletaDTO>> getListadoBicicletasEnEstacion(
			@PathVariable String idEstacion,
			@RequestParam int page,
			@RequestParam int size) throws Exception {
		
		Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());
		
		Page<BicicletaDTO> listado = this.servicio.getListadoBicicletasEnEstacion(paginacion, idEstacion).map((bicicleta) -> {
    		BicicletaDTO bicicletaDTO = fromBicicletaToDTO(bicicleta);
        	return bicicletaDTO;
    	});
					
		return this.pagedResourcesAssemblerBicicletaDTO.toModel(listado, bicicletaDTO -> {
        	EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);
        	try {
        		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
        				.methodOn(EstacionesController.class)
        				.getBicicletaById(bicicletaDTO.getId()))
        				.withSelfRel());
        		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
        				.methodOn(EstacionesController.class)
        				.bajaBicicleta(bicicletaDTO.getId(), new MotivoBajaDTO()))
        				.withRel("darDeBaja"));
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	return model;
    	});
	}
	
	//http://localhost:8081/estaciones?page=0&size=2
	@Operation(summary = "Obtener el listado de Estaciones", description ="Se indica el numero y tamaño de pagina que se quiere mostrar")
	@GetMapping
	public PagedModel<EntityModel<EstacionDTO>> getListadoEstaciones(
			@RequestParam int page,
			@RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());
		
		Page<EstacionDTO> listado = servicio.getListadoEstaciones(paginacion).map((estacion) -> {
			EstacionDTO estacionDTO = fromEstacionToDTO(estacion);	
			return estacionDTO;
		});
		
		return this.pagedResourcesAssemblerEstacionDTO.toModel(listado, estacionDTO -> {
        	EntityModel<EstacionDTO> model = EntityModel.of(estacionDTO);
        	try {
        		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
        				.methodOn(EstacionesController.class)
        				.getEstacionById(estacionDTO.getId()))
        				.withSelfRel());
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	return model;
    	});
	}
	
	//http://localhost:8081/estaciones/{idEstacion}
	@Operation(summary = "Obtener la Estacion indicada", description ="Se indica el id de la Estacion")
	@GetMapping("/{idEstacion}")
	public EstacionDTO getEstacionById(@PathVariable String idEstacion) throws Exception {
		Estacion estacion = servicio.getEstacion(idEstacion);
		EstacionDTO estacionDTO = fromEstacionToDTO(estacion);
		return estacionDTO;
	}
	
	//http://localhost:8081/estaciones/{idEstacion}/bicicletas-disponicles?page=0&size=2
	@Operation(summary = "Obtener el listado de Bicicletas disponibles en una Estacion", description ="Se indica la Estacion mediante su id y el numero y tamaño de pagina")
	@GetMapping("/{idEstacion}/bicicletas-disponibles")
	public PagedModel<EntityModel<BicicletaDTO>> getListadoBicicletasDisponiblesEnEstacion(
			@PathVariable String idEstacion,
			@RequestParam int page,
			@RequestParam int size) throws Exception {
		
		Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());

		Page<BicicletaDTO> listado = servicio.getListadoBicicletasDisponiblesEnEstacion(paginacion, idEstacion).map((bicicleta) -> {
    		BicicletaDTO bicicletaDTO = fromBicicletaToDTO(bicicleta);
        	return bicicletaDTO;
    	});
		
		return this.pagedResourcesAssemblerBicicletaDTO.toModel(listado, bicicletaDTO -> {
        	EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);
        	try {
        		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
        				.methodOn(EstacionesController.class)
        				.getBicicletaById(bicicletaDTO.getId()))
        				.withSelfRel());
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	return model;
    	});
	}
	
	//http://localhost:8081/estaciones/{idEstacion}/bicicletas/{idBicicleta}
	@Operation(summary = "Estacionar una Bicicleta en una Estacion", description = "Se indica la Bicicleta y Estacion donde se va a estacionar mediante sus ids")
	@PostMapping(value = "/{idEstacion}/bicicletas/{idBicicleta}")
	public ResponseEntity<Void> estacionarBicicleta(
			@PathVariable String idEstacion, 
			@PathVariable String idBicicleta) throws RepositorioException, EntidadNoEncontrada{
		servicio.estacionarBicicleta(idBicicleta, idEstacion);
		return ResponseEntity.noContent().build();
	}
	
	
	//Funcion auxiliar para los listados de bicicletas
	//http://localhost:8081/estaciones/bicicletas/{idBicicleta}}
	@Operation(summary = "Obtener la Bicicleta indicada", description ="Se indica el id de la Bicicleta")
	@GetMapping("/bicicletas/{idBicicleta}")
	public BicicletaDTO getBicicletaById(@PathVariable String idBicicleta) throws Exception {
		Bicicleta bicicleta = servicio.getBicicleta(idBicicleta);
		BicicletaDTO bicicletaDTO = fromBicicletaToDTO(bicicleta);
		return bicicletaDTO;
	}
	
	private EstacionDTO fromEstacionToDTO(Estacion estacion) {
    	EstacionDTO estacionDTO = new EstacionDTO();
		estacionDTO.setId(estacion.getId());
		estacionDTO.setNombre(estacion.getNombre());
		estacionDTO.setFechaAlta(estacion.getFechaAlta());
		estacionDTO.setPuestosTotales(estacion.getNumPuestos());
		estacionDTO.setPuestosLibres(estacion.getNumPuestos() - estacion.getBicicletas().size());
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
