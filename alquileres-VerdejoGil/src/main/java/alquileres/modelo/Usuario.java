package alquileres.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import repositorio.Identificable;

public class Usuario implements Identificable{
	private String id;
	private List<Reserva> reservas = new ArrayList<Reserva>();;
	private List<Alquiler> alquileres = new ArrayList<Alquiler>();;
	
	public Usuario(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void removeReserva(Reserva reserva) {
		this.reservas.remove(reserva);
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void addAlquiler(Alquiler alquiler) {
		this.alquileres.add(alquiler);
	}
	
	public void removeAlquiler(Alquiler alquiler) {
		this.alquileres.remove(alquiler);
	}
	
	//Devuelve el número de reservas caducadas.
	public int reservasCaducadas() {
		int contador = 0;
		for(Reserva r : reservas) {
			if(r.isCaducada())
				contador++;
		}
		return contador;
	}
	
	//Devuelve el tiempo total de alquileres iniciados hoy
	public long tiempoUsoHoy() {
		long tiempoUso = 0;	
		LocalDate hoy = LocalDate.now();

		for(Alquiler a : alquileres) {
			//Compara la fecha de alquiler con hoy
			if(a.getInicio().toLocalDate().equals(hoy))
				tiempoUso += a.getTiempo();
		}
		return tiempoUso;
	}
	
	//Devuelve el tiempo total de alquileres iniciados en la ultima semana
	public long tiempoUsoSemana() {
			long tiempoUso = 0;
			LocalDate semanaPasada = LocalDate.now().minusWeeks(1);
			
			for(Alquiler a : alquileres) {
				//Compara la fecha de alquiler con hoy
				if(a.getInicio().toLocalDate().isAfter(semanaPasada))
					tiempoUso += a.getTiempo();
			}
			return tiempoUso;
	}
	
	//Comprueba que no supere el tiempo semanal o diario
	public boolean superaTiempo() {
		return (tiempoUsoHoy() >= 60 || tiempoUsoSemana() >= 180);
	}
	
	//Comprueba si hay alguna reserva activa
	public Reserva reservaActiva() {
		if(reservas.size() > 0) {
			Reserva reserva = reservas.get(reservas.size() - 1);
			if(reserva.isActiva())
				return reserva;
		}
		return null;
	}
	
	//Comprueba si hay algun alquiler activo
	public Alquiler alquilerActivo() {
		if(alquileres.size() > 0) {
			Alquiler alquiler = alquileres.get(alquileres.size() - 1);
			if(alquiler.isActivo())
				return alquiler;
		}
		return null;
	}
	
	//Comprueba si hay 3 o mas reservas caducadas
	public boolean bloqueado() {
		return reservasCaducadas() >= 3;
	}

	@Override
	public String toString() {
		String cadena = "Usuario [id=" + id + ", bloqueado =" + this.bloqueado() +", reservas=[";
		
		for(Reserva r : reservas) {
			cadena += "[IdBici=" + r.getIdBicicleta() + ", Fecha creacion=" + r.getCreada() +  ", Fecha caducidad=" + r.getCaducidad() + "]";
		}
		
		cadena += "] alquileres=[";
		
		for(Alquiler a : alquileres) {
			cadena += "[IdBici=" + a.getIdBicicleta() + ", Fecha inicio=" + a.getInicio() +  ", Fecha fin=" + a.getFin() + "]";
		}
		
		cadena += "]";
		
		return cadena;
	}
	
	
}
