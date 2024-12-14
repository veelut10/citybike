package estaciones.eventos.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import estaciones.eventos.config.RabbitMQConfig;

@Component
public class EventListener {
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void handleEvent(Message mensaje) {
		System.out.println("Mensaje recibido: " + mensaje);
		String body = new String(mensaje.getBody());
		System.out.println("Evento:" + body);
	}
}
