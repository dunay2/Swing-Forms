package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import helpers.CreateButtonHelper;
import helpers.CreateFieldHelper;
import helpers.CreateRadioButtonHelper;
import modelo.UsuarioDTO;
import utilities.ProyectBundle;

public abstract class FrmUsuarioBase<T> extends JPanel implements  ActionListener, IFormulario<UsuarioDTO> {

	private Font regularFont,italicFont;
	private JLabel statusDisplay;
	// TODO elegir idioma en conexión
	private ResourceBundle rb;
	private int GAP = 10;

	private String MANDATORY_FIELDS_MSG = "MANDATORY_FIELDS_MSG";
	private String INVALID_DATE_MSG = "INVALID_DATE_MSG";

	private JPanel jpanelFields;
	private JPanel jpanelButtons;
	private JPanel jpanelRadioButtons;

	private JList<UsuarioDTO> jlistaUsuarios;
	

	protected abstract T loadRecord() throws Exception;

	protected abstract boolean validateRecord() throws Exception;

	public abstract void actionPerformed(ActionEvent e);

	public FrmUsuarioBase() {

		rb = ProyectBundle.getInstance("ES");

		// lectura mensajes de error
		MANDATORY_FIELDS_MSG = rb.getString(MANDATORY_FIELDS_MSG);
		INVALID_DATE_MSG = rb.getString(INVALID_DATE_MSG);

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		JPanel leftHalf = new JPanel() {

			// Don't allow us to stretch vertically.
			public Dimension getMaximumSize() {
				Dimension pref = getPreferredSize();
				return new Dimension(Integer.MAX_VALUE, pref.height);
			}
		};

		leftHalf.setLayout(new BoxLayout(leftHalf, BoxLayout.PAGE_AXIS));

		// Se toma un JPanel con los componentes creados según archivo properties
		jpanelFields = new CreateFieldHelper().createEntryFields();

		Component[] allTextfields = jpanelFields.getComponents();

		JComponent tf = null;

		// Se asocia los objetos jpanelFields a las variables privadas de las clases de
		// herencia
		Class<? extends FrmUsuarioBase> currentClass = getClass();
		Field[] fields = currentClass.getDeclaredFields();
		boolean foundFlag = false;

		for (int i = 0; i < allTextfields.length; i++) {
			// casteamos al tipo adecuado y buscamos en el form
			if ((allTextfields[i] instanceof JTextField)) {
				// aislamos el objeto
				tf = (JTextField) allTextfields[i];
				foundFlag = true;
			} else if ((allTextfields[i] instanceof JFormattedTextField)) {
				// aislamos el objeto
				tf = (JFormattedTextField) allTextfields[i];
				foundFlag = true;
			} else if ((allTextfields[i] instanceof JComboBox)) {
				// aislamos el objeto
				tf = (JComboBox) allTextfields[i];
				foundFlag = true;
			}

			if (foundFlag)
				for (Field field : fields) {
					// permitimos el acceso de reflection a las clases hijas
					field.setAccessible(true);
					foundFlag = false;
					// si se llaman igual comprobamos el tipo
					if (field.getName().equals(tf.getName()))
						if (field.getType().getName().equals(tf.getClass().getName())) {
							try {
								field.set(this, tf);
								break;
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
				}
		}

		leftHalf.add(jpanelFields);
		
		jpanelRadioButtons=(JPanel) new CreateRadioButtonHelper().createRadioButtons(this);
		
		leftHalf.add(jpanelRadioButtons);
		jpanelButtons = new CreateButtonHelper().createButtons(this);		
		leftHalf.add(jpanelButtons);
		
		add(leftHalf);
		add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
		add(createInfoDisplay());
		add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);


	}

//Panel derecho
	public JComponent createInfoDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
		CustomListModel listModel = new CustomListModel();

		jlistaUsuarios = new JList<UsuarioDTO>();

		// Ahora bajo el initComponents del constructor settearemos el modelo para que
		// el JList ya lo tome por defecto desde que arranquemos la aplicación:

		jlistaUsuarios.setModel(listModel);
		jlistaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		statusDisplay = new JLabel();
		statusDisplay.setHorizontalAlignment(JLabel.CENTER);
		regularFont = statusDisplay.getFont().deriveFont(Font.PLAIN, 16.0f);
		italicFont = regularFont.deriveFont(Font.ITALIC);

		// Lay out the panel.
		panel.setBorder(BorderFactory.createEmptyBorder(GAP / 2, // top
				0, // left
				GAP / 2, // bottom
				0)); // right
		panel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
		panel.add(jlistaUsuarios, BorderLayout.NORTH);
		panel.add(statusDisplay, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(200, 150));

		return panel;
	}

	public String getMANDATORY_FIELDS_MSG() {
		return this.MANDATORY_FIELDS_MSG;
	}

	public void setMANDATORY_FIELDS_MSG(String MANDATORY_FIELDS_MSG) {
		this.MANDATORY_FIELDS_MSG = MANDATORY_FIELDS_MSG;
	}

	public String getINVALID_DATE_MSG() {
		return this.INVALID_DATE_MSG;
	}

	public void setINVALID_DATE_MSG(String iNVALID_DATE_MSG) {
		this.INVALID_DATE_MSG = iNVALID_DATE_MSG;
	}

	public void setStatusDisplay(String strMessage) {
		statusDisplay.setText(strMessage);
	}

	public JPanel getJpanelFields() {
		return jpanelFields;
	}

	public void setJpanelFields(JPanel jpanelFields) {
		this.jpanelFields = jpanelFields;
	}

	public JPanel getJpanelButtons() {
		return jpanelButtons;
	}

	public void setJpanelButtons(JPanel jpanelButtons) {
		this.jpanelButtons = jpanelButtons;
	}

	public JList<UsuarioDTO> getListaUsuarios() {
		return jlistaUsuarios;
	}

	 protected void limpiaPantalla() {

		// Se asocia los objetos jpanelFields a las variables privadas de las clases de

		 // herencia
		 
		 Component[] allTextfields=jpanelFields.getComponents();
		 
		for (int i = 0; i < allTextfields.length; i++) {
			// casteamos al tipo adecuado y buscamos en el form
			if ((allTextfields[i] instanceof JTextField)) {
				// aislamos el objeto

				JTextField jTextField = (JTextField) allTextfields[i];
				jTextField.setText("");
				System.out.println("borrado campo " + jTextField.getName());

			} else if ((allTextfields[i] instanceof JFormattedTextField)) {
				// aislamos el objeto
				JFormattedTextField jFormattedTextField = (JFormattedTextField) allTextfields[i];
				jFormattedTextField.setText("");
				System.out.println("borrado campo " + jFormattedTextField.getName());

			}
		}
	}

	//public JPanel jpanelRadioButtons {
	//	return jpanelRadioButtons;
	//}

	//public void setJpanelRadioButtons(JPanel jpanelRadioButtons) {
		//this.jpanelRadioButtons = jpanelRadioButtons;
	//}

	protected JRadioButton getLangRadioButton(String strIdioma) {
		
		//TODO RECORRER PARA IDENTIFICAR EL IDIOMA
		JRadioButton jRadioButton = null;
		
		

		switch (strIdioma) {
		case "EN":
			jRadioButton = (JRadioButton) jpanelRadioButtons.getComponent(0);
			break;
		case "ES":
			jRadioButton = (JRadioButton) jpanelRadioButtons.getComponent(1);
			break;
		case "GE":
			jRadioButton = (JRadioButton) jpanelRadioButtons.getComponent(2);
			break;
		case "FR":
			jRadioButton = (JRadioButton) jpanelRadioButtons.getComponent(3);
			break;
		case "":
			jRadioButton = (JRadioButton) jpanelRadioButtons.getComponent(4);
			break;
		default:
			jRadioButton = (JRadioButton) jpanelRadioButtons.getComponent(0);
		}
		
		return jRadioButton;

	}
	
	protected void setNullRadioButton() {
		//TODO VALIDAR SELECCION CORRECTA DE BOTONERA
		Component[] jComponent = jpanelRadioButtons.getComponents();

		JRadioButton jRadioButton = null;
		
		
		for (int i = 0; i < jComponent.length; i++) {
			
			if (jComponent[i] instanceof JRadioButton )
			{
				jRadioButton = (JRadioButton)jComponent[i];
				if (jRadioButton.getName().equals("NONE"))
				{
					jRadioButton.setSelected(true);
					System.out.println("Establecido selected false: nombre radio en panel " + jComponent[i].getName());
					break;
	
				}
								
			}
						
		}



	
}
}
