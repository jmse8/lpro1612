/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Data.DBClass;
import java.io.Console;

import Logic.*;
import java.util.Scanner;

/**
 * Classe responsável pelo thread através do qual o administrador pode controlar o servidor
 * @author jmse8
 */
public class AdminThread extends Thread {
    static boolean start;
    static boolean stop;
    static boolean exit;
    
    public AdminThread()
    {
        super("adminThread");
        this.start = false;
        this.stop = false;
        this.exit = false;
        
        
    }
    
    public void run(){
        
        Scanner input = new Scanner(System.in);
        String line = null;
        String[] words;
        DBClass db = new DBClass();
        
        while(true){
            try{
            line = input.nextLine();
            }
            catch(NullPointerException e){
            }
            if(line == null)
                continue;
            words = line.split(" ");
            System.out.println(line);
            switch(words[0]){
                case "start":
                    start = true;
                    stop = false;
                    break;
                case "ban":
                    db.banUser(words[1]);
                    break;
                case "unban":
                    db.unbanUser(words[1]);
                    break;
                case "stop":
                    stop = true;
                    start = false;
                    break;
                case "exit":
                    start = false;
                    stop = false;
                    exit = true;
                    break;
            }
            
            if(exit==true)
                break;
             
        }
    }
}
