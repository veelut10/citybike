package modelo;

import java.time.LocalDate;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;


public class Historico{
		@BsonId
	    private String id;
		@BsonRepresentation(BsonType.OBJECT_ID)
	    private String bicicleta;
	    @BsonProperty("estacion")
	    private Estacion estacion;
	    @BsonProperty("fecha_inicio")
	    private LocalDate fechaInicio;
	    @BsonProperty("fecha_fin")
	    private LocalDate fechaFin = null;
	

	public Historico(String bicicleta, Estacion estacion, LocalDate fechaInicio) {
		this.bicicleta = bicicleta;
		this.estacion = estacion;
		this.fechaInicio = fechaInicio;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBicicleta() {
		return bicicleta;
	}
	public void setBicicleta(String bicicleta) {
		this.bicicleta = bicicleta;
	}
	public Estacion getEstacion() {
		return estacion;
	}
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
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
