/** 
 *	File Name: Game2048Listener.java
 *	Name: Ms. I. Lam
 *	Course: ICS3U1
 *	Date: May 6, 2025
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * Game2048Listener class manages user keyboard inputs.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
 * It implements KeyListener class
 */
public class Game2048Listener implements KeyListener {
   private Game2048GUI gui;
   private Game2048 game;

/**
 * Constructs the Game2048Listener object by taking in the <i>game</i> and the <i>gui</i>.
 *
 * @param game		The game object that contains the logic.
 * @param gui		The GUI object of the game.
 */     
   public Game2048Listener (Game2048 game, Game2048GUI gui) {
      this.game = game;
      this.gui = gui;
      gui.addListener (this);
   }

/**
 * Overriding the keyPressed method from KeyListener class. Invoked when a key has been pressed. See the class description for KeyEvent for a definition of a key pressed event.
 *
 * @param e			Contains the event from the user's keyboard input.
 */     
   public void keyPressed(KeyEvent e) {
      int direction = -1;
      int key = e.getKeyCode();
     
      if( key == KeyEvent.VK_LEFT) {
         direction = Game2048.LEFT; 
      } else if( key == KeyEvent.VK_DOWN) {
         direction = Game2048.DOWN; 
      } else if( key == KeyEvent.VK_RIGHT) {
         direction = Game2048.RIGHT; 
      } else if( key == KeyEvent.VK_UP) {
         direction = Game2048.UP; 
      }
     
      game.move(direction); 
   
   }

/**
 * Overriding the keyReleased method from KeyListener class. Invoked when a key has been released. See the class description for KeyEvent for a definition of a key released event.
 *
 * @param e			Contains the event from the user's keyboard input.
 */     	
   public void keyReleased(KeyEvent e) {
   }

/**
 * Overriding the keyTyped method from KeyListener class. Invoked when a key has been typed. See the class description for KeyEvent for a definition of a key typed event.
 *
 * @param e			Contains the event from the user's keyboard input.
 */  	
   public void	keyTyped(KeyEvent e) {
   }
}

/**
 * Game2048ButtonListener class manages button input. 
 * It implements ActionListener class
 */
class Game2048ButtonListener implements ActionListener {
   final String SAVEGAMEBUTTON = "Save Game";
   final String LOADGAMEBUTTON = "Load Game";
   final String RESTARTGAMEBUTTON = "Restart Game";
   final String EXITBUTTON = "Exit";
   
   private Game2048GUI gui;
   private Game2048 game;
   
   public Game2048ButtonListener (Game2048 game, Game2048GUI gui) {
      this.game = game;
      this.gui = gui;
      gui.addListener (this);
   }

   public void actionPerformed(ActionEvent e) {
      // detect which button is clicked
      String button = e.getActionCommand();
      
      if (button.equals(EXITBUTTON)) {
         // exit game
         System.exit(0);
      } else if (button.equals(RESTARTGAMEBUTTON)) {
         // restart game
         game.newGame();   
      } else {
         // save or load game
         // prompt user for the file name
         String fileName = JOptionPane.showInputDialog(null, "File Name: ", "File Name", JOptionPane.QUESTION_MESSAGE);   
         if (fileName != null) {
            if (button.equals(SAVEGAMEBUTTON)) {
               if (!game.saveToFile(fileName)) {
                  JOptionPane.showMessageDialog(null, "Problem Saving Game!", "Error", JOptionPane.PLAIN_MESSAGE, null); 
               }
            } else if (button.equals(LOADGAMEBUTTON)) {
               if (!game.loadFromFile(fileName)) {
                  JOptionPane.showMessageDialog(null, "Problem Loading Game!", "Error", JOptionPane.PLAIN_MESSAGE, null);           
               } 
            }
         }
      }
      // Game frame regain focus to detect keyboard input
      gui.gameWindowRequestFocus(); 
   }
}