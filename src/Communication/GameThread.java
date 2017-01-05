/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Data.DBClass;
import Logic.game;
import Logic.game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Responsável por executar um jogo.
 * @author eduardo
 */
public class GameThread extends Thread {
     private Socket socket = null;
    
     ServerSocket serverSocket = null;
      game newGame = new game();
     int port;
     
   
     public GameThread(int port) {
        super("GameThread");
        this.port = port;
    }
     
     @Override
     public void run() {
         CreateSocket(port);
        boolean finish=false;
        boolean live = false;

         while(!finish){
               System.out.println("TOU DENTRO DO GAME THREAD");
         System.out.println("CRIOU SOCKET");
          
         try{
             socket=serverSocket.accept();
         }catch(IOException io){
             System.out.println("Accept failed: " + io);
         }
         String inputLine = null;
         
          String players=gameProtocol("numPlayers");
          int numPlayers=Integer.parseInt(players);
          if(numPlayers==2 && live==false){
             gameProtocol("startGame"); 
             live=true;
          }
      
         try{
           
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            
            List<String> outputLine = new ArrayList<>();
            
         
                inputLine = in.readLine();
                System.out.println("in.readline");
                if(inputLine != null){
                    String result="false";
                    result=gameProtocol(inputLine);
                     out.println(result);
                     
                    out.close();
                    String words[]=inputLine.split("::");
                     if("checkEnemyMove".equals(words[0])){
                         String checkEnd=gameProtocol("checkEnd::"+words[2]);
                         if("true".equals(checkEnd)) finish = true; //para tentar: so retornar true para fechar se for o jogador q perdeu, usar inputLine para ter o jogador q está a aceder
                    }
                }
           
         }catch (IOException io){
             System.out.println("ERROR GameThread: " + io);
         }
           
          
         }
         gameProtocol("endGame");
     }

     public void CreateSocket(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting on " + port);
        }
        
        catch(IOException e)
        {
            System.out.println("Error ServerSocket: " + e);
        }
    }
    /**
     * Protocolo que é usado durante o jogo.
     * @param inputLine String com o comando que se pretende efectuar.
     * @return Uma String com o resultado da efecução.
     */
     private String gameProtocol(String inputLine){
         String result="false";
         String[] words;
         String state;
         words = inputLine.split("::");
        
        state = words[0];
       
          if(state.equals("placeBoat")){
             int x,y,boatNumber;
             String orientation,out,player;
              
             x=Integer.parseInt(words[1]);
             y=Integer.parseInt(words[2]);
             orientation=words[3];
             boatNumber=Integer.parseInt(words[4]);
             player=words[5];

           result= newGame.checkBoatPosition(x,y,orientation,boatNumber,player);
           System.out.println("esta a sair depois do check : "+result);
         }
          if(state.equals("makePlay")){
              int x,y;
              String player;
              x=Integer.parseInt(words[1]);
             y=Integer.parseInt(words[2]);
             player=words[3];
             
             result=newGame.makePlay(x,y,player);
          }
          
          if(state.equals("getLastPlay")){
              String player;
              player=words[1];
              
              result=newGame.getLastPlay(player);
              System.out.println("a ultma jogada foi: "+result);
          }
          
          if(state.equals("checkEnemyMove")){
              String player=words[2];
              String move=words[1];
              
              result=newGame.checkMove(move,player);
          }
          if(state.equals("checkTurn")){
              result = newGame.checkTurn();
          }
          if(state.equals("checkEnd")){
              String player = words[1];
              result=newGame.checkEnd(player);
          }
          if(state.equals("endGame")){
              DBClass db = new DBClass();
              int id = port-1024;
              db.endGame(id);
              newGame.endGame(id);
          }
          if(state.equals("numPlayers")){
              int players = newGame.numPlayers();
              result = Integer.toString(players);
          }
          if(state.equals("startGame")){
               DBClass db = new DBClass();
              int id = port-1024;
              db.gameIsLive(id);
              
          }
          if(state.equals("sendUsername")){
              String player = words[1];
              String username = words[2];
              newGame.sendUsername(player,username);
          }
          if(state.equals("chat")){
              String msg = words[1];
              String username = words[2];
              int id = port-1024;
              newGame.sendMsg(id, msg,username);
          }
          if(state.equals("checkChat")){
              int numMsg=newGame.getNumChat();
              result = Integer.toString(numMsg);
          }
          if(state.equals("getChatMsg")){
              int id = port-1024;
              DBClass db = new DBClass();
              result=db.getChatMsg(id);
          }
          if(state.equals("checkStart")){
              result=newGame.checkStart();
          }
          if(state.equals("checkSurrender")){
              result=newGame.checkSurrender();
          }
          if(state.equals("spectateGame")){
              System.out.println("spectatinggg");
              result=newGame.spectateGame(words[1]);
          }
          if(state.equals("checkNumMoves")){
              result=newGame.checkNumMoves(words[1]);
          }
         return result;
         
       
     }
    
}
