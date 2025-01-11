package pasarela.retrofit;

public class ClaimsDTO {
	private String id;
	private String nombre;
	private String rol;
	
	public ClaimsDTO(String id, String nombre, String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
	}
	
	public ClaimsDTO() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
}
