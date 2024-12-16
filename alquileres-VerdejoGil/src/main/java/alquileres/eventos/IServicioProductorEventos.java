package alquileres.eventos;

import alquileres.modelo.Alquiler;

public interface IServicioProductorEventos {
	
	void producirEventoAlquilar(Alquiler alquiler) throws Exception;
	
	void producirEventoAlquilerConcluido(Alquiler alquiler, String idEstacion) throws Exception;
}
