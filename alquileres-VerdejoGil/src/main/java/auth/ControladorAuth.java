package auth;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("auth")
public class ControladorAuth {
	
	//curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/x-www-form-urlencoded" -d "username=juan&password=clave&rol=usuario"
	
	@POST
	@Path("/login")
	@PermitAll
	public Response login(@FormParam("username") String username, @FormParam("password") String password, @FormParam("rol") String rol) {

		Map<String, Object> claims = verificarCredenciales(username, password, rol);
		if (claims != null) {
			Date caducidad = Date.from(Instant.now().plusSeconds(3600)); // 1 hora de validez
			String token = Jwts.builder()
					.setClaims(claims)
					.signWith(SignatureAlgorithm.HS256, "secreto")
					.setExpiration(caducidad).compact();

			return Response.ok(token).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
		}
	}
	
		
	private Map<String, Object> verificarCredenciales(String username, String password, String rol) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", username);
		claims.put("roles", rol);
		
		return claims;
	}

}
