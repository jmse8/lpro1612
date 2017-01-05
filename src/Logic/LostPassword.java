/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.List;
import javax.swing.JOptionPane;
import Communication.*;
/**
 *Impletamente a lógica de negócio da interface gráfica de pedido de nova password.
 * @author lpro1612
 */
public class LostPassword {
    String email;
    int port=1612;
    /**
     * Constrói e Inicializa um pedido de mudança de password relativo a um email.
     * @param email Email associado à conta cuja password se pretende recuperar.
     */
    public LostPassword(String email){
        this.email=email;
    }
    /**
     * Inicializa uma mudança de password. 
     */
    public void lostPassword(){
        String[] output = {"recoverpassword", email};
        
        Protocol p = new Protocol(port);
        List<String> input = p.sendToServer(output);
         
        JOptionPane.showMessageDialog(null, input);
    }
}
