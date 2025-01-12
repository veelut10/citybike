package alquileres.servicio;

import retrofit.estaciones.ClienteEstaciones;
import retrofit.estaciones.ServicioAlquileresException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import estaciones.modelo.EstacionDTO;

public class ServicioEstaciones implements IServicioEstaciones{
	
	ClienteEstaciones clienteEstaciones;
	
	@Override
	public boolean hasHuecoDisponible(String idEstacion) throws ServicioAlquileresException {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://estaciones:8080/estaciones/").addConverterFactory(GsonConverterFactory.create()).build();
		
		clienteEstaciones = retrofit.create(ClienteEstaciones.class);
		
		EstacionDTO estacion;
		try {
			estacion = clienteEstaciones.getEstacionById(idEstacion).execute().body();
		} catch (IOException e) {
			throw new ServicioAlquileresException("Error al recibir la Estacion con id " + idEstacion + " del microservicio Estaciones", e);
		}
		
		return (estacion.getPuestosLibres() > 0);
	}
	
	@Override
	public void situarBicicleta(String idBicicleta, String idEstacion) throws ServicioAlquileresException  {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://estaciones:8080/estaciones/").addConverterFactory(GsonConverterFactory.create()).build();
		
		clienteEstaciones = retrofit.create(ClienteEstaciones.class);
		
		try {
			clienteEstaciones.estacionarBicicleta(idEstacion, idBicicleta).execute().body();
		} catch (IOException e) {
			throw new ServicioAlquileresException("Error al recibir la Estacion con id " + idEstacion + " del microservicio Estaciones", e);
		}
	}
}
