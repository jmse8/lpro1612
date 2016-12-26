/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientba;

import java.util.List;
import javax.swing.JOptionPane;

/**
 * Implementa um novo jogo
 * @author lpro1612
 */
public class CreateGame {
    int port=1612;
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
            JOptionPane.showMessageDialog(null,e);
            
        }
        return dados;
    }
    /**
     * Cria um novo jogo com um determinado nome, password, criador, e tipo. 
     * @param name Nome do jogo.
     * @param password Password do jogo.
     * @param owner Criador do jogo.
     * @param type Tipo do jogo, public ou private. 
     * @return Um inteiro com o Id do jogo na base de dados 
     */ 
    
    public int newGame(String name, String password, String owner, String type){
        
        String[] output = {"Game", name, password, owner, type};
        Protocol p = new Protocol(port);
        
        List<String> input = p.sendToServer(output);
        JOptionPane.showMessageDialog(null, input);
        if(!"Game Created".equals(input.get(0))) return 0;
        String[] out = {"getGameId",name};
        Protocol p2 = new Protocol(port);
        List<String> id=p2.getFromServer(out);
        String[] game_id = id.get(0).split("::");
        int gameId = Integer.parseInt(game_id[0]);
        
        return gameId;
        
    }
    
}
