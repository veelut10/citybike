package alquileres.rest.modelo;

import java.util.ArrayList;
import java.util.List;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;

public class UsuarioDTO {
	private String id;
	private long tiempoUsoHoy;
	private long tiempoUsoSemana;
	private boolean bloqueado;
	private List<ReservaDTO> reservas = new ArrayList<ReservaDTO>();
	private List<AlquilerDTO> alquileres = new ArrayList<AlquilerDTO>();	
	
	public UsuarioDTO() {

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTiempoUsoHoy() {
		return tiempoUsoHoy;
	}
	public void setTiempoUsoHoy(long tiempoUsoHoy) {
		this.tiempoUsoHoy = tiempoUsoHoy;
	}
	public long getTiempoUsoSemana() {
		return tiempoUsoSemana;
	}
	public void setTiempoUsoSemana(long tiempoUsoSemanal) {
		this.tiempoUsoSemana = tiempoUsoSemanal;
	}
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public List<ReservaDTO> getReservas() {
		return reservas;
	}
	public void setReservas(List<ReservaDTO> reservas) {
		this.reservas = reservas;
	}
	public List<AlquilerDTO> getAlquileres() {
		return alquileres;
	}
	public void setAlquileres(List<AlquilerDTO> alquileres) {
		this.alquileres = alquileres;
	}
}
