package alquileres.eventos;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


import servicio.FactoriaServicios;

//Servlet encargado de inicializar el servicio consumidor de eventos
@WebListener
public class ServletConsumidoEventos implements ServletContextListener{
	
	private IServicioConsumidorEventos consumidorEventos;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		consumidorEventos = FactoriaServicios.getServicio(IServicioConsumidorEventos.class);
		try {
			consumidorEventos.handleEvent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
