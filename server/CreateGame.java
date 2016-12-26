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
public class CreateGame {
    DBClass db = new DBClass();
    
    public String getGameID(String data){
             
             List<String> outputLine=null;
             String id;
             id=db.getGameId(data);
             
             return id;
    }
    
    public void startGame(String data){
            List<String> outputLine=null;
            int id = Integer.parseInt(data);
            outputLine.add(db.startGame(id));
    }
    
    
    
}
