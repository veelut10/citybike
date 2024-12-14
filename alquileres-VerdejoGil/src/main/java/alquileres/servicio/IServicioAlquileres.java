package alquileres.servicio;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import retrofit.estaciones.ServicioAlquileresException;

public interface IServicioAlquileres {
	
	// Método para reservar una bicicleta
    public void reservar(String idUsuario, String idBicicleta) throws RepositorioException, EntidadNoEncontrada;

    // Método para confirmar una reserva
    public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

    // Método para alquilar una bicicleta sin reserva previa
    public void alquilar(String idUsuario, String idBicicleta) throws RepositorioException, EntidadNoEncontrada;

    // Método para obtener el historial del usuario
    public Usuario historialUsuario(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

    // Método para dejar una bicicleta
    public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada, ServicioAlquileresException;

    // Método para liberar bloqueo eliminando reservas caducadas
    public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada;
    
    //Metodo para borrar las reservas para la bicicleta con id idBicicleta de los usuario
    //Metodo que se usa al recibir evento de microservicio estaciones
    void buscarAndEliminarReservasActivasDeBicicleta(String idBicicleta) throws RepositorioException, EntidadNoEncontrada;
    
    //Metodo para probar los usuarios bloqueados
    public void setTiempos(String idUsuario) throws RepositorioException, EntidadNoEncontrada;
}
