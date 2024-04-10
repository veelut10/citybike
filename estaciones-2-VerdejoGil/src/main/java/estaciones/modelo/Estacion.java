package estaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estacion{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="nombre")
	private String nombre;
	@Column(name = "fecha_alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;
	@Column(name="num_puestos")
    private int numPuestos;
	@Column(name="direccion")
    private String direccion;
	@Column(name="longitud")
    private double longitud;
	@Column(name="latitud")
    private double latitud;
    
    @OneToMany(mappedBy = "estacion")
    private List<Bicicleta> bicicletas = new ArrayList<Bicicleta>();

	public Estacion(String nombre, int numPuestos, String direccion, double longitud, double latitud, LocalDate fechaAlta) {
		super();
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.direccion = direccion;
		this.longitud = longitud;
		this.latitud = latitud;
		this.fechaAlta = fechaAlta;
	}
	
	public Estacion() {

	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Integer id) {
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
