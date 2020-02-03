package vista;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import org.apache.log4j.Logger;
import exception.EX_INVALID_DATE;
import exception.EX_MANDATORY_FIELDS;
import modelo.GestorUsuario;
import modelo.IGestorUsuario;
import modelo.UsuarioDTO;
import utilities.ProyectBundle;
import utilities.ProyectLocale;

/**
 * Based on TextInputDemo.java uses these additional files: SpringUtilities.java
 * ...
 */

public class FrmUsuario extends FrmUsuarioBase<UsuarioDTO> {
	public FrmUsuario() {
		cargarLista();
	}

	/*************************************************************
	 * private fields DE OBLIGADA DECLARACION. 
	 * Deben coincidir con los valores en
	 * los ficheros properties *
	 *************************************************************/

	private JTextField usuarioField;
	private JTextField passwordField;
	private JTextField carpetaField;
	private JTextField idiomaField;
	private JFormattedTextField fechaAltaField;
	private JFormattedTextField fechaBajaField;
	private JComboBox<String> rolField;
	private CustomListModel listModel;

	static Logger logger = org.apache.log4j.Logger.getLogger(FrmUsuario.class);
//TODO  
	/* GESTIONAR RADIO BUTTONS AL REALIZAR CARGA
	 * GUARDAR DATOS EN DISCO
	 * LEER DATOS DE DISCO A LISTA
	 * SELECCIONAR DE LISTA Y CARGAR EN FORM
	 * BORRAR POR NOMBRE
	 * MODIFICAR POR NOMBRE
	 */
	/*********************************************
	 * Gestion de eventos *
	 ********************************************/
	public void actionPerformed(ActionEvent e) {

		setStatusDisplay(e.getActionCommand());

		String accionSeleccionada = e.getActionCommand();

		logger.debug(e.getSource().toString());

		// tratamiento de radio options
		if (e.getSource() instanceof JRadioButton) {
			ProyectBundle.getInstance(accionSeleccionada);

			ProyectLocale c = new ProyectLocale();
			c.cambiarIdiomaTextos(getJpanelFields());
			c.cambiarIdiomaTextos(getJpanelButtons());

		}
		// tratamiento de acciones de boton
		UsuarioDTO usuarioDTO = null;
		if (e.getSource() instanceof JButton) {
			try {
							
				switch (accionSeleccionada) {
				case "Create":
					usuarioDTO = loadRecord();
					validateRecord();
					crearDatos(usuarioDTO);
					break;

				case "Delete":
					borrarDatos(usuarioField.getText());
					break;

				case "Search":
					buscarDatos(usuarioDTO);

					break;

				case "modify":
					validateRecord();
					modificarDatos(usuarioDTO);
					break;

				case "CreateRandomUser":
					CreateRandomUser();
				}

			} catch (Exception actionException) {

				logger.error(actionException.getMessage());

			}
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
	public void borrarDatos(String userName) {

		IGestorUsuario gestorUsuario = new GestorUsuario();
		gestorUsuario.bajaUsuario(userName);

	}

	/* buscar registro */
	@Override
	public void buscarDatos(UsuarioDTO usuarioDTO) throws Exception {
		try {

			UsuarioDTO auxUsuarioDTO;

			IGestorUsuario gestorUsuario = new GestorUsuario();
			auxUsuarioDTO = gestorUsuario.getUsuario(usuarioDTO.getNombreUsuario());

			cargarCamposTexto(auxUsuarioDTO);

			// TODO agregar lógica para gestionar el radiobutton
		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}
	}

	private void cargarLista() {
		IGestorUsuario gestorUsuario = new GestorUsuario();
		gestorUsuario.getUsuarios();

		listModel = getListModel();

		for (int i = 0; i < gestorUsuario.getUsuarios().size() - 1; i++) {
			listModel.addUsuario(gestorUsuario.getUsuarios().get(i));
		}

	}

	private void cargarCamposTexto(UsuarioDTO usuarioDTO)

	{

		try {
			usuarioField.setText(usuarioDTO.getNombreUsuario());
			passwordField.setText(usuarioDTO.getPassword());
			rolField.setSelectedIndex(usuarioDTO.getCodigoRol());

			fechaAltaField.setText(new SimpleDateFormat("dd/MM/yyyy").format(usuarioDTO.getFechaAlta()));

			fechaBajaField.setText(usuarioDTO.getFechaBaja().toString());
			carpetaField.setText(usuarioDTO.getCarpetaDoc());
			idiomaField.setText(usuarioDTO.getIdioma().toString());
			// TODO agregar lógica para gestionar el radiobutton
		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}

	}

	/**********************************************
	 * Carga del DTO con los datos del formulario
	 *********************************************/
	protected UsuarioDTO loadRecord() {

		String sDate;

		logger.debug("inicio loadRecord");

		// Se crea una instancia del bean usuario
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		// Se inicializa el objeto con los valores
		usuarioDTO.setNombreUsuario(usuarioField.getText());
		usuarioDTO.setPassword(passwordField.getText());
		usuarioDTO.setCodigoRol(Integer.parseInt(rolField.getSelectedItem().toString()));

		if (!(fechaAltaField.getText().isEmpty())) {

			sDate = fechaAltaField.getText();
			try {
				usuarioDTO.setFechaAlta(new SimpleDateFormat("dd/MM/yyyy").parse(sDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!(fechaBajaField.getText().isEmpty())) {
			sDate = fechaBajaField.getText();
			try {
				usuarioDTO.setFechaBaja(new SimpleDateFormat("dd/MM/yyyy").parse(sDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
				d = new SimpleDateFormat("dd/MM/yyyy").parse(fechaAltaField.getText());
			if (!(fechaBajaField.getText().isEmpty()))
				d = new SimpleDateFormat("dd/MM/yyyy").parse(fechaBajaField.getText());
		} catch (Exception e) {
			throw new EX_INVALID_DATE(getINVALID_DATE_MSG());
		}
		;

		logger.debug("*****Usuario validado******");
		return true;
		// TODO validacion para fechas
		// Validacion idioma
	}

	private void CreateRandomUser() {

		IGestorUsuario gestorUsuario = new GestorUsuario();
		UsuarioDTO usuarioDTO = gestorUsuario.CreateRandomUser();

		cargarCamposTexto(usuarioDTO);
	}

}
