/**
* The Game2048 class.
*
* This class represents a 2048 game.
* It contains all the logics need to 
* enforce the rules of the game.
*/

import java.lang.Math;
import java.io.*;
import java.util.*;

public class Game2048 {
   // =========================================================================
   // Constants and variables required by other classes
   // Do not change the names nor delete them
   // You may modify the value of the constants

   public static final int NUM_ROW = 4;
   public static final int NUM_COLUMN = 4;

   // The number user must reach to win. It should be a power of 2
   public static final int WINNING_NUMBER = 2048;

   public static final int LEFT = 0;
   public static final int DOWN = 1;
   public static final int RIGHT = 2;
   public static final int UP = 3;

   private Game2048GUI gui;

   // ======================================================================

   // === *** Your "global" constants & variables can be added starting here ***
   // ===//

   // Global Constant Declaration
   public static final int EMPTY_BOX = -1;
   public static final int ROW_1 = 0;
   public static final int ROW_2 = 1;
   public static final int ROW_3 = 2;
   public static final int ROW_4 = 3;
   public static final int ROW_LENGTH = 4;

   // public static final int COL_1 = 0;
   // public static final int COL_2 = 1;
   public static final int COL_3 = 2;
   public static final int COL_4 = 3;
   public static final int COL_LENGTH = 4;

   public static final String SAVE_FILE = "ExampleSave.txt";
   // public static final String ICON_FILE_FOLDER =
   // "C:/Users/Kelly/Desktop/ICS3U-2048Game";

   // Global Variable Declaration
   private static int grid[][] = new int[NUM_ROW][NUM_COLUMN];
   private static int userScore = 0;

   /**
    * Constructs Game2048 object.
    *
    * @param gameGUI The GUI object that will be used by this class.
    */
   public Game2048(Game2048GUI gameGUI) {
      gui = gameGUI;
   }

   // Method Declaration (all methods are public non static)
   /*
    * Method name: "initializeGrid"
    * Return type: void
    * Parameters: none
    * Description: initialize playing grid array with empty box (-1) value
    */
   public void initializeGrid() {

      // for loop to initialize grid with empty box
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            grid[i][j] = -1;
         }
      }
   }

   /*
    * Method name: "testGrid"
    * Return type: void
    * Parameters: none
    * Description: Initializes the grid with a value, for debugging purposes only.
    */
   public void testGrid() {

      // for loop to initialize grid with 1024
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            grid[i][j] = 1024;
         }
      }
   }

   /*
    * Method name: "setRandomSquares"
    * Return type: void
    * Parameters: none
    * Description: chooses an empty cell (random row, random column) and spawns
    * either a 4 tile (0.1 chance) or a 2 (0.9 chance) in that cell.
    */
   public void setRandomSquares() {
      // Variable Declaration
      int randRow, randCol, numberToAdd;

      // pick a random empty cell
      randRow = (int) (Math.random() * NUM_ROW);
      randCol = (int) (Math.random() * NUM_COLUMN);
      while (grid[randRow][randCol] != EMPTY_BOX) {
         randRow = (int) (Math.random() * NUM_ROW);
         randCol = (int) (Math.random() * NUM_COLUMN);
      }

      // decide which tile to add
      if (Math.random() > 0.9) {
         numberToAdd = 4;
      } else {
         numberToAdd = 2;
      }

      // place the tile
      grid[randRow][randCol] = numberToAdd;
   }

   /*
    * Method name: "shiftRowleft"
    * Return type: int[]
    * Return Description: Returns the given row as an array after the shift is
    * completed.
    * Parameters: int[] row
    * Parameter Description: Grabs a specified row as an array from the grid.
    * Description: Merges elements in a specified row using a 3 step process:
    * 1) pushes all the elements in the specified direction to eliminate gaps,
    * 2) determine if merges are possible and if so, merge the elements starting
    * from the leftmost element,
    * 3) pushes all the elements again to eliminate gaps after merging
    */
   public int[] shiftRowleft(int[] row) {
      // Variable Declaration
      int[] result = new int[ROW_LENGTH];

      // row length is 4
      for (int i = 0; i < ROW_LENGTH; i++) {
         result[i] = row[i];
      }

      // stage1: left-to-right shift all elementsto the left
      // i.e. 0,2,0,2 -> 2,2,0,0
      for (int i = 0; i < ROW_LENGTH; i++) {
         if (result[i] != EMPTY_BOX) {
            for (int j = i; j > 0; j--) { // was j > -1
               if (result[j - 1] == EMPTY_BOX) {
                  int temp = result[j];
                  result[j] = result[j - 1];
                  result[j - 1] = temp;
               }
            }
         }
      }

      // stage 2: combine elements
      // i.e. 2,2,0,0 -> 4,0,0,0
      for (int i = 0; i < ROW_4 - 1; i++) {
         if (result[i] == result[i + 1] && result[i] != EMPTY_BOX) {
            result[i] *= 2;
            result[i + 1] = EMPTY_BOX;
            userScore += result[i];
         }
      }

      // stage 3: continuing left-to-right-shift after merge of elements in the
      for (int i = 0; i < ROW_LENGTH; i++) {
         if (result[i] != EMPTY_BOX) {
            for (int j = i; j > 0; j--) { // was j > -1
               if (result[j - 1] == EMPTY_BOX) {
                  int temp = result[j];
                  result[j] = result[j - 1];
                  result[j - 1] = temp;
               }
            }
         }
      }

      return result;
   }

   /*
    * Method name: "shiftRowRight"
    * Return type: int[]
    * Return Description: Returns the given row as an array after the shift is
    * completed.
    * Parameters: int[] row
    * Parameter Description: Grabs a specified row as an array from the grid.
    * Description: Merges elements in a specified row using a 3 step process:
    * 1) pushes all the elements in the specified direction to eliminate gaps,
    * 2) determine if merges are possible and if so, merge the elements starting
    * from the rightmost element,
    * 3) pushes all the elements in the specified direction again to eliminate gaps
    * after merging
    */
   public int[] shiftRowRight(int[] row) {
      // Variable Declaration
      int[] result = new int[ROW_LENGTH];

      // copy input parameter
      for (int i = 0; i < ROW_LENGTH; i++) {
         result[i] = row[i];
      }

      // stage 1: right-to-left shift all elements to the right
      for (int i = ROW_4; i >= ROW_1; i--) {
         if (result[i] != EMPTY_BOX) {
            for (int j = i; j < 3; j++) {
               if (result[j + 1] == EMPTY_BOX) {
                  int temp = result[j];
                  result[j] = result[j + 1];
                  result[j + 1] = temp;
               }
            }
         }
      }

      // stage 2: combine elements (e.g. 0,0,2,2 -> 0,0,0,4)
      for (int i = ROW_4 - 1; i > -1; i--) {
         if (result[i] == result[i + 1] && result[i] != EMPTY_BOX) {
            result[i + 1] = EMPTY_BOX;
            result[i] *= 2;
            userScore += result[i];
         }
      }

      // stage 3: redo right-to-left shift
      for (int i = ROW_4; i >= ROW_1; i--) {
         if (result[i] != EMPTY_BOX) {
            for (int j = i; j < 3; j++) {
               if (result[j + 1] == EMPTY_BOX) {
                  int temp = result[j];
                  result[j] = result[j + 1];
                  result[j + 1] = temp;
               }
            }
         }
      }

      return result;
   }

   /*
    * Method name: "validMove"
    * Return type: boolean
    * Return Description: Returns if a move can be made (true) or if a move can't
    * be made (false)
    * Parameters: none
    * Description: makes a copy of the current game grid and try merging in all
    * directions.
    * If a merge can be made (the shifted array is different from the original),
    * return TRUE.
    * Once all directions are exhausted, and all shifted arrays are the same as the
    * original, return FALSE.
    */
   public boolean validMove() {
      // Variable Declaration
      int[][] testGrid = new int[NUM_ROW][NUM_COLUMN];

      // Make a copy of the current grid
      int[][] original = new int[NUM_ROW][NUM_COLUMN];
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            original[i][j] = grid[i][j];
         }
      }

      // Try all four directions

      // LEFT
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            testGrid[i][j] = original[i][j];
         }
         int[] shifted = shiftRowleft(testGrid[i]);
         if (!Arrays.equals(shifted, original[i])) {
            return true;
         }
      }

      // RIGHT
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            testGrid[i][j] = original[i][j];
         }
         int[] shifted = shiftRowRight(testGrid[i]);
         if (!Arrays.equals(shifted, original[i])) {
            return true;
         }
      }

      // UP
      for (int col = 0; col < NUM_COLUMN; col++) {
         int[] column = new int[NUM_ROW];
         for (int row = 0; row < NUM_ROW; row++) {
            column[row] = original[row][col];
         }
         int[] shifted = shiftRowleft(column);
         for (int row = 0; row < NUM_ROW; row++) {
            if (shifted[row] != original[row][col]) {
               return true;
            }
         }
      }

      // DOWN
      for (int col = 0; col < NUM_COLUMN; col++) {
         int[] column = new int[NUM_ROW];
         for (int row = 0; row < NUM_ROW; row++) {
            column[row] = original[row][col];
         }
         int[] shifted = shiftRowRight(column);
         for (int row = 0; row < NUM_ROW; row++) {
            if (shifted[row] != original[row][col]) {
               return true;
            }
         }
      }

      // No valid moves found
      return false;
   }// end of validMoves method

   /*
    * Method name: "newGame"
    * Return type: void
    * Parameters: none
    * Description: calls initialize grid method, sets score to 0, calls random
    * squares method twice, connects grid and score to frontend using gui API
    */
   public void newGame() {
      // Variable Declaration

      // initialize grid
      initializeGrid();
      // set score to 0
      userScore = 0;
      gui.setScore(userScore);
      // Set the Random Squares
      setRandomSquares();
      setRandomSquares();
      // testGrid();
      // System.out.println("sigma3");
      // connect grid to frontend using api
      // for (int[] i : grid) {
      // for (int j : i) {
      // System.out.print(j);
      // System.out.print(" ");
      // }
      // System.out.print("\n");
      // }
      gui.displayGrid(grid);
      // System.out.println("sigma4");

   }// end of newGame method

   /*
    * Method name: "move"
    * Return type: void
    * Parameters: int direction
    * Parameter Description: direction based on the global constant (e.g. left is
    * 0.)
    * Description: First, check if win/lose condition is reached.
    * If none of them are reached, check direction.
    * Once direction is determined, shift and merge rows/columns by calling the
    * respective method and looping.
    */
   public void move(int direction) {
      // variable declaration
      boolean changeMade = false;
      int[][] copiedArray = new int[NUM_ROW][NUM_COLUMN];
      // copy array
      for (int i = 0; i < ROW_LENGTH; i++) {
         for (int j = 0; j < COL_LENGTH; j++) {
            copiedArray[i][j] = grid[i][j];
         }
      }

      // debug code
      // System.out.println("valid move is: " + validMove());
      // System.out.println("MOVING " + direction);

      // check if moves are possible. if not, end game in a lose & allow for
      // restart/exit
      if (!validMove()) {
         // System.out.println("No valid moves available!!!! !!!!!!!!!!!!!!!!!!!!");
         gui.showGameOver();
         if (gui.showPlayAgain()) {
            newGame();
         }
         return;
      }

      // check if 2048 is reached. if yes, end game in win & allow for restart/exit
      for (int i = 0; i < ROW_LENGTH; i++) {
         for (int j = 0; j < COL_LENGTH; j++) {
            if (grid[i][j] == 2048) {
               gui.showGameWon();
               if (gui.showPlayAgain()) {
                  newGame();
               }

               return;
            }
         }
      }

      // System.out.println("move method called with direction: " + direction);
      // depending on the direction of the arrow key, call its corresponding method
      if (direction == LEFT) {
         // System.out.println("Moving left");
         // shift all rows to the left (loop row shift method for each row)
         for (int i = 0; i < NUM_ROW; i++) {
            grid[i] = shiftRowleft(grid[i]);
         }
      } else if (direction == RIGHT) {
         // System.out.println("Moving right");
         // shift all rows to the right
         for (int i = 0; i < NUM_ROW; i++) {
            grid[i] = shiftRowRight(grid[i]);
         }
      } else if (direction == UP) {
         // System.out.println("Moving up");
         // shift all columns up (loop column shift method for each column)
         for (int i = 0; i < NUM_COLUMN; i++) {
            int[] column = new int[NUM_ROW];
            for (int j = 0; j < NUM_ROW; j++) {
               column[j] = grid[j][i];
            }
            column = shiftRowleft(column);
            for (int j = 0; j < NUM_ROW; j++) {
               grid[j][i] = column[j];
            }
         }
      } else if (direction == DOWN) {
         // System.out.println("Moving down");
         // shift all columns down
         for (int i = 0; i < NUM_COLUMN; i++) {
            int[] column = new int[NUM_ROW];
            for (int j = 0; j < NUM_ROW; j++) {
               column[j] = grid[j][i];
            }
            column = shiftRowRight(column);
            for (int j = 0; j < NUM_ROW; j++) {
               grid[j][i] = column[j];
            }
         }
      }

      // set change made to true if a change was made
      for (int i = 0; i < ROW_LENGTH; i++) {
         for (int j = 0; j < COL_LENGTH && changeMade == false; j++) {
            if (copiedArray[i][j] != grid[i][j]) {
               changeMade = true;
            }
         }
      }
      // check if the move actually changed anything
      if (changeMade) {
         setRandomSquares(); // add a new random square after each valid move
      } else {
         // System.out.println("UR MOVE DIDNT MERGE OR CHANGE ANYTHING
         // RAHHHHHHHHHHHHHHHHR");
      }
      // connect grid to frontend using api
      gui.displayGrid(grid);
      // connect score to frontend using api
      gui.setScore(userScore);

      // test print array
      // for (int[] i : grid) {
      // for (int j : i) {
      // System.out.print(j);
      // System.out.print(" ");
      // }
      // System.out.print("\n");
      // }
      // System.out.println("akdkdlakdj");
   }// end of move method

   /*
    * Method name: "saveToFile"
    * Return type: boolean
    * Return Description: if the write is successful, return true. if the write is
    * unsuccessful (IOException), return false.
    * Parameters: String fileName
    * Parameter Description: name of the file you want to write to (prompted in the
    * GUI)
    * Description: saves game board to file, in the form of ExampleSave.txt, where
    * -1 represents an empty box, and the last line represents the score.
    */
   public boolean saveToFile(String fileName) {
      // System.out.println("WRITING TO FILE FORLLOWING: ");
      try {
         BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
         for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
               br.write(String.valueOf(grid[i][j]) + " ");
               // System.out.print(String.valueOf(grid[i][j]) + " ");
            }
            br.newLine();
            // System.out.print("\n");
         }

         // WRITE SCORE HERE
         br.write(String.valueOf(userScore));
         // System.out.print(userScore);
         br.close();
         return true;
      } catch (IOException e) {
         return false;
      }
   }// end of save method

   /*
    * Method name: "loadFromFile"
    * Return type: boolean
    * Return Description: if the load is successful, return true. if the load is
    * unsuccessful (IOException), return false.
    * Parameters: String fileName
    * Parameter Description: name of the file you want to load from (prompted in
    * the GUI)
    * Description: writes game board to grid array, and writes the last line to the
    * score.
    */
   public boolean loadFromFile(String fileName) {
      // Variable declaration
      boolean load = false;

      // read file using scanner class
      // Try catch
      try {
         BufferedReader in = new BufferedReader(new FileReader(fileName));
         // initialize array
         initializeGrid();

         // Initilaize scanner to read file input and save to grid array
         Scanner fs = new Scanner(new File(fileName));
         for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
               grid[i][j] = fs.nextInt();
               System.out.print("" + grid[i][j] + " ");
            }
            System.out.println();
         }
         // scan last integer (should be the score)
         userScore = fs.nextInt();
         gui.setScore(userScore);
         // System.out.println("THE ASHDAJSGDHJFFGD SCORE IS " + userScore);
         in.close();
         load = true;
      } catch (IOException e) {
         load = false;
      }

      // since newGame doesn't run, display grid after assigning values to it
      gui.displayGrid(grid);

      // return
      return load;
   }// end of load method
}// end of class
