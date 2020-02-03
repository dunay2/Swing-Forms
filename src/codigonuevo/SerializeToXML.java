package codigonuevo;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import modelo.UsuarioDTO;

public class SerializeToXML {	

	private static String ruta="G:\\ESTUDIOS\\ATRIUM\\ejercicios\\Ejercicio Tema 1\\CRUD USER\\bbdd\\";
	
	private static final String SERIALIZED_FILE_NAME="usuario.xml";

	public static void main(String args[]){

		UsuarioDTO usuarioDTO=new UsuarioDTO(); 		
		usuarioDTO.setNombreUsuario("pepe");
		usuarioDTO.setCodigoRol(67);
		usuarioDTO.setCarpetaDoc("c:\\mi carpeta");
		usuarioDTO.setPassword("1234");
		usuarioDTO.setIdioma("ES".toCharArray()); 
				
		XMLEncoder encoder=null;
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(ruta.concat(SERIALIZED_FILE_NAME))));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(usuarioDTO);
		encoder.close();

	}

	/*private static UserSettings deserializeFromXML() throws IOException {
	    FileInputStream fis = new FileInputStream("settings.xml");
	    XMLDecoder decoder = new XMLDecoder(fis);
	    UserSettings decodedSettings = (UserSettings) decoder.readObject();
	    decoder.close();
	    fis.close();
	    return decodedSettings;
	}
	*/
}