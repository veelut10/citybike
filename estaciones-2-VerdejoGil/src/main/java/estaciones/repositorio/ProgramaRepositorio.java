package estaciones.repositorio;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.EstacionesApp;
import estaciones.modelo.Estacion;

public class ProgramaRepositorio {
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto =
				SpringApplication.run(EstacionesApp.class, args);
		
		RepositorioEstaciones repositorio = contexto.getBean(RepositorioEstaciones.class);
		
		Estacion e = new Estacion("a", 0, "CALEE", 0, 0, LocalDate.now());
		
		repositorio.save(e);
		
		contexto.close();
	}
}
