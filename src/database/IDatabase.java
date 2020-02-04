package database;

import java.util.SortedMap;

/**
 *
 * @author ashh412 Interfaz genérica de acceso a datos. Crea los métodos save y
 * load
 * @param <T>
 */
public interface IDatabase<T> {

    void save(SortedMap<String, T> e);

    SortedMap<String, T> load(String id);

}