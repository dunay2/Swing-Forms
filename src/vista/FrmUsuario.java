package vista;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
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

@SuppressWarnings("serial")
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

	/**
	 * Constructor: se agrega un gestor de eventos a la lista de usuarios
	 */
	public FrmUsuario() {

		jlistaUsuarios = getListaUsuarios();
		jlistaUsuarios.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {

				if (!(evt.getValueIsAdjusting())) {
					UsuarioDTO currentRecod = new UsuarioDTO();
					CustomListModel cl = (CustomListModel) jlistaUsuarios.getModel();

					int idx = jlistaUsuarios.getSelectedIndex();
					if (idx != -1)

						currentRecod = cl.getUsuarioDTO(idx);

					logger.debug(evt.toString());

					logger.debug("***llamada a   metodo cargarCamposTexto desde evento lista***");
					cargarCamposTexto(currentRecod);

				}

			}

		});

	}

	public void valueChanged(ActionEvent e) {
		System.out.println("valor select");
	}

	/*********************************************
	 * Gestion de eventos *
	 * 
	 ********************************************/
	public void actionPerformed(ActionEvent event) {

		setStatusDisplay(event.getActionCommand());

		String accionSeleccionada = event.getActionCommand();

		logger.debug(event.getSource().toString());

		// tratamiento de radio options
		if (event.getSource() instanceof JRadioButton) {
			ProyectBundle.getInstance(accionSeleccionada);

			ProyectLocale proyectLocale = new ProyectLocale();

			proyectLocale.cambiarIdiomaTextos(getJpanelFields());
			proyectLocale.cambiarIdiomaTextos(getJpanelButtons());

			idiomaField.setText(accionSeleccionada);

		}
		// tratamiento de acciones de boton
		UsuarioDTO usuarioDTO = null;
		if (event.getSource() instanceof JButton) {
			try {

				switch (accionSeleccionada) {

				case "Create":

					usuarioDTO = loadRecord();
					validateRecord();
					crearDatos(usuarioDTO);
					agregarUsuarioALista(usuarioDTO);

					break;

				case "Delete":

					borrarDatos(usuarioField.getText());
					BorrarUsuarioDeLista(usuarioField.getText());
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

					break;

				}

			} catch (Exception actionException) {

				logger.error(actionException.getMessage());

			}
		}

		logger.debug("Estado--> " + accionSeleccionada);

	}

	/************************************************************
	 * Implementaciones de la interfaz*
	 *
	 ***********************************************************/
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

		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}
	}

	/************************************************************
	 * Fin Implementaciones de la interfaz*
	 *
	 ***********************************************************/

	/**********************************************
	 * Carga en los campos del formulario el DTO
	 * 
	 *********************************************/

	private void cargarCamposTexto(UsuarioDTO usuarioDTO) {

		logger.debug("***inicio metodo cargarCamposTexto***");

		try {
			usuarioField.setText(usuarioDTO.getNombreUsuario());
			passwordField.setText(usuarioDTO.getPassword());
			rolField.setSelectedIndex(usuarioDTO.getCodigoRol());

			if (!(usuarioDTO.getFechaAlta() == null)) {
				fechaAltaField.setText(new SimpleDateFormat("dd/MM/yyyy").format(usuarioDTO.getFechaAlta()));
			} else {
				fechaAltaField.setText("");
			}

			if (!(usuarioDTO.getFechaBaja() == null)) {
				fechaBajaField.setText(usuarioDTO.getFechaBaja().toString());
			}

			else {
				fechaBajaField.setText("");
			}

			carpetaField.setText(usuarioDTO.getCarpetaDoc());

			if (!(usuarioDTO.getIdioma() == null)) {

				String strIdioma = new String(usuarioDTO.getIdioma());
				if (!(strIdioma.isEmpty())) {

					idiomaField.setText(strIdioma);

					getLangRadioButton(strIdioma).setSelected(true);

				}
			} else {
				idiomaField.setText("");
				setNullRadioButton();

			}

		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}
		logger.debug("***fin metodo cargarCamposTexto***");
	}

	/**********************************************
	 * Carga en el DTO los datos del formulario
	 * 
	 *********************************************/
	protected UsuarioDTO loadRecord() {

		String sDate;

		logger.debug("***inicio metodo loadRecord***");

		// Se crea una instancia del bean usuario
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		// Se inicializa el objeto con los valores
		usuarioDTO.setNombreUsuario(usuarioField.getText());
		usuarioDTO.setPassword(passwordField.getText());
		usuarioDTO.setCodigoRol(Integer.parseInt(rolField.getSelectedItem().toString()));

		sDate = fechaAltaField.getText();

		if (checkDate(sDate)) {

			try {

				usuarioDTO.setFechaAlta(new SimpleDateFormat("dd/MM/yyyy").parse(sDate));

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		sDate = fechaBajaField.getText();

		if (checkDate(sDate)) {

			try {

				usuarioDTO.setFechaBaja(new SimpleDateFormat("dd/MM/yyyy").parse(sDate));

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		usuarioDTO.setCarpetaDoc(carpetaField.getText());

		if (!idiomaField.getText().isEmpty()) {
			usuarioDTO.setIdioma(idiomaField.getText().toCharArray());

		}

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

	/**
	 * Comprobar fecha
	 * 
	 */
	private boolean checkDate(String sDate) {
		boolean error = false;

		if (sDate.isEmpty()) {
			error = true;
		}

		if (!(error)) {
			String sDate2 = sDate;
			sDate2 = sDate2.replaceAll("/", "").trim();
			if (sDate2.length() > 0) {
				try {
					@SuppressWarnings("unused")
					Date d = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

				} catch (ParseException e) {
					error = true;
					// TODO
					//incluir MENSAJE DE FECHA NO VALIDA
					
					e.printStackTrace();
				}

			}

		}

		return !error;

	}

	/********************************************
	 * Lista de reglas de validacion *
	 * 
	 ********************************************/
	protected boolean validateRecord() throws Exception {

		if (usuarioField.getText().isEmpty() || passwordField.getText().isEmpty()) {

			setStatusDisplay(getMANDATORY_FIELDS_MSG());

			throw new EX_MANDATORY_FIELDS(getMANDATORY_FIELDS_MSG());

		}

		if (usuarioField.getText().trim().equals("") || passwordField.getText().trim().equals("")) {

			setStatusDisplay(getMANDATORY_FIELDS_MSG());

			throw new EX_MANDATORY_FIELDS(getMANDATORY_FIELDS_MSG());

		}

		try {

			@SuppressWarnings("unused")
			Date date = null;

			if (!(fechaAltaField.getText().isEmpty())) {

				date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaAltaField.getText());
			}

			if (!(fechaBajaField.getText().isEmpty())) {

				date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaBajaField.getText());
			}

		} catch (Exception e) {

			setStatusDisplay(getINVALID_DATE_MSG());

			throw new EX_INVALID_DATE(getINVALID_DATE_MSG());
		}

		setStatusDisplay("*****Usuario validado******");

		return true;

	}

	/*****
	 * Crea un usuario aleatorio
	 * 
	 */

	private UsuarioDTO CreateRandomUser() {

		IGestorUsuario gestorUsuario = new GestorUsuario();
		UsuarioDTO usuarioDTO = gestorUsuario.CreateRandomUser();

		cargarCamposTexto(usuarioDTO);

		return usuarioDTO;
	}


	/*****
	 * Agrega un elemento a la lista mediante el uso del modelo subyacente
	 * 
	 */
	private void agregarUsuarioALista(UsuarioDTO usuarioDTO) {

		CustomListModel customListModel = (CustomListModel) jlistaUsuarios.getModel();

		customListModel.addUsuario(usuarioDTO);
	}

	/*****
	 * Elimina un elemento de la lista de usuarios mediante el uso del modelo
	 * subyacente
	 * 
	 */
	private void BorrarUsuarioDeLista(String userName) {

		CustomListModel customListModel = (CustomListModel) jlistaUsuarios.getModel();

		customListModel.removeUsuario(userName);

	}
	// TODO No crear usuario duplicado en lista y mostrar mensaje usuario ya existe.

	// TODO Incluir navegacion bloqueando botones

}
