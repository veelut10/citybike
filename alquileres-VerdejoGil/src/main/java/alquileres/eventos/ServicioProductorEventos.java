package alquileres.eventos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import alquileres.eventos.modelo.AlquilerEvento;
import alquileres.modelo.Alquiler;

public class ServicioProductorEventos implements IServicioProductorEventos{

	@Override
	public void producirEventoAlquilar(Alquiler alquiler) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://user:password@rabbitmq:5672");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//Declaracion del Exchange		
		String exchangeName = "citybike";	
		String bindingKey = "citybike.alquileres.bicicleta-alquilada";
		String type = "topic";
		boolean durable = true;
		
		channel.exchangeDeclare(exchangeName, type, durable);
		
		AlquilerEvento alquilerEvento = new AlquilerEvento(alquiler, "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String alquilerJson = objectMapper.writeValueAsString(alquilerEvento);
		
		channel.basicPublish(exchangeName, bindingKey, new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), alquilerJson.getBytes());
		
		channel.close();
		connection.close();
	}

	@Override
	public void producirEventoAlquilerConcluido(Alquiler alquiler, String idEstacion) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://user:password@rabbitmq:5672");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "citybike";
		String routingKey = "citybike.alquileres.bicicleta-alquiler-concluido";
		
		AlquilerEvento alquilerEvento = new AlquilerEvento(alquiler, idEstacion);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String alquilerJson = objectMapper.writeValueAsString(alquilerEvento);
		
		channel.basicPublish(exchangeName, routingKey, new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), alquilerJson.getBytes());
		
		channel.close();
		connection.close();
		
	}
}
