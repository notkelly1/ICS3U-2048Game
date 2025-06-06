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
   
   public static final int COL_1 = 0;
   public static final int COL_2 = 1;
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
   Method name: "initializeGrid"
   Return type: void 
   Parameters: none
   Description: initialize playing grid array with empty box (-1) value
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
   Method name: "testGrid"
   Return type: void 
   Parameters: none
   Description: Initializes the grid with a value, for debugging purposes only.
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
   Method name: "setRandomSquares"
   Return type: void 
   Parameters: none
   Description: chooses an empty cell (random row, random column) and spawns either a 4 tile (0.1 chance) or a 2 (0.9 chance) in that cell.
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
   Method name: "shiftRowleft"
   Return type: int[]
   Return Description: Returns the given row as an array after the shift is completed.
   Parameters: int[] row
   Parameter Description: Grabs a specified row as an array from the grid.
   Description: Merges elements in a specified row using a 3 step process: 
   1) pushes all the elements in the specified direction to eliminate gaps,
   2) determine if merges are possible and if so, merge the elements starting from the leftmost element,
   3) pushes all the elements again to eliminate gaps after merging
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
      // for (int i = ROW_4; i > 0; i--) {
         
      //    // case: 3 consecutive elements are the same
         
      //    if(i >= 2 && result[i] == result[i - 1] && result[i] == result[i - 2] && result[i] != EMPTY_BOX){
      //       // since this is already in shift row left,
      //          // merge starting from the leftmost end
      //          // i.e. 2, 2, 2 ,0 -> 4, 0, 2, 0 -> (stage 3: 4, 2, 0, 0)
      //          System.out.println("LEFTLEFTLEFT i should be MERGING this row to the LEFT but kelly cant code");
      //    }
               
      //    // case: elements are the same
      //    if (result[i] == result[i - 1] && result[i] != EMPTY_BOX) {
      //       // set element to 0, set previous element to double
      //       // i.e. ..,2,2,... -> ...,4,0,...
      //       // result[i] = EMPTY_BOX;
      //       // result[i - 1] *= 2; 
      //       result[i] *= 2;
      //       result[i - 1] = EMPTY_BOX;
      //    }
      // }
      for (int i = 0; i < ROW_4 - 1; i++) {
         if (result[i] == result[i + 1] && result[i] != EMPTY_BOX) {
            result[i] *= 2;
            result[i + 1] = EMPTY_BOX;
            userScore += result[i];
         }
      }

      // stage 3: continuing left-to-right-shift after merge of elements in the direction of key pressed
      // i.e. 4,0,4,0 -> 4,4,0,0
      //for (int i = 0; i < 4; i++) {
      // for (int i = 0; i < ROW_4; i++){
      //    if (result[i] != EMPTY_BOX) {
      //       for (int j = i; j > 0; j--) { // was j > -1
      //          if (result[j - 1] == EMPTY_BOX) {
      //             int temp = result[j];
      //             result[j] = result[j - 1];
      //             result[j - 1] = temp;
      //          }
      //       }
      //    }
      // }
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
   Method name: "shiftRowRight"
   Return type: int[]
   Return Description: Returns the given row as an array after the shift is completed.
   Parameters: int[] row
   Parameter Description: Grabs a specified row as an array from the grid.
   Description: Merges elements in a specified row using a 3 step process: 
   1) pushes all the elements in the specified direction to eliminate gaps,
   2) determine if merges are possible and if so, merge the elements starting from the rightmost element,
   3) pushes all the elements in the specified direction again to eliminate gaps after merging
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
      // for (int i = ROW_1; i < ROW_4; i++) {
      //    // case: 3 consecutive elements are the same 
      //     if( (i + 2 < ROW_LENGTH) && result[i] == result[i + 1] && result[i] == result[i + 2] && result[i] != EMPTY_BOX){
      //       // since this is already in shift row right,               
      //          System.out.println("RIGHTRIGHTRIGHTRIGHT i should be MERGING this row to the RIGHT but kelly cant code");
      //          // merge starting from the rightmost end 
      //          // i.e. 2, 2, 2 ,0 -> 2, 0, 4, 0 -> (stage 3: 0, 0, 2, 4) 
      //    }
         
      //    if (result[i] == result[i + 1] && result[i] != EMPTY_BOX) {
      //       result[i + 1] = EMPTY_BOX;
      //       result[i] *= 2;
      //    }
      // }
      for (int i = ROW_4 - 1; i > -1; i--) {
         if (result[i] == result[i + 1] && result[i] != EMPTY_BOX) {
            result[i + 1] = EMPTY_BOX;
            result[i] *= 2;
            userScore += result[i];
         }
      }

      // stage 3: redo right-to-left shift
      // for (int i = ROW_4; i >= ROW_1; i--) {
      //    if (result[i] != EMPTY_BOX) {
      //       for (int j = i; j < ROW_4; j++) {
      //          if (result[j + 1] == EMPTY_BOX) {
      //             int temp = result[j];
      //             result[j] = result[j + 1];
      //             result[j + 1] = temp;
      //          }
      //       }
      //    }
      // }
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
   Method name: "validMove"
   Return type: boolean
   Return Description: Returns if a move can be made (true) or if a move can't be made (false)
   Parameters: none
   Description: makes a copy of the current game grid and try ....
   */
   public boolean validMove() {
      // Variable Declaration
      
      // Make a copy of the current grid
      int[][] original = new int[NUM_ROW][NUM_COLUMN];
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
               original[i][j] = grid[i][j];
         }
      }

      // Try all four directions
      boolean canMove = false;
      int[][] testGrid = new int[NUM_ROW][NUM_COLUMN];

      // LEFT
      for (int i = 0; i < NUM_ROW; i++) {
         // arraycopy copies the original row to the testGrid row
         System.arraycopy(original[i], 0, testGrid[i], 0, NUM_COLUMN);
         int[] shifted = shiftRowleft(testGrid[i]);
         if (!Arrays.equals(shifted, original[i])) {
               return true;
         }
      }

      // RIGHT
      for (int i = 0; i < NUM_ROW; i++) {
         System.arraycopy(original[i], 0, testGrid[i], 0, NUM_COLUMN);
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
      System.out.println("balls3");
      // connect grid to frontend using api
      for (int[] i : grid) {
         for (int j : i) {
            System.out.print(j);
            System.out.print(" ");
         }
         System.out.print("\n");
      }
      gui.displayGrid(grid);
      System.out.println("balls4");

   }// end of newGame method

   public void move(int direction) {
      System.out.println("valid move is: " + validMove());
      System.out.println("MOVING " + direction);
      if (!validMove()) {
         System.out.println("No valid moves available!!!! !!!!!!!!!!!!!!!!!!!!");
         return;
      }
      System.out.println("move method called with direction: " + direction);
      // depending on the direction of the arrow key, call its corresponding method 
      if (direction == LEFT) {
         System.out.println("Moving left");
         // shift all rows to the left (loop row shift method for each row)
         for (int i = 0; i < NUM_ROW; i++) {
            grid[i] = shiftRowleft(grid[i]);
         }
      } else if (direction == RIGHT) {
         System.out.println("Moving right");
         // shift all rows to the right
         for (int i = 0; i < NUM_ROW; i++) {
            grid[i] = shiftRowRight(grid[i]);
         }
      } else if (direction == UP) {
         System.out.println("Moving up");
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
         System.out.println("Moving down");
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

      setRandomSquares(); // add a new random square after each move
      // connect grid to frontend using api
      gui.displayGrid(grid);
      // connect score to frontend using api
      gui.setScore(userScore);
   }// end of move method

   public boolean saveToFile(String fileName) {
      // Variable // declaration
//       boolean save = false;
//       
//       // return
//       return save;
      try {
         BufferedWriter br = new BufferedWriter(new FileWriter(SAVE_FILE));
         for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < COL_LENGTH; j++) {
                br.write(String.valueOf(grid[i][j]));
            }
            br.write("\n");
         }
         
         //WRITE SCORE HERE AJDKLFJADLKFJSLKDJFLKASJFDLKJSALKFJLKSADJFLKAJFLKSAJLKFJDAKFJSALKJFLKSAJFLKJDLKAFJLKSAFJLKJSLKFJSDA
         br.write(userScore);
         return true;
      } catch (IOException e) {
          return false;
      }
   }// end of save method

   public boolean loadFromFile(String fileName) {
      // Variable declaration
      boolean load = false;
      
      // read file using scanner class
      // Try catch
      try
      {
         BufferedReader in = new BufferedReader(new FileReader(fileName));
         // initialize array?????????????????????? (since newGame doesn't run)
         initializeGrid();
         
         // Initilaize scanner to read file input and save to grid array
         Scanner fs = new Scanner(new File(fileName));
         for(int i = 0; i < ROW_LENGTH; i++)
         {
            for(int j = 0; j < COL_LENGTH; j++)
            {
               grid[i][j] = fs.nextInt();
               System.out.print(""+ grid[i][j] + " ");
            }
            //System.out.println();
         }
         // scan last integer (should be the score)
         userScore = fs.nextInt();
         gui.setScore(userScore);
         System.out.println("THE STUPID ASHDAJSGDHJFFGD SCORE IS " + userScore);
         in.close();
      }
      catch(IOException e){
      }
      
      // since newGame doesn't run, display grid after assigning values to it
      gui.displayGrid(grid);
      
      // return
      return load;
   }// end of load method
}// end of class