package pasarela.retrofit;


public class UsuarioContraseña {

	private String idUsuario;
	private String contraseña;
	
	public UsuarioContraseña(String idUsuario, String contraseña) {
		super();
		this.idUsuario = idUsuario;
		this.contraseña = contraseña;
	}
	
	public UsuarioContraseña() {

	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
}
