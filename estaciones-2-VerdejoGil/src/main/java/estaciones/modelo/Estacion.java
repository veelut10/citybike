package estaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="estacion")
public class Estacion{

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
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
    
	@OneToMany(cascade=CascadeType.ALL,
			   fetch = FetchType.EAGER)
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
