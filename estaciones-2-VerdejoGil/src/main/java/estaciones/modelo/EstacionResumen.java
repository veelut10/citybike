package estaciones.modelo;

import java.time.LocalDate;

public class EstacionResumen {
	
	private String id;
	private String nombre;
    private LocalDate fechaAlta;
    private int numPuestos;
    private String direccion;
    private double longitud;
    private double latitud;
    private int huecosLibres;
    
    
	public EstacionResumen(String id, String nombre, LocalDate fechaAlta, int numPuestos, String direccion,
			double longitud, double latitud, int huecosLibres) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaAlta = fechaAlta;
		this.numPuestos = numPuestos;
		this.direccion = direccion;
		this.longitud = longitud;
		this.latitud = latitud;
		this.huecosLibres = huecosLibres;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public String getDireccion() {
		return direccion;
	}

	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public int isHuecosLibres() {
		return huecosLibres;
	}
}
