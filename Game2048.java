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
    public static final int EMPTY_BOX = -1;
    //public static final String SAVE_FILE = "ExampleSave.txt";    
    //public static final String ICON_FILE_FOLDER = "C:/Users/Kelly/Desktop/ICS3U-2048Game";

    // Global Variable Declaration
    private static int grid[][] = new int [NUM_ROW][NUM_COLUMN];
 
 
 /**
  * Constructs Game2048 object.
  *
  * @param gameGUI	The GUI object that will be used by this class.
  */   
    public Game2048(Game2048GUI gameGUI) {
       gui = gameGUI;
    }
 
 // Method Declaration (all methods are public non static)
    
    public void initializeGrid(){
      
         // for loop to initialize grid with empty box
         for(int i = 0; i < NUM_ROW; i++){
            for(int j=0; j < NUM_COLUMN; j++){
               grid[i][j]=-1;
            }
         }
    }
    
    public void testGrid(){
      // for loop to initialize grid with empty box
         for(int i = 0; i < NUM_ROW; i++){
            for(int j=0; j < NUM_COLUMN; j ++){
               grid[i][j]=1024;
            }
         }
    }
    
    public void setRandomSquares() {
       // Variable Declaration
       int randRow;
       int randCol;
       int numberToAdd;
       
       randRow = (int) (Math.random() * 4); // random decimal between 0 and 1, cast to int
       randCol = (int) (Math.random() * 4);
       if (Math.random() > 0.9) {
           numberToAdd = 4;
       }
       else {
           numberToAdd = 2;
       }
       grid[randRow][randCol] = numberToAdd;
   }
   
   /* 
   public int[] shiftRowleft(int[] row) {
       int[] result = new int[4];
       
       // everything "4"here means the row length. its always 4 long
       for (int i = 0; i < 4; i++) {
           result[i] = row;
       }
       
       // stage1: left-to-right shift all elementsto the left
       // i.e. 0,2,0,2 -> 2,2,0,0
       for (int i = 0; i < 4; i++) {
           if (result[i] != EMPTY_BOX) {
               for (int j = i; j > -1; j--) {
                   if (result[j - 1] == 0) {
                       int temp = result[j];
                       result[j] = result[j - 1];
                       result[j - 1] = temp;
                   }
               }
           }
       }
       
       // stage 2: comvine
       
       // stage 3:redo left-to-right-shift
   }*/
    
    public void newGame(){
      // Variable Declaration
      
      // initialize grid
      initializeGrid();
      
      // Set the Random Squares
      setRandomSquares();
      setRandomSquares();
      //testGrid();
      System.out.println("balls3");
      // connect grid to frontend using api
      for (int[] i: grid) {
          for (int j: i) {
              System.out.print(j);
              System.out.print(" ");
          }
          System.out.print("\n");
      }
      gui.displayGrid(grid);
      System.out.println("balls4");
      
    }//end of newGame method
    
    public void move(int direction){
    }//end of move method
   
   public boolean saveToFile(String fileName){
      //Variable declaration
      boolean save = false;
      
      //return
      return save;
   }//end of save method
   
   public boolean loadFromFile(String fileName){
   //   Variable declaration
      boolean load = false;
      
      //return
      return load;
   }// end of load method
   
   public void addRandomTile() {
      
   }
}//end of class