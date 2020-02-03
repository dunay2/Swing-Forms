package codigonuevo;

/*
//Autor Diego Rios
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.UsuarioDTO;

/**
 *
 * @author DRIOS Propósito: agregar persistencia al proyecto mediante acceso a
 *         un fichero de texto donde se guardan los objetos serializados
 */
public class TextDatabase implements IDatabase {

//Implementamos el método save para guardar objetos
	@Override
	public void save(LinkedHashMap hm, String className) {

		try {
			FileOutputStream fout = null;



			// Convertimos el HashMap en el tipo que vamos a guardar

			LinkedHashMap hmfile = (LinkedHashMap<String, UsuarioDTO>) hm;

			// TODO parametrizar entrada y tipo de clase en properties con reflection
			className = className.concat(".data");

			try {
				fout = new FileOutputStream(className, false);

			} catch (FileNotFoundException ex) {
				Logger.getLogger(TextDatabase.class.getName()).log(Level.SEVERE, null, ex);
			}

			ObjectOutputStream oosh = new ObjectOutputStream(fout);
			oosh.writeObject(hmfile);

			fout.close();

		} catch (FileNotFoundException ex) {
			Logger.getLogger(TextDatabase.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(TextDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public LinkedHashMap load(String fileName) {
		LinkedHashMap<String, ?> e = new LinkedHashMap();
		FileInputStream file;
		ObjectInputStream in;
		fileName = fileName.concat(".data");
		// Deserialization
		File f = new File(fileName);
		if (f.exists()) {

			try {
				file = new FileInputStream(fileName);

				in = new ObjectInputStream(file);

				e = (LinkedHashMap<String, ?>) in.readObject();

				file.close();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(TextDatabase.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException | ClassNotFoundException ex) {
				Logger.getLogger(TextDatabase.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return e;
	}
}