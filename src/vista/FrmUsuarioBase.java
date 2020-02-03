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
import helpers.CreateButtonHelper;
import helpers.CreateFieldHelper;
import helpers.CreateRadioButtonHelper;
import modelo.GestorUsuario;
import modelo.UsuarioDTO;
import utilities.ProyectBundle;


public abstract class FrmUsuarioBase<T> extends JPanel implements ActionListener,IFormulario<UsuarioDTO>   {

	private Font regularFont, italicFont;
	private JLabel statusDisplay;
	// TODO elegir idioma en conexión
	private ResourceBundle rb;
	private int GAP = 10;
	
	
	private String MANDATORY_FIELDS_MSG="MANDATORY_FIELDS_MSG";
	private String INVALID_DATE_MSG="INVALID_DATE_MSG";
	
	private JPanel jpanelFields;
	private JPanel jpanelButtons;
	
	
	private CustomListModel listModel;
	
	
	protected abstract T loadRecord() throws Exception;
	protected abstract boolean validateRecord() throws Exception;
	
	public abstract void actionPerformed(ActionEvent e);

	public FrmUsuarioBase() {
		
		rb=ProyectBundle.getInstance("ES");
		
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

		// Se toma un JPanel con los componentes creados según archivo properties
		jpanelFields = new CreateFieldHelper().createEntryFields();

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
		
		jpanelButtons=new CreateButtonHelper().createButtons(this);
		
		leftHalf.add(jpanelButtons);		
		add(leftHalf);
		add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
		add(createInfoDisplay());
		add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
		add(new CreateRadioButtonHelper().createRadioButtons(this));
		
		
	}


//Panel derecho
	public JComponent createInfoDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
		JList<UsuarioDTO> listaUsuarios;
		
		
		listaUsuarios= new JList();
		
		listModel = new CustomListModel();
		//Ahora bajo el initComponents del constructor settearemos el modelo para que el JList ya lo tome por defecto desde que arranquemos la aplicación:

		listaUsuarios.setModel(listModel);
				
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
		panel.add(listaUsuarios, BorderLayout.NORTH);
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
	public CustomListModel getListModel() {
		return listModel;
	}
	

	
	
}
