package alquileres.servicio.test;

import alquileres.servicio.IServicioAlquileres;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import retrofit.estaciones.ServicioAlquileresException;
import servicio.FactoriaServicios;

public class ProgramaServicio {
	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada, ServicioAlquileresException {

		IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
	}

}
