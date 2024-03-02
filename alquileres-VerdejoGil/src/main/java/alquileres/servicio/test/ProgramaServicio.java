package alquileres.servicio.test;

import alquileres.servicio.IServicioAlquileres;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ProgramaServicio {
	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		IServicioAlquileres servicioAlquileres;
		servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
		String idUsuario = "1";
		String idBici = "1";
		
		servicioAlquileres.alquilar(idUsuario, idBici);
		
	}

}
