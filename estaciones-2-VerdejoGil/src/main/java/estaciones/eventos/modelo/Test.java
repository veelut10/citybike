package estaciones.eventos.modelo;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import estaciones.EstacionesApp;
import estaciones.eventos.bus.PublicadorEventos;
import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacion;

public class Test {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EstacionesApp.class, args);
		
		PublicadorEventos sender = context.getBean(PublicadorEventos.class);
		
		Bicicleta bici = new Bicicleta("Modelo", LocalDate.now(), new Estacion());
		
		sender.emitirEventoBicicletaDesactivada(bici);
		
		System.out.println("Bici enviada");
	}
}
