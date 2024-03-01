package alquileres.servicio.test;

import alquileres.modelo.Usuario;
import alquileres.servicio.IServicioAlquileres;
import alquileres.servicio.ServicioAlquileres;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ProgramaServicio {
	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		ServicioAlquileres servicioAlquileres = new ServicioAlquileres();
		
		Usuario u = new Usuario();
		String idBici = "1";
		
		servicioAlquileres.reservar(u.getId(), idBici);
		
	}

}
