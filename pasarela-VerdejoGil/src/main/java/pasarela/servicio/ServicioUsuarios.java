package pasarela.servicio;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.retrofit.ClaimsDTO;
import pasarela.retrofit.ClienteUsuarios;
import pasarela.retrofit.IdentificadorOAuth2;
import pasarela.retrofit.UsuarioContraseña;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class ServicioUsuarios implements IServicioUsuarios {

	ClienteUsuarios clienteUsuarios;
	
	@Override
	public Map<String, Object> getClaimsConContraseña(UsuarioContraseña usuarioContraseña){
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://usuarios:5000/api/usuarios/").addConverterFactory(GsonConverterFactory.create()).build();
		
		clienteUsuarios = retrofit.create(ClienteUsuarios.class);
		
		// Obtener los claims del servicio usuarios
		ClaimsDTO claimsDTO = null;
		try {
			claimsDTO = clienteUsuarios.verificarUsuarioContraseña(usuarioContraseña).execute().body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(claimsDTO == null) {
        	return null;
        }
		
		// Crear el token y el JSON con los claims y el token
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
		
        return claims;
	}

	@Override
	public ClaimsDTO getClaimsConOAuth2(IdentificadorOAuth2 identificadorOAuth2){
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://usuarios:5000/api/usuarios/").addConverterFactory(GsonConverterFactory.create()).build();
		
		clienteUsuarios = retrofit.create(ClienteUsuarios.class);
		
		ClaimsDTO claims = null;
		try {
			claims = clienteUsuarios.verificarUsuarioOAuth2(identificadorOAuth2).execute().body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return claims;
	}
	
	

}
