package alquileres.servicio;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import alquileres.persistencia.jpa.AlquilerEntidad;
import alquileres.persistencia.jpa.ReservaEntidad;
import alquileres.persistencia.jpa.UsuarioEntidad;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioAlquileres implements IServicioAlquileres{
	
	private Repositorio<UsuarioEntidad, String> repositorioUsuarios = FactoriaRepositorios.getRepositorio(UsuarioEntidad.class);
	
	//IServicioEstaciones servicio = FactoriaServicios.getServicio(IServicioEstaciones.class);

	private static int ID = 0;
	
	@Override
	public void reservar(String idUsuario, String idBicicleta) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
			
		if (idBicicleta == null || idBicicleta.isEmpty())
			throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
		
		Usuario usuario = null;
		UsuarioEntidad usuarioEntidad = null;
		try {
			usuarioEntidad = repositorioUsuarios.getById(idUsuario);
			if(usuarioEntidad != null) {
				usuario = convertirEntidadToModelo(usuarioEntidad);
			}
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.add(usuarioEntidad);
		}	
		
		if(usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado() && !usuario.superaTiempo()) {
			Reserva reserva = new Reserva(idBicicleta, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
			usuario.addReserva(reserva);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.update(usuarioEntidad);
		}
	}

	@Override
	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		Usuario usuario = null;
		UsuarioEntidad usuarioEntidad = null;
		try {
			usuarioEntidad = repositorioUsuarios.getById(idUsuario);
			if(usuarioEntidad != null) {
				usuario = convertirEntidadToModelo(usuarioEntidad);
			}
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.add(usuarioEntidad);
		}	
		
		Reserva reserva = usuario.reservaActiva();
		if(reserva != null) {
			String idBicicleta = reserva.getIdBicicleta();
			Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			usuario.removeReserva(reserva);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.update(usuarioEntidad);
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
		UsuarioEntidad usuarioEntidad = null;
		Alquiler alquiler = null;
		try {
			usuarioEntidad = repositorioUsuarios.getById(idUsuario);
			if(usuarioEntidad != null) {
				usuario = convertirEntidadToModelo(usuarioEntidad);
			}
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.add(usuarioEntidad);
		}	
		if(usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado() && !usuario.superaTiempo()) {
			alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.update(usuarioEntidad);
		}
	}

	@Override
	public Usuario historialUsuario(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())	
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		Usuario usuario = null;
		UsuarioEntidad usuarioEntidad = null;
		try {
			usuarioEntidad = repositorioUsuarios.getById(idUsuario);
			if(usuarioEntidad != null) {
				usuario = convertirEntidadToModelo(usuarioEntidad);
			}
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.add(usuarioEntidad);
		}	
		return convertirEntidadToModelo(repositorioUsuarios.getById(usuarioEntidad.getId()));
	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
							
		if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idBicicleta: no debe ser nulo ni vacio");
		
		UsuarioEntidad usuarioEntidad = repositorioUsuarios.getById(idUsuario);
		Usuario usuario = convertirEntidadToModelo(usuarioEntidad);
		if(usuario.alquilerActivo() != null /* && servicio.hasHuecoDisponible() */) {
			usuario.alquilerActivo().setFin(LocalDateTime.now());
			//servicio.situarBicicleta();
			repositorioUsuarios.update(usuarioEntidad);
		}
	}

	@Override
	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		// Control de integridad de los datos	
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
				
		Usuario usuario = null;
		UsuarioEntidad usuarioEntidad = null;
		try {
			usuarioEntidad = repositorioUsuarios.getById(idUsuario);
			if(usuarioEntidad != null) {
				usuario = convertirEntidadToModelo(usuarioEntidad);
			}
		} catch (Exception e) {
			usuario = new Usuario(idUsuario);
			usuarioEntidad = convertirModeloToEntidad(usuario);
			repositorioUsuarios.add(usuarioEntidad);
		}
		
		Iterator<Reserva> iterator = usuario.getReservas().iterator();
		while (iterator.hasNext()) {
		    Reserva r = iterator.next();
		    if (r.isCaducada()) {
		        iterator.remove();
		    }
		}
		usuarioEntidad = convertirModeloToEntidad(usuario);
		repositorioUsuarios.update(usuarioEntidad);
	}
	
	//Funcion para las pruebas de bloquado
	public void setTiempos(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		UsuarioEntidad usuarioEntidad = repositorioUsuarios.getById(idUsuario);
		Usuario usuario = convertirEntidadToModelo(usuarioEntidad);
		
		for(Reserva r : usuario.getReservas())
			r.setCaducidad(LocalDateTime.now().minusDays(1));
		
		usuarioEntidad = convertirModeloToEntidad(usuario);
		repositorioUsuarios.update(usuarioEntidad);
	}
	
	
	public static UsuarioEntidad  convertirModeloToEntidad (Usuario usuario) {
		List<ReservaEntidad> reservas = new ArrayList<ReservaEntidad>();
        List<AlquilerEntidad> alquileres = new ArrayList<AlquilerEntidad>();

        for(Reserva r : usuario.getReservas()) {
            ReservaEntidad reserva = new ReservaEntidad();
            reserva.setId(String.valueOf(ID));
            ID++;
            reserva.setIdBicicleta(r.getIdBicicleta());
            reserva.setFechaCreacion(java.sql.Date.valueOf(r.getCreada().toLocalDate()));
            reserva.setFechaCaducidad(java.sql.Date.valueOf(r.getCaducidad().toLocalDate()));
            reservas.add(reserva);
        }

        for(Alquiler a : usuario.getAlquileres()) {
            AlquilerEntidad alquiler = new AlquilerEntidad();
            alquiler.setId(String.valueOf(ID));
            ID++;
            alquiler.setIdBicicleta(a.getIdBicicleta());
            alquiler.setFechaInicio(java.sql.Date.valueOf(a.getInicio().toLocalDate()));
            if(a.getFin() == null)
                alquiler.setFechaFin(null);
            else
                alquiler.setFechaFin(java.sql.Date.valueOf(a.getFin().toLocalDate()));
            alquileres.add(alquiler);
        }
        
        return new UsuarioEntidad(usuario.getId(), reservas, alquileres);
	}
	
	public static Usuario convertirEntidadToModelo(UsuarioEntidad usuarioEntidad) {

	    List<Reserva> reservas = new ArrayList<>();
	    List<Alquiler> alquileres = new ArrayList<>();

	    for (ReservaEntidad r : usuarioEntidad.getReservas()) {
	        Reserva reserva = new Reserva();
	        reserva.setIdBicicleta(r.getIdBicicleta());
	        Instant instant = Instant.ofEpochMilli(r.getFechaCreacion().getTime());
	        LocalDateTime tiempo = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	        reserva.setCreada(tiempo);
	        Instant instantfin = Instant.ofEpochMilli(r.getFechaCaducidad().getTime());
	        LocalDateTime tiempo2 = LocalDateTime.ofInstant(instantfin, ZoneId.systemDefault());
	        reserva.setCaducidad(tiempo2);
	        reservas.add(reserva);
	    }

	    for (AlquilerEntidad a : usuarioEntidad.getAlquileres()) {
	        Alquiler alquiler = new Alquiler();
	        alquiler.setIdBicicleta(a.getIdBicicleta());
	        Instant instant = Instant.ofEpochMilli(a.getFechaInicio().getTime());
	        LocalDateTime tiempo = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	        alquiler.setInicio(tiempo);
	        if (a.getFechaFin() == null) {
	            alquiler.setFin(null);
	        } else {
	        	Instant instant2 = Instant.ofEpochMilli(a.getFechaFin().getTime());
		        LocalDateTime tiempo2 = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault());
	            alquiler.setFin(tiempo2);
	        }
	        alquileres.add(alquiler);
	    }

	    return new Usuario(usuarioEntidad.getId(), reservas, alquileres);
	}
	
}
