/** 
 *	File Name: Game2048GUI.java
 *	Name: Ms. I. Lam
 *	Course: ICS3U1
 *	Date: May 6, 2025
 */
 
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

/** 
 * The Game2048GUI class is responsible for managing all graphical user interface (GUI) components 
 * of the 2048 game, including the display of the logo, score, and game grid. 
 * This class handles only the visual representation and user interface interaction; 
 * it does not contain any of the core game logic.
 */

public class Game2048GUI {

/*
 ****************************
 * List of public constants *
 ****************************
 */
 
/**
 * The number of rows in the game grid 
 */
   final static int NUM_ROW = Game2048.NUM_ROW;

/**
 * The number of columns in the game grid
 */   
   final static int NUM_COLUMN = Game2048.NUM_COLUMN;

/*
 ***************************************************************
 * List of private constants to be used within this class only *
 * These following constants are used to create the GUI        *
 ***************************************************************
 */

/**
 * The size of the slot holding each number tile
 */     
   private final int PIECE_SIZE = 75;

/**
 * The thickness of the border between the slots on the grid
 */      
   private final int BORDER_THICKNESS = 3;

/**
 * The width of the game frame in pixel (calculated based on the size of each slot)
 */    
   private final int FRAME_WIDTH = (PIECE_SIZE * NUM_COLUMN) + (BORDER_THICKNESS * NUM_COLUMN * 2);

/**
 * The width of the logo banner in pixel
 */  
   private final int LOGO_WIDTH = FRAME_WIDTH;

/**
 * The height of the logo banner in pixel (logo width / 3)
 */    
   private final int LOGO_HEIGHT = LOGO_WIDTH / 3;

/**
 * The width of the score panel in pixel
 */    
   private final int SCORE_WIDTH = FRAME_WIDTH;

/**
 * The height of the score panel in pixel (logo height / 2)
 */    
   private final int SCORE_HEIGHT = LOGO_HEIGHT / 4;

/**
 * The width of the button panel in pixel
 */    
   private final int BUTTON_WIDTH = (PIECE_SIZE * NUM_COLUMN) + (BORDER_THICKNESS * NUM_COLUMN * 2);

/**
 * The height of the button panel in pixels (logo height / 2)
 */    
   private final int BUTTON_HEIGHT = LOGO_HEIGHT / 2;

/**
 * The height of the game JFrame in pixel
 */
   // + 25 is a trial on error value to have all components fits in the frame       
   private final int FRAME_HEIGHT = LOGO_HEIGHT + SCORE_HEIGHT + (PIECE_SIZE * NUM_ROW) + (BORDER_THICKNESS * NUM_ROW * 2) + BUTTON_HEIGHT + 25;

/**
 * The width of the game grid in pixel
 */    
   private final int GRID_WIDTH = NUM_COLUMN * PIECE_SIZE; //+ (BORDER_THICKNESS * NUM_COLUMN * 2);

/**
 * The height of the game grid in pixel
 */       
   private final int GRID_HEIGHT = NUM_ROW * PIECE_SIZE; // + (BORDER_THICKNESS * NUM_ROW * 2);

/**
 * The background color of the game grid
 */     
   private final Color GRID_BG_COLOR = Color.white;
   
/**
 * The background color of the score board
 */
   private final Color SCORE_BOARD_BG_COLOR = new Color(221, 232, 243);
   
/**
 * The settings for line border of the game grid
 */    
   private final LineBorder GRID_BORDER = new LineBorder (Color.gray, BORDER_THICKNESS, false);

/**
 * Filename prefix of the number tile image filenames
 */
   private final String NUMBERS_ICON_FILE_PREFIX = "icon";
   
/**
 * Name of folder where image files are stored
 */
   private final String ICON_FILE_FOLDER = "images";
      
/**
 * Filename of the logo banner image filename
 */         
   private final String LOGO_ICON_FILE = ICON_FILE_FOLDER + "/iconLogo.jpg";


/*
 **************************
 * List of private fields *
 **************************
 */

/**
 * An array that stores all the filename of the <i>slots</i> image filename. 
 * Index 0 contains the filename of the image for value 2, index 1 
 * contains the image for value 4 - index n for image for value 2^(n+1)
 */         
   private String[] slotIconFile;

// The game's graphics components: JFrame, JLabel and JButton

/**
 * The main JFrame of the game.
 */ 
   private JFrame mainFrame;
 
/**
 * The grid (2D array) of JLabels that display the game pieces.
 */ 
   private JLabel[][] guiGrid;

/**
 * The JLabel that displays the score.
 */    
   private JLabel scoreLabel;

/**
 * JButton for "Save Game" 
 */ 
   private JButton saveGameButton;
   
/**
 * JButton for "Load Game" 
 */
   private JButton loadGameButton;
   
/**
 * JButton for "Restart Game" 
 */
   private JButton restartGameButton;
   
/**
 * JButton for "Exit" 
 */
   private JButton exitButton;

//================= CONSTRUCTOR =================//
/**
 * Constructor for the Game2048GUI class.
 */
   public Game2048GUI() {
      this.initIconFiles();   // Initial config from the file config.txt
      this.initSlots();       // Initial each slots' visual appearance
      this.createFrame();     // Create the game grid
   }

//================= PRIVATE METHODS ====================//
/**
 * Initialize the array of file names corresponsing to the number tile images.
 */
   private void initIconFiles() {
      // The array size is determined based on the WINNING_NUMBER constant
      int numLevel = (int)(Math.log10(Game2048.WINNING_NUMBER) / Math.log10(2));
      slotIconFile = new String[numLevel];
      
      for (int i = 0; i < numLevel; i++) {
         int pieceValue = (int)(Math.pow(2, i + 1));
         // the file names are formed by a prefix (defined by a constant) followed by the number values
         slotIconFile[i] = ICON_FILE_FOLDER + "/" + NUMBERS_ICON_FILE_PREFIX + pieceValue + ".jpg";
      }
      
   }

/**
 * Create and initializes the 2D array of JLables used to display the number
 * tiles on the game grid.  Each JLabel represents a slot on the grid and
 * is configured with preferred size, alignment, border.
 */   
   private void initSlots() {
      guiGrid = new JLabel[NUM_ROW][NUM_COLUMN];
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            guiGrid[i][j] = new JLabel();
            guiGrid[i][j].setPreferredSize(new Dimension(PIECE_SIZE, PIECE_SIZE));
            guiGrid[i][j].setHorizontalAlignment (SwingConstants.CENTER);
            guiGrid[i][j].setBorder (GRID_BORDER);       
         }
      }
   }

/**
 * Create a JPanel to contain the JLabel that display the game logo banner.
 *
 * @return	the JPanel created for the purpose
 */   
   private JPanel createLogoPanel() {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
      JLabel logo = new JLabel();
      logo.setIcon(new ImageIcon(LOGO_ICON_FILE));
      panel.add(logo);
      return panel;
   }

/**
 * Create a JPanel to contain the Jabel to display score.
 *
 * @return	the JPanel created for the purpose
 */ 
   private JPanel createScorePanel() {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
      panel.setBackground(SCORE_BOARD_BG_COLOR);
      panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
   
      JLabel label = new JLabel(" Score: ");
      scoreLabel = new JLabel("0");
   
      Font regularFont = new Font ("Arial", Font.BOLD, 22);
      label.setFont(regularFont);
      scoreLabel.setFont(regularFont);
      
      panel.add(label);
      panel.add(this.scoreLabel);
   
      return panel;   
   }

/**
 * Create a JPanel that contains the grid of slots used to store the number tiles.
 *
 * @return	the JPanel created for the purpose
 */    
   private JPanel createGridPanel() {
      JPanel panel = new JPanel(); 
      panel.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
      panel.setBackground(GRID_BG_COLOR);
      panel.setLayout(new GridLayout(NUM_ROW, NUM_COLUMN));
   
      for (int i = 0; i < NUM_ROW; i++) {
         for (int j = 0; j < NUM_COLUMN; j++) {
            panel.add(guiGrid[i][j]);
         }
      }
      return panel;    
   }
   
/**
 * Create a JPanel to be used to store all action buttons.
 * 
 * @return	the JPanel created for the purpose
 */   
   private JPanel createButtonPanel() {
      // panel for the buttons
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
      panel.setLayout(new GridLayout(2, 2));
      
      Font regularFont = new Font ("Arial", Font.BOLD, 14);
      
      // button for save game
      saveGameButton = new JButton("Save Game");
      saveGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      saveGameButton.setFont(regularFont);
                              
      // button for load game
      loadGameButton = new JButton("Load Game");
      loadGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      loadGameButton.setFont(regularFont);
     
      // button for restart game
      restartGameButton = new JButton("Restart Game");
      restartGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      restartGameButton.setFont(regularFont);
   
      // button for exit
      exitButton = new JButton("Exit");
      exitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      exitButton.setFont(regularFont);        
   
      panel.add(saveGameButton);
      panel.add(loadGameButton);
      panel.add(restartGameButton);
      panel.add(exitButton);   
      
      return panel;
   }
   
/**
 * Create the JFrame that contains the entire game.
 * 
 * This JFrame contains the following:
 * <ul>
 * 	<li>mainPanel - The <b>main</b> JPanel that wraps around all the other sub JPanels
 * 	<li>logoPanel - The JPanel that wraps around the <b>logo</b>.
 * 	<li>scorePanel - The JPanel that wraps around the score to be displayed.
 * 	<li>gridPanel - The JPanel that wraps around all the <b>slots</b>.
 * </ul>
 */        
   private void createFrame() {
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
   
   // Create game frame
      mainFrame = new JFrame ("2048");
      mainFrame.setLocation( dim.width/2 - FRAME_WIDTH/2, dim.height/2 - FRAME_HEIGHT/2); 
   
      JPanel mainPanel = (JPanel)mainFrame.getContentPane();
      mainPanel.setLayout (new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setPreferredSize(new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
         
      mainPanel.add(createLogoPanel());
      mainPanel.add(createGridPanel());
      mainPanel.add(createScorePanel());
      mainPanel.add(createButtonPanel());
   
   // Show main frame
      mainFrame.setContentPane(mainPanel);
      mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      mainFrame.setVisible(true);
      mainFrame.setResizable(false);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
   }

/**
 * Determine the index that represents the specified number value.
 * 
 * @param number  the value of a slot.
 * @return			the index representing the specified number value
 */   
   private int numberToIndex(int number) {
      return (int)((Math.log10(number) / Math.log10(2)) - 1);
   }

//================= PUBLIC METHODS ====================//
/**
 * Displays a dialog box indicating that the game is over.
 * This method should be called when no more valid moves are possible.
 */     
   public void showGameOver() {
      JOptionPane.showMessageDialog(null, "Game Over!" , "The game is finished", JOptionPane.PLAIN_MESSAGE); 
   }

/**
 * Displays a dialog box indicating that the player has won the game.
 * This method should be called when the player reaches the winning number.
 */ 	
   public void showGameWon() {
      JOptionPane.showMessageDialog(null, "You Have Won!" , "The game is finished", JOptionPane.PLAIN_MESSAGE); 
   }
   
/**
 * Displays a dialog box asking the user whether they want to play a new game.
 *
 *  @return			<code>true</code> if the select "Yes"; the program exits otherwise.
 */
    public boolean showPlayAgain() {
      int choice = JOptionPane.showConfirmDialog(null, "Play Again" , "Play New Game?", JOptionPane.YES_NO_OPTION); 

      if (choice == JOptionPane.YES_OPTION) {
         return true;
      } else {
         // choice == JOptionPane.NO_OPTION or dialog is closed without selection
         System.exit (0);
      }
      return false;
   }
   
/**
 * Adds a KeyListener to the main game window (mainFrame) to capture
 * keyboard input from the user. 
 *
 * @param listener	the KeyListener to added to mainFrame
 */   
   public void addListener (Game2048Listener listener) {
      mainFrame.addKeyListener(listener);
   }
   
/**
 * Adds an ActionListener to control buttons (Save, Load, Restart and Exit).
 *
 * @param listener	the Actionlistener to be added to each control buttons
 */ 
   public void addListener(Game2048ButtonListener listener) {
      saveGameButton.addActionListener(listener);
      loadGameButton.addActionListener(listener);
      restartGameButton.addActionListener(listener);
      exitButton.addActionListener(listener);
   }

/**
 * Requests focus to the main game window (mainFrame) so that it can receive
 * keyboard input through the attached KeyListener.
 */
   public void gameWindowRequestFocus() {
      mainFrame.requestFocusInWindow(); 
   }

/**
 * Updates the score display on the scoreboard with the specified value.
 *
 * @param score	the current game score to display
 */      
   public void setScore(int score) {
      this.scoreLabel.setText(score + "");
   }

/**
 * Displays the specified number tile on the slot at the given location on
 * the game grid.
 *
 * @param rowIndex			the row index of the slot on the grid 
 * @param columnIndex		the column index of the slot on the grid
 * @param number  			the number to be displayed; must be a valid number tile value (e.g., 2, 4, 8, ... 2048)
 */        
   public void displaySlot(int rowIndex, int columnIndex, int number) {
      int index = numberToIndex(number);	
      guiGrid[rowIndex][columnIndex].setIcon(new ImageIcon(slotIconFile[index]));  
   
   }	

/**
 * Display the specified number on the specified location on the grid with a specified delay time.
 *
 * @param rowIndex			the row index of the slot on the grid 
 * @param columnIndex		the column index of the slot on the grid
 * @param number  			the number to be displayed; must be a valid number tile value (e.g., 2, 4, 8, ... 2048)
 * @param delayTime        the delay in milliseconds before the number tile is displayed
 */        
   public void displaySlot(int rowIndex, int columnIndex, int number, int delayTime) {
      ActionListener taskPerformer = 
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               displaySlot(rowIndex, columnIndex, number);
            }
         };
      Timer clock = new Timer(delayTime, taskPerformer);
      clock.setRepeats(false);
      clock.start();
   }
   
/**
 * Clears the number tile displayed on the slot at the specified location.
 *
 * @param rowIndex			the row index of the slot on the grid
 * @param columnIndex		the column index of the slot on the grid
 */          
   public void clearSlot(int rowIndex, int columnIndex) {
      guiGrid[rowIndex][columnIndex].setIcon(null);
   }

/**
 * Displays the current game state on the game grid based on the given 2D array
 * of values.  Valid values are powers of 2; empty slot should have a negative value (e.g., -1)
 *
 * @param grid	   the 2D array representing the current state of the game grid */         
   public void displayGrid(int[][] grid) {
      for( int i = 0; i < NUM_ROW; i++) {
         for( int j = 0; j < NUM_COLUMN; j++) {
            int slotValue = grid[i][j];
            int slotIndex = numberToIndex(slotValue);
            if( slotValue >= 0) {
               guiGrid[i][j].setIcon(new ImageIcon(slotIconFile[slotIndex]));
            } else {
               guiGrid[i][j].setIcon(null);
            }
         }
      } 
   }   	

/**
 * Clears all number tiles from the game grid.
 *
 */         
   public void clearGrid() {
      for( int i = 0; i < NUM_ROW; i++) {
         for( int j = 0; j < NUM_COLUMN; j++) {
            guiGrid[i][j].setIcon(null);
         }
      } 
   }   	

/**
 * Main method of the program. It starts by initializing Game2048Gui, Game2048 and Game2048Listener object.
 */     
   public static void main(String[] args) {
      Game2048GUI gui = new Game2048GUI();
      Game2048 game = new Game2048(gui);
      Game2048Listener listener = new Game2048Listener (game, gui);
      Game2048ButtonListener butListener = new Game2048ButtonListener(game, gui); 
      
      // request focus on the game grid to allow keyboard input
      gui.gameWindowRequestFocus(); 
      
      // start the game logic
      game.newGame();
   }
}