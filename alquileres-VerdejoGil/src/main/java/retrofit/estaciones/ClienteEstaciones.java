package retrofit.estaciones;

import estaciones.modelo.EstacionDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClienteEstaciones {
	
	@GET("{idEstacion}")
	Call<EstacionDTO> getEstacionById(@Path("idEstacion") String idEstacion);
	
	@POST("{idEstacion}/bicicletas/{idBicicleta}")
	Call<Void> estacionarBicicleta(@Path("idEstacion") String idEstacion, @Path("idBicicleta") String idBicicleta);
}
