/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Data.DBClass;
import java.util.ArrayList;
import java.util.List;

/**
 * Regista uma novo usuário.
 * @author Eduardo
 */
public class Register {
    
    
    DBClass db = new DBClass();
    
    private final String[] data;

    public Register(String[] data) {
        this.data = data;
    }
    /**
     * Regista um novo usuárioa com Username, password, email, primeiro nome e último nome.
     * @return Uma lista de strings em que na primeira posição tem o resultado do registo.
     */
    public List<String> Registrate(){
            String username, password, email, firstName, lastName;
            List<String> outputLine = new ArrayList<>();
            username = data[1];
            password = data[2];
            email = data[3];
            firstName = data[4];
            lastName = data[5];
            System.out.println("REGISTERING...");
            outputLine.add(db.writeToDB(email,username, password, firstName, lastName));
            return outputLine;
    }
}
