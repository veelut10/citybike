package alquileres.rest;

import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.FormParam;
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

import alquileres.eventos.IServicioConsumidorEventos;
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

	private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Context
	private UriInfo uriInfo;
	
	// Para usar el controlador primero se necesitara un token que se generara mediante la funcion login:
	
	//curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/x-www-form-urlencoded" -d "username=usuario&password=clave"

	// Con la informacion de este token, si los claims tienen como rol="usuario" (la contruccion de los claims esta en ControladorAuth)
	// el resto de funciones REST estara disponibles sustituyendo el token por jwt_token
	
	// curl -X GET http://localhost:8080/api/alquileres/1 -H "Authorization: Bearer jwt_token"
	
	@GET
	@Path("{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"usuario", "gestor"})
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
	
	// curl -i -X POST --data "idBicicleta=1" http://localhost:8080/api/alquileres/1/reserva -H "Authorization: Bearer jwt_token"

	@POST
	@Path("/{idUsuario}/reserva")
	@RolesAllowed("usuario")
	public Response reservar(@PathParam("idUsuario") String idUsuario, @FormParam("idBicicleta") String idBicicleta) throws Exception {
		servicio.reservar(idUsuario, idBicicleta);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X POST -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/confirma -H "Authorization: Bearer jwt_token"

	@POST
	@Path("/{idUsuario}/confirma")
	@RolesAllowed("usuario")
	public Response confirmarReserva(@PathParam("idUsuario") String idUsuario) throws Exception {
		servicio.confirmarReserva(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X POST --data "idBicicleta=1" http://localhost:8080/api/alquileres/1/alquiler -H "Authorization: Bearer jwt_token"

	@POST
	@Path("/{idUsuario}/alquiler")
	@RolesAllowed("usuario")
	public Response alquilar(@PathParam("idUsuario") String idUsuario, @FormParam("idBicicleta") String idBicicleta) throws Exception {
		servicio.alquilar(idUsuario, idBicicleta);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X PUT -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/estacion/1/deja -H "Authorization: Bearer jwt_token"

	@PUT
	@Path("/{idUsuario}/estacion/{idEstacion}/deja")
	@RolesAllowed("usuario")
	public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario, @PathParam("idEstacion") String idEstacion) throws Exception {
		servicio.dejarBicicleta(idUsuario, idEstacion);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	// curl -i -X PUT -H "Content-type: application/json" http://localhost:8080/api/alquileres/1/libera -H "Authorization: Bearer jwt_token"

	@PUT
	@Path("/{idUsuario}/libera")
	@RolesAllowed("gestor")
	public Response liberarBloqueo(@PathParam("idUsuario") String idUsuario) throws Exception {
		servicio.liberarBloqueo(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
