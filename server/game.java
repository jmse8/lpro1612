/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public class game {
    ArrayList<String> positionsOwner = new ArrayList<String>();
    ArrayList<String> positionsEnemy = new ArrayList<String>();
    ArrayList<String> ownerPlays = new ArrayList<String>();
    ArrayList<String> enemyPlays = new ArrayList<String>();
    int numJogadasOwner=0;
    int numHitsOwner=0;
    int numJogadasEnemy=0;
    int numHitsEnemy=0;
    int numPlayers=0;
    String play="player1";
    String finish="false";
    String owner="false";
    String enemy="false";
    int messages=0;
    boolean end=false;
    
   
    public String checkBoatPosition(int x, int y, String orientation, int boatNumber, String player){
        String result="false";
        boolean rst=false;
        System.out.println("trying to place boat nº" + boatNumber+" on" +x +y +" "+orientation);
        if(boatNumber ==0){
            System.out.println("tou no 0 com"+x+y+orientation);
            if("vertical".equals(orientation) && y>5)
                return "false";
                
            else if("horizontal".equals(orientation)&& x>5)
                return "false";
            
            else if("vertical".equals(orientation)){
                String x_axis=Integer.toString(x);
                for(int k=y; k<y+5 ; k++){             
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                } 
                
                for(int k=y; k<y+5 ; k++){
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        positionsOwner.add(x_axis+y_axis);
                    }
                    else positionsEnemy.add(x_axis+y_axis);
                }
            }
            else if("horizontal".equals(orientation)){
                String y_axis=Integer.toString(y);
                for(int k=x; k<x+5 ; k++){             
                    String x_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                }
                for(int k=x; k<x+5 ; k++){
                        String x_axis=Integer.toString(k);
                        if("player1".equals(player)){
                            positionsOwner.add(x_axis+y_axis);
                        }
                        else positionsEnemy.add(x_axis+y_axis);
                }  
            }
            numPlayers++;
           
        }
        else if (boatNumber == 1){
            if("vertical".equals(orientation) && y>6)
                return "false";
            else if("horizontal".equals(orientation)&&x>6)
                return "false";
            
            else if("vertical".equals(orientation)){
                String x_axis=Integer.toString(x);
                for(int k=y; k<y+4 ; k++){             
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                }  
                for(int k=y; k<y+4 ; k++){
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        positionsOwner.add(x_axis+y_axis);
                    }
                    else positionsEnemy.add(x_axis+y_axis);
                }
            }
            else if("horizontal".equals(orientation)){
                String y_axis=Integer.toString(y);
                for(int k=x; k<x+4 ; k++){             
                    String x_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                }
                for(int k=x; k<x+4 ; k++){
                        String x_axis=Integer.toString(k);
                        if("player1".equals(player)){
                            positionsOwner.add(x_axis+y_axis);
                        }
                        else positionsEnemy.add(x_axis+y_axis);
                }  
            }
          
        }
        else if (boatNumber == 2 || boatNumber ==3){
            if("vertical".equals(orientation) && y>7)
                return "false";
            else if("horizontal".equals(orientation)&&x>7)
                return "false";
            else if("vertical".equals(orientation)){
                String x_axis=Integer.toString(x);
                for(int k=y; k<y+3 ; k++){             
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                }  
                for(int k=y; k<y+3 ; k++){
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        positionsOwner.add(x_axis+y_axis);
                    }
                    else positionsEnemy.add(x_axis+y_axis);
                }
            }
            else if("horizontal".equals(orientation)){
                String y_axis=Integer.toString(y);
                for(int k=x; k<x+3 ; k++){             
                    String x_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                }
                for(int k=x; k<x+3 ; k++){
                        String x_axis=Integer.toString(k);
                        if("player1".equals(player)){
                            positionsOwner.add(x_axis+y_axis);
                        }
                        else positionsEnemy.add(x_axis+y_axis);
                }  
            }

            
        }
        
  
        else if (boatNumber == 4){
            if("vertical".equals(orientation) && y>8)
                return "false";
            else if("horizontal".equals(orientation)&&x>8)
                return "false";
            
            else if("vertical".equals(orientation)){
                String x_axis=Integer.toString(x);
                for(int k=y; k<y+2 ; k++){             
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                } 
                for(int k=y; k<y+2 ; k++){
                    String y_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        positionsOwner.add(x_axis+y_axis);
                    }
                    else positionsEnemy.add(x_axis+y_axis);
                }
            }
            else if("horizontal".equals(orientation)){
                String y_axis=Integer.toString(y);
                for(int k=x; k<x+2 ; k++){             
                    String x_axis=Integer.toString(k);
                    if("player1".equals(player)){
                        rst=positionsOwner.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                    else {
                        rst=positionsEnemy.contains(x_axis+y_axis);
                        if(rst == true) return "false";
                    }
                }
                for(int k=x; k<x+2 ; k++){
                        String x_axis=Integer.toString(k);
                        if("player1".equals(player)){
                            positionsOwner.add(x_axis+y_axis);
                        }
                        else positionsEnemy.add(x_axis+y_axis);
                }  
            }
            
        }
        return "true";
    }
    
    public String makePlay(int x, int y, String player){
        String x_axis=Integer.toString(x);
        String y_axis=Integer.toString(y);
        
        if("player1".equals(player)){
            if(ownerPlays.contains(x_axis+y_axis)) return "false"; //ja jogou aqui
            numJogadasOwner++;
            if(positionsEnemy.contains(x_axis+y_axis)){
                 ownerPlays.add(x_axis+y_axis); // acertou 
                 numHitsOwner++;
                 if(numHitsOwner==17){
                     finish="true";
                     return "win";
                 }
                 return "hit";
            }
            ownerPlays.add(x_axis+y_axis); // falhou
            play="player2";
            return "miss";
        }
        else{
            if(enemyPlays.contains(x_axis+y_axis)) return "false"; //ja jogou aqui
            numJogadasEnemy++;
            if(positionsOwner.contains(x_axis+y_axis)){
                 enemyPlays.add(x_axis+y_axis); // acertou 
                 numHitsEnemy++;
                 if(numHitsEnemy==17){
                     finish="true";
                     return "win";
                 }
                 return "hit";
            }
            enemyPlays.add(x_axis+y_axis); // falhou
            play="player1";
            return "miss";
        }
    }
    
    public String getLastPlay(String player){
        String move="";
        
        if("player1".equals(player)){
            if(enemyPlays.isEmpty() ) return "";
            move=enemyPlays.get(enemyPlays.size()-1);
        }
        else {
            if(ownerPlays.isEmpty()) return "";
            move = ownerPlays.get(ownerPlays.size()-1);
        }
        
        return move;
    }
    
    public String checkMove(String move, String player){
        boolean result;
        if("player1".equals(player)){
            result=positionsOwner.contains(move);
            if(result){
                if(numHitsEnemy ==17){
                    finish="true";
                    return "hit::lost";
                }
                return "hit";
            }
            else return "miss";
        }
        else{
            result=positionsEnemy.contains(move);
            if(result){
                if(numHitsOwner==17){
                    finish="true";
                    return "hit::lost";
                }
                return "hit";
            }
            else return "miss";
        }
    }
    public String checkTurn(){
        return play;
    }
    
    public String checkEnd(String player){
        if("player1".equals(player) && numHitsEnemy==17)
             return "true";
        if("player2".equals(player) && numHitsOwner==17)
            return "true";
        
        return "false";
    }   
    
    public int numPlayers(){
        return numPlayers;
    }
    public void sendUsername(String player, String username) {
        if("player1".equals(player)){
            owner=username;
        }
        else if("player2".equals(player)){
            enemy=username;
        }
    }
    
    public void endGame (int id) {
        DBClass db = new DBClass();
        if(numHitsOwner == 17){
            db.updateUserInfo(owner,enemy, numHitsOwner, numHitsEnemy);
        }
        else if (numHitsEnemy == 17){
            db.updateUserInfo(enemy, owner, numHitsEnemy, numHitsOwner);
        }
        String boatsOwner="",boatsEnemy="";
        String ownerMoves="", enemyMoves="";
        for(int i =0; i< positionsOwner.size() ; i++){
            boatsOwner=boatsOwner +","+positionsOwner.get(i);
            boatsEnemy=boatsEnemy+","+positionsEnemy.get(i);
        }
        for(int k=0; k<ownerPlays.size(); k++){
            ownerMoves = ownerMoves+ ownerPlays.get(k)+",";
        }
        for( int j=0; j<enemyPlays.size(); j++){
            enemyMoves = enemyMoves+ enemyPlays.get(j)+",";
        }
        end=true;
        db.updateGameInfo(id,boatsOwner, boatsEnemy, ownerMoves, enemyMoves);
    }
    
    public void sendMsg (int id, String msg, String username){
        DBClass db = new DBClass();
        messages++;
        db.sendMsg(msg, username, id);
    }
    public int getNumChat(){
        return messages;
    }
    public String checkStart(){
        return enemy;
    }
    public String checkSurrender() {
        if(end){
            return "surrender";
        }
        return "false";
    }
    
}
