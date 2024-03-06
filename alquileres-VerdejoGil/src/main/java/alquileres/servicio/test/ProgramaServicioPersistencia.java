package alquileres.servicio.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Properties;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import utils.PropertiesReader;

public class ProgramaServicioPersistencia {
	
	/**
	 * CREATE TABLE `ALQUILER` ( `ID` varchar(255), `IDBICICLETA` varchar(255) DEFAULT
	 * NULL, `FECHAINICIO` DATE DEFAULT NULL, `FECHAFIN` DATE DEFAULT NULL,
	 * PRIMARY KEY (`ID`) );
	 * 
	 */
	
	/**
	 * CREATE TABLE `RESERVA` ( `ID` varchar(255), `IDBICICLETA` varchar(255) DEFAULT
	 * NULL, `FECHACREACION` DATE DEFAULT NULL, `FECHACADUCIDAD` DATE DEFAULT NULL,
	 * PRIMARY KEY (`ID`) );
	 * 
	 */

	private static Connection getConnection() {
		try {
			PropertiesReader properties = new PropertiesReader("jdbc.properties");
			String host = properties.getProperty("host");
			String port = properties.getProperty("port");
			String user = properties.getProperty("user");
			String pass = properties.getProperty("pass");
			Properties connectionProps = new Properties();
			connectionProps.put("user", user);
			connectionProps.put("password", pass);
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/alquileres?serverTimezone="+ZoneId.systemDefault(), connectionProps);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void crearAlquiler(String id, String idBicicleta, Date fechaInicio, Date fechaFin) {
		try {
			Connection con = getConnection();			
			/*PreparedStatement stmt = con.prepareStatement(
					"INSERT into alquiler (ID, IDBICICLETA, FECHAINICIO, FECHAFIN) " + "values (?,?,?,?)");

			stmt.setString(1, id);
			stmt.setString(2, idBicicleta);
			stmt.setDate(3, new Date(fechaInicio.getTime()));
			stmt.setDate(4, new Date(fechaFin.getTime()));
			*/
			
			PreparedStatement stmt = con.prepareStatement(
					"CREATE TABLE alquiler (ID varchar(255), IDBICICLETA varchar(255) DEFAULT NULL, FECHAINICIO DATE DEFAULT NULL, FECHAFIN DATE DEFAULT NULL, PRIMARY KEY (`ID`) )");
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			crearAlquiler("1", "12", new Date(12), new Date(0));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
