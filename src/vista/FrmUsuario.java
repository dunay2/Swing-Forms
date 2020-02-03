package vista;

import java.awt.Component;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

	/*************************************************************
	 * private fields DE OBLIGADA DECLARACION. Deben coincidir con los valores en
	 * los ficheros properties *
	 *************************************************************/

	private JTextField usuarioField;
	private JTextField passwordField;
	private JTextField carpetaField;
	private JTextField idiomaField;
	private JFormattedTextField fechaAltaField;
	private JFormattedTextField fechaBajaField;
	private JComboBox<String> rolField;
	private JList<UsuarioDTO> jlistaUsuarios;

	static Logger logger = org.apache.log4j.Logger.getLogger(FrmUsuario.class);

	public FrmUsuario() {
		jlistaUsuarios = getListaUsuarios();

		jlistaUsuarios.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				UsuarioDTO currentRecod = new UsuarioDTO();
				CustomListModel cl = (CustomListModel) jlistaUsuarios.getModel();

				int idx = jlistaUsuarios.getSelectedIndex();
				if (idx != -1)

					currentRecod = cl.getUsuarioDTO(idx);
				cargarCamposTexto(currentRecod);
				System.out.println("Current selection: " + idx);

			}

		});

	}

	public void valueChanged(ActionEvent e) {
		System.out.println("valor select");
	}

//TODO  
	/*
	 * GESTIONAR RADIO BUTTONS AL REALIZAR CARGA GUARDAR DATOS EN DISCO LEER DATOS
	 * DE DISCO A LISTA SELECCIONAR DE LISTA Y CARGAR EN FORM BORRAR POR NOMBRE
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
					agregarUsuarioALista(usuarioDTO);

					break;

				case "Delete":
					usuarioDTO = loadRecord();
					borrarDatos(usuarioField.getText());
					BorrarUsuarioDeLista(usuarioDTO);
					limpiaPantalla();
					break;

				case "Search":
					usuarioDTO = loadRecord();
					buscarDatos(usuarioDTO);

					break;

				case "Modify":
					validateRecord();
					usuarioDTO = loadRecord();
					modificarDatos(usuarioDTO);
					break;

				case "CreateRandomUser":
					CreateRandomUser();
					usuarioDTO = loadRecord();
					crearDatos(usuarioDTO);
					agregarUsuarioALista(usuarioDTO);

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

			limpiaPantalla();
			cargarCamposTexto(auxUsuarioDTO);

			// TODO agregar lógica para gestionar el radiobutton
		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}
	}

//Carga los datos del usuario en pantalla 
	private void cargarCamposTexto(UsuarioDTO usuarioDTO)

	{

		try {
			usuarioField.setText(usuarioDTO.getNombreUsuario());
			passwordField.setText(usuarioDTO.getPassword());
			rolField.setSelectedIndex(usuarioDTO.getCodigoRol());

			fechaAltaField.setText(new SimpleDateFormat("dd/MM/yyyy").format(usuarioDTO.getFechaAlta()));

			if (usuarioDTO.getFechaBaja() == null)
				fechaBajaField.setText("");
			else {
				fechaBajaField.setText(usuarioDTO.getFechaBaja().toString());
			}

			carpetaField.setText(usuarioDTO.getCarpetaDoc());
			// idiomaField.setText(usuarioDTO.getIdioma().toString());
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

	private UsuarioDTO CreateRandomUser() {

		IGestorUsuario gestorUsuario = new GestorUsuario();
		UsuarioDTO usuarioDTO = gestorUsuario.CreateRandomUser();

		cargarCamposTexto(usuarioDTO);

		return usuarioDTO;
	}

	private void agregarUsuarioALista(UsuarioDTO usuarioDTO) {

		CustomListModel cl = (CustomListModel) jlistaUsuarios.getModel();

		cl.addUsuario(usuarioDTO);
	}

	private void BorrarUsuarioDeLista(UsuarioDTO usuarioDTO) {

		CustomListModel cl = (CustomListModel) jlistaUsuarios.getModel();

		cl.removeUsuario(usuarioDTO);

	}

	private void limpiaPantalla() {

		Component[] allTextfields = getJpanelFields().getComponents();

		// Se asocia los objetos jpanelFields a las variables privadas de las clases de
		// herencia

		for (int i = 0; i < allTextfields.length; i++) {
			// casteamos al tipo adecuado y buscamos en el form
			if ((allTextfields[i] instanceof JTextField)) {
				// aislamos el objeto

				JTextField p = (JTextField) allTextfields[i];
				p.setText("");

			} else if ((allTextfields[i] instanceof JFormattedTextField)) {
				// aislamos el objeto
				JFormattedTextField p = (JFormattedTextField) allTextfields[i];
				p.setText("");

			}
		}
	}

}
