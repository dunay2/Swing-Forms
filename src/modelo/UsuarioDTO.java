package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ashh412
 * DTO Objeto usuario
 *
 */
public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9141649440410051879L;
	private String nombreUsuario;
	private String password;
	private int codigoRol;
	private Date fechaAlta;
	private Date fechaBaja;
	private String carpetaDoc;
	private char[] idioma ;
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCodigoRol() {
		return codigoRol;
	}
	public void setCodigoRol(int codigoRol) {
		this.codigoRol = codigoRol;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getCarpetaDoc() {
		return carpetaDoc;
	}
	public void setCarpetaDoc(String carpetaDoc) {
		this.carpetaDoc = carpetaDoc;
	}
	public char[] getIdioma() {
		return idioma;
	}
	public void setIdioma(char[] idioma) {
		this.idioma = idioma;
	}
	
	
}
