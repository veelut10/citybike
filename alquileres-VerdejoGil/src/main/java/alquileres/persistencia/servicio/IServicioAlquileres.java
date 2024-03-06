package alquileres.persistencia.servicio;

import java.time.LocalDate;

import repositorio.RepositorioException;

public interface IServicioAlquileres {
	String crear (String id, String idBicicleta, LocalDate fechaInicio, LocalDate fechaFin) throws RepositorioException;
}
