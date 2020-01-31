package controlador;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;

import codigonuevo.Ventana;
import vista.FrmUsuario;

public class Inicio {
	public static void main(String[] args) {
		// Basic logger
		BasicConfigurator.configure();
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				// CREACION DEL CONTROLADOR DELEGADO
				createAndShowGUI();
			}
		});
	}
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Gestión");
		frame.setBounds(100, 100, 400, 350);
	//	frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new FrmUsuario());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
				
	}
	
}
