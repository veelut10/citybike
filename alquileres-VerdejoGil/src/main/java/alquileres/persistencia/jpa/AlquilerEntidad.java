package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="alquiler")

public class AlquilerEntidad implements Identificable,Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "idbicicleta")
	private String idBicicleta;
	
	@Column(name = "fecha_inicio", columnDefinition = "DATE")
	private LocalDate fechaInicio;

	@Column(name = "fecha_fin", columnDefinition = "DATE")
	private LocalDate fechaFin;

	public AlquilerEntidad(String id, String idBicicleta, LocalDate fechaInicio, LocalDate fechaFin) {
		this.id = id;
		this.idBicicleta = idBicicleta;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	public AlquilerEntidad() {
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

	
}
