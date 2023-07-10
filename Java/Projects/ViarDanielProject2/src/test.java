import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
/*Daniel Viar
 * EECS 1510
 * Dr.Hobbs
 * 
 * This class is a GUI and running game for Connect Four. It flashes the winning cells when the user wins
 * 
 */
public class test extends Application //opening class
{

	
	@Override
	public void start(Stage primaryStage) 
	{
	
      Cells cell = new Cells(); // Cells class object to be able to access the class
     
      GridPane gridPane = new GridPane(); // declaring a gridPane to hold the array of circles
		
      //Nested loops access each element of the cellArray
      for(int i = 0; i < cell.cellArray.length; i++) 
      {
		for(int j = 0; j < cell.cellArray[i].length; j++) 
		{
			gridPane.add(cell.cellArray[i][j], j, i); // adds the circle objects to the array

		}
      }
      
      //setting the alignment of the gridPane
      gridPane.setAlignment(Pos.CENTER);
      gridPane.setHgap(5);
      gridPane.setVgap(5);
      //calls the running game method
      cell.runningGame();
			
		
      BorderPane borderPane = new BorderPane();
      Label label = new Label("green");
      borderPane.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
      borderPane.setCenter(label);
      borderPane.setTop(gridPane);
    
			
      Scene scene = new Scene(borderPane, 600, 600, Color.DARKBLUE);//Declaring and intializing the scene
		
      primaryStage.setScene(scene);//adds the scene to the stage
	  primaryStage.show(); // Shows the stage
	}
	
	public class Cells // This class runs the game and sets up the board
	{ 
		private int choice = 0;
		
		public Circle[][] cellArray = new Circle[6][7]; //This is the array that holds the board
		
		
		
		private String turn = "R"; // Setting the first turn for the red disk
		
		public Cells() 
		{
			board(); // This method sets the board
		}
		
		//this method sets up the board
		public Circle[][] board() 
		{
			//nested loops to access each element of the array
			for(int i = 0; i < cellArray.length; i++) 
			{
				for(int j = 0; j < cellArray[i].length; j++) 
				{
					//Initializing each circle and setting the attributes
					cellArray[i][j] = new Circle(40,Color.WHITE);
					cellArray[i][j].setStroke(Color.BLACK);
				}
			}
		
			return cellArray; //Returns the filled board
			
		}
		public void runningGame() //this method runs the game	
		{ 		
			setColor(); // calls the setColor method
			
		}
		
		public void setColor() 
		{
			//Nested loop to access each element of the array
			for(int i = 0; i < cellArray.length; i++) 
			{

				for(int j = 0; j < cellArray[i].length; j++)
				{
					final int x = i; // The compiler does not like using i and j because it can't reach the variables
					final int y = j; //	They also must be final so the value doesn't change with the loop
			
					//This handles the event when the user clicks
					cellArray[i][j].setOnMouseClicked(e->
					{
						//Calls the checkIfWhite and checkUnder to make sure the spot clicked is allowed
						if(checkIfWhite(x,y)) 
						{
							if(checkUnder(x,y)) 
							{
								if(turn.equals("R")) //Designates reds turn
								{ 
					
									cellArray[x][y].setFill(Color.RED); // Sets the circle that was clicked to be red
								
									if(isWinner()) { //Checks if the spot clicked is a winner
										timer();//Calls the timer method
										System.out.println("Would you like to play again?");
										if(choice == 1)
											board();
									}
									else//if it is not a winner it moves on to the next turn
										turn = "Y";
								}
								else if(turn.equals("Y")) 
								{ //Designates yellow turn
								
									cellArray[x][y].setFill(Color.YELLOW); // Sets the circle that was clicked to be yellow
									if(isWinner()) //Checks if the spot clicked is a winner
										timer();//Calls the timer method
									else//if it is not a winner it moves on to the next turn
										turn = "R";
								}
							}
						}
				
					});
				}
			}
		}
		
		//This method checks whether there are any white spots under the attempted spots
		public boolean checkUnder(int row, int column) 
		{
		//If the user tries to put it in the bottom row, it will always be a safe place so it always returns true at row 5
			if(row == 5)
				return true;
			//This if statement checks the row under the attempted spot to make sure it has a colored disk under it
			if(row  + 1 <= 5 && (cellArray[row + 1][column].getFill().equals(Color.RED)
					|| cellArray[row + 1][column].getFill().equals(Color.YELLOW))) 
			{
				
				return true;
			}
		
		//if the spot is not applicable, the method returns false
			return false;
		}
		
		//this method checks if the spot is a white spot before a disk is placed
		public boolean checkIfWhite(int row, int column) 
		{
			//this statement checks if the spot clicked it white
				if(cellArray[row][column].getFill().equals(Color.WHITE)) 
						return true;	
			//otherwise the method returns false	
			return false;
			
		}
		
		private int [][] winningPos = new int[4][2]; // This array holds all the i,j values that hold a winner
		
		/*This method checks to see if the board has a horizontal win by cycling through the array and
		 * adding to a counter variable if the following disk is the matching color. It also sets the winning
		 * coordinates into the winningPos array
		 */
		public String horizontalWin() 
		{
			//These variables hold the number of occurrences of the pattern we are looking for
			int rcount = 0;
			int ycount = 0;
			//Nested loops to access each element of the array
			for(int i = 0; i < cellArray.length; i++) 
			{
				//Reset the count variables each row to prevent cross over
				rcount = 0;
				ycount = 0;
				for(int j = 0; j < cellArray[i].length; j++) 
				{
					//this checks if the spot checked is red, if it is it increments the count by one
					if(cellArray[i][j].getFill().equals(Color.RED)) 
					{
							rcount++;
					
						if(rcount == 4) //If the count variable is 4 (a match) it returns true
						{
							//these statements set the winning coordinates to the winningPos array 
							winningPos[0][0] = i; winningPos[0][1] = j;
							winningPos[1][0] = i; winningPos[1][1] = j - 1;
							winningPos[2][0] = i; winningPos[2][1] = j - 2;
							winningPos[3][0] = i; winningPos[3][1] = j - 3;
							//The statement returns R to show the winner
							return "R";
						}
					}
					else // If there is not a red disk next, it stops the count and resets it
							rcount = 0;
					//this checks if the spot checked is yellow, if it is it increments the count by one
						if(cellArray[i][j].getFill().equals(Color.YELLOW)) 
						{
							ycount++;
							if(ycount == 4) //If the count variable is 4 (a match) it returns true
							{
								//these statements set the winning coordinates to the winningPos array 
								winningPos[0][0] = i; winningPos[0][1] = j;
								winningPos[1][0] = i; winningPos[1][1] = j - 1;
								winningPos[2][0] = i; winningPos[2][1] = j - 2;
								winningPos[3][0] = i; winningPos[3][1] = j - 3;
								//The statement returns Y to show the winner
								return "Y";
							}
						}
					else // If there is not a red disk next, it stops the count and resets it
						ycount = 0;
					
				}
			}
			//if the checks all fail, the method returns n
			return "n";
		}
		
		
		/*This method checks for a vertical win by looping through the cell array and checking the fill at each i,j
		 * and 3 spots above it. If it passes the 3 spots above it returns a counter variable. It also sets the 
		 * winningPos spots for the winning coordinates
		 */
		public String verticalWin() 
		{
			//These variables hold the number of occurrences of the pattern we are looking for
			int rcount = 0;
			int ycount = 0;
			//Nested loops to access each element of the array
			for(int i = 0; i < cellArray.length; i++)
			{
				//Reset the count variables each row to prevent cross over
				rcount = 0;
				ycount = 0;
				
				for(int j = 0; j < cellArray[i].length; j++)
				{
					//this checks if the spot checked is red, if it is it increments the count by one
						if(cellArray[i][j].getFill().equals(Color.RED)) 
						{
							rcount++;
							// this sets the win coordinates
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							//checks the spot above i,j and returns true is the color is red
							if(i - 1 >= 0 && cellArray[i - 1][j].getFill().equals(Color.RED)) 
							{
								rcount++;
								// this sets the win coordinates
								winningPos[1][0] = i-1;
								winningPos[1][1] = j;
								//checks the 2 spots above i,j and returns true is the color is red
								if(i - 2 >= 0 && cellArray[i - 2][j].getFill().equals(Color.RED))
								{
									rcount++;
									// this sets the win coordinates
									winningPos[2][0] = i-2;
									winningPos[2][1] = j;
									//checks the 3 spots above i,j and returns true is the color is red
									if(i - 3 >= 0 && cellArray[i - 3][j].getFill().equals(Color.RED)) 
									{
										rcount++;
										// this sets the win coordinates
										winningPos[3][0] = i-3;
										winningPos[3][1] = j;
									}
								}
							}
							
							//If the counter variable is 4 (a match), it returns R		
							if(rcount == 4) 
								return "R";
							//if the counter fails, it sets the count to 0 and resets the winningPos array
							else 
							{
							
							rcount = 0;
							for(int x = 0; x < winningPos.length;x++) 
							{
								for(int y = 0; y < winningPos[x].length; y++) 
								{
									winningPos[x][y] = 0;	
								}
							}
							}
						}
						if(cellArray[i][j].getFill().equals(Color.YELLOW)) 
						{
							ycount++;
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							if(i - 1 >= 0 && cellArray[i - 1][j].getFill().equals(Color.YELLOW)) 
							{
								ycount++;
								winningPos[1][0] = i - 1;
								winningPos[1][1] = j;
								if(i - 2 >= 0 && cellArray[i - 2][j].getFill().equals(Color.YELLOW)) 
								{
									ycount++;
									winningPos[2][0] = i-2;
									winningPos[2][1] = j;
									if(i - 3 >= 0 && cellArray[i - 3][j].getFill().equals(Color.YELLOW)) 
									{
										ycount++;
										winningPos[3][0] = i-3;
										winningPos[3][1] = j;
									}
								}
							}
							
								
						if(ycount == 4) 
							return "Y";
						else
						{
							ycount = 0;
							for(int x = 0; x < winningPos.length;x++) 
							{
								for(int y = 0; y < winningPos[x].length; y++) 
								{
									winningPos[x][y] = 0;
									
									
								}
							}
						}
					}
						
				}
					
			}
			
			
			return "n";
		}
		
		public String diagonalUpWin()
		{
			
			int rcount = 0;
			int ycount = 0;
			
			
			for(int i = 0; i < cellArray.length; i++) 
			{
				
				rcount = 0;
				ycount = 0;
				for(int j = 0; j < cellArray[i].length; j++) 
				{
						if(cellArray[i][j].getFill().equals(Color.RED)) 
						{
							rcount++;
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							if((i - 1 >= 0 && j + 1 <= 6) && cellArray[i - 1][j +1].getFill().equals(Color.RED))
							{
								rcount++;
								winningPos[1][0] = i - 1;
								winningPos[1][1] = j + 1;
								if((i - 2 >= 0 && j + 2 <= 6) && cellArray[i - 2][j + 2].getFill().equals(Color.RED))
								{
									rcount++;
									winningPos[2][0] = i - 2;
									winningPos[2][1] = j + 2;
									if((i - 3 >= 0 && j + 3 <= 6) && cellArray[i - 3][j + 3].getFill().equals(Color.RED))
									{
										rcount++;
										winningPos[3][0] = i - 3;
										winningPos[3][1] = j + 3;
									}
								}
							}
							
						if(rcount == 4) 
							return "R";
						else 
						{
							rcount = 0;
							for(int x = 0; x < winningPos.length;x++)
							{
								for(int y = 0; y < winningPos[x].length; y++) 
								{
									winningPos[x][y] = 0;
									
									
								}
							}
						}
						}
					
						
						if(cellArray[i][j].getFill().equals(Color.YELLOW)) 
						{
							ycount++;
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							if((i - 1 >= 0 && j + 1 <= 6) && cellArray[i - 1][j +1].getFill().equals(Color.YELLOW))
							{
								ycount++;
								winningPos[1][0] = i - 1;
								winningPos[1][1] = j + 1;
								if((i - 2 >= 0 && j + 2 <= 6) && cellArray[i - 2][j + 2].getFill().equals(Color.YELLOW))
								{
									ycount++;
									winningPos[2][0] = i - 2;
									winningPos[2][1] = j + 2;
									if((i - 3 >= 0 && j + 3 <= 6) && cellArray[i - 3][j + 3].getFill().equals(Color.YELLOW))
									{
										ycount++;
										winningPos[3][0] = i - 3;
										winningPos[3][1] = j + 3;
									}
										
								}
							}
							
								
						if(ycount == 4) 
							return "Y";
						else {
							ycount = 0;
							for(int x = 0; x < winningPos.length;x++) 
							{
								for(int y = 0; y < winningPos[x].length; y++)
								{
									winningPos[x][y] = 0;
									
									
								}
							}
						}
						}
						
					}
					
				}
			
			
			return "n";
		}
		
	public String diagonalDownWin() 
	{
		int rcount = 0;
		int ycount = 0;
			
			
		for(int i = 0; i < cellArray.length; i++)
		{
				
			rcount = 0;
			ycount = 0;
			for(int j = 0; j < cellArray[i].length; j++)
			{
					if(cellArray[i][j].getFill().equals(Color.RED))
					{
						rcount++;
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						if((i + 1 <= 5 && j + 1 <= 6) && cellArray[i + 1][j + 1].getFill().equals(Color.RED)) 
						{
							rcount++;
							winningPos[1][0] = i + 1;
							winningPos[1][1] = j + 1;
							if((i + 2 <= 5 && j + 2 <= 6) && cellArray[i + 2][j + 2].getFill().equals(Color.RED))
							{
								rcount++;
								winningPos[2][0] = i + 2;
								winningPos[2][1] = j + 2;
								if((i + 3 <= 5 && j + 3 <= 6) && cellArray[i + 3][j + 3].getFill().equals(Color.RED))
								{
									rcount++;
									winningPos[3][0] = i + 3;
									winningPos[3][1] = j + 3;
								}
							}
						}							
					if(rcount == 4) 
						return "R";
					else 
					{
						rcount = 0;
						for(int x = 0; x < winningPos.length;x++) 
						{
							for(int y = 0; y < winningPos[x].length; y++) 
							{
								winningPos[x][y] = 0;	
							}
						}
					}
				}
						
						if(cellArray[i][j].getFill().equals(Color.YELLOW)) 
						{
							ycount++;
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							if((i + 1 <= 5 && j + 1 <= 6) && cellArray[i + 1][j + 1].getFill().equals(Color.YELLOW))
							{
								ycount++;
								winningPos[1][0] = i + 1;
								winningPos[1][1] = j + 1;
								if((i + 2 <= 5 && j + 2 <= 6) && cellArray[i + 2][j + 2].getFill().equals(Color.YELLOW)) 
								{
									ycount++;
									winningPos[2][0] = i + 2;
									winningPos[2][1] = j + 2;
									System.out.println(ycount);
									if((i + 3 <= 5 && j + 3 <= 6) && cellArray[i + 3][j + 3].getFill().equals(Color.YELLOW))
									{
											ycount++;
											winningPos[3][0] = i + 3;
											winningPos[3][1] = j + 3;
									}
										
								}
							}
						if(ycount == 4) 
							return "Y";
						else 
						{
							ycount = 0;
							for(int x = 0; x < winningPos.length;x++) 
							{
								for(int y = 0; y < winningPos[x].length; y++) 
								{
									winningPos[x][y] = 0;	
								}
							}
						}
						}
					
					}
					
				}
			
			
			return "n";
		}
	
	
		public boolean x = true;
		
		
		public void flash() 
		{
		
           			 if(x) 
           			 {
           			  cellArray[winningPos[0][0]][winningPos[0][1]].setFill(Color.LIGHTGREEN);
           			  cellArray[winningPos[1][0]][winningPos[1][1]].setFill(Color.LIGHTGREEN);
           			  cellArray[winningPos[2][0]][winningPos[2][1]].setFill(Color.LIGHTGREEN);
           			  cellArray[winningPos[3][0]][winningPos[3][1]].setFill(Color.LIGHTGREEN);
           			  x = false;
           			 
           			 }
           			 else
           			 {
           				 if(turn.equals("R")) 
           				 {
           					 cellArray[winningPos[0][0]][winningPos[0][1]].setFill(Color.RED);
           					 cellArray[winningPos[1][0]][winningPos[1][1]].setFill(Color.RED);
           					 cellArray[winningPos[2][0]][winningPos[2][1]].setFill(Color.RED);
           					 cellArray[winningPos[3][0]][winningPos[3][1]].setFill(Color.RED);
           					 x = true;;
             				turn = "R";
           				 }
           				 else if(turn.equals("Y")) 
           				 {
           					
           					cellArray[winningPos[0][0]][winningPos[0][1]].setFill(Color.YELLOW);
                 			cellArray[winningPos[1][0]][winningPos[1][1]].setFill(Color.YELLOW);
                 			cellArray[winningPos[2][0]][winningPos[2][1]].setFill(Color.YELLOW);
                 			cellArray[winningPos[3][0]][winningPos[3][1]].setFill(Color.YELLOW);
                 			x = true;;
                 			
           				}
           				
           			 } 
		}
		public void timer() 
		{
			 Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), e-> 
			 {
				 flash();
			 }));
			 timeline.setCycleCount(Timeline.INDEFINITE);
	         timeline.play();
		}
		
		public boolean isWinner() 
		{
			if(horizontalWin().equals("R")) 
			{
				System.out.println("HR");
				return true;
			}
			else if(horizontalWin().equals("Y")) 
			{
				System.out.println("HY");
				return true;
			}
			else if(verticalWin().equals("R")) 
			{
				System.out.println("VR");
				return true;
			}
			else if(verticalWin().equals("Y")) 
			{
				System.out.println("VR");
				return true;
			}
			else if(diagonalUpWin().equals("R")) 
			{
				System.out.println("DUR");
				return true;
			}
			else if(diagonalUpWin().equals("Y")) 
			{
				System.out.println("DUY");
				return true;
			}
			else if(diagonalDownWin().equals("R"))
			{
				System.out.println("DDR");
				return true;
			}
			else if(diagonalDownWin().equals("Y"))
			{
				System.out.println("DDY");
				return true;
			}
			return false;
		}		
}
	
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) 
	{
		
	    launch(args);
	}
}