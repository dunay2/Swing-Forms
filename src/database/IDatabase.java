package database;


import java.util.HashMap;

/**
 *
 * @author ashh412 Interfaz genérica de acceso a datos. Crea los métodos save y
 * load
 * @param <T>
 */
public interface IDatabase<T> {

    void save(HashMap<String, T> e);

    HashMap<String, T> load(String id);

}