package estaciones.repositorio;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.EstacionesApp;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class ProgramaRepositorio {
	
	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		
		ConfigurableApplicationContext contexto = SpringApplication.run(EstacionesApp.class, args);
		
		IServicioEstaciones servicio = contexto.getBean(IServicioEstaciones.class);
		
		String estacionId = servicio.altaEstacion("a", 120, "CALEE", 0, 0);
		
		servicio.altaBicicleta("a", estacionId);
		
		servicio.altaBicicleta("b", estacionId);
		
		servicio.altaBicicleta("c", estacionId);
		
		servicio.altaBicicleta("d", estacionId);
		
		System.out.println("ALKFJKASFJLASFj");
		
		contexto.close();
	}
}
