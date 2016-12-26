/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diogo
 */
public class Login {
    DBClass db = new DBClass();
    
    private final String[] data;

    public Login(String[] data) {
        this.data = data;
    }
    
    public List<String> LoginChecker(){
            List<String> outputLine;
            outputLine = new ArrayList<>();
            String username, password;
            
            username = data[1];
            password = data[2];
            
            outputLine.add(db.CheckLogin(username, password));
            return outputLine;
    }
}
