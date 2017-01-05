/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Communication.Protocol;
import java.util.List;

/**
 *Implementa a lógica de negócia responsável pela interface gráfica de Ranking.
 * @author eduardo
 */
public class Ranking {
     int port=1612;
     
    /**
     * Reúne todos os jogadores ordenados pelo número de vitórias. 
     * @return Um vector de strings com a informação dos jogadores a exibir.
     */
    public String[] fillRanking(){
            Protocol p = new Protocol(port);
            String[] output={"rankingUsers"};
            List<String> input=p.getFromServer(output);
            int i=0;
            String dados[]=new String[input.size()];
          
           for(i=0; i<input.size(); i++){    
                 dados[i] = input.get(i);       
           }
           return dados;
    }
    
}
