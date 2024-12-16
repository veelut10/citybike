package estaciones.eventos.modelo;

import java.time.LocalDate;

import estaciones.modelo.Bicicleta;

public class BicicletaEvento {
	
	private String id;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private String motivoBaja;
    private boolean isDisponible;
    
	public BicicletaEvento(Bicicleta bici) {
		super();
		this.id = bici.getId();
		this.fechaAlta = bici.getFechaAlta();
		this.fechaBaja = bici.getFechaBaja();
		this.motivoBaja = bici.getMotivoBaja();
		this.isDisponible = bici.isDisponible();
	}
	
	public BicicletaEvento() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean isDisponible() {
		return isDisponible;
	}

	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}
    
}
