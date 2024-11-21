package alquileres.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alquiler {
	private String id;
	private String idBicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	
	public Alquiler(String idBicicleta, LocalDateTime inicio) {
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
	}

	public Alquiler() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
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
	
	//Comprueba si el alquiler esta activo
	public boolean isActivo() {
		if(fin == null)
			return true;
		else
			return false;
	}
	
	//Tiempo en minutos del alquiler
	public long getTiempo() {
		if(isActivo())
			return ChronoUnit.MINUTES.between(inicio, LocalDateTime.now());
		else
			return ChronoUnit.MINUTES.between(inicio, fin);
	}

	@Override
	public String toString() {
		return "Alquiler [id=" + id + ", idBicicleta=" + idBicicleta + ", inicio=" + inicio + ", fin=" + fin + "]";
	}
}
