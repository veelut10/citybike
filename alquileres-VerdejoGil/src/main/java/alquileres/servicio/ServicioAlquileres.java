package alquileres.servicio;

import java.time.LocalDateTime;
import java.util.Iterator;

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
	
	IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	
	@Override
	public void reservar(String idUsuario, String idBicicleta) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
			
		if (idBicicleta == null || idBicicleta.isEmpty())
			throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
		
		Usuario usuario = null;
		try {
			usuario = repositorioUsuarios.getById(idUsuario);
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			repositorioUsuarios.add(usuario);
		}	
		
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
		
		Usuario usuario = null;
		try {
			usuario = repositorioUsuarios.getById(idUsuario);
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			repositorioUsuarios.add(usuario);
		}	
		
		Reserva reserva = usuario.reservaActiva();
		if(reserva != null) {
			String idBicicleta = reserva.getIdBicicleta();
			Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			usuario.removeReserva(reserva);
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
		
		Usuario usuario = null;
		Alquiler alquiler = null;
		try {
			usuario = repositorioUsuarios.getById(idUsuario);
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			repositorioUsuarios.add(usuario);
		}	
		if(usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado() && !usuario.superaTiempo()) {
			alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			repositorioUsuarios.update(usuario);
		}
	}

	@Override
	public Usuario historialUsuario(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())	
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		Usuario usuario = null;
		try {
			usuario = repositorioUsuarios.getById(idUsuario);
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			repositorioUsuarios.add(usuario);
		}	
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
		if(usuario.alquilerActivo() != null && servicioEstaciones.hasHuecoDisponible(idEstacion)) {
			usuario.alquilerActivo().setFin(LocalDateTime.now());
			servicioEstaciones.situarBicicleta(idEstacion);
			repositorioUsuarios.update(usuario);
		}
	}

	@Override
	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
				
		Usuario usuario = null;
		try {
			usuario = repositorioUsuarios.getById(idUsuario);
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			repositorioUsuarios.add(usuario);
		}
		
		Iterator<Reserva> iterator = usuario.getReservas().iterator();
		while (iterator.hasNext()) {
		    Reserva r = iterator.next();
		    if (r.isCaducada()) {
		        iterator.remove();
		    }
		}
		
		repositorioUsuarios.update(usuario);
	}
	
	//Funcion para las pruebas de bloquado
	public void setTiempos(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		Usuario usuario = repositorioUsuarios.getById(idUsuario);
		
		for(Reserva r : usuario.getReservas())
			r.setCaducidad(LocalDateTime.now().minusDays(1));
		
		repositorioUsuarios.update(usuario);
	}
	
}
