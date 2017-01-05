/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Communication.Protocol;
import java.util.List;

/**
 *Implementa a lógica de negócio que efectua a mudança de password.
 * @author eduardo
 */
public class ChangePassword {
    int port = 1612;
    /**
     * Verifica se a password está correcta.
     * @param user - Username do jogador que pretendo mudar a password.
     * @param password - Password que vai ser submetida a teste.
     * @return True em caso de a password ser válida. False em caso de não ser válida.
     */
    public boolean checkOldPass (String user,String password){
        boolean result= false;
        String encode = MD5Pwd.getInstance().encode(user, password);
        String[] output = {"login" , user, encode};
        Protocol p = new Protocol(port);
        
        List<String> input = p.sendToServer(output);
        if(input.get(0).equals("LOGGED IN")){
            result=true;
         }
         return result;
    }
    /**
     * Efectua uma mudança de password.
     * @param password - nova password
     * @param user - Username a que vai ser atribuido uma nova password.
     */
    public void newPassword (String password, String user){
        String encode = MD5Pwd.getInstance().encode(user, password);
        
        
        String[] output = {"newPassword", user, encode};
        Protocol p = new Protocol(port);
        
        List<String> input = p.sendToServer(output);
    }
}
