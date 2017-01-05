/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 * Guarda informações sobre o username.
 * @author lpro1612
 */
public class SavedData {
    
    private static String username;
    /** 
     * Constrói e inicializa um novo username a guardar.
     * @param username Username que se pretende guardar.
     */
    public SavedData(String username)
    {
        this.username = username;
    }
    
    /**
     * Constrói um objeto da classe SavedData. 
    */
    public SavedData()
    {
        
    }
    /**
     * Retorna o username.
     * @return Uma String com o username.
     */
    public String getUsername()
    {
        return username;
    }
}
