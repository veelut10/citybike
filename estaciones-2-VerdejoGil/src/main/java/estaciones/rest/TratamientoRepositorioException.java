package estaciones.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import repositorio.RepositorioException;

public class TratamientoRepositorioException {
	
	@ExceptionHandler(RepositorioException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RespuestaError handleGlobalException(RepositorioException ex) {
		return new RespuestaError("Internal Server Error", ex.getMessage());
	}
	
	private static class RespuestaError {
		private String estado;
		private String mensaje;
		
		public RespuestaError(String estado, String mensaje) {
			this.estado = estado;
			this.mensaje = mensaje;
		}
	}
}
