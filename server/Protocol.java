/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import javax.script.*;

import php.java.bridge.*;
import php.java.script.*;
import php.java.servlet.*;



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
            String username, password, email, firstName, lastName;
            
            username = words[1];
            password = words[2];
            email = words[3];
            firstName = words[4];
            lastName = words[5];
            System.out.println("REGISTERING...");
            outputLine.add(db.writeToDB(email,username, password, firstName, lastName));  
        }
        
        else if(state.equalsIgnoreCase("login"))
        {
            String username, password;
            
            username = words[1];
            password = words[2];
            
            outputLine.add(db.CheckLogin(username, password));
        }
        
        else if(state.equalsIgnoreCase("recoverpassword"))
        {
            String email = words[1];
            
            outputLine.add(db.CheckEmail(email));
            if(outputLine.get(0).equalsIgnoreCase("New Password sent.")){
                String user = db.getUser(email);
                String newPassword = generatePassword();
                
                db.modifyPassword(user, newPassword);
              
                SendMail mail = new SendMail();
                outputLine.add(mail.send(email, newPassword));
            }
        }
        
        else if(state.equalsIgnoreCase("getProfile"))
        {
            String username, password, email, firstName, lastName, gamesWon, gamesLost, hits;
            username = words[1];
            firstName = db.getFirstName(username);
            lastName = db.getLastName(username);
            email = db.getEmail(username);
            gamesWon = db.getGamesWon(username);
            gamesLost = db.getGamesLost(username);
            hits = db.getHits(username);
            
            outputLine.add(username + "::" + firstName + "::" + lastName + "::" + email + "::" + gamesWon + "::" + gamesLost + "::" + hits);
        }
        
        else if(state.equals("changePassword"))
        {
            String username, password, oldPassword, newPassword;
            username = words[1];
            oldPassword = words[2];
            newPassword = words[3];
            
            password = db.getPassword(db.getEmail(username));
            
            if(password.equals(oldPassword))
            {
                outputLine.add(db.modifyPassword(username, newPassword));
            }
            
            {
                outputLine.add("Wrong Old Password");
            }
            
            
        }
         else if(state.equalsIgnoreCase("getPlayers")){
             String players;
             outputLine.add(db.getPlayers());
         }
        
         else if(state.equalsIgnoreCase("Game")){
             String gameName, password,type;
             int owner;
             gameName=words[1];
             password=words[2];
             owner=db.getId(words[3]);
             type=words[4];
             outputLine.add(db.insertGame(gameName,password,type,owner));
         }
        
         else if(state.equalsIgnoreCase("activeGames")){
             //outputLine[0]=db.getGames();
             //retornar string[], preenchida na dbclass
            outputLine=db.getGames();
             
         }
         else if(state.equalsIgnoreCase("getGameId")){
             String gameName=words[1];
             String id;
             id=db.getGameId(gameName);
             outputLine.add(id);
          
             int port = Integer.parseInt(id); 
             port=port+1024;
             System.out.println("TOU NO GET GAME ID");
            GameThread game = new GameThread(port);
            game.start();
            
         }
         
         else if(state.equals("startGame")){
            int id = Integer.parseInt(words[1]);
           
             outputLine.add(db.startGame(id));
            outputLine.add(words[1]);
            System.out.println("FIM DO STARTGAME");
           
            
         }
         else if(state.equals("makePlay")){
             int x,y,id;
             String orientation,out;
             boolean result;
              
             x=Integer.parseInt(words[1]);
             y=Integer.parseInt(words[2]);
             orientation=words[3];
             id=Integer.parseInt(words[4]);
            
            System.out.println("cheguei no makeplay");
             outputLine.add(words[1]);
             outputLine.add(words[2]);
             outputLine.add(words[3]);
             outputLine.add(words[4]);
             
             
         }
        
         else if(state.equals("getOwner")){
            int id = Integer.parseInt(words[1]);
            System.out.println("getOwner:" + id); 
            outputLine.add(db.getOwner(id));
            System.out.println(outputLine.get(0));
         }
        
         else if(state.equalsIgnoreCase("place_boats")){
            int id = Integer.parseInt(words[1]);
            String player = words[2];
            outputLine.add(db.place_boats(id, player, words[3]));
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
