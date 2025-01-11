package pasarela.rest;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pasarela.retrofit.ClaimsDTO;
import pasarela.retrofit.IdentificadorOAuth2;
import pasarela.retrofit.UsuarioContraseña;
import pasarela.servicio.IServicioUsuarios;

//http://localhost:8090/auth/oauth2 para obtener el token
@RestController
@RequestMapping("/auth")
public class PasarelaController {
	
	private IServicioUsuarios servicioUsuarios;
	
	@Autowired
	public PasarelaController(IServicioUsuarios servicio) {
		this.servicioUsuarios = servicio;
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> autenticarConContraseña( @RequestBody UsuarioContraseña usuarioContraseña) throws Exception {
		
		Map<String, Object> claimsAndToken = servicioUsuarios.getClaimsConContraseña(usuarioContraseña);
		
		if(claimsAndToken == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(claimsAndToken);
	}

	@PostMapping(value = "/oauth2")
	public void autenticarConOAuth2() throws Exception {
		
	}
}
