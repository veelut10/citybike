package estaciones.rest.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstacionDTO {
	
	private String id;
	private String nombre;
    private LocalDate fechaAlta;
    private int numPuestos;
    private String direccion;
    private double longitud;
    private double latitud;
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

	public int getNumPuestos() {
		return numPuestos;
	}
	
	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
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
}
