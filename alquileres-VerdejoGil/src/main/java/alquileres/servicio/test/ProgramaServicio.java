package alquileres.servicio.test;

import alquileres.servicio.IServicioAlquileres;
import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ProgramaServicio {
	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
		Usuario u = new Usuario();
		String idBici = "1";
		
		servicioAlquileres.reservar(u.getId(), idBici);
		
	}

}
