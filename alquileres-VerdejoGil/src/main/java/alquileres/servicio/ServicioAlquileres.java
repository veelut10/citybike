package alquileres.servicio;

import java.time.LocalDateTime;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioAlquileres implements IServicioAlquileres{
	
	private Repositorio<Usuario, String> repositorioUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
	
	IServicioEstaciones servicio = FactoriaServicios.getServicio(IServicioEstaciones.class);

	@Override
	public void reservar(String idUsuario, String idBicicleta) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
			
		if (idBicicleta == null || idBicicleta.isEmpty())
			throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
			
		Usuario usuario = repositorioUsuarios.getById(idUsuario);
		System.out.println("Hola");
		if(usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado() && !usuario.superaTiempo()) {
			Reserva reserva = new Reserva(idBicicleta, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
			usuario.addReserva(reserva);
			repositorioUsuarios.update(usuario);
		}
	}

	@Override
	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		Usuario usuario = repositorioUsuarios.getById(idUsuario);
		Reserva reserva = usuario.reservaActiva();
		if(reserva != null) {
			String idBicicleta = reserva.getIdBicicleta();
			Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			repositorioUsuarios.update(usuario);
		}
	}

	@Override
	public void alquilar(String idUsuario, String idBicicleta) throws RepositorioException, EntidadNoEncontrada {

		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
					
		if (idBicicleta == null || idBicicleta.isEmpty())
			throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
		
		Usuario usuario = repositorioUsuarios.getById(idUsuario);
		if(usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado() && !usuario.superaTiempo()) {
			Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			repositorioUsuarios.update(usuario);
		}
	}

	@Override
	public Usuario historialUsuario(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())	
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		return repositorioUsuarios.getById(idUsuario);
	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
							
		if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
		
		Usuario usuario = repositorioUsuarios.getById(idUsuario);
		if(usuario.alquilerActivo() != null && servicio.hasHuecoDisponible()) {
			usuario.alquilerActivo().setFin(LocalDateTime.now());
			servicio.situarBicicleta();
			repositorioUsuarios.update(usuario);
		}
	}

	@Override
	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
				
		Usuario usuario = repositorioUsuarios.getById(idUsuario);
		for(Reserva r : usuario.getReservas()) {
			if(r.isCaducada())
				usuario.removeReserva(r);
		}
		repositorioUsuarios.update(usuario);
	}
	
	
}
