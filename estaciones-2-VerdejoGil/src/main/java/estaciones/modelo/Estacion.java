package estaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estacion")
public class Estacion{

	@Id
	private String id;
	private String nombre;
    private LocalDate fechaAlta;
    private int numPuestos;
    private String direccion;
    private double longitud;
    private double latitud;
    private List<Bicicleta> bicicletas = new ArrayList<Bicicleta>();

	public Estacion(String nombre, int numPuestos, String direccion, double longitud, double latitud) {
		super();
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.direccion = direccion;
		this.longitud = longitud;
		this.latitud = latitud;
		this.fechaAlta = LocalDate.now();
	}
	
	public Estacion() {

	}

	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(String id) {
		this.id = id;
		
	}

	public String getNombre() {
		return nombre;
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
	
	public boolean hasHueco() {
		return (bicicletas.size() < numPuestos);
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

	//
	public List<Bicicleta> getBicicletas() {
		return new ArrayList<>(bicicletas);
	}


	public void setBicicletas(List<Bicicleta> bicicletas) {
		this.bicicletas = new ArrayList<>(bicicletas);
	}
	
	public boolean addBici(Bicicleta bici) {
		return bicicletas.add(bici);
	}
	
	public boolean removeBici(Bicicleta bici) {
		return bicicletas.remove(bici);
	}
}
