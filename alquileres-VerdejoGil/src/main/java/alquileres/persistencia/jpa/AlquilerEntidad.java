package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

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
	
	@Column(name = "fecha_inicio")
	private String fechaInicio;

	@Column(name = "fecha_fin")
	private String fechaFin;
	
	public AlquilerEntidad(String idBicicleta, String fechaInicio, String fechaFin) {
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

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
}