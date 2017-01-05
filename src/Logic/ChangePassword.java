/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Data.DBClass;
import java.util.List;

/**
 *Altera a password de um usuário.
 * @author Diogo
 */
public class ChangePassword {
    DBClass db = new DBClass();
    
    private final String[] data;
    
    public ChangePassword(String[] data) {
        this.data = data;
    }
    
    public List<String> PasswordChange(){
            String username, password, oldPassword, newPassword;
            List<String> outputLine = null;
            username = data[1];
            oldPassword = data[2];
            newPassword = data[3];
            
            password = db.getPassword(db.getEmail(username));
            
            if(password.equals(oldPassword))
            {
                outputLine.add(db.modifyPassword(username, newPassword));
            }
            
            {
                outputLine.add("Wrong Old Password");
            }
            return outputLine;
    }
}
