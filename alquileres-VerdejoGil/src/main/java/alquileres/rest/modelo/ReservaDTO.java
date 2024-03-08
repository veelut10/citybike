package alquileres.rest.modelo;


public class ReservaDTO {
	private String idBicicleta;
	private String fechaCreada;
	private String fechaCaducidad;
	private boolean caducada;
	
	public ReservaDTO() {

	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public String getFechaCreada() {
		return fechaCreada;
	}

	public void setFechaCreada(String fechaCreada) {
		this.fechaCreada = fechaCreada;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public boolean isCaducada() {
		return caducada;
	}

	public void setCaducada(boolean caducada) {
		this.caducada = caducada;
	}
	
	
}
