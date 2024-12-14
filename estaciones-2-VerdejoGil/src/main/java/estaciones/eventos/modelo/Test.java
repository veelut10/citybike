package estaciones.eventos.modelo;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import estaciones.EstacionesApp;
import estaciones.eventos.bus.PublicadorEventos;

public class Test {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EstacionesApp.class, args);
		
		PublicadorEventos sender = context.getBean(PublicadorEventos.class);
		
		BicicletaEvento bici = new BicicletaEvento("1", LocalDate.now(), LocalDate.now().plusDays(1), "motivo", false);
		
		sender.emitirEvento(bici);
		
		System.out.println("Bici enviada");
	}
}
