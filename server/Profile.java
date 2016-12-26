/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.List;

/**
 *
 * @author Diogo
 */
public class Profile {
    
    DBClass db = new DBClass();
    
    private final String[] data;

    public Profile(String[] data) {
        this.data = data;
    }
    
    public List<String> GetDataFromDB(){
            String username, password, email, firstName, lastName, gamesWon, gamesLost, hits;
            List<String> outputLine = null;
            username = data[1];
            firstName = db.getFirstName(username);
            lastName = db.getLastName(username);
            email = db.getEmail(username);
            gamesWon = db.getGamesWon(username);
            gamesLost = db.getGamesLost(username);
            hits = db.getHits(username);
            
            outputLine.add(username + "::" + firstName + "::" + lastName + "::" + email + "::" + gamesWon + "::" + gamesLost + "::" + hits);
            return outputLine;
    }
    
    
}
