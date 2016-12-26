/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 *
 * @author Diogo
 */
public class LostPassword {
    
    DBClass db = new DBClass();
    
    private final String[] data;

    public LostPassword(String[] data) {
        this.data = data;
    }
    
    public List<String> ChangePassword(){
            List<String> outputLine = null;
            
            String email = data[1];
            
            outputLine.add(db.CheckEmail(email));
            if(outputLine.get(0).equalsIgnoreCase("New Password sent.")){
                String user = db.getUser(email);
                String newPassword = generatePassword();
                
                db.modifyPassword(user, newPassword);
              
                SendMail mail = new SendMail();
                outputLine.add(mail.send(email, newPassword));
            }
            return outputLine;
    
    }
    
     private String generatePassword()
    {
        SecureRandom random = new SecureRandom();
        
        String aux = "newPass__";
        String password = new BigInteger(130, random).toString(32);
        
        password = aux.concat(password);
        
        return password;
    }
}



