package alquileres.persistencia.repositorio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import alquileres.persistencia.jpa.AlquilerEntidad;
import alquileres.persistencia.jpa.ReservaEntidad;
import alquileres.persistencia.jpa.UsuarioEntidad;
import alquileres.repositorio.RepositorioUsuario;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioUsuarioJPA implements RepositorioUsuario{
	
	public Class<UsuarioEntidad> getClase() {
		return UsuarioEntidad.class;
	}

	public String getNombre() {
		return UsuarioEntidad.class.getName();
	}
	
	@Override
	public String add(Usuario usuario) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		UsuarioEntidad usuarioEntidad = convertirModeloToEntidad(usuario);
		try {
			em.getTransaction().begin();
			em.persist(usuarioEntidad);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al guardar la entidad con id " + usuarioEntidad.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
		return usuario.getId();
	}
	
	@Override
	public void update(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		UsuarioEntidad usuarioEntidad = convertirModeloToEntidad(usuario);
		try {
			em.getTransaction().begin();

			UsuarioEntidad instance = em.find(getClase(), usuarioEntidad.getId());
			if (instance == null) {
				throw new EntidadNoEncontrada(usuarioEntidad.getId() + " no existe en el repositorio");
			}
			usuarioEntidad = em.merge(usuarioEntidad);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al actualizar la entidad con id " + usuarioEntidad.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	@Override
	public void delete(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		UsuarioEntidad usuarioEntidad = convertirModeloToEntidad(usuario);
		try {
			em.getTransaction().begin();
			UsuarioEntidad instance = em.find(getClase(), usuarioEntidad.getId());
			if (instance == null) {
				throw new EntidadNoEncontrada(usuario.getId() + " no existe en el repositorio");
			}
			em.remove(usuarioEntidad);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al borrar la entidad con id " + usuarioEntidad.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	@Override
	public Usuario getById(String id) throws EntidadNoEncontrada, RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			UsuarioEntidad instance = em.find(getClase(), id);
			if (instance != null) {
				em.refresh(instance);
			} else {
				throw new EntidadNoEncontrada(id + " no existe en el repositorio");
			}
			Usuario usuario = convertirEntidadToModelo(instance);
			return usuario;
		} catch (RuntimeException re) {
			throw new RepositorioException("Error al recuperar la entidad con id " + id, re);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getAll() throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model from " + getNombre() + " model ";
			Query query = em.createQuery(queryString);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			List<UsuarioEntidad> usuariosEntidades = query.getResultList();
			List<Usuario> usuarios = new ArrayList<Usuario>();
			for(UsuarioEntidad ue : usuariosEntidades) {
				Usuario u = convertirEntidadToModelo(ue);
				usuarios.add(u);
			}
			return usuarios;
		} catch (RuntimeException re) {
			throw new RepositorioException("Error buscando todas las entidades de " + getNombre(), re);
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getIds() throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model.id from " + getNombre() + " model ";
			Query query = em.createQuery(queryString);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return query.getResultList();
		} catch (RuntimeException re) {
			throw new RepositorioException("Error buscando todos los ids de " + getNombre(), re);
		}
	}
	
	//Funciones auxiliares
	public static UsuarioEntidad  convertirModeloToEntidad (Usuario usuario) {
		List<ReservaEntidad> reservas = new ArrayList<ReservaEntidad>();
        List<AlquilerEntidad> alquileres = new ArrayList<AlquilerEntidad>();

        for(Reserva r : usuario.getReservas()) {
            ReservaEntidad reservaEntidad = new ReservaEntidad();
            reservaEntidad.setIdBicicleta(r.getIdBicicleta());
            reservaEntidad.setFechaCreacion(r.getCreada().toString());
            reservaEntidad.setFechaCaducidad(r.getCaducidad().toString());
            reservas.add(reservaEntidad);
        }

        for(Alquiler a : usuario.getAlquileres()) {
            AlquilerEntidad alquilerEntidad = new AlquilerEntidad();
            alquilerEntidad.setIdBicicleta(a.getIdBicicleta());
            alquilerEntidad.setFechaInicio(a.getInicio().toString());
            if(a.getFin() == null)
                alquilerEntidad.setFechaFin(null);
            else
                alquilerEntidad.setFechaFin(a.getFin().toString());
            alquileres.add(alquilerEntidad);
        }
        
        return new UsuarioEntidad(usuario.getId(), reservas, alquileres);
	}
	
	public static Usuario convertirEntidadToModelo(UsuarioEntidad usuarioEntidad) {

	    List<Reserva> reservas = new ArrayList<>();
	    List<Alquiler> alquileres = new ArrayList<>();

	    for (ReservaEntidad r : usuarioEntidad.getReservas()) {
	        Reserva reserva = new Reserva();
	        reserva.setIdBicicleta(r.getIdBicicleta());
	        reserva.setCreada(LocalDateTime.parse(r.getFechaCreacion()));
	        reserva.setCaducidad(LocalDateTime.parse(r.getFechaCaducidad()));
	        reservas.add(reserva);
	    }

	    for (AlquilerEntidad a : usuarioEntidad.getAlquileres()) {
	        Alquiler alquiler = new Alquiler();
	        alquiler.setIdBicicleta(a.getIdBicicleta());
	        alquiler.setInicio(LocalDateTime.parse(a.getFechaInicio()));
	        if (a.getFechaFin() == null) {
	            alquiler.setFin(null);
	        } else {
	            alquiler.setFin(LocalDateTime.parse(a.getFechaFin()));
	        }
	        alquileres.add(alquiler);
	    }

	    return new Usuario(usuarioEntidad.getId(), reservas, alquileres);
	}
}
