package estaciones.rest.modelo;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad Bicicleta")
public class BicicletaDTO {

	@Schema(description = "Identificador de la Bicicleta")
	private String id;
	
	@Schema(description = "Modelo de la Bicicleta")
	@NotNull(message = "El modelo no debe ser nulo")
    @NotEmpty(message = "El modelo no debe estar vacío")
    private String modelo;
	
	@Schema(description = "Fecha de Alta de la Bicicleta")
    private LocalDate fechaAlta;
	
	@Schema(description = "Fecha de Baja de la Bicicleta")
    private LocalDate fechaBaja = null;
	
	@Schema(description = "Motivo de baja de la Bicicleta")
    private String motivoBaja = null;
	
	@Schema(description = "Identificador de la estacion en la que esta situada la Bicicleta")
	@NotNull(message = "El identificador de la estacion no debe ser nulo")
    @NotEmpty(message = "El identificador de la estacion no debe estar vacío")
    private String idEstacion;
	
	@Schema(description = "Comprobacion de ver si esta disponible la Bicicleta")
    private boolean isDisponible = true;

	public BicicletaDTO() {

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
	
	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}
	
	public boolean isDisponible() {
		return isDisponible;
	}

	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}
}
