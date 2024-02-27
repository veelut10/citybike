package modelo;

import java.time.LocalDateTime;

public class Reserva {
	private String idBicicleta;
	private LocalDateTime creada;
	private LocalDateTime caducidad;
	
	public Reserva(String idBicicleta, LocalDateTime creada, LocalDateTime caducidad) {
		this.idBicicleta = idBicicleta;
		this.creada = creada;
		this.caducidad = caducidad;
	}
	
	
	public String getIdBicicleta() {
		return idBicicleta;
	}


	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}


	public LocalDateTime getCreada() {
		return creada;
	}


	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}


	public LocalDateTime getCaducidad() {
		return caducidad;
	}


	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}

	//Esta caducada si el instante actual es mayor que la fecha de caducidad
	public boolean isCaducada() {
        return LocalDateTime.now().isAfter(caducidad);
    }
	
	//Esta activa si no esta caducada
	public boolean isActiva() {
		return !isCaducada();
	}
}
