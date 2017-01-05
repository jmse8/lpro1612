/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Communication.Protocol;
import java.util.List;

/**
 *
 * @author eduardo
 */
public class MainMenu {
    int port = 1612;
    /**
     * Implementa a exibição da lista de jogadores online 
     * @return Um vector de Strings contendo os usernames dos jogadores que se encontram online 
     */
    public String[] fillPlayers(){
        String[] dados={} ;
        try{
            Protocol p = new Protocol(port);
            String[] output={"getPlayers"};
            List<String> input=p.getFromServer(output);
            
           dados = input.get(0).split("::");   
        }catch(Exception e){
          //  JOptionPane.showMessageDialog(null,e);
            
        }
        return dados;
    }
    
}
