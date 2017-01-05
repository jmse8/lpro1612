/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class AudioPlayer{
   
    int flag;
    File soundFile;
    AudioInputStream audioIn;
    Clip clip;
    String s;
    
    public void getName(String s){
        this.s = s;
    }
   
   public void soundBegin() {
        
      try {
         
         // Open an audio input stream.
         soundFile = new File(s);
         audioIn = AudioSystem.getAudioInputStream(soundFile);
         AudioFormat format = audioIn.getFormat();
         // Get a sound clip resource.
         clip = AudioSystem.getClip();
          // Open audio clip and load samples from the audio input stream.
         
         clip.open(audioIn);        
      
        
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
 
  }

    public int getRun(){
         if(clip.isRunning())
             flag = 1;
         else
             flag = 0;
         return flag;
    }

    
    public void pause(){
        clip.stop();
    }
    
    public void start(){
        clip.start();
        
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void resume(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
