/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Data.DBClass;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Muda a password no caso de um usuário não saber.
 * @author eduardo
 */
public class LostPassword {
    
    DBClass db = new DBClass();
    
    private final String[] data;

    public LostPassword(String[] data) {
        this.data = data;
    }
    /**
     * Efectua a mudança de password para uma aleatória.
     * @return Uma Lista de strings em que a primeira posição determina o resultado da mudança.
     */
    public List<String> ChangePassword(){
            List<String> outputLine = new ArrayList<>();;
            
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
    /**
     * Método auxiliar que gera uma nova password aleatória de 32 carateres 
     * @return String nova password
     */
    
     private String generatePassword()
    {
        SecureRandom random = new SecureRandom();
        
        String aux = "newPass__";
        String password = new BigInteger(130, random).toString(32);
        
        password = aux.concat(password);
        
        return password;
    }
}



