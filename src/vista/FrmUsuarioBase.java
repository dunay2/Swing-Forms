package vista;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ResourceBundle;
import javax.swing.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import exception.EX_MANDATORY_FIELDS;
import modelo.GestorUsuario;
import modelo.UsuarioDTO;
import utilities.CreateButtonHelper;
import utilities.CreateFieldHelper;
import utilities.CreateRadioButtonHelper;
import utilities.ProyectBundle;


public abstract class FrmUsuarioBase<T> extends JPanel implements ActionListener,IFormulario<UsuarioDTO>   {

	// private fields de obligada declaracion
	// deben coincidir con los valores en los ficheros properties

	private boolean addressSet = false;
	private Font regularFont, italicFont;
	private JLabel statusDisplay;
	// TODO elegir idioma en conexi�n
	private ResourceBundle rb;
	//ResourceBundle.getBundle("properties.ApplicationResources_en_EN_en");
	private int GAP = 10;
	private String currentLocal = "en";
	private String MANDATORY_FIELDS_MSG="MANDATORY_FIELDS_MSG";
	private String INVALID_DATE_MSG="INVALID_DATE_MSG";
	

	JPanel jpanelFields;

	protected abstract T loadRecord() throws Exception;
	protected abstract boolean validateRecord() throws Exception;
	
	public abstract void actionPerformed(ActionEvent e);

	public FrmUsuarioBase() {
		rb=ProyectBundle.getProyectBundle("ES");
		
		//lectura mensajes de error
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

		// Se toma un JPanel con los componentes creados seg�n archivo properties
		jpanelFields = new CreateFieldHelper(getCurrentLocal()).createEntryFields();

		Component[] allTextfields = jpanelFields.getComponents();

		JComponent tf = null;

		// Se asocia los objetos jpanelFields a las variables privadas de las clases de
		// herencia
		Class currentClass = getClass();
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
					//permitimos el acceso de reflection a las clases hijas
					field.setAccessible(true);
					foundFlag = false;
					// si se llaman igual comprobamos el tipo
					if (field.getName().equals(tf.getName()))
						if (field.getType().getName().equals(tf.getClass().getName())) {
							try {
								field.set(this, tf);
								break;
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				}
		}

		leftHalf.add(jpanelFields);
		leftHalf.add(new CreateButtonHelper(getCurrentLocal()).createButtons(this));		
		add(leftHalf);
		add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
		add(new CreateRadioButtonHelper().createRadioButtons(this));
		add(createAddressDisplay());
		
	}


//Panel derecho
	protected JComponent createAddressDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
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
		panel.add(statusDisplay, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(200, 150));

		return panel;
	}

	protected String getCurrentLocal() {
		return currentLocal;
	}

	protected void setCurrentLocal(String currentLocal) {
		this.currentLocal = currentLocal;
	}

	protected String getMANDATORY_FIELDS_MSG() {
		return this.MANDATORY_FIELDS_MSG;
	}

	protected void setMANDATORY_FIELDS_MSG(String MANDATORY_FIELDS_MSG) {
		this.MANDATORY_FIELDS_MSG = MANDATORY_FIELDS_MSG;
	}

	protected String getINVALID_DATE_MSG() {
		return INVALID_DATE_MSG;
	}

	protected void setINVALID_DATE_MSG(String iNVALID_DATE_MSG) {
		INVALID_DATE_MSG = iNVALID_DATE_MSG;
	}

	protected void setStatusDisplay(String strMessage) {
		statusDisplay.setText(strMessage);  
	}
	
}
