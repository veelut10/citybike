package retrofit.estaciones;

import estaciones.modelo.EstacionDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClienteEstaciones {
	
	@GET("estaciones/{idEstacion}")
	Call<EstacionDTO> getEstacionById(@Path("idEstacion") String idEstacion);
	
	@POST("estaciones/{idEstacion}/bicicletas/{idBicicleta}")
	Call<Void> estacionarBicicleta(@Path("idEstacion") String idEstacion, @Path("idBicicleta") String idBicicleta);
}
