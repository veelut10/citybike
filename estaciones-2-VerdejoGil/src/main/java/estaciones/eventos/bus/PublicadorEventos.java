package estaciones.eventos.bus;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import estaciones.eventos.config.RabbitMQConfig;
import estaciones.eventos.modelo.BicicletaEvento;
import estaciones.modelo.Bicicleta;

//Clase que emite los eventos al bus de datos
@Component
public class PublicadorEventos {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void emitirEventoBicicletaDesactivada(Bicicleta bicicleta) {	
		BicicletaEvento bicicletaEvento = new BicicletaEvento(bicicleta);
		
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.BINDING_KEY, bicicletaEvento);
	}
}
