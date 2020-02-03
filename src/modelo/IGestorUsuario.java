package modelo;

import java.util.HashMap;
import java.util.List;

public interface IGestorUsuario {

public List<UsuarioDTO> getUsuarios();
public void altaUsuario(UsuarioDTO usuarioDTO);
public void bajaUsuario(String userName);
public void modificaUsuario(UsuarioDTO usuarioDTO);
public UsuarioDTO getUsuario(String nombreUsuario);
public UsuarioDTO CreateRandomUser();








}
