/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Data.DBClass;
import java.util.ArrayList;

/**
 * Implementa a execução do jogo.
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
    
   /**
    * Verifica se a posição do barco pretendido é válida.
    * @param x - coordenada no eixo das abcissas.
    * @param y - coordanas no eixo das ordenadas.
    * @param orientation - Orientação do barco, vertical ou horizontal.
    * @param boatNumber - Número do barco que se pretendo colocar.
    * @param player - Username do jogador que está a colocar o barco.
    * @return Uma String com o resultado da verificação.
    */
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
    /**
     * Efectua uma jogada.
     * @param x - coordenada no eixo das abcissas.
     * @param y - coordenada no eixo das ordenadas.
     * @param player - Jogador que pretendo colocar o barco
     * @return String com o resultado da tentiva de jogada.
     */
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
    /**
     * Obtem a última jogada de um jogador.
     * @param player player1 se for o owner, player2 se for o adversário do owner.
     * @return Posição da última jogada efectuada pelo jogador pretendido.
     */
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
    /**
     * Verifica se a jogada efectuada foi um acerto ou uma falha.
     * @param move Coordenadas da jogada.
     * @param player Jogador que efectua a jogada.
     * @return String com o resultado da jogada.
     */
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
    /**
     * Verifica quem é o próximo jogador a jogar.
     * @return String com player1 caso seja o owner a jogar, player2 se for o adversário do owner,
     */
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
    /**
     * verifica quantos jogadores estão no jogo.
     * @return número de jogadores no jogo.
     */
    public int numPlayers(){
        return numPlayers;
    }
    /**
     * Envia o username do jogador presente no jogo.
     * @param player player1 se for owner, player2 se for o adversário.
     * @param username username do jogador.
     */
    public void sendUsername(String player, String username) {
        if("player1".equals(player)){
            owner=username;
        }
        else if("player2".equals(player)){
            enemy=username;
        }
    }
    /**
     * Termina um jogo.
     * @param id  Id do jogo a terminar.
     */
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
    /**
     * Envia uma mensagem para o chat de jogo.
     * @param id Id do jogo para o qual a mensagem foi enviada.
     * @param msg Mensagem que foi enviada.
     * @param username Username do jogador que enviou a mensagem.
     */
    public void sendMsg (int id, String msg, String username){
        DBClass db = new DBClass();
        messages++;
        db.sendMsg(msg, username, id);
    }
    /**
     * Obtem o número de mensagens no chat.
     * @return Número total de mensagens no chat.
     */
    public int getNumChat(){
        return messages;
    }
    /**
     * Verifica se um jogo está pronto a começar.
     * @return Uma string com o nome do jogador 2.
     */
    public String checkStart(){
        return enemy;
    }
    /**
     * Verifica se algum jogador desistiu do jogo.
     * @return Uma string com surrender caso alguem tenha desistido, "false" em caso contrário.
     */
    public String checkSurrender() {
        if(end){
            return "surrender";
        }
        return "false";
    }
    /**
     * obtem as informações a enviar para um observador.
     * @param player Jogador presente no jogo do qual se prente obter as informações.
     * @return Informações pretendidas.
     */
    public String spectateGame(String player){
        String rst="";
        
        if("player1".equals(player)){
            for(int i=0; i< positionsOwner.size();i++){
                rst=rst+ positionsOwner.get(i)+"::";
            }
        }
        
        else if ("player2".equals(player)) {
            for(int i=0; i< positionsEnemy.size();i++){
                rst=rst+ positionsEnemy.get(i)+"::";
            }
        }
        else if("moves_player1".equals(player)){
            for(int i=0; i< ownerPlays.size();i++){
                rst=rst+ ownerPlays.get(i)+"::";
            }
        }
        else if("moves_player2".equals(player)){
            for(int i=0; i< enemyPlays.size();i++){
                rst=rst+ enemyPlays.get(i)+"::";
            }
        }
        return rst;
    }
    /**
     * Verifica o n+umero de jogadas efectuadas por um jogador.
     * @param player player1 se for o owner, player2 se for o adversário.
     * @return string com o número de jogadas.
     */
    public  String checkNumMoves(String player){
        String numMoves="0";
        if("player1".equals(player)){
            return Integer.toString(ownerPlays.size());
        }
        else if ("player2".equals(player)) return Integer.toString(enemyPlays.size());
        
        return numMoves;
    }
    
}
