package alquileres.servicio;

public interface IServicioEstaciones {
	public boolean hasHuecoDisponible(String idEstacion);
	
	public boolean situarBicicleta(String idBicicleta, String idEstacion);
}
