
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/*Daniel Viar
 * EECS 1510
 * Dr.Hobbs
 * 
 * This class is a GUI and running game for Connect Four. It flashes the winning cells when the user wins
 * 
 */
public class ConnectFourFX extends Application // opening class
{

	@Override
	public void start(Stage primaryStage)
	{

		Cells cell = new Cells(); // Cells class object to be able to access the class

		GridPane gridPane = new GridPane(); // declaring a gridPane to hold the array of circles

		// Nested loops access each element of the cellArray
		for (int i = 0; i < cell.cellArray.length; i++)
		{
			for (int j = 0; j < cell.cellArray[i].length; j++)
			{
				gridPane.add(cell.cellArray[i][j], j, i); // adds the circle objects to the array

			}
		}

		// setting the alignment of the gridPane
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		// calls the running game method
		cell.runningGame();

		BorderPane borderPane = new BorderPane();

		borderPane.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		borderPane.setCenter(cell.label);
		borderPane.setTop(gridPane);

		Scene scene = new Scene(borderPane, 600, 600, Color.DARKBLUE);// Declaring and intializing the scene

		primaryStage.setScene(scene);// adds the scene to the stage
		primaryStage.setTitle("ConnectFourFX");
		primaryStage.show(); // Shows the stage
	}

	public class Cells // This class runs the game and sets up the board
	{
		public Circle[][] cellArray = new Circle[6][7]; // This is the array that holds the board

		private String turn = "R"; // Setting the first turn for the red disk

		public Cells()
		{
			board(); // This method sets the board
		}

		// this method sets up the board
		public Circle[][] board()
		{
			// nested loops to access each element of the array
			for (int i = 0; i < cellArray.length; i++)
			{
				for (int j = 0; j < cellArray[i].length; j++)
				{
					// Initializing each circle and setting the attributes
					cellArray[i][j] = new Circle(40, Color.WHITE);
					cellArray[i][j].setStroke(Color.BLACK);
				}
			}

			return cellArray; // Returns the filled board

		}

		public void runningGame() // this method runs the game
		{
			setColor(); // calls the setColor method

		}

		/*
		 * This method handles the mouse click even as well as most other functions of
		 * the GUI. It first accesses each element of the array. Then, it uses event
		 * driven programming to detect where the user clicks on the gridPane. It then
		 * calls several methods to make sure the spot the user has chosen is allowed.
		 * If the methods called return true, the spot clicked is filled. Then, the
		 * board is checked to see if there is a winner
		 */
		public void setColor()
		{
			// Nested loop to access each element of the array
			for (int i = 0; i < cellArray.length; i++)
			{

				for (int j = 0; j < cellArray[i].length; j++)
				{
					final int x = i; // The compiler does not like using i and j because it can't reach the variables
					final int y = j; // They also must be final so the value doesn't change with the loop

					// This handles the event when the user clicks
					System.out.println(i + " " + j);
					cellArray[i][j].setOnMouseClicked(e ->
					{

						// Calls the checkIfWhite and checkUnder to make sure the spot clicked is
						// allowed
						if (checkIfWhite(x, y))
						{
							if (checkUnder(x, y))
							{
								if (turn.equals("R")) // Designates reds turn
								{

									cellArray[x][y].setFill(Color.RED); // Sets the circle that was clicked to be red

									if (isWinner())
									{ // Checks if the spot clicked is a winner
										timer();// Calls the timer method
									} else// if it is not a winner it moves on to the next turn
										turn = "Y";
								} else if (turn.equals("Y"))
								{ // Designates yellow turn

									cellArray[x][y].setFill(Color.YELLOW); // Sets the circle that was clicked to be
																			// yellow
									if (isWinner()) // Checks if the spot clicked is a winner
										timer();// Calls the timer method
									else// if it is not a winner it moves on to the next turn
										turn = "R";
								}
							}
						}

					});
				}
			}
		}

		/*
		 * This method checks whether there are any white spots under the attempted
		 * spots by checking the spot an the array under the attempted location to place
		 * a disk
		 */
		public boolean checkUnder(int row, int column)
		{
			// If the user tries to put it in the bottom row, it will always be a safe place
			// so it always returns true at row 5
			if (row == 5)
				return true;
			// This if statement checks the row under the attempted spot to make sure it has
			// a colored disk under it
			if (row + 1 <= 5 && (cellArray[row + 1][column].getFill().equals(Color.RED)
					|| cellArray[row + 1][column].getFill().equals(Color.YELLOW)))
			{

				return true;
			}

			// if the spot is not applicable, the method returns false
			return false;
		}

		// this method checks if the spot is a white spot before a disk is placed
		public boolean checkIfWhite(int row, int column)
		{
			// this statement checks if the spot clicked it white
			if (cellArray[row][column].getFill().equals(Color.WHITE))
				return true;
			// otherwise the method returns false
			return false;

		}

		private int[][] winningPos = new int[4][2]; // This array holds all the i,j values that hold a winner

		/*
		 * This method checks to see if the board has a horizontal win by cycling
		 * through the array and adding to a counter variable if the following disk is
		 * the matching color. It also sets the winning coordinates into the winningPos
		 * array
		 */
		public String horizontalWin()
		{
			// These variables hold the number of occurrences of the pattern we are looking
			// for
			int rcount = 0;
			int ycount = 0;
			// Nested loops to access each element of the array
			for (int i = 0; i < cellArray.length; i++)
			{
				// Reset the count variables each row to prevent cross over
				rcount = 0;
				ycount = 0;
				for (int j = 0; j < cellArray[i].length; j++)
				{
					// this checks if the spot checked is red, if it is it increments the count by
					// one
					if (cellArray[i][j].getFill().equals(Color.RED))
					{
						rcount++;

						if (rcount == 4) // If the count variable is 4 (a match) it returns true
						{
							// these statements set the winning coordinates to the winningPos array
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							winningPos[1][0] = i;
							winningPos[1][1] = j - 1;
							winningPos[2][0] = i;
							winningPos[2][1] = j - 2;
							winningPos[3][0] = i;
							winningPos[3][1] = j - 3;
							// The statement returns R to show the winner
							return "R";
						}
					} else // If there is not a red disk next, it stops the count and resets it
						rcount = 0;
					// this checks if the spot checked is yellow, if it is it increments the count
					// by one
					if (cellArray[i][j].getFill().equals(Color.YELLOW))
					{
						ycount++;
						if (ycount == 4) // If the count variable is 4 (a match) it returns true
						{
							// these statements set the winning coordinates to the winningPos array
							winningPos[0][0] = i;
							winningPos[0][1] = j;
							winningPos[1][0] = i;
							winningPos[1][1] = j - 1;
							winningPos[2][0] = i;
							winningPos[2][1] = j - 2;
							winningPos[3][0] = i;
							winningPos[3][1] = j - 3;
							// The statement returns Y to show the winner
							return "Y";
						}
					} else // If there is not a red disk next, it stops the count and resets it
						ycount = 0;

				}
			}
			// if the checks all fail, the method returns n
			return "n";
		}

		/*
		 * This method checks for a vertical win by looping through the cell array and
		 * checking the fill at each i,j and 3 spots above it. If it passes the 3 spots
		 * above it returns a counter variable. It also sets the winningPos spots for
		 * the winning coordinates
		 */
		public String verticalWin()
		{
			// These variables hold the number of occurrences of the pattern we are looking
			// for
			int rcount = 0;
			int ycount = 0;
			// Nested loops to access each element of the array
			for (int i = 0; i < cellArray.length; i++)
			{
				// Reset the count variables each row to prevent cross over
				rcount = 0;
				ycount = 0;

				for (int j = 0; j < cellArray[i].length; j++)
				{
					// this checks if the spot checked is red, if it is it increments the count by
					// one
					if (cellArray[i][j].getFill().equals(Color.RED))
					{
						rcount++;
						// this sets the win coordinates
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						// checks the spot above i,j and returns true is the color is red
						if (i - 1 >= 0 && cellArray[i - 1][j].getFill().equals(Color.RED))
						{
							rcount++;
							// this sets the win coordinates
							winningPos[1][0] = i - 1;
							winningPos[1][1] = j;
							// checks the 2 spots above i,j and returns true is the color is red
							if (i - 2 >= 0 && cellArray[i - 2][j].getFill().equals(Color.RED))
							{
								rcount++;
								// this sets the win coordinates
								winningPos[2][0] = i - 2;
								winningPos[2][1] = j;
								// checks the 3 spots above i,j and returns true is the color is red
								if (i - 3 >= 0 && cellArray[i - 3][j].getFill().equals(Color.RED))
								{
									rcount++;
									// this sets the win coordinates
									winningPos[3][0] = i - 3;
									winningPos[3][1] = j;
								}
							}
						}

						// If the counter variable is 4 (a match), it returns R
						if (rcount == 4)
							return "R";
						// if the counter fails, it sets the count to 0 and resets the winningPos array
						else
						{

							rcount = 0;
							for (int x = 0; x < winningPos.length; x++)
							{
								for (int y = 0; y < winningPos[x].length; y++)
								{
									winningPos[x][y] = 0;
								}
							}
						}
					}
					// this checks if the spot checked is yellow, if it is it increments the count
					// by one
					if (cellArray[i][j].getFill().equals(Color.YELLOW))
					{
						ycount++;
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						if (i - 1 >= 0 && cellArray[i - 1][j].getFill().equals(Color.YELLOW))
						{
							ycount++;
							winningPos[1][0] = i - 1;
							winningPos[1][1] = j;
							if (i - 2 >= 0 && cellArray[i - 2][j].getFill().equals(Color.YELLOW))
							{
								ycount++;
								winningPos[2][0] = i - 2;
								winningPos[2][1] = j;
								if (i - 3 >= 0 && cellArray[i - 3][j].getFill().equals(Color.YELLOW))
								{
									ycount++;
									winningPos[3][0] = i - 3;
									winningPos[3][1] = j;
								}
							}
						}

						if (ycount == 4)
							return "Y";
						else
						{
							ycount = 0;
							for (int x = 0; x < winningPos.length; x++)
							{
								for (int y = 0; y < winningPos[x].length; y++)
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

		/*
		 * This method checks for a diagonal up win by looping through through the array
		 * and checking the fill at each i, j and the diagonal spots near it. If it
		 * increments 4 times, it is a win. It also sets up the coordinates for
		 * winningPos array
		 */

		public String diagonalUpWin()
		{
			// count variables
			int rcount = 0;
			int ycount = 0;

			// Loop through to get each spot of the array
			for (int i = 0; i < cellArray.length; i++)
			{
				// resetting counts for each row
				rcount = 0;
				ycount = 0;
				for (int j = 0; j < cellArray[i].length; j++)
				{
					// if the fill is red increment the red count and set the cords
					if (cellArray[i][j].getFill().equals(Color.RED))
					{
						rcount++;
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						if ((i - 1 >= 0 && j + 1 <= 6) && cellArray[i - 1][j + 1].getFill().equals(Color.RED))
						{
							rcount++;
							winningPos[1][0] = i - 1;
							winningPos[1][1] = j + 1;
							if ((i - 2 >= 0 && j + 2 <= 6) && cellArray[i - 2][j + 2].getFill().equals(Color.RED))
							{
								rcount++;
								winningPos[2][0] = i - 2;
								winningPos[2][1] = j + 2;
								if ((i - 3 >= 0 && j + 3 <= 6) && cellArray[i - 3][j + 3].getFill().equals(Color.RED))
								{
									rcount++;
									winningPos[3][0] = i - 3;
									winningPos[3][1] = j + 3;
								}
							}
						}
						// if the count is 4 (a win) return R
						if (rcount == 4)
							return "R";
						else
						{// reseting the count and reseting the winning array
							rcount = 0;
							for (int x = 0; x < winningPos.length; x++)
							{
								for (int y = 0; y < winningPos[x].length; y++)
								{
									winningPos[x][y] = 0;

								}
							}
						}
					}

					// if the fill is yellow increment the yellow count and set the cords
					if (cellArray[i][j].getFill().equals(Color.YELLOW))
					{
						ycount++;
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						if ((i - 1 >= 0 && j + 1 <= 6) && cellArray[i - 1][j + 1].getFill().equals(Color.YELLOW))
						{
							ycount++;
							winningPos[1][0] = i - 1;
							winningPos[1][1] = j + 1;
							if ((i - 2 >= 0 && j + 2 <= 6) && cellArray[i - 2][j + 2].getFill().equals(Color.YELLOW))
							{
								ycount++;
								winningPos[2][0] = i - 2;
								winningPos[2][1] = j + 2;
								if ((i - 3 >= 0 && j + 3 <= 6)
										&& cellArray[i - 3][j + 3].getFill().equals(Color.YELLOW))
								{
									ycount++;
									winningPos[3][0] = i - 3;
									winningPos[3][1] = j + 3;
								}

							}
						}

						// if the count is 4 (a win) return Y
						if (ycount == 4)
							return "Y";
						else
						{
							// reseting the count and reseting the winning array
							ycount = 0;
							for (int x = 0; x < winningPos.length; x++)
							{
								for (int y = 0; y < winningPos[x].length; y++)
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

		/*
		 * This method checks for a diagonal down win by looping through through the
		 * array and checking the fill at each i, j and the diagonal spots near it. If
		 * it increments 4 times, it is a win. It also sets up the coordinates for
		 * winningPos array
		 */
		public String diagonalDownWin()
		{
			// Initializing count variables
			int rcount = 0;
			int ycount = 0;
			// using for loops to loop through the array
			for (int i = 0; i < cellArray.length; i++)
			{
				// resetting counts each row to prevent over lap
				rcount = 0;
				ycount = 0;
				for (int j = 0; j < cellArray[i].length; j++)
				{
					// if the fill is red increment the red count and set the cords
					if (cellArray[i][j].getFill().equals(Color.RED))
					{
						rcount++;
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						if ((i + 1 <= 5 && j + 1 <= 6) && cellArray[i + 1][j + 1].getFill().equals(Color.RED))
						{
							rcount++;
							winningPos[1][0] = i + 1;
							winningPos[1][1] = j + 1;
							if ((i + 2 <= 5 && j + 2 <= 6) && cellArray[i + 2][j + 2].getFill().equals(Color.RED))
							{
								rcount++;
								winningPos[2][0] = i + 2;
								winningPos[2][1] = j + 2;
								if ((i + 3 <= 5 && j + 3 <= 6) && cellArray[i + 3][j + 3].getFill().equals(Color.RED))
								{
									rcount++;
									winningPos[3][0] = i + 3;
									winningPos[3][1] = j + 3;
								}
							}
						}
						// if the count is 4 (a win) return R
						if (rcount == 4)
							return "R";
						else
						{// reseting the count and reseting the winning array
							rcount = 0;
							for (int x = 0; x < winningPos.length; x++)
							{
								for (int y = 0; y < winningPos[x].length; y++)
								{
									winningPos[x][y] = 0;
								}
							}
						}
					}
					// if the fill is yellow increment the yellow count and set the cords
					if (cellArray[i][j].getFill().equals(Color.YELLOW))
					{
						ycount++;
						winningPos[0][0] = i;
						winningPos[0][1] = j;
						if ((i + 1 <= 5 && j + 1 <= 6) && cellArray[i + 1][j + 1].getFill().equals(Color.YELLOW))
						{
							ycount++;
							winningPos[1][0] = i + 1;
							winningPos[1][1] = j + 1;
							if ((i + 2 <= 5 && j + 2 <= 6) && cellArray[i + 2][j + 2].getFill().equals(Color.YELLOW))
							{
								ycount++;
								winningPos[2][0] = i + 2;
								winningPos[2][1] = j + 2;
								System.out.println(ycount);
								if ((i + 3 <= 5 && j + 3 <= 6)
										&& cellArray[i + 3][j + 3].getFill().equals(Color.YELLOW))
								{
									ycount++;
									winningPos[3][0] = i + 3;
									winningPos[3][1] = j + 3;
								}

							}
						}
						// if the count is 4 (a win) return Y
						if (ycount == 4)
							return "Y";
						else
						{ // reseting the count and reseting the winning array
							ycount = 0;
							for (int x = 0; x < winningPos.length; x++)
							{
								for (int y = 0; y < winningPos[x].length; y++)
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

		// a boolean used to start the animation
		private boolean animationKey = true;
		// Label used in the winning screen
		public Label label = new Label();

		/*
		 * This method handles the actual animation by switching between two colors
		 * using the winningPos array
		 */
		public void flash()
		{
			// This handles the first shift and swaps the color of all the winning spots
			if (animationKey)
			{
				cellArray[winningPos[0][0]][winningPos[0][1]].setFill(Color.LIGHTGREEN);
				cellArray[winningPos[1][0]][winningPos[1][1]].setFill(Color.LIGHTGREEN);
				cellArray[winningPos[2][0]][winningPos[2][1]].setFill(Color.LIGHTGREEN);
				cellArray[winningPos[3][0]][winningPos[3][1]].setFill(Color.LIGHTGREEN);
				animationKey = false;
			} else
			{
				// if the win was red, it changes a label to reflect that and swaps back to the
				// original color
				if (turn.equals("R"))
				{
					label.setTextFill(Color.RED);
					label.setText("RED WINS");
					label.setFont(new Font(50));

					cellArray[winningPos[0][0]][winningPos[0][1]].setFill(Color.RED);
					cellArray[winningPos[1][0]][winningPos[1][1]].setFill(Color.RED);
					cellArray[winningPos[2][0]][winningPos[2][1]].setFill(Color.RED);
					cellArray[winningPos[3][0]][winningPos[3][1]].setFill(Color.RED);
					animationKey = true;
					;
				}
				// if the win was yellow, it changes a label to reflect that and swaps back to
				// the original color
				else if (turn.equals("Y"))
				{
					label.setTextFill(Color.YELLOW);
					label.setText("YELLOW WINS");
					label.setFont(new Font(50));

					cellArray[winningPos[0][0]][winningPos[0][1]].setFill(Color.YELLOW);
					cellArray[winningPos[1][0]][winningPos[1][1]].setFill(Color.YELLOW);
					cellArray[winningPos[2][0]][winningPos[2][1]].setFill(Color.YELLOW);
					cellArray[winningPos[3][0]][winningPos[3][1]].setFill(Color.YELLOW);
					animationKey = true;
					;

				}

			}
		}

		/*
		 * Calls the flash() method and sets up the timer
		 * 
		 */
		public void timer()
		{
			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), e ->
			{
				flash();
			}));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
		}

		// This method just checks which of the win methods return true and then returns
		// that winner
		public boolean isWinner()
		{
			if (horizontalWin().equals("R"))
				return true;
			else if (horizontalWin().equals("Y"))
				return true;
			else if (verticalWin().equals("R"))
				return true;
			else if (verticalWin().equals("Y"))
				return true;
			else if (diagonalUpWin().equals("R"))
				return true;
			else if (diagonalUpWin().equals("Y"))
				return true;
			else if (diagonalDownWin().equals("R"))
				return true;
			else if (diagonalDownWin().equals("Y"))
				return true;
			return false;
		}
	}
}