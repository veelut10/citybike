package alquileres.eventos.modelo;

import java.time.LocalDateTime;

import alquileres.modelo.Alquiler;

public class AlquilerEvento {
	private String idBicicleta;
	private String idEstacion;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	
	
	public AlquilerEvento(Alquiler alquiler, String idEstacion) {
		this.idBicicleta = alquiler.getIdBicicleta();
		this.idEstacion = idEstacion;
		this.inicio = alquiler.getInicio();
		this.fin = alquiler.getFin();
	}
	
	public AlquilerEvento() {

	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	
}
