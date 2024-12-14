package estaciones.eventos.modelo;

import java.time.LocalDate;

public class BicicletaEvento {
	
	private String id;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private String motivoBaja;
    private boolean isDisponible;
    
	public BicicletaEvento(String id, LocalDate fechaAlta, LocalDate fechaBaja, String motivoBaja,
			boolean isDisponible) {
		super();
		this.id = id;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.motivoBaja = motivoBaja;
		this.isDisponible = isDisponible;
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
