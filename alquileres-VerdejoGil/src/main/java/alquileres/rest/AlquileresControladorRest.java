package alquileres.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import alquileres.rest.modelo.AlquilerDTO;
import alquileres.rest.modelo.ReservaDTO;
import alquileres.rest.modelo.UsuarioDTO;
import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

@Path("alquileres")
public class AlquileresControladorRest {

	IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Context
	private UriInfo uriInfo;

	// http://localhost:8080/api/alquileres/1
	
	@GET
	@Path("{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoria(@PathParam("idUsuario") String idUsuario) throws Exception {
		
		//Se obtiene el usuario
		Usuario usuario = servicio.historialUsuario(idUsuario);

		//Se crea el DTO
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		//Se setean los parametros de usuarioDTO y se envia el DTO
		usuarioDTO.setId(idUsuario);
		usuarioDTO.setTiempoUsoHoy(usuario.tiempoUsoHoy());
		usuarioDTO.setTiempoUsoSemana(usuario.tiempoUsoSemana());
		usuarioDTO.setBloqueado(usuario.bloqueado());
		
		ArrayList<ReservaDTO> reservas = new ArrayList<ReservaDTO>();
		ArrayList<AlquilerDTO> alquileres = new ArrayList<AlquilerDTO>();
		
		for(Reserva r : usuario.getReservas()) {
			ReservaDTO reservaDTO = new ReservaDTO();
			reservaDTO.setIdBicicleta(r.getIdBicicleta());
			reservaDTO.setFechaCreada(r.getCreada().toString());
			reservaDTO.setFechaCaducidad(r.getCaducidad().toString());
			reservaDTO.setCaducada(r.isCaducada());
			reservas.add(reservaDTO);
		}
		
		for(Alquiler a : usuario.getAlquileres()) {
			AlquilerDTO alquilerDTO = new AlquilerDTO();
			alquilerDTO.setIdBicicleta(a.getIdBicicleta());
			alquilerDTO.setFechaInicio(a.getInicio().toString());
			if(a.getFin() == null)
				alquilerDTO.setFechaFin("_/_/_");
			else
				alquilerDTO.setFechaFin(a.getFin().toString());
			alquilerDTO.setActivo(a.isActivo());
			alquilerDTO.setTiempo(a.getTiempo());
			alquileres.add(alquilerDTO);
		}
		
		usuarioDTO.setReservas(reservas);
		usuarioDTO.setAlquileres(alquileres);

		return Response.status(Response.Status.OK).entity(usuarioDTO).build();
	}
	
	// curl -i -X POST -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/bicicleta/1/reservar

	@POST
	@Path("/{idUsuario}/bicicleta/{idBicicleta}/reservar")
	public Response reservar(@PathParam("idUsuario") String idUsuario, @PathParam("idBicicleta") String idBicicleta) throws Exception {
		servicio.reservar(idUsuario, idBicicleta);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X PUT -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/confirmar

	@PUT
	@Path("/{idUsuario}/confirmar")
	public Response confirmarReserva(@PathParam("idUsuario") String idUsuario) throws Exception {
		servicio.confirmarReserva(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X POST -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/bicicleta/1/alquilar

	@POST
	@Path("/{idUsuario}/bicicleta/{idBicicleta}/alquilar")
	public Response alquilar(@PathParam("idUsuario") String idUsuario, @PathParam("idBicicleta") String idBicicleta) throws Exception {
		servicio.alquilar(idUsuario, idBicicleta);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X PUT -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/dejar

	@PUT
	@Path("/{idUsuario}/estacion/{idEstacion}/dejar")
	public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario, @PathParam("idEstacion") String idEstacion) throws Exception {
		servicio.dejarBicicleta(idUsuario, idEstacion);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X PUT -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/liberar

	@PUT
	@Path("/{idUsuario}/liberar")
	public Response liberarBloqueo(@PathParam("idUsuario") String idUsuario) throws Exception {
		servicio.liberarBloqueo(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
