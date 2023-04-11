//Ara Garabedian

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;

// main BallGame class that extends Application, allows us to use JFX
public class BallGameProjectR extends Application
{

    // Defining our GamePane class
    public class GamePane extends GridPane // extends gridpane so we can use their class features
    {
        // creating a gridpane
        GridPane gp = new GridPane(); 

            // Creating four buttons for the game
            Button topButton = new Button();
            Button bottomButton = new Button();
            Button leftButton = new Button();
            Button rightButton = new Button();

            
            // Creating a canvas for the ball
            Canvas canvas = new Canvas(80,80);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            
            // Setting the default visibility of ball and buttons
            boolean displayBall = true;
            boolean displayRightBall = false;
            boolean displayTopBall = false;
            boolean displayLeftBall = false;
            boolean displayBottomBall = false;
            
            // Constructor for our GamePane class
            public GamePane() 
            {
                super(); // Accessing the features of GridPane class
                
                               
                leftButton.setPrefSize(20,80);  // Setting the size of the left button
                leftButton.setOnAction(new ButtonListener()); // Adding button listener to the left button
                
                topButton.setPrefSize(80,20); // Setting the size of the top button
                topButton.setOnAction(new ButtonListener()); // Adding button listener to the top button

                
                rightButton.setPrefSize(20,80);  // Setting the size of the right button
                rightButton.setOnAction(new ButtonListener()); // Adding button listener to the right button
                
                bottomButton.setPrefSize(80,20);  // Setting the size of the bottom button
                bottomButton.setOnAction(new ButtonListener()); // Adding button listener to the bottom button
                
                gc.fillOval(0, 0, 80, 80);
            
                // Adding the buttons and the ball to the GridPane
                add(topButton, 1, 0); // Top button
                add(bottomButton, 1, 2); // Bottom button
                add(canvas, 1, 1); // The ball
                add(leftButton, 0, 1); // Left button
                add(rightButton, 2, 1); // Right button

            }

        // Creating the draw method
        public void draw() 
        {
            if(displayBall) 
            { // Checks if the ball is visible (true)
                gc.fillOval(0,0,80,80); // Draws the ball
            } 
            else 
            { // if not, the ball is erased/invisible
                gc.clearRect(0,0,80,80);
            }

           // Checks if the top button is visible
         if(displayTopBall) 
         {
             // Places the top button if it's visible
             topButton.setVisible(true);
         } 
         else 
         {
             // Makes the top button invisible if it's not visible
             topButton.setVisible(false);
         }
         
         // Checks if the left button is visible
         if(displayLeftBall) 
         {
             // Places the left button if it's visible
             leftButton.setVisible(true);
         } 
         else 
         {
             // Makes the left button invisible if it's not visible
             leftButton.setVisible(false);
         }
         
         // Checks if the right button is visible
         if(displayRightBall) 
         {
             // Places the right button if it's visible
             rightButton.setVisible(true);
         } 
         else 
         {
             // Makes the right button invisible if it's not visible
             rightButton.setVisible(false);
         }
         
         // Checks if the bottom button is visible
         if(displayBottomBall) 
         {
             // Places the bottom button if it's visible
             bottomButton.setVisible(true);
         } 
         else 
         {
             // Makes the bottom button invisible if it's not visible
             bottomButton.setVisible(false);
         }
         
         // Checks if the player has won (1 ball remaining)
         if(ballCounter == 1) 
         {
             // Sets the label text to green
             label.setTextFill(Color.GREEN);
             // Sets the background to green
             root.setBackground(new Background(new BackgroundFill(new Color(0,1,0,0.65), CornerRadii.EMPTY, Insets.EMPTY)));
             // Changes the label text to "You Win!"
             label.setText("You Have Won!!!");
         } 
         else if(remainingMoves == 0 && ballCounter != 1) 
             {
             // Checks if the player has lost
             // Sets the label to red text
             label.setTextFill(Color.RED);
             // Sets the background to red
             root.setBackground(new Background(new BackgroundFill(new Color(1,0,0,0.65), CornerRadii.EMPTY, Insets.EMPTY)));
             // Changes the label text to "You Lose!"
             label.setText("You Lost, Try Again");
         } 
         else 
             {
             // If the player hasn't won or lost yet
             // Sets the text fill to cyan
             label.setTextFill(Color.ORANGE);
             // Displays the number of balls and moves remaining
             label.setText("Balls Left: "+ ballCounter +" \t\t\t\t\t Moves Left: "+ remainingMoves);            
             }
        }

        // Below are accessor methods that allow you to retrieve the current 
        //visibility state of the ball and button, and also to update those values if needed.
        public boolean getDisplayBall() 
        {
            return displayBall;
        }

        public void setDisplayBall(boolean displayBall) 
        {
            this.displayBall = displayBall;
        }

        public void setDisplayTopBall(boolean displayTopBall) 
        {
            this.displayTopBall = displayTopBall;
        }

        public void setDisplayLeftBall(boolean displayLeftBall) 
        {
            this.displayLeftBall = displayLeftBall;
        }

        public void setDisplayRightBall(boolean displayRightBall) 
        {
            this.displayRightBall = displayRightBall;
        }

        public void setDisplayBottomBall(boolean displayBottomBall) 
        {
            this.displayBottomBall = displayBottomBall;
        }

        // This function gets the button that is currently selected based on its ID
         public Button getButtonSelected(char buttonName) 
         {
         if(buttonName == 't') 
         { // If the ID is 't', return the top button
         return topButton;
         } else if(buttonName == 'l') 
         { // If the ID is 'l', return the left button
         return leftButton;
         } else if(buttonName == 'r') 
         { // If the ID is 'r', return the right button
         return rightButton;
         } else { // If there aren't any other buttons, return the bottom button
         return bottomButton;
         }
      }
    }
     // private variables
    private int remainingMoves; // int keep check of remaining moves
    private int ballCounter; // int to track how many balls are on the board
    private GamePane[][] ballGameBoard = new GamePane[4][4]; // 4x4 array of gamepanes
   
    private BorderPane root = new BorderPane(); // root; borderpane
    private Label label = new Label(); // label to be shown at the top of the screen

    
     // The purpose of this method is to check if a move can be made.
    public void checkMove(GamePane[][] gameBoard)// It takes in a 2D array of GamePane objects as input.
    {
         // Set the ballCounter and remainingMoves variables to 0.
         ballCounter = 0;
         remainingMoves = 0;
         
         // Use a nested for loop to iterate over each GamePane object in the 2D array.
         for(int i=0; i<4; i++) {
             for(int j=0; j<4; j++) {
         
                 // Check if the current GamePane object has a visible ball.
                 if(gameBoard[i][j].getDisplayBall() == true) {
                     ballCounter++; // Increment the ballCounter variable.
                 }
         
                         
                 // Check if there is a ball 2 spaces below the current GamePane object.
                 if(j+2 < 4) {
                     if(gameBoard[i][j+1].getDisplayBall() == true && gameBoard[i][j+2].getDisplayBall() == false && gameBoard[i][j].getDisplayBall() == true) {
                         gameBoard[i][j].setDisplayTopBall(true); // Set the top button to true.
                         remainingMoves++; // Increment the remainingMoves variable.
                     } else {
                         gameBoard[i][j].setDisplayTopBall(false); // Set the top button to false.
                     }
                 }
         
                 // Check if there is a ball 2 spaces to the left of the current GamePane object.
                 if(i-2 > -1) {
                     if(gameBoard[i-1][j].getDisplayBall() == true && gameBoard[i-2][j].getDisplayBall() == false && gameBoard[i][j].getDisplayBall() == true) {
                         gameBoard[i][j].setDisplayRightBall(true); // Set the right button to true.
                         remainingMoves++; // Increment the remainingMoves variable.
                     } else {
                         gameBoard[i][j].setDisplayRightBall(false); // Set the right button to false.
                     }
                 }
                  // Check if there is a ball 2 spaces above the current GamePane object.
                 if(j-2 > -1) {
                     if(gameBoard[i][j-1].getDisplayBall() == true && gameBoard[i][j-2].getDisplayBall() == false && gameBoard[i][j].getDisplayBall() == true) {
                         gameBoard[i][j].setDisplayBottomBall(true); // Set the bottom button to true.
                         remainingMoves++; // Increment the remainingMoves variable.
                     } else {
                         gameBoard[i][j].setDisplayBottomBall(false); // Set the bottom button to false.
                     }
                 }

         
                 // Check if there is a ball 2 spaces to the right of the current GamePane object.
                 if(i+2 < 4) {
                     if(gameBoard[i+1][j].getDisplayBall() == true && gameBoard[i+2][j].getDisplayBall() == false && gameBoard[i][j].getDisplayBall() == true) {
                         gameBoard[i][j].setDisplayLeftBall(true); // Set the left button to true.
                         remainingMoves++; // Increment the remainingMoves variable.
                     } else {
                         gameBoard[i][j].setDisplayLeftBall(false); // Set the left button to false.
                     }
                 }
             }
         }
    }
    
    //Listener for each button
    public class ButtonListener implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e) 
        {
            
            for(int i=0;i<4;i++) {
                for(int j=0;j<4;j++) {
                    if(e.getSource() == ballGameBoard[i][j].getButtonSelected('t')) {//determine if the top button was clicked
                    
                        click(i,j,'t'); 
                    } else if(e.getSource() == ballGameBoard[i][j].getButtonSelected('l')) { //determine if the left button was clicked

                        click(i,j,'l'); 
                    } else if(e.getSource() == ballGameBoard[i][j].getButtonSelected('r')) {//determine if the right button was clicked
                        click(i,j,'r'); 
                    } else if(e.getSource() == ballGameBoard[i][j].getButtonSelected('b')) { //determine if the bottom button was clicked

                        click(i,j,'b'); 
                    }
                }
            }
        }
    }
    
    //This method handles the clicks on the game board. It takes in the x and y coordinates of the clicked ball and the button name ('t' for top, 'l' for left, 'r' for right, and 'b' for bottom).
    public void click(int x, int y, char buttonName) { 
    
        //If the button clicked is the top button, make the ball at the clicked coordinates invisible, make the ball jumped over invisible, and make the ball 2 spaces down visible.
        if(buttonName == 't') { 
            ballGameBoard[x][y].setDisplayBall(false); 
            ballGameBoard[x][y+1].setDisplayBall(false);
            ballGameBoard[x][y+2].setDisplayBall(true); 
            
          //If the button clicked is the left button, make the ball at the clicked coordinates invisible, make the ball jumped over invisible, and make the ball 2 spaces to the right visible. 
        } else if(buttonName == 'l') { 
            ballGameBoard[x][y].setDisplayBall(false); 
            ballGameBoard[x+1][y].setDisplayBall(false); 
            ballGameBoard[x+2][y].setDisplayBall(true); 
            
          //If the button clicked is the right button, make the ball at the clicked coordinates invisible, make the ball jumped over invisible, and make the ball 2 spaces to the left visible.
        } else if(buttonName == 'r') {
            ballGameBoard[x][y].setDisplayBall(false); 
            ballGameBoard[x-1][y].setDisplayBall(false);             
            ballGameBoard[x-2][y].setDisplayBall(true); 
            
            
        } else { // if it is the bottom button
            ballGameBoard[x][y].setDisplayBall(false); // makes the ball invis.
            ballGameBoard[x][y-1].setDisplayBall(false); // the ball it jumped over invis.
            ballGameBoard[x][y-2].setDisplayBall(true); // the ball 2 spaces above is becomes true
        }
        // now it recalls the checkmove method to add the new buttons
        checkMove(ballGameBoard);
        // it also redraws the gameboard with the for loop below
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                ballGameBoard[i][j].draw();
            }
        }
    }

     //This is the main method for starting the game.
    public void start(Stage stage)
    {
    
        //Creating a new gridpane to store gamepanes.
        GridPane gridpane = new GridPane(); 
 
         //Setting the label to the top center of the borderpane.
         root.setTop(label); 
         
          //Giving a black background to the label.
         label.setStyle("-fx-background-color: black;");
         
         //Setting the label to the center of the screen.
         root.setAlignment(label, Pos.CENTER); 
         
                 
         //Setting vertical gap to 15.
         gridpane.setVgap(15); 
         
          //Setting horizontal gap to 15.
         gridpane.setHgap(15);

         
         //Setting the gridpane to the center.
         gridpane.setAlignment(Pos.CENTER); 
         
         //Creating a 4x4 game board with a for loop.
         for(int i=0;i<4;i++) 
         {
             for(int j=0;j<4;j++) 
             {
                 GamePane hold = new GamePane(); //Creating a new gamepane.
                 ballGameBoard[i][j] = hold; //Adding it to the board array.
                 gridpane.add(ballGameBoard[i][j], i, j); //Adding all the elements into my gridpane.
                 if(i == 0 && j == 2) 
                 { //Starting the ball at 0,2 as not there.
                     ballGameBoard[i][j].setDisplayBall(false); //Setting ball visibility to false.
                 }
             }
         }
         
         //Checks the moves (adds buttons and the number of balls/moves left).
         checkMove(ballGameBoard);
         
         //Draws out the gameboard with the draw method using another for loop.
         for(int i=0;i<4;i++) 
         {
             for(int j=0;j<4;j++) 
             {
                 ballGameBoard[i][j].draw();
             }
         }
         
         //Setting the gridpane to the center of the borderpane.
         root.setCenter(gridpane);
         
         //Creating a 600x600 scene based off the borderpane.
         Scene scene = new Scene(root, 600, 600);
         
         //Title of the game.
         stage.setTitle("KickBallGame");
         
         //Showing the stage.
         stage.setScene(scene);
         stage.show();
    }
    
    
    //Launches the main method to pop up the stage.
    public static void main(String[] args)
    {
        launch(args);
    }  
} 