package estaciones.modelo;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bicicleta")
public class Bicicleta{
	@Id
	private String id;
    private String modelo;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja = null;
    private String motivoBaja = null;
    private Estacion estacion;
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
