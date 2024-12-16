package estaciones.eventos.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import estaciones.eventos.config.RabbitMQConfig;
import estaciones.eventos.modelo.AlquilerEvento;
import estaciones.servicio.IServicioEstaciones;

//Clase que escucha los eventos de otros microservicios
@Component
public class EventListener {
	
	private IServicioEstaciones servicioEstaciones;

	@Autowired
	public EventListener(IServicioEstaciones servicioEstaciones) {
		this.servicioEstaciones = servicioEstaciones;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void handleEvent(Message mensaje) throws Exception {
		String body = new String(mensaje.getBody());
		String routingKey = mensaje.getMessageProperties().getReceivedRoutingKey();
		
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        if(routingKey.equals("citybike.alquileres.bicicleta-alquilada")) {
        	AlquilerEvento alquilerEvento = objectMapper.readValue(body, AlquilerEvento.class);
        	servicioEstaciones.cambiarEstadoBicicletaToNoDisponible(alquilerEvento.getIdBicicleta());
        }
        else if(routingKey.equals("citybike.alquileres.bicicleta-alquiler-concluido")) {
        	AlquilerEvento alquilerEvento = objectMapper.readValue(body, AlquilerEvento.class);
        	servicioEstaciones.estacionarBicicleta(alquilerEvento.getIdBicicleta(), alquilerEvento.getIdEstacion());
        }
	}
}
