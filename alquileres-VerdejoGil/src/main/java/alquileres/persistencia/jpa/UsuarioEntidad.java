package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="usuario")
public class UsuarioEntidad implements Identificable {

	@Id
    private String id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)	
    private List<ReservaEntidad> reservas;

    
    @OneToMany(cascade = CascadeType.ALL)    
    private List<AlquilerEntidad> alquileres;

    public UsuarioEntidad() {
	}
    
	public UsuarioEntidad(String id, List<ReservaEntidad> reservas, List<AlquilerEntidad> alquileres) {
		super();
		this.id = id;
		this.reservas = reservas;
		this.alquileres = alquileres;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public List<AlquilerEntidad> getAlquileres() {
		return this.alquileres;
	}
	
	public void setAlquileres(List<AlquilerEntidad> alquileres) {
		this.alquileres = alquileres;
	}

	public List<ReservaEntidad> getReservas() {
		return this.reservas;
	}
	
	public void setReservas(List<ReservaEntidad> reservas) {
		this.reservas = reservas;
	}
}
