package helpers;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class CreateRadioButtonHelper {

	private int GAP = 0;

	// Constructor con idioma para capturar el recurso de idioma correcto

	public JComponent createRadioButtons(ActionListener acListener) {

		JRadioButton rdbtnEspa;
		JRadioButton rdbtnEnglish;
		JRadioButton rdbtnGerman;
		JRadioButton rdbtnFrench;

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		ButtonGroup G = new ButtonGroup();

		// JRadioButton on radioPanel

		rdbtnEnglish = new JRadioButton("English");
		rdbtnEspa = new JRadioButton("Espa\u00F1ol");
		rdbtnGerman = new JRadioButton("German");
		rdbtnFrench = new JRadioButton("French");

		//Habilitar eventos y agregar la acción 
		rdbtnEnglish.setActionCommand("EN");
		rdbtnEnglish.addActionListener(acListener);

		rdbtnEspa.setActionCommand("ES");
		rdbtnEspa.addActionListener(acListener);

		rdbtnGerman.setActionCommand("GE");
		rdbtnGerman.addActionListener(acListener);

		rdbtnFrench.setActionCommand("FR");
		rdbtnFrench.addActionListener(acListener);

		// añadir al grupo
		G.add(rdbtnEnglish);
		G.add(rdbtnEspa);
		G.add(rdbtnGerman);
		G.add(rdbtnFrench);

		panel.add(rdbtnEnglish);
		panel.add(rdbtnEspa);
		panel.add(rdbtnGerman);
		panel.add(rdbtnFrench);

		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5, GAP - 5));
		return panel;

	}

}
