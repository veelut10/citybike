package pasarela.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.retrofit.ClaimsDTO;
import pasarela.retrofit.IdentificadorOAuth2;
import pasarela.servicio.IServicioUsuarios;


//Clase que recibe los datos de github de la autenticacion y crea el token
@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	
	private IServicioUsuarios servicioUsuarios;
	
	@Autowired
	public SecuritySuccessHandler(IServicioUsuarios servicio) {
		this.servicioUsuarios = servicio;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		
		IdentificadorOAuth2 identificadorOAuth2 = new IdentificadorOAuth2();
		identificadorOAuth2.setoAuth2Id(usuario.getName());
		
		System.out.println(identificadorOAuth2.getoAuth2Id());
		
		// Obtiene los claims del servicio usuario con el identificador de github
		ClaimsDTO claimsDTO = servicioUsuarios.getClaimsConOAuth2(identificadorOAuth2);
		
		if(claimsDTO != null) {
			
			//Crea el token de github con los claims 
			Map<String , Object> claims = new HashMap<String, Object>();
			claims.put("id", claimsDTO.getId());
			claims.put("nombre", claimsDTO.getNombre());
			claims.put("rol", claimsDTO.getRol());
			
			Date caducidad = Date.from(Instant.now().plusSeconds(3600)); //1 hora de validez
			
			String token = Jwts.builder()
					.setClaims(claims)
					.signWith(SignatureAlgorithm.HS256, "secreto")
					.setExpiration(caducidad)
					.compact();
			
			claims.put("token", token);
			
			//Convertir los claims en JSON para enviarlo
			response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        ObjectMapper objectMapper = new ObjectMapper();
	        String claimsJSON = objectMapper.writeValueAsString(claims);

	        // Escribir el JSON en el cuerpo de la respuesta
	        response.getWriter().append(claimsJSON);
		}
		else
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "El usuario buscado no esta dado de alta");
	}
}
