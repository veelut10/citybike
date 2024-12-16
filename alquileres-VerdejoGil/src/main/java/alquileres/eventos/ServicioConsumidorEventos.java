package alquileres.eventos;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import alquileres.eventos.modelo.BicicletaEvento;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import alquileres.repositorio.RepositorioUsuario;
import alquileres.servicio.IServicioAlquileres;
import alquileres.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioConsumidorEventos implements IServicioConsumidorEventos{
	
	private IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Override
	public void handleEvent() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://mcnwuwgv:sx-pRl9UgCjXZGjttD9tyFjp6mw6CZZp@rat.rmq2.cloudamqp.com/mcnwuwgv");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//Declaracion de cola y enlace con el exchange
		final String exchangeName = "citybike";
		final String queueName = "citybike-alquileres";
		final String bindingKey = "citybike.estaciones.*";
		final String BICICLETA_DESACTIVADA = "citybike.estaciones.bicicleta-desactivada";

		boolean durable = true;
		boolean exclusive = false;
		boolean autodelete = false;
		Map<String, Object> properties = null; // sin propiedades
		channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);
		
		channel.queueBind(queueName, exchangeName, bindingKey);

		//Configuracion del Consumidor
		boolean autoAck = false;
		String etiquetaConsumidor = "citybike-consumidor";
		
		//Se encarga de recibir los eventos y hacer con ellos lo que deba
		channel.basicConsume(queueName, autoAck, etiquetaConsumidor,
				new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body) throws IOException {
						String routingKey = envelope.getRoutingKey();
						String contentType = properties.getContentType();
						long deliveryTag = envelope.getDeliveryTag();
						
						//Recibir bicicleta-desactivada de microservicio estaciones
						if(routingKey.equals(BICICLETA_DESACTIVADA)) {
							
							//Transformar de JSON a objeto
							String contenido = new String(body);
							
							ObjectMapper objectMapper = new ObjectMapper();
							
							objectMapper.registerModule(new JavaTimeModule()); // Para manejar fechas si usas LocalDate/LocalDateTime
							objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);		
							
							BicicletaEvento bici = objectMapper.readValue(contenido, BicicletaEvento.class);
					
							try {
								servicioAlquileres.buscarAndEliminarReservasActivasDeBicicleta(bici.getId());
							} catch (RepositorioException | EntidadNoEncontrada e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}	
						// Confirma el procesamiento
						channel.basicAck(deliveryTag, false);
					}
				});

	}
}
