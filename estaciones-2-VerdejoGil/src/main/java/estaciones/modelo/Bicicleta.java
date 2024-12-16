package estaciones.modelo;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="bicicleta")
public class Bicicleta{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@Column(name="nombre")
    private String modelo;
    @Column(name = "fecha_alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;
    @Column(name = "fecha_baja", columnDefinition = "DATE")
    private LocalDate fechaBaja = null;
    @Column(name="motivo_baja")
    private String motivoBaja = null;
    
    @ManyToOne
    @JoinColumn(name = "estacion")
    private Estacion estacion;
    @Column(name = "disponible")
    private boolean isDisponible = true;


	public Bicicleta(String modelo, LocalDate fechaAlta, Estacion estacion) {
		super();
		this.modelo = modelo;
		this.fechaAlta = fechaAlta;
		this.estacion = estacion;
	}
	

	public Bicicleta() {

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}
	
	public Estacion getEstacion() {
		return estacion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
	
	public boolean isDisponible() {
		return isDisponible;
	}

	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}
	
	public void darBaja(String motivoBaja) {
		this.setMotivoBaja(motivoBaja);
        this.setFechaBaja(LocalDate.now());
        this.setEstacion(null);
        this.setDisponible(false);
	}
}
