/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Data.DBClass;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import Logic.*;


/**
 * Implementa o protocolo de comunicação com o cliente
 * @author lpro1612
 */
public class Protocol {
    
    private String state;
    DBClass db = new DBClass();
   
    
    /**
     * Processa a informação que chegou do cliente e retorna uma resposta
     * @param inputLine String recebida do cliente
     * @return Lista de Strings com a resposta a enviar ao cliente
     */
    public List<String> processInput(String inputLine)
    {
        String[] words;
        List<String> outputLine = new ArrayList<>();
           
        words = inputLine.split("::");
        
        state = words[0];
        System.out.println("protocol_state: " + state);
        if(state.equalsIgnoreCase("register"))
        {
            Register registo = new Register(words);
            outputLine= registo.Registrate();
        }
        
        else if(state.equalsIgnoreCase("login"))
        {
            Login login = new Login(words);
            outputLine=login.LoginChecker();
            
        }
        
        else if(state.equalsIgnoreCase("recoverpassword"))
        {
         
            LostPassword recover = new LostPassword(words);
            outputLine=recover.ChangePassword();
        }
        
        else if(state.equalsIgnoreCase("getProfile"))
        {
            Profile profile = new Profile(words);
            outputLine = profile.GetDataFromDB();
            System.out.println(outputLine.get(0));
             
        }
        
        else if(state.equals("changePassword"))
        {
            ChangePassword change = new ChangePassword(words);
            outputLine= change.PasswordChange();
        }
        
         else if(state.equalsIgnoreCase("getPlayers")){
             outputLine.add(db.getPlayers());
         }
        
         else if(state.equalsIgnoreCase("Game")){
            
            GameUtil insert = new GameUtil(words);
            outputLine = insert.insertGame();
         }
        
         else if(state.equals("sendOpponent")){
            
             
             GameUtil sendopponent = new GameUtil(words);
             outputLine = sendopponent.setOpponent();
         }
         
         else if(state.equalsIgnoreCase("activeGames")){
            outputLine=db.getGames("waiting");
             
         }
         else if(state.equalsIgnoreCase("getGameId")){
           
             String id;
          
           
             GameUtil getgameid = new GameUtil(words);
             id = getgameid.getGameId();
             outputLine.add(id);
             int port = Integer.parseInt(id); 
             port=port+1024;
            GameThread game = new GameThread(port);
            game.start();
            
         }
         
         else if(state.equals("startGame")){
         
          GameUtil start = new GameUtil(words);
          outputLine = start.startGame();
         }
         
         else if(state.equals("makePlay")){
             int x,y,id;
             String orientation,out;
             boolean result;
              
             x=Integer.parseInt(words[1]);
             y=Integer.parseInt(words[2]);
             orientation=words[3];
             id=Integer.parseInt(words[4]);
           
             outputLine.add(words[1]);
             outputLine.add(words[2]);
             outputLine.add(words[3]);
             outputLine.add(words[4]);
             
             
         }
         
         else if(state.equals("finishedGames")){
             outputLine = db.getGames("end");
         }
        
         else if(state.equals("getOwner")){
         
          
          GameUtil getowner = new GameUtil(words);
          outputLine = getowner.getOwner();
         }
        
         else if(state.equalsIgnoreCase("place_boats")){
      
          
          GameUtil placeboats = new GameUtil(words);
          outputLine = placeboats.placeBoats();
         }
        
         else if(state.equalsIgnoreCase("wait_to_start")){
             int id = Integer.parseInt(words[1]);
             String player = words[2];
             outputLine.add("wait_to_start");
             outputLine.add(db.check_boats(id, player));
         }
        
        else if(state.equalsIgnoreCase("wait_to_play")){
             int id = Integer.parseInt(words[1]);
             String player;
             if(words[2].equals("player1"))
                 player = "player2";
             else 
                 player = "player1";
             outputLine.add("wait_to_play");
             outputLine.add(db.check_moves(id, player));
         }
        
        else if(state.equalsIgnoreCase("save_move")){
            int id = Integer.parseInt(words[1]);
            String player = words[2];
            String move = words[3];
            outputLine.add(db.save_move(id, player, move));
        }
        else if(state.equals("gameInfo")){
            int id = Integer.parseInt(words[1]);
            outputLine.add(db.getGameInfo(id));
        }
        else if (state.equalsIgnoreCase("liveGames")){
             outputLine=db.getGames("live");
         }
         else if(state.equalsIgnoreCase("rankingUsers")){
             outputLine=db.getUsersRanking();
         }
         else if(state.equalsIgnoreCase("setOffline")){
             outputLine.add(db.setOffline(words[1]));
         }
         else if(state.equalsIgnoreCase("newPassword")){
             outputLine.add(db.modifyPassword(words[1],words[2]));
         }
        return outputLine;
    }
}
