package estaciones.rest.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad Estacion")
public class EstacionDTO {
	
	@Schema(description = "Identificador de la Estacion")
	private String id;
	
	@Schema(description = "Nombre de la Estacion")
	@NotNull(message = "El nombre no debe ser nulo")
    @NotEmpty(message = "El nombre no debe estar vacío")
	private String nombre;
	
	@Schema(description = "Fecha de alta de la Estacion")
    private LocalDate fechaAlta;
	
	@Schema(description = "Puestos totales de la Estacion")
	@Positive(message = "El número de puestos totales debe ser mayor que cero")
    private int puestosTotales;
	
	@Schema(description = "Puestos libres de la Estacion")
    private int puestosLibres;
	
	@Schema(description = "Direccion de la Estacion")
	@NotNull(message = "La direccion no debe ser nula")
    @NotEmpty(message = "La direccion no debe estar vacía")
    private String direccion;
	
	@Schema(description = "Coordenada de longitud de la Estacion")
	@DecimalMin(value = "-180.0", message = "La longitud no debe ser menor que -180")
    @DecimalMax(value = "180.0", message = "La longitud no debe ser mayor que 180")
    private double longitud;
	
	@Schema(description = "Coordenada de latitud de la Estacion")
	@DecimalMin(value = "-90.0", message = "La latitud no debe ser menor que -90")
	@DecimalMax(value = "90.0", message = "La latitud no debe ser mayor que 90")
    private double latitud;
	
	@Schema(description = "Identificacion de las bicicletas estacionadas en la Estacion")
    private List<String> idBicicletas = new ArrayList<String>();
	
	public EstacionDTO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public int getPuestosTotales() {
		return puestosTotales;
	}
	
	public void setPuestosTotales(int puestosTotales) {
		this.puestosTotales = puestosTotales;
	}
	
	public int getPuestosLibres() {
		return puestosLibres;
	}
	
	public void setPuestosLibres(int puestosLibres) {
		this.puestosLibres = puestosLibres;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public List<String> getIdBicicletas() {
		return new ArrayList<>(idBicicletas);
	}

	public void setIdBicicletas(List<String> idBicicletas) {
		this.idBicicletas = new ArrayList<>(idBicicletas);
	}

	@Override
	public String toString() {
		return "EstacionDTO [id=" + id + ", nombre=" + nombre + ", fechaAlta=" + fechaAlta + ", puestosTotales="
				+ puestosTotales + ", puestosLibres=" + puestosLibres + ", direccion=" + direccion + ", longitud="
				+ longitud + ", latitud=" + latitud + ", idBicicletas=" + idBicicletas + "]";
	}
	
	
}
