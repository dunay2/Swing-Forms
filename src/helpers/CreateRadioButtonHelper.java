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
		JRadioButton rdbtnNone;

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		ButtonGroup G = new ButtonGroup();

		// JRadioButton on radioPanel

		rdbtnEnglish = new JRadioButton("English");
		rdbtnEspa = new JRadioButton("Espa\u00F1ol");
		rdbtnGerman = new JRadioButton("German");
		rdbtnFrench = new JRadioButton("French");
		rdbtnNone = new JRadioButton("None");
		

		//Habilitar eventos y agregar la acci�n 
		rdbtnEnglish.setActionCommand("EN");
		rdbtnEnglish.setName("EN");
		rdbtnEnglish.addActionListener(acListener);

		rdbtnEspa.setActionCommand("ES");
		rdbtnEspa.setName("ES");
		rdbtnEspa.addActionListener(acListener);

		rdbtnGerman.setActionCommand("GE");
		rdbtnGerman.setName("GE");
		rdbtnGerman.addActionListener(acListener);

		rdbtnFrench.setActionCommand("FR");
		rdbtnFrench.setName("FR");
		rdbtnFrench.addActionListener(acListener);
		
		rdbtnNone.setActionCommand("NONE");
		rdbtnNone.setName("NONE");
		rdbtnNone.addActionListener(acListener);
		

		// a�adir al grupo
		G.add(rdbtnEnglish);
		G.add(rdbtnEspa);
		G.add(rdbtnGerman);
		G.add(rdbtnFrench);
		G.add(rdbtnNone);

		panel.add(rdbtnEnglish);
		panel.add(rdbtnEspa);
		panel.add(rdbtnGerman);
		panel.add(rdbtnFrench);
		panel.add(rdbtnNone);
		

		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5, GAP - 5));
		return panel;

	}

}
