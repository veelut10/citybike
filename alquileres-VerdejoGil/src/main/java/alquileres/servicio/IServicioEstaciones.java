package alquileres.servicio;

import retrofit.estaciones.ServicioAlquileresException;

public interface IServicioEstaciones {
	public boolean hasHuecoDisponible(String idEstacion) throws ServicioAlquileresException;
	
	public void situarBicicleta(String idBicicleta, String idEstacion) throws ServicioAlquileresException;
}
