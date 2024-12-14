package estaciones.eventos.bus;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import estaciones.eventos.config.RabbitMQConfig;

@Component
public class PublicadorEventos {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void emitirEvento(Object evento) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.BINDING_KEY, evento);
	}
}
