package modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import codigonuevo.Imanager;
import codigonuevo.TextDatabase;

public class UsuarioDAO extends TextDatabase implements Imanager<UsuarioDTO> {

	private LinkedHashMap<String, UsuarioDTO> usuarios = new LinkedHashMap<String, UsuarioDTO>();

	@SuppressWarnings("unchecked")
	public UsuarioDAO() {

		usuarios = load("UsuarioDTO");// Pasamos el nombre del fichero
	}

	@Override // alta usuario
	public void add(UsuarioDTO usuarioDTO) throws Exception {
		try {
			// Agregamos un usuario al hashmap
			usuarios.put(usuarioDTO.getNombreUsuario(), usuarioDTO);

//Guardar los datos 

			save(usuarios, "UsuarioDTO");

		} catch (Exception e) {
			System.out.println("No se puede introducir el usuario. Ha habido un error.");
		}

	}

	@Override // bajaUsuario
	public void delete(String userName) throws Exception {
		try {
			usuarios.remove(userName);
			 
			save(usuarios, "UsuarioDTO");
		} catch (Exception e) {
			System.out.println("No se puede borrar el usuario. Ha habido un error.");
		}

	}

	@Override
	public void update(UsuarioDTO usuarioDTO) throws Exception {
		try {
			String key = usuarioDTO.getNombreUsuario();
			if (usuarios.containsKey(key)) {
				usuarios.put(key, usuarioDTO);
			}

			save(usuarios, "UsuarioDTO");
		} catch (Exception e) {
			//
		}

	}

	// Propósito:
	// Buscar la clave en el HashMap devolver el objeto person si existe

	@Override
	public UsuarioDTO search(String e) {

		if (usuarios.containsKey(e)) {
			// Si encontramos el elemento en la búsqueda devolvemos el elemento
			return usuarios.get(e);
		}
		return null;
	}

	/*********************************************************/

	@Override
	public void print(UsuarioDTO e) {
		// TODO Auto-generated method stub

	}

	@Override
	public SortedMap<String, UsuarioDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioDTO> getList() {

		// Examinar si es mejor devolver un hashmap

		/*
		 * Iterator<Map.Entry<String, UsuarioDTO>> it = persons.entrySet().iterator();
		 * 
		 * printHeader(); while (it.hasNext()) {
		 * 
		 * listFormat(it.next().getValue()); }
		 */

		ArrayList<UsuarioDTO> al = new ArrayList<UsuarioDTO>();

		for (Map.Entry<String, UsuarioDTO> entry : usuarios.entrySet()) {

			al.add(entry.getValue());
		}

		return al;

	}

}
