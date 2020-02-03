package modelo;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;

import utilities.UserGenerator;

public class GestorUsuario implements IGestorUsuario {

	private UsuarioDAO usuarioDAO;
	//private Collection<UsuarioDAO> usuarioDAOColl;

	public GestorUsuario() {
		usuarioDAO = new UsuarioDAO();
	}

	@Override
	public List<UsuarioDTO> getUsuarios() {
		ArrayList<UsuarioDTO> al = null;
		
		try {
			al=(ArrayList<UsuarioDTO>) usuarioDAO.getList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;

	}

	@Override
	public void altaUsuario(UsuarioDTO usuarioDTO) {

		try {
			usuarioDAO.add(usuarioDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void bajaUsuario(String userName) {
		try {
			usuarioDAO.delete (userName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void modificaUsuario(UsuarioDTO usuarioDTO) {

		try {
			usuarioDAO.update  (usuarioDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public UsuarioDTO getUsuario(String nombreUsuario) {
		
		return usuarioDAO.search(nombreUsuario) ;

	}
	
	@Override
	public UsuarioDTO CreateRandomUser()
	{
		return UserGenerator.generateRandomPerson();
	}
}
