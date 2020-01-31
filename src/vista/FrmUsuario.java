package vista;

import java.awt.event.*;
import java.sql.Date;
import javax.swing.*;
import org.apache.log4j.Logger;
import exception.EX_INVALID_DATE;
import exception.EX_MANDATORY_FIELDS;
import modelo.GestorUsuario;
import modelo.IGestorUsuario;
import modelo.UsuarioDTO;

/**
 * Based on TextInputDemo.java uses these additional files: SpringUtilities.java
 * ...
 */

public class FrmUsuario extends FrmUsuarioBase<UsuarioDTO> {
	public FrmUsuario() {
	}

	/*************************************************************
	 * private fields DE OBLIGADA DECLARACION. Deben coincidir con los valores en
	 * los ficheros properties *
	 *************************************************************/

	private JTextField usuarioField;
	private JTextField passwordField;
	private JTextField carpetaField;
	private JTextField idiomaField;
	private JFormattedTextField fechaAltaField;
	private JFormattedTextField  fechaBajaField;
	private JComboBox<String> rolField;

	JPanel jpanelFields;

	static Logger logger = org.apache.log4j.Logger.getLogger(FrmUsuario.class);

	/*********************************************
	 * Gestion de eventos *
	 ********************************************/
	public void actionPerformed(ActionEvent e) {
		
		setStatusDisplay(e.getActionCommand());
		
		String accionSeleccionada = e.getActionCommand();

		try {
			validateRecord();

			UsuarioDTO usuarioDTO = loadRecord();

			//TODO separar las acciones de botones de las acciones de combo
			//realizar validaciones solo cuando el registro se va a guardar
			//TODO Cambiar el texto de los botones y de las etiquetas
			switch (accionSeleccionada) {
			case "Create":
				crearDatos(usuarioDTO);
				break;

			case "Delete":
				borrarDatos(usuarioDTO);
				break;

			case "Search":
				buscarDatos(usuarioDTO);

				break;

			case "modify":
				modificarDatos(usuarioDTO);
				break;
				
				
			case "EN":
				break;
			case "ES":
				break;
			case "GE":
				break;
			case "FR":
				break;
				
			}

		} catch (Exception actionException) {
			
			logger.error(actionException.getMessage());

		}
		logger.debug("Estado--> " + accionSeleccionada);
	
	}

	/* crear registro */
	@Override
	public void crearDatos(UsuarioDTO usuarioDTO) {

		IGestorUsuario gestorUsuario = new GestorUsuario();
		gestorUsuario.altaUsuario(usuarioDTO);

	}

	/* modifica registro */
	@Override
	public void modificarDatos(UsuarioDTO usuarioDTO) {
		
		IGestorUsuario gestorUsuario = new GestorUsuario();
		gestorUsuario.modificaUsuario(usuarioDTO);

	}

	/* borrar registro */
	@Override
	public void borrarDatos(UsuarioDTO usuarioDTO) {

		IGestorUsuario gestorUsuario = new GestorUsuario();
		gestorUsuario.bajaUsuario(usuarioDTO);

	}

	/* buscar registro */
	@Override
	public void buscarDatos(UsuarioDTO usuarioDTO) throws Exception {
		try {
			
			UsuarioDTO auxUsuarioDTO;
			
			IGestorUsuario gestorUsuario = new GestorUsuario();
			auxUsuarioDTO=gestorUsuario.getUsuario(usuarioDTO);
			
			usuarioField.setText(auxUsuarioDTO.getNombreUsuario());			
			passwordField.setText(auxUsuarioDTO.getPassword());
			rolField.setSelectedIndex(auxUsuarioDTO.getCodigoRol());
			fechaAltaField.setText(auxUsuarioDTO.getFechaAlta().toString());
			fechaBajaField.setText(auxUsuarioDTO.getFechaBaja().toString());
			carpetaField.setText(auxUsuarioDTO.getCarpetaDoc());
			idiomaField.setText(auxUsuarioDTO.getIdioma().toString());
			//TODO agregar lógica para gestionar el radiobutton
		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}
	}

	/**********************************************
	 * Carga del DTO con los datos del formulario
	 *********************************************/
	protected UsuarioDTO loadRecord() {
		logger.debug("inicio loadRecord");

		// Se crea una instancia del bean usuario
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		// Se inicializa el objeto con los valores
		usuarioDTO.setNombreUsuario(usuarioField.getText());
		usuarioDTO.setPassword(passwordField.getText());
		usuarioDTO.setCodigoRol(Integer.parseInt(rolField.getSelectedItem().toString()) );
		
		if (!(fechaAltaField.getText().isEmpty())) {
			usuarioDTO.setFechaAlta(Date.valueOf(fechaAltaField.getText()));
		}

		if (!(fechaBajaField.getText().isEmpty())) {
			usuarioDTO.setFechaBaja(Date.valueOf(fechaBajaField.getText()));
		}

		usuarioDTO.setCarpetaDoc(carpetaField.getText());

		// usuarioDTO.setIdioma();

		// TODO usuarioDTO.setIdioma(idioma);
		// DEBUG***************************
		logger.debug("*****Se muestra los datos de usuario capturados");
		logger.debug(usuarioDTO.getNombreUsuario());
		logger.debug(usuarioDTO.getPassword());
		logger.debug(usuarioDTO.getCodigoRol());
		logger.debug(usuarioDTO.getFechaAlta());
		logger.debug(usuarioDTO.getFechaBaja());
		logger.debug(usuarioDTO.getCarpetaDoc());
		logger.debug(usuarioDTO.getIdioma());

		logger.debug("*****Fin mostrar");
		/* DEBUG ****************************/

		return usuarioDTO;
	}

	/********************************************
	 * Lista de reglas de validacion *
	 ********************************************/
	protected boolean validateRecord() throws Exception {

		if (usuarioField.getText().isEmpty() || passwordField.getText().isEmpty()) {

			throw new EX_MANDATORY_FIELDS(getMANDATORY_FIELDS_MSG());
		}

		if (usuarioField.getText().trim().equals("") || passwordField.getText().trim().equals("")) {

			throw new EX_MANDATORY_FIELDS(getMANDATORY_FIELDS_MSG());
		}

		try {
			Date d = null;
			;
			if (!(fechaAltaField.getText().isEmpty()))
				d = Date.valueOf(fechaAltaField.getText());
			if (!(fechaBajaField.getText().isEmpty()))
				d = Date.valueOf(fechaBajaField.getText());
		} catch (Exception e) {
			throw new EX_INVALID_DATE(getINVALID_DATE_MSG());
		}
		;

		logger.debug("*****Usuario validado******");
		return true;
		// TODO validacion para fechas
		// Validacion idioma
	}

}
