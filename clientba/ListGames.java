/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientba;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Inicializa uma nova lista de jogos para selectionar.
 * @author lpro1612
 */
public class ListGames {
    int port=1612;
    /**
     * Implementa a lista de jogos que o utilizador pode selecionar para jogar, assistir ou rever.
     * @return Um vector de strings com as informações de cada jogo em cada posição do vector
     */
    
     public String[] fillGames(){
        
        
            Protocol p = new Protocol(port);
            String[] output={"activeGames"};
            List<String> input=p.getFromServer(output);
            int i=0;
            String dados[]=new String[input.size()];
          
           for(i=0;i<input.size(); i++){    
                 //String[] dados = input.get(i).split("::"); 
                 dados[i] = input.get(i);
                /* javax.swing.JButton join = new javax.swing.JButton();
                 join.setText("join");
                 join.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        //joinActionPerformed(evt);
                     }
                });*/
                         
           }
           return dados;
    }
    
}
