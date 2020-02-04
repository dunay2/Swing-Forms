package helpers;

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
import utilities.ProyectBundle;
import utilities.SpringUtilities;

public class CreateFieldHelper {

	
	/*
	 * Esta clase crea los campos de entrada de datos del formulario a partir de un archivo properties de configuracion 
	 * Devuelve un objecto JPanel con los objetos JTextField, JFormattedTextField y JComboBox definidos
	 *  
	 */


	@SuppressWarnings("unchecked")
	public JPanel createEntryFields() {
		int GAP = Integer.parseInt(ProyectBundle.getGAP());

		//leemos los valores de configuracion desde el archivo properties
		String[] fieldsName = ProyectBundle.getfieldsName();
		String[] fieldsType = ProyectBundle.getfieldsType();
		String[] fieldsMaxLength = ProyectBundle.getfieldsMaxLength();
		String[] fieldsFormat = ProyectBundle.getfieldsFormat();
		String[] labelStrings = ProyectBundle.getLabelStrings();

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
				((JComboBox<String>) auxTextField).setModel(new DefaultComboBoxModel<String>(ProyectBundle.getRoleStrings()));
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
			labels[i].setName("lbl".concat(fieldsName[i]) );
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

	

}
