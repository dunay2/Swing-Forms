package utilities;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//clase de navegacion

public class ProyectLocale {

	// recibir un jframe, recorrer los controles de labels, asignar nuevo valor de texto.
	
	public void cambiarIdiomaTextos(JPanel j) {
		String[] fieldsName;
		
		Component[] componentes = j.getComponents();
		int k = 0;
		for (int i = 0; i < componentes.length; i++) {
	
			if (componentes[i] instanceof JLabel) {
				 fieldsName = ProyectBundle.getLabelStrings();

				((JLabel) componentes[i]).setText (fieldsName[k++]);
			}
			
			if (componentes[i] instanceof JButton) {
				fieldsName = ProyectBundle.getButtonStrings();

				((JButton) componentes[i]).setText (fieldsName[k++]);
			}
						
		}
	}
	
	public void activarTextFields(JPanel j) {
		Component[] componentes = j.getComponents();
		for (int i = 0; i < componentes.length; i++) {
			if (componentes[i] instanceof JTextField) {
				((JTextField) componentes[i]).setEnabled(true);
			}
		}
	}
	

}
