package pasarela.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteUsuarios {

	@POST("api/usuarios/verificacionContraseña")
	Call<ClaimsDTO> verificarUsuarioContraseña(@Body UsuarioContraseña usuarioContraseña);

	@POST("api/usuarios/verificacionOauth2")
	Call<ClaimsDTO> verificarUsuarioOAuth2(@Body IdentificadorOAuth2 identificadorOAuth2);
}
