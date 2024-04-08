package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorios.Identificable;

public class Estacion implements Identificable{
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String nombre;
    private LocalDate fechaAlta;
    private int numPuestos;
    private String direccion;
    private double longitud;
    private double latitud;
    
    private List<SitioTuristico> sitiosTuristicos = new ArrayList<SitioTuristico>();
    private Set<String> bicicletas = new HashSet<>();
    //Un historico por bicicleta
    private List<Historico> historico = new ArrayList<Historico>();


	public Estacion(String nombre, int numPuestos, String direccion, double longitud, double latitud, LocalDate fechaAlta) {
		super();
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.direccion = direccion;
		this.longitud = longitud;
		this.latitud = latitud;
		this.fechaAlta = fechaAlta;
	}
	

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
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

	public String getDireccion() {
		return direccion;
	}

	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}
	
	public List<SitioTuristico> getSitiosTuristicos() {
	    return new ArrayList<>(sitiosTuristicos);
	}



	public void setSitiosTuristicos(List<SitioTuristico> sitiosTuristicos) {
		this.sitiosTuristicos = new ArrayList<>(sitiosTuristicos);
	}

	//
	public Set<String> getBicicletas() {
		return new HashSet<>(bicicletas);
	}


	public void setBicicletas(Set<String> bicicletas) {
		this.bicicletas = new HashSet<>(bicicletas);
	}
	
	public List<Historico> getHistorico() {
		return new ArrayList<>(historico);
	}


	public void setHistorico(List<Historico> historico) {
		this.historico = new ArrayList<>(historico);
	}
	
	public boolean addBici(Bicicleta bici) {
		return bicicletas.add(bici.getId());
	}
	
	public boolean removeBici(Bicicleta bici) {
		return bicicletas.remove(bici.getId());
	}

	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", fechaAlta=" + fechaAlta + ", numPuestos=" + numPuestos
				+ ", direccion=" + direccion + ", longitud=" + longitud + ", latitud=" + latitud + ", sitiosTuristicos="
				+ sitiosTuristicos + "]";
	}
	
	
    
}
