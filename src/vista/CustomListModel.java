package vista;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import modelo.GestorUsuario;
import modelo.IGestorUsuario;
import modelo.UsuarioDTO;

public class CustomListModel extends AbstractListModel {

	private ArrayList<UsuarioDTO> lista = new ArrayList<>();

	public CustomListModel() {
		IGestorUsuario gestorUsuario = new GestorUsuario();

		for (int i = 0; i < gestorUsuario.getUsuarios().size(); i++) {

			lista.add(gestorUsuario.getUsuarios().get(i));

		}

	}

	public UsuarioDTO getUsuarioDTO(int idx) {
		IGestorUsuario gestorUsuario = new GestorUsuario();

		return gestorUsuario.getUsuarios().get(idx);

	}

	@Override
	public int getSize() {
		return lista.size();
	}

	@Override
	public Object getElementAt(int index) {
		UsuarioDTO usuarioDTO = (UsuarioDTO) lista.get(index);
		return usuarioDTO.getNombreUsuario();
	}

	public void addUsuario(UsuarioDTO usuarioDTO) {
		lista.add(usuarioDTO);
		this.fireIntervalAdded(this, getSize(), getSize() + 1);
	}

	public void removeUsuario(String userName) {
		
		int size = lista.size();
		for (int i = 0; i < size; i++) {
			if (this.getElementAt(i).equals(userName)) {
				lista.remove(i);
				fireIntervalRemoved(this, i, i);
				break;
			}
		}

	}

	public UsuarioDTO getPersona(int index) {
		return lista.get(index);
	}

}