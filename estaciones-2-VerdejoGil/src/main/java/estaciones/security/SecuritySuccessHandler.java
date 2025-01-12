package estaciones.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/*

//Clase que recibe los datos de github de la autenticacion y crea el token
@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		
		System.out.println(usuario.getName());
		// Identificador de github
		String login = usuario.getAttribute("login");
		System.out.println(login);
		
		//Añadir los roles al token
		Map<String, Object> claims = fetchUserInfo(usuario);
		
		Date caducidad = Date.from(Instant.now().plusSeconds(3600)); //1 hora de validez
		
		String token = Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, "secreto")
				.setExpiration(caducidad)
				.compact();
		
		response.getWriter().append(token);
	}

	private Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario) {
		
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", usuario.getAttribute("login"));
		
		//Modificar para probar los roles del usuario
		claims.put("rol", "gestor");
		
		return claims;
	}
	
}
*/
