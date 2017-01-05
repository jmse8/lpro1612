/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Communication.*;
import java.util.List;
/**
 * Classe que fornece informação sobre um jogo
 * @author jmse8
 */
public class gameInfo {
    int port = 1612;
    int id;
    
   public gameInfo(int id)
   {
        this.id = id;
   }
   
   /**
    * Método que faz o pedido ao servidor para obter informação sobre um jogo
    * @return String[] com as posições dos barcos e as jogadas feitas pelos jogadores
    */
   public String[] getInfo()
   {
       String[] dados = {};
     
        try{
            Protocol p = new Protocol(port);
            String[] output={"gameInfo" , Integer.toString(id)};
            List<String> input=p.getFromServer(output);
            
           dados = input.get(0).split("::"); 
        }catch(Exception e){
            
        }
       return dados;
   }
    
}
