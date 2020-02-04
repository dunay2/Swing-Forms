package database;

import java.util.SortedMap;

/**
 *
 * @author ashh412 Interfaz gen�rica de acceso a datos. Crea los m�todos save y
 * load
 * @param <T>
 */
public interface IDatabase<T> {

    void save(SortedMap<String, T> e);

    SortedMap<String, T> load(String id);

}