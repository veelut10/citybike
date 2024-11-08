package alquileres.servicio;

import java.util.HashMap;
import java.util.Map;

public class ServicioUsuarios implements IServicioUsuarios{
	
	//El rol es implementado por el servicio y no al hacer el login
	@Override
	public Map<String, Object> verificarCredenciales(String username, String password) {
		Map<String, Object> claims = new HashMap<String, Object>();
		if(username.equals("usuario")) {
			claims.put("sub", username);
			claims.put("roles", "usuario");
		}
		else if(username.equals("gestor")) {
			claims.put("sub", username);
			claims.put("roles", "gestor");
		}
		else {
			return null;
		}
		return claims;
	}
}
