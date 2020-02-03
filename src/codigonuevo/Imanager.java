package codigonuevo;

import java.util.HashMap;
import java.util.List;

import modelo.UsuarioDTO;

/**
 *
 * @author ashh412
 * @param <T>
 * @param <N>
 */

/*
 Propósito:Interfaz de Gestores de objetos
 */
//Cosas que puede hacer un gestor 
public interface Imanager<T> {

    //Para devolver el nombre de las instancias
    default String getClassName() {
        return this.getClass().getSimpleName();
    }

    public void print(T e);
    

    public HashMap<String, T> getAll();

    //Para actualizar los datos de una entidad
    public void update(T e) throws Exception;

    //Para borrar una entidad
    public void delete(String e) throws Exception;
//Para agregar una entidad

    public void add(T e) throws Exception;
//Para buscar un elemento

    public T search(String s) throws Exception;;

    public List<UsuarioDTO> getList();

}