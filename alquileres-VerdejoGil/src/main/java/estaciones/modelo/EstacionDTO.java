package estaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstacionDTO {
	
	private String id;
	private String nombre;
    private int puestosTotales;
    private int puestosLibres;
    private String direccion;
    private double longitud;
    private double latitud;
	
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

}
