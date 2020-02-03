package codigonuevo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;

/**
 *
 * @author ashh412 Interfaz gen�rica de acceso a datos. Crea los m�todos save y
 * load
 * @param <T>
 */
public interface IDatabase<T> {

    void save(HashMap<String, T> e);

    HashMap<String, T> load(String id);

}