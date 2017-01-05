/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import java.util.*;
import javax.swing.JOptionPane;

import Communication.*;

/**
 *Implementa a lógica de negócio relativa à interface gráfica de login.
 * @author lpro1612
 */
public class Login {
    String username;
    String password;
    int port=1612;
    /**
     * Inicializa um Login com o par username e password inseridos na classe LoginGUI
     * @param username Username com que se pretende fazer login.
     * @param password Password relativa ao username inserido. 
     */
    public Login(String username, String password){
        this.username=username;
        this.password=password;
    }
    /**
     * Responsável por verificar a autenticidade do par username/password. 
     * @return true em caso de sucesso
     *         false em caso de erro
     */
    
    public boolean checkLogin(){
        boolean result=false;
        String words[] = password.split("__");
         
         String encode = null;
         
         if(!words[0].equals("newPass"))
                encode = MD5Pwd.getInstance().encode(username, password);
         
         else 
             encode = password;
        
        String[] output = {"login" , username, encode};
        Protocol p = new Protocol(port);
        
        List<String> input = p.sendToServer(output);
        
        JOptionPane.showMessageDialog(null, input.get(0));
        
         if(input.get(0).equals("LOGGED IN")){
            SavedData login = new SavedData(username);
            result=true;
         }
         return result;
    }
    
}
