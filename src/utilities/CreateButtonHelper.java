package utilities;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CreateButtonHelper {

	private ResourceBundle rb = ResourceBundle.getBundle("properties.ApplicationResources_en_EN_en");

//Constructor con idioma para capturar el recurso de idioma correcto
	public CreateButtonHelper(String language) {
		rb = ProyectBundle.getProyectBundle(language);
	}

	public JPanel createButtons(ActionListener actionListener) {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		int GAP = Integer.parseInt(rb.getString("GAP"));
		JButton button;

		String[] buttonStrings = getButtonStrings();
		String[] buttonActions = getButtonActionStrings();

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

	private String[] getButtonStrings() {
		String[] labelStrings = rb.getString("user.buttons.strings").split(",");
		return labelStrings;
	}

	private String[] getButtonActionStrings() {
		String[] labelStrings = rb.getString("user.buttons.actions.strings").split(",");
		return labelStrings;
	}

}
