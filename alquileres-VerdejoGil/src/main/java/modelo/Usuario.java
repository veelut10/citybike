package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import repositorio.Identificable;

public class Usuario implements Identificable{
	private static int contador = 0;
	private String id;
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();;
	private ArrayList<Alquiler> alquileres = new ArrayList<Alquiler>();;
	
	public Usuario() {
		this.id = String.valueOf(contador);
		contador++;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void removeReserva(Reserva reserva) {
		this.reservas.remove(reserva);
	}

	public ArrayList<Alquiler> getAlquileres() {
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
		Reserva reserva = reservas.get(reservas.size() - 1);
		if(reserva.isActiva())
			return reserva;
		else
			return null;
	}
	
	//Comprueba si hay algun alquiler activo
	public Alquiler alquilerActivo() {
		Alquiler alquiler = alquileres.get(alquileres.size() - 1);
		if(alquiler.isActivo())
			return alquiler;
		else
			return null;
	}
	
	//Comprueba si hay 3 o mas reservas caducadas
	public boolean bloqueado() {
		return reservasCaducadas() >= 3;
	}
}
