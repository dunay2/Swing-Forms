package database;

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
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.UsuarioDTO;

/**
 *
 * @author DRIOS Prop�sito: agregar persistencia al proyecto mediante acceso a
 *         un fichero de texto donde se guardan los objetos serializados
 */
public class TextDatabase implements IDatabase {

//Implementamos el m�todo save para guardar objetos
	@Override
	public void save(SortedMap hm) {

		try {
			FileOutputStream fout = null;

			//TODO
			//comprobar si guarda cuando no hay objetos en la lista
			
//Capturar el tipo
//Obtenemos el primer objeto para saber su tipo y guardar en su fichero
			Iterator<Entry<String, Object>> it = hm.entrySet().iterator();
//Tomamos el primer valor para conocer la clase hija que vamos a guardar
			Entry<String, Object> ite = it.next();
			Object objectType = ite.getValue();

//Guardamos el nombre de la clase hija
			String filename = objectType.getClass().getSimpleName();

			// Convertimos el Map en el tipo que vamos a guardar			

			//TODO
			//El tipo a guardar debe ser generico, nada de UsuarioDTO
			
			SortedMap	hmfile = (SortedMap<String, UsuarioDTO>) hm;
		
			
			// TODO parametrizar entrada y tipo de clase? puede ser por reflection?
			filename = filename.concat(".data");

			try {
				fout = new FileOutputStream(filename, false);

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
	public SortedMap<String, ?> load(String fileName) {
		SortedMap<String, ?> e= new TreeMap(); //= new SortedMap();
				
		FileInputStream file;
		ObjectInputStream in;
		fileName = fileName.concat(".data");
		// Deserialization
		File f = new File(fileName);
		if (f.exists()) {

			try {
				file = new FileInputStream(fileName);

				in = new ObjectInputStream(file);

				e = (SortedMap<String, ?>) in.readObject();

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