import java.util.Scanner;

/*Daniel Viar
 * EECS 1510
 * Dr.Hobbs
 * 4/4/2020
 * 
 * This project is a connect four game that plays until the user decides to stop
 * 
 */
public class ConnectFour
{
	static Scanner userInput = new Scanner(System.in); // Declaring the scanner
	public static void main(String [] args)
	{
		char [][] board = new char [6][7]; //The array that is being used and printed for the game
		runningGame(board); // This method runs the game
		userInput.close(); // Closing the scanner
	}
	/*
	 * The dropDisk method takes in the board, column the user chose, and the color of the disk
	 * then it makes sure that the entered column is a valid choice and invokes the columnFull method
	 * to make sure the column is not full. If either of these cases happen the user can input another one.
	 * Then, the for loops work to drop the disk
	 * 
	 */
	
	
	private static void runningGame(char [][] board) 
	{
		 // Declaring the scanner for column input
		int playTime = 1; // setting parameter for the loop that determines whether the user would like to play another game
		while(playTime==1) // This loop runs until the user puts in a zero stopping the game
		{// if the user keeps putting in ones, the board will reset and another game can be played

		int count = 0;// variable keeping track of the turns
		int column = -1;// the initial column value 
		while(count < 42) // this loop keeps track of the turns and runs till there are no more spaces
		{
			
			printBoard(board); // The method call prints the board
			
			if(isWinner(board))// this condition checks if the isWinner method is true
			{
				
				System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)");
				playTime = userInput.nextInt(); // The user decides if they want to play a new game
				if(playTime == 1) // this conditional and following variable value changes
				{// reset the board
				count = 0;
				column = -1;
			
					for(int i = 0; i < board.length; i++) //These for loops reset the board filling all
					{// the spaces with blanks instead of what was previously there
						for(int j = 0; j < board[i].length;j++) 
						{
							board[i][j] = ' ' ;
					
						}	
					}
				printBoard(board); // This prints the newly blanked out board for the user to play again
				}
				else // if the isWinner method returns false then nothing happens and the conditon breaks
					break;	
			}
					
					/*Yellow goes first cause count starts at 0 and this conditional checks for even numbers. 
					 * Yellow gets the even numbers and every time count is even, 
					 * it takes in the column the user wants to put the disk into.
					 * Then it calls the drop disk method
					 */ 
			if(count % 2 == 0) // Even turn so yellow's turn
			{ 
				column = userInput.nextInt(); //Takes in user input (column)
				dropDisk(board, column, 'Y'); // calls the dropDisk method for Y
				
			}
		/*Red goes second cause count starts at 0 and this conditional checks for odd numbers. 
		 *Red gets the odd numbers and every time count is odd, 
		 *it takes in the column the user wants to put the disk into
		 * Then it calls the drop disk method
		 */
			if(count % 2 != 0) // odd turn so red's turn
			{ 
				column = userInput.nextInt(); // Takes in user input (column)
				dropDisk(board, column, 'R');// calls the dropDisk method for R
			}
				count++; // This changes the turn 
			}
		
			if(boardFull(board)) //This conditional checks if the boardFull method is true
			{
				/*This conditional tells the user the board is full and asks them if they would like to play
				 * another then the for loops reset the board
				 */
				System.out.println("I declare a draw");
				System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)");
				playTime = userInput.nextInt(); // Takes in the user's choice if they want to play again
				if(playTime == 1) { // If the user wants to play again, resets the board
				//These for-loops access every element and set it to a blank spot
					for(int i = 0; i < board.length; i++)
					{
						for(int j = 0; j < board[i].length;j++) 
						{
							board[i][j] = ' ' ;
					
						}	
					}
				}
				else //if the user selects 0 it terminates the game
					break;
		
			}
		}
	}
	
	private static void dropDisk(char [][] board, int column, char color) 
	{
		// Scanner in case the user needs to put in a new column number
		int count = 0; // declaring a count variable for the length of the loop
		while(column >= 7)
	 	{
	 		System.out.print("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL.");
			column = userInput.nextInt();// the user has an option to select a new column
	 	}
		while(columnIsFull(board,column))
		{
		System.out.print("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL.");
		column = userInput.nextInt(); // the user has an option to select a new column
		}

		while(count <= 5) 
		{ /*The length of the board is 5 so it does not cause an arrayOutOfBounds error because it cannot
		  *access outside the array. Checks to make sure that The space is empty in the spot 
		  *of the chosen column. If it is not, it checks the spot above until it finds an open spot. 
		  *If the column was full, the user is already told from the call to the column full method above
		  */
			if((board[count][column] != 'R' && board[count][column] != 'Y') || board[count][column] == ' ') 
			{
		
				board[count][column] = color; //This code "drops the disk" by changing the blank spot to
				// the chosen color
				break; // once the spot has been changed the loop stops because there is no reason to keep it going
			}
			count++; // This increments the loop to check the row above
		}
	}
	
	
	
		/*This method checks if the chosen column is full and is called in the dropDisk method
		 * It takes in the board and the chosen column as parameters
		 */
		
	private static boolean columnIsFull(char [][] board, int column) 
	{
		int count = 0; // a count variable to increment if a spot is full
		// This loop accesses every spot in the column
		for(int i = 0; i < board.length; i++)
		{
			if(board[i][column] != ' ' && (board[i][column] == 'R' || board[i][column] == 'Y'))
							
				/*The conditional checks if the spot has a disk or no empty spaces
				 * if this is true, it increments count by one
				 */
				count++;
					
		}
		if(count == 6) //If count = 6, then all the spaces are full and returns true
			return true;
		
		return false; //otherwise the method is false		
	}
	/*This method checks if the board is full. It checks every element in the array and if there is a Y
	 * or R in that element, it increments a count variable. If this count variable is equal to the size
	 * of the board it returns true, otherwise it returns false
	 * 
	 */
	private static boolean boardFull(char [][] board)
	{
		int count = 0; // Declaring a counter variable to use 
		for(int i = 0; i < board.length; i++) // Nested for-loops to access every element of the array
		{
			for(int j = 0; j < board[i].length;j++) 
			{
				if(board[i][j] == 'R' || board[i][j] == 'Y')// if the element is full 
					count++;// increase the count by one
			}
		}
		
		if(count >= 42) // If the count is greater than or equal to 42,(the size of the board) it is full
			return true; // the method returns true
		else// otherwise it returns false
			return false;
	}
	
	/*This method checks is there is a horizontal win for red. A count variable is declared and the loop
	 * checks every element. As the loop accesses each element, it moves from left to right. When the row is 
	 * over it moves up to the next row. So the if statement checks if the element is R then increments.
	 * If it does this four times and check equals 4, the method returns true. If the element was Y or a blank , it
	 * goes to the else statement bring check down to zero again. Check is also set to zero at the beginning
	 * of each row to prevent crossover
	 */
	private static boolean horizontalRedWin(char [][] board)
	{
		int check = 0; // declaring a counter variable
		
		for(int i = 0; i < board.length; i++)// Nested for loops to access each element
		{
			check = 0; // Set to 0 for each new row
			for(int j = 0; j < board[i].length;j++) 
			{
				
				if(board[i][j] == 'R') // Checks if the element is R
				{
					check++;// Increments check if it is R
				
					if(check == 4)// If check becomes 4, it means there was 4 R's in a row so a win
						return true;// the method returns true
				}
				else // If there is something other than a R, it brings check to zero
					check = 0;
			}
			
		}
		return false;// If the method never returns true, it returns false by default
	}
	
	
	/*This method checks is there is a horizontal win for Yellow. A count variable is declared and the loop
	 * checks every element. As the loop accesses each element, it moves from left to right. When the row is 
	 * over it moves up to the next row. So the if statement checks if the element is Y then increments.
	 * If it does this four times and check equals 4, the method returns true. If the element was R or a blank , it
	 * goes to the else statement bring check down to zero again. Check is also set to zero at the beginning
	 * of each row to prevent crossover
	 */
	
	private static boolean horizontalYellowWin(char [][] board)
	{
		int check = 0; // declaring a counter variable
		
		for(int i = 0; i < board.length; i++)// Nested for loops to access each element
		{
			check = 0;// Set to 0 for each new row
			for(int j = 0; j < board[i].length;j++) 
			{
				
				if(board[i][j] == 'Y') // Checks if the element is Y 
				{
					check++;// Increments check if it is Y
				
					if(check == 4)// If check becomes 4, it means there was 4 Y's in a row so a win
						return true;// the method returns true
				}
				else // If there is something other than a Y, it brings check to zero
					check = 0;
			}
			
		}
		return false; // If the method never returns true, it returns false by default
	}
	
	
	/*This method checks for a vertical win for red. We declare a count variable check to keep track of how many
	 * matching R's. We then access each element of the array with nested for-loops. First we check if there is a
	 * R. If it is true, check is incremented by one. Then we make sure the element above it is an index that exists
	 * to prevent an out of bounds error and we check if it is also R. We again increment by one. We do this 2 more
	 * times. If check is 4, it is a winner and the method returns true. Check is reset every row to prevent any
	 * overlap from the previous row. If check is never 4, the method returns false indicating it was not
	 * a winning board
	 */
	private static boolean verticalRedWin(char [][] board)
	{
		int check = 0; // declaring the count variable
		
		for(int i = 0; i < board.length; i++) // Nested for-loops to check each element
		{
			check = 0; // reseting check so there is no overlap from the previous row
			for(int j = 0; j < board[i].length;j++) 
			{
				if(board[i][j] == 'R') //checking if the element is R
				{
					check++; // if it is we increment by 1
					if((i + 1) <=5  && board[i+1][j] == 'R' ) //Checking the spot above is a valid index
					{										 // and it is R
						check++; // If it is we increment by 1
						if((i + 2) <= 5 && board[i+2][j] == 'R')//Checking the spot above is a valid index
						{										// and it is R
							check++;// if it is we increment by 1
							if((i + 3) <= 5 && board[i+3][j] == 'R')//Checking the spot above is a valid index
																	// and it is R
								check++; // If it is we increment by 1
						}
					}
				}
				if (check == 4) // If check becomes 4, the board is a winner so it returns true
					return true;
				else // if the check does not return 4, it resets the value and moves on to the next element
					check = 0;
			}
		}
		return false; // if the method does not return true, it returns false by default
	}
	
	
	/*This method checks for a vertical win for yellow. We declare a count variable check to keep track of how many
	 * matching Y's. We then access each element of the array with nested for-loops. First we check if there is a
	 * Y. If it is true, check is incremented by one. Then we make sure the element above it is an index that exists
	 * to prevent an out of bounds error and we check if it is also Y. We again increment by one. We do this 2 more
	 * times. If check is 4, it is a winner and the method returns true. Check is reset every row to prevent any
	 * overlap from the previous row. If check is never 4, the method returns false indicating it was not
	 * a winning board
	 */

	private static boolean verticalYellowWin(char [][] board)
	{
		int check = 0; // declaring the count variable
		
		for(int i = 0; i < board.length; i++) // Nested for-loops to check each element
		{
			check = 0; // reseting check so there is no overlap from the previous row
			for(int j = 0; j < board[i].length;j++) 
			{
				if(board[i][j] == 'Y') //checking if the element is Y
				{
					check++; // if it is we increment by 1
					
					if((i + 1) <= 5 && board[i+1][j] == 'Y') //Checking the spot above is a valid index
					{										// and it is Y
						check++; // if it is we increment by 1
						if((i + 2) <= 5 && board[i+2][j] == 'Y') //Checking the spot above is a valid index
						{										// and it is Y
							check++; // if it is we increment by 1
							if((i + 3) <= 5 && board[i+3][j] == 'Y' )//Checking the spot above is a valid index
																	// and it is Y
								
								check++; // if it is we increment by 1
						}		
					}	
		
					
				}
				if(check == 4) // If check becomes 4, the board is a winner so it returns true
					return true;
				else 
					check = 0; // if the check does not return 4, it resets the value and moves on to the next element
			}
		}
		return false; // if the method does not return true, it returns false by default
	}

	/*This method checks whether there is a win diagonally upwards(so like going up some stairs) for R. 
	 * We first declare a counter variable to keep track of how many diagonal up R's there are. Then we check
	 * every element with nested for-loops reseting check with every new row to prevent row overlap. Next, we
	 * check the element for an R. Then we make sure the element over right one and up on are contained in the
	 * array to prevent an out of bounds error and check if it is an R. If it is we increment check by 1.
	 * We do this 2 more times. If check is 4 the method returns true and it is a winning board. If the one of the 
	 * elements is not an R, it sets check to 0. The method returns false by default.
	 */
	private static boolean diagonalUpRedWin(char [][] board)
	{
		int check = 0; // declaring a counter variable
		
		for(int i = 0; i < board.length; i++) // nested for-loops to check each element
		{
			check = 0; // setting check to 0 prevent row overlap
			for(int j = 0; j < board[i].length;j++) 
			{
				if(board[i][j] == 'R') // checking if the element is R
				{
					check++; // increments by 1
					if(((i + 1) <= 5 && (j + 1) <= 6) && board[i+1][j+1] == 'R')
					{ // checking if the wanted element is not outta bounds and is R
						check++; // increments by 1
						if(((i + 2) <= 5 && (j + 2) <= 6) && board[i+2][j+2] == 'R')
						{ // checking if the wanted element is not outta bounds and is R
							check++; // increments by 1
							if(((i + 3) <= 5 && (j + 3) <= 6) && board[i+3][j + 3] == 'R')
								// checking if the wanted element is not outta bounds and is R
								check++; // increments by 1
						}
							
					}
				}
				if(check == 4) // if check equals 4 at that element, then the method returns true
					return true;
				else // if it does not then check is reset for the next check
					check = 0;
			}
		}
		return false; // if the method does not return true it returns false by default
	}
	
	
	/*This method checks to see if there is a win diagonally downward (like going down stairs) for red. We first declare
	 * a counter variable. Then we use nested loops to access every element of the array reseting the count at the
	 * beginning of every new row to prevent row overlap. Then we check if the element is R. We then check to see
	 * if the element below and to the right are valid indexes and if those elements are R's. If it is, we increment
	 * check by one. We do this 2 more times. If check equals 4, then the method returns true and the board is a 
	 * winner. If the checked elements are not R's check is set to 0. 
	 * If the method never returns true it returns false
	 */
	
	private static boolean diagonalDownRedWin(char [][] board)
	{
		
		int check = 0; //  declaring the counter variable
		
		for(int i = 0; i < board.length; i++) //  using nested for-loops to access each element
		{
			check = 0; // reseting check to prevent loop overlap
			for(int j = 0; j < board[i].length;j++) 
			{	
				if(board[i][j] == 'R') // checking if the element is R
				{
					check++; // if it is we increment by 1
					if(((i - 1) >= 0  && (j + 1) <= 6) && board[i-1][j+1] == 'R')
					{ //checking if the element is in bounds and is R 
						check++; // if it is increment by 1
						
						if(((i - 2) >= 0 && (j + 2) <= 6) && board[i-2][j+2] == 'R')
						{ //checking if the element is in bounds and is R 
							check++; // if it is increment by 1
							
							if(((i - 3) >= 0 && (j + 3) <= 6) &&board[i - 3][j + 3 ] == 'R')
							{ //checking if the element is in bounds and is R 
								check++; // if it is increment by 1
							}
						}
					
					}
					if(check == 4) // if check is 4, the method returns true
						return true;
					else // otherwise it resets check to 0
						check = 0;
				}
				
			}
			
		}
		
		
		return false; // If the method does not return true, it returns false by default
	}
	
	
	/*This method checks whether there is a win diagonally upwards(so like going up some stairs) for Y. 
	 * We first declare a counter variable to keep track of how many diagonal up Y's there are. Then we check
	 * every element with nested for-loops reseting check with every new row to prevent row overlap. Next, we
	 * check the element for an Y. Then we make sure the element over right one and up on are contained in the
	 * array to prevent an out of bounds error and check if it is an Y. If it is we increment check by 1.
	 * We do this 2 more times. If check is 4 the method returns true and it is a winning board. If the one of the 
	 * elements is not an Y, it sets check to 0. The method returns false by default.
	 */
	
	private static boolean diagonalYellowUpWin(char [][] board)
	{
		int check = 0; // declaring a counter variable
		
		for(int i = 0; i < board.length; i++) // nested for-loops to check each element
		{
			check = 0; // setting check to 0 prevent row overlap
			for(int j = 0; j < board[i].length;j++) 
			{
				if(board[i][j] == 'Y') // checking if the element is Y
				{
					check++;// if it is increment by 1
					if(((i + 1) <= 5 && (j + 1) <= 6) && board[i+1][j+1] == 'Y')
					{// checking if the wanted element is not outta bounds and is Y
						check++;// if it is increment by 1
						if(((i + 2) <= 5 && (j + 2) <= 6) && board[i+2][j+2] == 'Y')
						{// checking if the wanted element is not outta bounds and is Y
							check++;// if it is increment by 1
							if(((i + 3) <= 5 && (j + 3) <= 6) && board[i+3][j + 3] == 'Y')
								// checking if the wanted element is not outta bounds and is Y
								check++;// if it is increment by 1
						}
							
					}
				}
				if(check == 4) // if check is 4, the method returns true
					return true;
				else   // otherwise it resets check to 0
					check = 0;
			}
		}
		return false; // If the method does not return true, it returns false by default
	}
	
	
	/*This method checks to see if there is a win diagonally downward (like going down stairs) for yellow. We first declare
	 * a counter variable. Then we use nested loops to access every element of the array reseting the count at the
	 * beginning of every new row to prevent row overlap. Then we check if the element is Y. We then check to see
	 * if the element below and to the right are valid indexes and if those elements are Y's. If it is, we increment
	 * check by one. We do this 2 more times. If check equals 4, then the method returns true and the board is a 
	 * winner. If the checked elements are not Y's check is set to 0. 
	 * If the method never returns true it returns false
	 */
	
	private static boolean diagonalYellowDownWin(char [][] board)
	{
		int check = 0; // declaring a counter variable
		
		for(int i = 0; i < board.length; i++) // nested for-loops to check each element
		{
			check = 0; // setting check to 0 prevent row overlap
			for(int j = 0; j < board[i].length;j++) 
			{	
			
				if(board[i][j] == 'Y') // checking if the element is Y
				{
					check++;// if it is increment by 1
					if(((i - 1) >= 0  && (j + 1) <= 6) && board[i-1][j+1] == 'Y')
					{// checking if the wanted element is not outta bounds and is Y
						check++;// if it is increment by 1
						
						if(((i - 2) >= 0 && (j + 2) <= 6) && board[i-2][j+2] == 'Y')
						{// checking if the wanted element is not outta bounds and is Y
							check++;// if it is increment by 1
							
							if(((i - 3) >= 0 && (j + 3) <= 6) &&board[i - 3][j + 3 ] == 'Y')
							{// checking if the wanted element is not outta bounds and is Y
								check++;// if it is increment by 1
							}
						}
					
					}
					if(check == 4) // if check is 4, the method returns true
						return true;
					else // otherwise it resets check to 0
						check = 0;
				}
				
					
			}
		
		}
		return false; // If the method does not return true, it returns false by default
	}
	
	/* This method checks to see if there is a winner by calling all the methods that check to see if the board is 
	 * a winner. If any of these return true, the player who won is determined and it is printed to the console.
	 * If all of the called methods return false, the whole method returns false
	 *   
	 */
	
	
	private static boolean isWinner(char [][] board) 
	{
		if(horizontalYellowWin(board)) 
		{
			System.out.println("Y has WON the game!");
			return true;
		}
		else if(horizontalRedWin(board))
		{
			System.out.println("R has WON the game!");
			return true;
		}
		else if(verticalRedWin(board))
		{
			System.out.println("R has WON the game!");
			return true;
		}
		else if(verticalYellowWin(board))
		{
			System.out.println("Y has WON the game!");
			return true;
		}
		else if (diagonalUpRedWin(board))
		{
			System.out.println("R has WON the game!");
			return true;
		} 
		else if(diagonalDownRedWin(board))
		{
			System.out.println("R has WON the game!");
			return true;
		}
		else if(diagonalYellowUpWin(board))
		{
			System.out.println("Y has WON the game!");
			return true;
		}
		else if(diagonalYellowDownWin(board))
		{
			System.out.println("Y has WON the game!");
			return true;
		}
		return false;
		
		
		
	}
	
	/*This method prints the board by accessing each element of the array and printing the bars
	 * and then printing the value at that spot of the loop
	 * 
	 */
	private static void printBoard(char[][] board)    
	{ 
		for(int i = board.length - 1; i >= 0; i--)     
		{        System.out.print("| ");       
			for(int j = 0; j <board[i].length; j++)       
			{           
			System.out.print(board[i][j] + " | ");        
			}        
		System.out.println();//new line     
		}   
	}
		
}




