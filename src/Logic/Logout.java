/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Communication.Protocol;
import java.util.List;

/**
 * Implementa o logout.
 * @author eduardo
 */
public class Logout {
    int port =1612;
    /**
     * Coloca um user offline.
     * @param username - Username do usuário que pretende fazer logout.
     */
    public Logout(String username){
        String[] output = {"setOffline", username};
        Protocol p = new Protocol(port);
        
        p.sendToServer(output);
    }
    
}
