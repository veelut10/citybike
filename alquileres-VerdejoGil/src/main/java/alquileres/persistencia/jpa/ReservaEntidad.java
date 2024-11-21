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
@Table(name = "reserva")
public class ReservaEntidad implements Identificable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "idbicicleta")
	private String idBicicleta;

	@Column(name = "fecha_creacion")
	private String fechaCreacion;

	@Column(name = "fecha_caducidad")
	private String fechaCaducidad;

	public ReservaEntidad(String id, String idBicicleta, String fechaCreacion, String fechaCaducidad) {
		this.id = id;
		this.idBicicleta = idBicicleta;
		this.fechaCreacion = fechaCreacion;
		this.fechaCaducidad = fechaCaducidad;
	}
	
	public ReservaEntidad() {
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

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
}
