/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientba;
 
import java.io.*;
import java.net.*;

import java.util.*;


/**
 * Responsável pela comunicação com o Servidor
 *  @author lpro1612
 * 
 */
public class Client {
   
    String hostname;
    int port; 
    String dataToSend;
    List<String> receivedData;
 
   
    /**
     * 
     * Inicializa um objeto da classe Client com o hostname e a porta a conectar e os dados a enviar passados como argumentos
     * @param hostname hostname a conectar
     * @param port porta a conectar
     * @param dataToSend String a enviar
     */
    public Client(String hostname, int port, String dataToSend)
    {
        this.hostname = hostname;
        this.port = port;
        this.dataToSend = dataToSend;
        receivedData = new ArrayList<>();
    }
    
    /**
     * Estabelece a ligação ao Servidor 
     * Abre um socket, envia os dados e espera pela resposta, guardando-a no field receivedData
     *
     */
    
    public void ConnectToServer()
    {
        try{
            Socket serverSocket = new Socket(hostname, port);
             System.out.println("OK");
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            
        
            out.println(dataToSend);
            
            int i=0;
            
            while(true)
            {
                String s;
                s = in.readLine();
                System.out.println("o s no client é:"+s);
                if(s == null)
                    break;
                receivedData.add(s);
                System.out.println("recebiiiiii:");
                System.out.println(receivedData.get(i));
                
                i++;
            }
        }
        
        catch(IOException e)
        {
                System.out.println("IOException caught.");
        }
    }
    
    
}
