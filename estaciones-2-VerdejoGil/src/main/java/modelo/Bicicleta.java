package modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorios.Identificable;

@Entity
@Table(name="bicicleta")
public class Bicicleta implements Identificable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Column(name="nombre")
    private String modelo;
    @Column(name = "fecha_alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;
    @Column(name = "fecha_baja", columnDefinition = "DATE")
    private LocalDate fechaBaja = null;
    @Column(name="motivo_baja")
    private String motivoBaja = null;
    @Column(name="estacion")
    private String estacion;
    @Column(name="historico")
    private String historico = null;
    private boolean isDisponible = true;


	public Bicicleta(String modelo, LocalDate fechaAlta, String estacion) {
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
	
	public String getEstacion() {
		return estacion;
	}

	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
	
	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}
	
	public boolean isDisponible() {
		return isDisponible;
	}


	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}
}
