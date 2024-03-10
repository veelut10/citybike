package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.sql.Date;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "idbicicleta")
	private String idBicicleta;
	
	@Column(name = "fecha_inicio", columnDefinition = "DATE")
	private Date fechaInicio;

	@Column(name = "fecha_fin", columnDefinition = "DATE")
	private Date fechaFin;
	
	public AlquilerEntidad(String idBicicleta, Date fechaInicio, Date fechaFin) {
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
}