package retrofit.estaciones;

@SuppressWarnings("serial")
public class ServicioAlquileresException extends Exception{
	
	public ServicioAlquileresException(String msg) {
        super(msg);
    }

	public ServicioAlquileresException(String msg, Throwable causa) {		
		super(msg, causa);
	}
}
