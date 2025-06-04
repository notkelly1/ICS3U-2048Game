/**
* The Game2048 class.
*
* This class represents a 2048 game.
* It contains all the logics need to 
* enforce the rules of the game.
*/

import java.lang.Math;
import java.io.*;

public class Game2048 {
// =========================================================================
// Constants and variables required by other classes
// Do not change the names nor delete them
// You may modify the value of the constants

   public static final int NUM_ROW = 4;
   public static final int NUM_COLUMN = 4;
   
   // The number user must reach to win.  It should be a power of 2
   public static final int WINNING_NUMBER = 2048; 
   
   public static final int LEFT 	= 0;
   public static final int DOWN	= 1;
   public static final int RIGHT = 2;
   public static final int UP 	= 3;

   private Game2048GUI gui;

// ======================================================================

//=== *** Your "global" constants & variables can be added starting here *** ===//

   // Global Constant Declaration
   public static final String SAVE_FILE = "ExampleSave.txt";
   
   // Global Variable Declaration
   public static int grid[][];


/**
 * Constructs Game2048 object.
 *
 * @param gameGUI	The GUI object that will be used by this class.
 */   
   public Game2048(Game2048GUI gameGUI) {
      gui = gameGUI;
   }

// Method Declaration
   public static void newGame(){
   }// end of newGame method
   
   public static void move(int direction){
   }// end of move method
   
   public static boolean saveToFile(String fileName){
      // Variable declaration
      boolean save = false;
      
      // return
      return save;
   }// end of save method
   
   public static boolean loadFromFile(String fileName){
      // Variable declaration
      boolean load = false;
      
      // return
      return load;
   }// end of load method
}// end of class 