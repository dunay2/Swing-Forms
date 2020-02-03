package vista;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import modelo.UsuarioDTO;

public class CustomListModel extends AbstractListModel{

	private ArrayList<UsuarioDTO> lista = new ArrayList<>();
	
    @Override
    public int getSize() {
    	return lista.size();
    }

    @Override
    public Object getElementAt(int index) {
    	 UsuarioDTO usuarioDTO = (UsuarioDTO) lista.get(index);
    	   return usuarioDTO.getNombreUsuario(); }

    
    public void addUsuario(UsuarioDTO p){
        lista.add(p);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    public void eliminarUsuario(int index){
        lista.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    public UsuarioDTO getPersona(int index){
        return lista.get(index);
    }
    
}