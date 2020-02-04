package vista;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JPanel;
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
	private ButtonGroup buttonGroup;
	

	static Logger logger = org.apache.log4j.Logger.getLogger(FrmUsuario.class);

	/**
	 * Constructor: se agrega un gestor de eventos a la lista de usuarios
	 */
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
			
			String strIdioma=new String (usuarioDTO.getIdioma());
			idiomaField.setText(strIdioma);
			
			if (!strIdioma.isEmpty())
			{
				JRadioButton jRadioButton = null;
				switch (strIdioma)
				{
				case "EN":
					jRadioButton=(JRadioButton) getJpanelRadioButtons().getComponent(0);
					break;
				case "ES":
					jRadioButton=(JRadioButton) getJpanelRadioButtons().getComponent(1);
					break;
				case "GE":
					jRadioButton=(JRadioButton) getJpanelRadioButtons().getComponent(2);
					break;
				case "FR":
					jRadioButton=(JRadioButton) getJpanelRadioButtons().getComponent(3);
					break;
				}
				
				jRadioButton.setSelected(true);
				//jbutton.isSelected();
							
			}
			else
			{
				Component[] jComponent= getJpanelRadioButtons().getComponents();
				
				JRadioButton jRadioButton=null;
				for (int i=0;i<4;i++)
				{
					jRadioButton=(JRadioButton) getJpanelRadioButtons().getComponent(i);
					jRadioButton.setSelected(false);	
					
				System.out.println("nombre radio en panel " +	getJpanelRadioButtons().getComponent(i).getName() ); 
				}
				
				for (int i=0;i<4;i++)
				{
					jRadioButton=(JRadioButton) jComponent[i];
					
					jRadioButton=(JRadioButton) getJpanelRadioButtons().getComponent(i);
					jRadioButton.setSelected(false);
					jRadioButton=null;
					
				System.out.println("nombre del radio " + jRadioButton.getName() ); 
				}
				
				
			}

			
			
			//ButtonGroup= (JRadioButton[]) getJpanelRadioButtons().getComponents();
			
			//buttonGroup.getSelection();
			
			//components[0].getText();
			//System.out.println("valor de com "  + components[0].getText());
			
		} catch (Exception actionException) {
			logger.error(actionException.getMessage());
		}

	}

	/**********************************************
	 * Carga en el DTO los datos del formulario
	 * 
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
			
				e.printStackTrace();
			}
		}

		if (!(fechaBajaField.getText().isEmpty())) {
			sDate = fechaBajaField.getText();
			try {
				usuarioDTO.setFechaBaja(new SimpleDateFormat("dd/MM/yyyy").parse(sDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		usuarioDTO.setCarpetaDoc(carpetaField.getText());

		usuarioDTO.setIdioma(idiomaField.getText().toCharArray());

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
	 * 
	 ********************************************/
	protected boolean validateRecord() throws Exception {

		if (usuarioField.getText().isEmpty() || passwordField.getText().isEmpty()) {

			throw new EX_MANDATORY_FIELDS(getMANDATORY_FIELDS_MSG());
		}

		if (usuarioField.getText().trim().equals("") || passwordField.getText().trim().equals("")) {

			throw new EX_MANDATORY_FIELDS(getMANDATORY_FIELDS_MSG());
		}

		try {
			
			@SuppressWarnings("unused")
			Date date = null;

			if (!(fechaAltaField.getText().isEmpty()))
				date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaAltaField.getText());
			if (!(fechaBajaField.getText().isEmpty()))
				date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaBajaField.getText());
		} catch (Exception e) {
			throw new EX_INVALID_DATE(getINVALID_DATE_MSG());
		}
		;

		logger.debug("*****Usuario validado******");
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
	private void BorrarUsuarioDeLista(UsuarioDTO usuarioDTO) {

		CustomListModel customListModel = (CustomListModel) jlistaUsuarios.getModel();

		customListModel.removeUsuario(usuarioDTO);
		
	}

}
