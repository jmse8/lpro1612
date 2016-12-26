/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientba;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *Implementa a lógica de negócio da interface gráfica de registo.
 * @author lpro1612
 */
public class Register {
    String user, passw, email, firstName, lastName;
    int port=1612;
    /**
     * Inicializa um registo com as informações inseridas no registerGUI.
     * @param user Username do novo registo.
     * @param passw Password do novo registo.
     * @param email Email do novo registo.
     * @param firstName Primeiro nome do utilizador que efectua o novo registo.
     * @param lastName Último Nome do utilizador que efectua o novo registo.
     */
    public Register(String user, String passw, String email, String firstName, String lastName){
        this.user=user;
        this.passw=passw;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    /**
     * Responsável por verificar a validade de cada um dos parâmetros e inserir um novo user na base de dados. 
     * @return true em caso de sucesso.
     *         false em caso de erro.
     */
    public boolean newReg(){
        String encode = MD5Pwd.getInstance().encode(user, passw);
        
        if(!ValidateEmail.isValidEmailAddress(email)){
            JOptionPane.showMessageDialog(null, "Email not valid");
            return false;
         }
        
        String[] output = {"register", user, encode, email, firstName, lastName};
        Protocol p = new Protocol(port);
        
        List<String> input = p.sendToServer(output);
        
        JOptionPane.showMessageDialog(null, input);
        String v = input.get(0);
        System.out.println(input.get(0));
        if(v != "YOU ARE NOW REGISTERED")//nao funciona dont know why
            return false;
        
        return true;
    }
}
