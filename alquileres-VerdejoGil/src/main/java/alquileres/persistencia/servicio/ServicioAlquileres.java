package alquileres.persistencia.servicio;

import java.time.LocalDate;

import alquileres.persistencia.jpa.AlquilerEntidad;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioAlquileres implements IServicioAlquileres {

	private Repositorio<AlquilerEntidad, String> repositorio = FactoriaRepositorios.getRepositorio(AlquilerEntidad.class);
	
	@Override
	public String crear(String id, String idBicicleta, LocalDate fechaInicio, LocalDate fechaFin) throws RepositorioException{
			if (id == null || id.isEmpty())
				throw new IllegalArgumentException("id: no debe ser nulo");

			if (idBicicleta == null || idBicicleta.isEmpty())
				throw new IllegalArgumentException("idBicicleta: no debe ser nulo");

			if (fechaInicio == null) {
				throw new IllegalArgumentException("fechaInicio: no debe ser nula");
			}
			
			if (fechaFin == null) {
				throw new IllegalArgumentException("fechaFin: no debe ser nula");
			}
			
			AlquilerEntidad alquiler = new AlquilerEntidad(id, idBicicleta, fechaInicio, fechaFin);
			String idAlquiler = repositorio.add(alquiler);
			return idAlquiler;
		}

}
