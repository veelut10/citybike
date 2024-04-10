package estaciones.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;

@RestController
@RequestMapping("/estaciones")
public class EstacionesController {
	private IServicioEstaciones servicio;

	@Autowired
	public EstacionesController(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}

	@GetMapping("/{id}")
	public EntityModel<Estacion> getEncuestaById(@PathVariable String id) throws Exception {
		Estacion estacion = servicio.getEstacion(1);

		// Envolver el DTO con EntityModel y agregar enlace self
		EntityModel<Estacion> model = EntityModel.of(estacion);
		model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionesController.class).getEncuestaById(id))
				.withSelfRel());

		return model;
	}
	
	@GetMapping
	public List<Bicicleta> getAllEncuestasPaginado() throws Exception {
		
		return this.servicio.getListadoBicicletasEnEstacion(1);
	}

}
