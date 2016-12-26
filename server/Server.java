/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmse8
 * 
 */

/**
 * Classe que implementa o servidor
 * 
 * @author jmse8
 */

public class Server {
     
    ServerSocket serverSocket = null;
    static ExecutorService executor;
  
    /**
     * Cria um socket para a porta indicada
     * @param port porta a escutar 
     */
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
     * Recebe os pedidos dos clientes e cria threads que os vão tratar
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public void listenForClient() throws InterruptedException, ExecutionException
    {
        ServerThread server_thread;
        try
        {
            Runnable runnable = new ServerThread(serverSocket.accept());
            
            executor.submit(runnable); 
        }
          
        catch(IOException e)
        {
            System.out.println("Accept failed: " + e);
        }
        
        System.out.println("Client connected!");
    }    
    
    public static void main(String[] args) throws IOException
    {
        Server server = new Server();
        executor = Executors.newFixedThreadPool(50);
        
        server.CreateSocket(1612);
        
        while(true)
        {
            try{
                server.listenForClient();
            } 
            catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
            
    }
    
}
