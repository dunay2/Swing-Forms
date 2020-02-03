package helpers;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import utilities.ProyectBundle;
/*
 * Clase encargada de la creacion de la botonera a partir de un archivo properties de configuracion 
	 * Devuelve un objecto JPanel con los objetos JTextField, JFormattedTextField y JComboBox definidos
 * */
public class CreateButtonHelper {

	private ProyectBundle rb;

	public JPanel createButtons(ActionListener actionListener) {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		int GAP = Integer.parseInt(ProyectBundle.getGAP());
		JButton button;

		String[] buttonStrings = ProyectBundle.getButtonStrings();
		String[] buttonActions = ProyectBundle.getButtonActionStrings();

		for (int i = 0; i < buttonStrings.length; i++) {

			button = new JButton(buttonStrings[i]);
			button.addActionListener(actionListener);
			button.setActionCommand(buttonActions[i]);
			panel.add(button);
		}

		// Match the SpringLayout's gap, subtracting 5 to make
		// up for the default gap FlowLayout provides.
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5, GAP - 5));
		return panel;
	}

}
