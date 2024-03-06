package alquileres.servicio.test;

import alquileres.modelo.Usuario;
import alquileres.servicio.IServicioAlquileres;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ProgramaServicio {
	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		IServicioAlquileres servicioAlquileres;
		servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
		Usuario usuario = null;
		String idUsuario = "1";
		String idUsuario2 = "2";
		String idUsuario3 = "3";
		String idBici = "1";
		
		servicioAlquileres.alquilar(idUsuario, idBici);
		//Comprueba que funcionen las reservas y la confirmacion de reservas ademas de crear el usuario
		usuario = servicioAlquileres.historialUsuario(idUsuario);
		
		System.out.println(usuario.toString());
		
		servicioAlquileres.reservar(idUsuario, idBici);
		
		usuario = servicioAlquileres.historialUsuario(idUsuario);
		
		System.out.println(usuario.toString());
		
		servicioAlquileres.confirmarReserva(idUsuario);
		
		usuario = servicioAlquileres.historialUsuario(idUsuario);
		
		System.out.println(usuario.toString() + "\n");
		
		
		//Comprueba que funcionen los alquileres y dejar bicicleta
		usuario = servicioAlquileres.historialUsuario(idUsuario2);
		
		System.out.println(usuario.toString());
		
		servicioAlquileres.alquilar(idUsuario2, idBici);
		
		usuario = servicioAlquileres.historialUsuario(idUsuario2);
		
		System.out.println(usuario.toString());
		
		servicioAlquileres.dejarBicicleta(idUsuario2, idBici);
		
		usuario = servicioAlquileres.historialUsuario(idUsuario2);
		
		System.out.println(usuario.toString() + "\n");
		
		
		//Comprobar que funciona el liberar bloqueo
		
		usuario = servicioAlquileres.historialUsuario(idUsuario3);
		
		System.out.println(usuario.toString());

		servicioAlquileres.reservar(idUsuario3, idBici);
		
		servicioAlquileres.setTiempos(idUsuario3);
		
		servicioAlquileres.reservar(idUsuario3, idBici);
		
		servicioAlquileres.setTiempos(idUsuario3);
		
		servicioAlquileres.reservar(idUsuario3, idBici);
		
		servicioAlquileres.setTiempos(idUsuario3);
		
		System.out.println(usuario.toString());
		
		servicioAlquileres.liberarBloqueo(idUsuario3);
		
		System.out.println(usuario.toString());
	}

}
