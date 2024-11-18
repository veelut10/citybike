package estaciones.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		// Comprueba que la petición lleve el token JWT y lo valida ...
		String authorization = request.getHeader("Authorization");

		// Establece el contexto de seguridad
		if (authorization != null && authorization.startsWith("Bearer ")) {
			String token = authorization.substring("Bearer ".length()).trim();
			
			//Obtener claims del token
			Claims claims = Jwts.parser().setSigningKey("secreto").parseClaimsJws(token).getBody();
			request.setAttribute("claims", claims);
			
			//Caducidad del token
			Date caducidad = claims.getExpiration();
			
			//Usuario del token
			String usuario = (String) claims.get("sub");
			
			//Comprobar si está caducado
			if (caducidad.before(new Date()))
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT ha expirado");
			
			String[] roles = claims.get("roles", String.class).split(",");
			ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			for(String rol : roles) 
				authorities.add(new SimpleGrantedAuthority(rol));
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
			// Establecemos la autenticación en el contexto de seguridad
			// Se interpreta como que el usuario ha superado la autenticación
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		
		filterChain.doFilter(request, response);
	}

}
