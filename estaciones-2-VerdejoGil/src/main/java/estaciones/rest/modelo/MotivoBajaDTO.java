package estaciones.rest.modelo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO del motivo de la baja de una Bicicleta")
public class MotivoBajaDTO {
	
	@Schema(description = "Motivo de la baja")
	@NotNull(message = "El motivo de la baja no debe ser nulo")
    @NotEmpty(message = "El motivo de la baja no debe estar vacío")
    private String motivoBaja;

    // Getters y Setters
    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }
}
