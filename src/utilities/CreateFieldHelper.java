package utilities;

import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.MaskFormatter;

import format.JTextFieldLimit;

public class CreateFieldHelper {

	private ResourceBundle rb;

	// Constructor con idioma para capturar el recurso de idioma correcto
	public CreateFieldHelper(String language) {
		rb = ProyectBundle.getProyectBundle(language);
	}

	/*
	 * Crea los campos de entrada de datps del formulario de a partir de un archivo properties de configuracion 
	 * Devuelve un objecto JPanel con los objetos JTextField, JFormattedTextField y JComboBox definidos
	 *  
	 */


	@SuppressWarnings("unchecked")
	public JPanel createEntryFields() {
		int GAP = Integer.parseInt(rb.getString("GAP"));

		//leemos los valores de configuracion desde el archivo properties
		String[] fieldsName = getfieldsName();
		String[] fieldsType = getfieldsType();
		String[] fieldsMaxLength = getfieldsMaxLength();
		String[] fieldsFormat = getfieldsFormat();
		String[] labelStrings = getLabelStrings();

		JPanel panel = new JPanel(new SpringLayout());
		JLabel[] labels = new JLabel[labelStrings.length];

		JComponent[] fields = new JComponent[labelStrings.length];
		JComponent auxTextField = null;
		
		// recuperamos los valores de los campos de la configuración
		for (int i = 0; i < fieldsName.length; i++) {

			switch (fieldsType[i]) {
			case "JTextField":
				auxTextField = new JTextField();
				auxTextField.setName(fieldsName[i]);
				((JTextField) auxTextField).setDocument(new JTextFieldLimit(Integer.parseInt(fieldsMaxLength[i])));
				((JTextField) auxTextField).setColumns(20);
				break;

			case "JComboBox":
				auxTextField = new JComboBox<String>();
				auxTextField.setName(fieldsName[i]);
				((JComboBox<String>) auxTextField).setModel(new DefaultComboBoxModel<String>(getRoleStrings()));
				break;

			case "JFormattedTextField":
				auxTextField = new JFormattedTextField(createFormatter(fieldsFormat[i]));
				auxTextField.setName(fieldsName[i]);
				((JFormattedTextField) auxTextField)
						.setDocument(new JTextFieldLimit(Integer.parseInt(fieldsMaxLength[i])));
				((JFormattedTextField) auxTextField).setColumns(20);
				break;
			}

			fields[i] = auxTextField;
		}

		// Associate label/field pairs, add everything,
		// and lay it out.
		for (int i = 0; i < labelStrings.length; i++) {
			labels[i] = new JLabel(labelStrings[i], JLabel.TRAILING);
			labels[i].setLabelFor(fields[i]);
			panel.add(labels[i]);
			panel.add(fields[i]);
		}
		SpringUtilities.makeCompactGrid(panel, labelStrings.length, 2, GAP, GAP, // init x,y
				GAP, GAP / 2);// xpad, ypad
		return panel;
	}

	// A convenience method for creating a MaskFormatter.
	private MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	private String[] getRoleStrings() {
		String[] roleStrings = rb.getString("user.roles.strings").split(",");
		return roleStrings;
	}

	private String[] getLabelStrings() {
		String[] labelStrings = rb.getString("user.labels.strings").split(",");
		return labelStrings;
	}

	private String[] getfieldsName() {
		String[] labelStrings = rb.getString("user.fields.names").split(",");
		return labelStrings;
	}

	private String[] getfieldsType() {
		String[] labelStrings = rb.getString("user.fields.types").split(",");
		return labelStrings;
	}

	private String[] getfieldsMaxLength() {
		String[] labelStrings = rb.getString("user.fields.maxlength").split(",");
		return labelStrings;
	}

	private String[] getfieldsFormat() {
		String[] labelStrings = rb.getString("user.fields.format").split(",");
		return labelStrings;
	}

}
