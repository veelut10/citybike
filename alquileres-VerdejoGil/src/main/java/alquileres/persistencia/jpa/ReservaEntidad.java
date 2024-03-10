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
@Table(name = "reserva")
public class ReservaEntidad implements Identificable, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "idbicicleta")
	private String idBicicleta;

	@Column(name = "fecha_creacion", columnDefinition = "DATE")
	private Date fechaCreacion;

	@Column(name = "fecha_caducidad", columnDefinition = "DATE")
	private Date fechaCaducidad;

	public ReservaEntidad(String id, String idBicicleta, Date fechaCreacion, Date fechaCaducidad) {
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
}
