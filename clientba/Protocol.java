package clientba;

import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementa o protocolo de comunicação com o servidor
 * @author lpro1612
 */

public class Protocol {
    String hostname = "localhost";
    int port ;
    
    /**
     * Envia um vetor de Strings para o servidor
     * @param output elemento 0 - instrução a realizar pelo servidor; restantes elementos - dados necessários
     * @return Lista de Strings com a resposta do servidor
     */
    public Protocol(int port){
        this.port=port;
    }
    public List<String> sendToServer(String[] output)
    {
        String outputLine = null;
        switch (output[0]) {
            case "login":
                outputLine = output[0] + "::" + output[1] + "::" + output[2];
                break;
            case "register":
                outputLine = output[0] + "::" + output[1] + "::" + output[2] + "::"  + output[3] + "::"  + output[4] + "::" + output[5];
                break;
            case "recoverpassword":
                outputLine = output[0] + "::" + output[1];
                break;
            case "Game":
                outputLine=output[0] + "::" + output[1]+"::" + output[2]+ "::" + output[3]+ "::" + output[4];
                break;
            case "startGame":
                outputLine = output[0] + "::" + output[1]; 
                break;
            case "getOwner":
                outputLine = output[0] + "::" + output[1];
                break;
            case "place_boats":
                outputLine = output[0] + "::" + output[1] + "::" + output[2] + "::" + output[3];
                break;
            case "wait_to_start":
                outputLine = output[0] + "::" + output[1] + "::" + output[2];
                System.out.println(outputLine);
                break;
            case "wait_to_play":
                outputLine = output[0] + "::" + output[1] + "::" + output[2];
                System.out.println(outputLine);
                break;
            case "save_move":
                outputLine = output[0] + "::" + output[1] + "::" + output[2] + "::" + output[3];
                System.out.println(outputLine);
                break;
            case "placeBoat":
                outputLine = output[0] + "::" + output[1] + "::" +output[2] + "::"+ output[3] + "::"+ output[4] +"::"+output[5];
                System.out.println(outputLine);
                break;
            case "makePlay":
                outputLine = output[0] + "::" + output[1] + "::" +output[2] + "::"+ output[3];
                System.out.println(outputLine);
                break;
            case "getLastPlay":
                outputLine = output[0]+"::"+output[1];
                System.out.println(outputLine);
                break;
            case "checkEnemyMove":
                outputLine= output[0]+"::"+output[1]+"::"+output[2];
                System.out.println(outputLine);
                break;
            case "checkTurn":
                outputLine=output[0];
                System.out.println(outputLine);
                break;
            case "endGame":
                outputLine=output[0];
                System.out.println(outputLine);
                break;
            case "sendUsername":
                outputLine=output[0]+"::"+output[1]+"::"+output[2];
                System.out.println(outputLine);
                break;
            case "chat":
                outputLine=output[0]+"::"+output[1]+"::"+output[2];
                System.out.println(outputLine);
                break;
            case "checkChat":
                outputLine=output[0];
                System.out.println(outputLine);
                break;
            case "getChatMsg":
                outputLine=output[0];
                System.out.println(outputLine);
                break;
            case "checkStart":
                outputLine=output[0];
                System.out.println(outputLine);
                break;
            case "checkSurrender":
                outputLine=output[0];
                System.out.println(outputLine);
                break;
            default:
                return null;
        }
        
        Client cl = new Client(hostname, port, outputLine);
        cl.ConnectToServer();
        
        return cl.receivedData;
    }
    
    /**
     * Realiza um pedido ao servidor
     * 
     * @param output elemento 0 - instrução a realizar pelo servidor; restantes elementos - dados necessários
     * @return Lista de Strings com a resposta do servidor
     */
    public List<String> getFromServer(String[] output)
    {
        List<String> input = new ArrayList<>();
        String outputLine;
        Client cl = null;
        
        int i=0; 
        
        switch (output[0]) {
            case "getProfile":
                outputLine = output[0] + "::" + output[1];
                cl = new Client(hostname, port, outputLine);
                cl.ConnectToServer();
                break;
            case "getPlayers":
                outputLine = output[0];
                cl = new Client(hostname, port, outputLine);
                cl.ConnectToServer();
                break;
            case "activeGames":
                outputLine = output[0];
                cl = new Client(hostname, port, outputLine);
                cl.ConnectToServer();
                break;
            case "getGameId":
                outputLine=output[0]+"::"+output[1];
                cl = new Client(hostname, port, outputLine);
                cl.ConnectToServer();
                break;
            default:
                break;
        }
         
        for(i=0; i<cl.receivedData.size(); i++)
        {
         //   input[i].add(cl.receivedData.get(i).split("::"));
            input.add(cl.receivedData.get(i));
           
        }
        
        return input;
    }
}
