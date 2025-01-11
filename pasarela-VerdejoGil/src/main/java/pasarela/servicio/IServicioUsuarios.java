package pasarela.servicio;

import java.util.Map;

import pasarela.retrofit.ClaimsDTO;
import pasarela.retrofit.IdentificadorOAuth2;
import pasarela.retrofit.UsuarioContraseña;

public interface IServicioUsuarios {
	
	Map<String, Object> getClaimsConContraseña(UsuarioContraseña usuarioContraseña);
	
	ClaimsDTO getClaimsConOAuth2(IdentificadorOAuth2 identificadorOAuth2);
}
