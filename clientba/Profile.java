/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientba;

import java.util.List;

/**
 * Implementa um novo perfil de utilizador.  
 * @author lpro1612
 */
public class Profile {
    String username;
    int port=1612;
    /**
     * Constrói e inicializa um novo perfil relativo a um username.
     * @param username Username do utilizador do qual se pretende construir o perfil.
     */
    
    public Profile(String username) {
        this.username=username;
    }
    /**
     * Reúne as informações de cada utilizador a exibir na sua página de perfil.
     * @return Vector de strings com o primeiro nome na primeira posição, último nome segunda posição, email na terceira posição, número de jogos ganhos na quarta posição, número de jogos perdidos na quinta posição número de acertos na sexta posição. 
     */
    public String[] getInfo (){
        
        Protocol p = new Protocol(port);
        String[] output = {"getProfile", username};
        List<String> input = p.getFromServer(output);
        
        String[] dados = input.get(0).split("::");
        
        return dados;
    }
    
}
