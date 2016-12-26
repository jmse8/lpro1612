/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.List;

/**
 *
 * @author Diogo
 */
public class BoardGame {
    
    DBClass db = new DBClass();
    
    
    public List<String> getOwner(String data){
        int id = Integer.parseInt(data);
        List<String> outputLine = null;
        outputLine.add(db.getOwner(id));
        System.out.println(outputLine.get(0));
        return outputLine;
    }
    
    public List<String> PlaceBoats(String[] data){
            List<String> outputLine=null;
            int id = Integer.parseInt(data[1]);
            String player = data[2];
            outputLine.add(db.place_boats(id, player, data[3]));
            return outputLine;
    }
    
    public List<String>WaitToStart(String[] data){
             int id = Integer.parseInt(data[1]);
             List<String> outputLine =null;
             String player = data[2];
             outputLine.add("wait_to_start");
             outputLine.add(db.check_boats(id, player));
             return outputLine;
    }
    
    public List<String>WaitToPlay(String[] data){
             int id = Integer.parseInt(data[1]);
             List<String> outputLine =null;
             String player;
             if(data[2].equals("player1"))
                 player = "player2";
             else 
                 player = "player1";
             outputLine.add("wait_to_play");
             outputLine.add(db.check_moves(id, player));
         
             return outputLine;
    }
    
    public List<String> SaveMove(String[]data){
            int id = Integer.parseInt(data[1]);
            List<String> outputLine=null;
            String player = data[2];
            String move = data[3];
            outputLine.add(db.save_move(id, player, move));
            return outputLine;
    }
    
    
}
