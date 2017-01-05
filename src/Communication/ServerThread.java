/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Logic.*;

/**
 * Classe que implementa um thread tratar um pedido de um cliente
 * @author lpro1612
 */
public class ServerThread extends Thread{
    private Socket socket = null;

    /**
     * Constrói e inicializa um objeto que ouvir do socket indicado
     * @param socket 
     */
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            String inputLine;
            List<String> outputLine = new ArrayList<>();
            inputLine = in.readLine();
            Protocol  pl = new Protocol();
           
            outputLine = pl.processInput(inputLine);
            if(outputLine.get(0).equals("wait_to_start")){
                while(outputLine.get(1) == null){
                    pl = new Protocol();
                    outputLine = pl.processInput(inputLine);
                }
                outputLine.remove(1);
                outputLine.remove(0);
                outputLine.add("READY TO START");
            }
            
            else if(outputLine.get(0).equals("wait_to_play")){
                String oldmoves = outputLine.get(1);
                while(outputLine.get(1) == oldmoves){
                    pl = new Protocol();
                    outputLine = pl.processInput(inputLine);
                }
                
                outputLine.remove(0);
                outputLine.add(0, "YOUR TURN");
            }
            
            for(String line : outputLine)
            {
                out.println(line);
            }
            
            out.close();
            in.close();
            
            socket.close();
        } 
        catch (IOException e) {
            System.out.println("ERROR ServerThread: " + e);
        }
    }
}
