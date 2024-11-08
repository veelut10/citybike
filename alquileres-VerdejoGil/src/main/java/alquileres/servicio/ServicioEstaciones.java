package alquileres.servicio;

public class ServicioEstaciones implements IServicioEstaciones{
	
	@Override
	public boolean hasHuecoDisponible(String idEstacion) {
		return true;
	}
	
	@Override
	public boolean situarBicicleta(String idBicicleta, String idEstacion) {
		return true;
	}
}
