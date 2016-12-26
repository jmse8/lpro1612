/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author eduardo
 */
public class SaveGames {
    
    game[] games= new game[10000];
 
    
    public void newGame(int id){
        games[id]=new game();
    }
    public void play(int x, int y, String orientation,int id) {
        games[id].play(x,y,orientation);
    }
}
