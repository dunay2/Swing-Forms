package modelo;

//import java.util.Collection;
import java.util.List;

public class GestorUsuario implements IGestorUsuario {

	private UsuarioDAO usuarioDAO;
	//private Collection<UsuarioDAO> usuarioDAOColl;

	public GestorUsuario() {
		usuarioDAO = new UsuarioDAO();
	}

	@Override
	public List<UsuarioDTO> getUsuarios() {

		try {
			usuarioDAO.listUsuarios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void altaUsuario(UsuarioDTO usuarioDTO) {

		try {
			usuarioDAO.altaUsuario(usuarioDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void bajaUsuario(UsuarioDTO usuarioDTO) {
		try {
			usuarioDAO.bajaUsuario(usuarioDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void modificaUsuario(UsuarioDTO usuarioDTO) {

		try {
			usuarioDAO.modificaUsuario(usuarioDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public UsuarioDTO getUsuario(UsuarioDTO usuarioDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
