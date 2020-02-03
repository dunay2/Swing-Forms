package codigonuevo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.LinkedHashMap;


/**
 *
 * @author ashh412 Interfaz genérica de acceso a datos. Crea los métodos save y
 * load
 * @param <T>
 */
public interface IDatabase<T> {

    void save(LinkedHashMap<String, T> e,String className);

    LinkedHashMap<String, T> load(String id);

}