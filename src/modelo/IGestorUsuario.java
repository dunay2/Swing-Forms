package modelo;

import java.util.List;

public interface IGestorUsuario {

public List<UsuarioDTO> getUsuarios();
public void altaUsuario(UsuarioDTO usuarioDTO);
public void bajaUsuario(UsuarioDTO usuarioDTO);
public void modificaUsuario(UsuarioDTO usuarioDTO);
public UsuarioDTO getUsuario(UsuarioDTO usuarioDTO);

}
