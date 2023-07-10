/*Knights path
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * This project takes in starting and ending coordinates between 0 and 7 and calculates
 * the shortest path between them that a knight could move. This class contains the main that
 * checks the coordinates and calls the functions that find the path.
 */

package KnightPath;

//Import scanner for user input
import java.util.Scanner;

public class project2
{

	public static void main(String[] args)
	{

		Scanner userInput = new Scanner(System.in);
		// Takes in the starting row and column
		System.out.println("Input the starting row and column separated by a space:");
		int startRow = userInput.nextInt();
		int startCol = userInput.nextInt();
		// This set of loops and conditions checks if the number inputed by the user is
		// valid
		while ((startRow < 0 || startCol < 0) || (startRow > 7 || startCol > 7))
		{
			if (startRow > 7 || startRow < 0)
			{
				System.out.println("The row must be between 0 and 7.  Try again.");
				startRow = userInput.nextInt();
				startCol = userInput.nextInt();
			}
			if (startCol > 7 || startCol < 0)
			{
				System.out.println("The column must be between 0 and 7.  Try again.");
				startRow = userInput.nextInt();
				startCol = userInput.nextInt();
			}
		}
		// Takes in final row and column
		System.out.println("Input the final row and column separated by a space:");
		int endRow = userInput.nextInt();
		int endCol = userInput.nextInt();
		// This set of loops and conditions checks if the number inputed by the user is
		// valid
		while ((endRow < 0 || endCol < 0) || (endRow > 7 || endCol > 7))
		{
			if (endRow > 7 || endRow < 0)
			{
				System.out.println("The row must be between 0 and 7.  Try again.");
				endRow = userInput.nextInt();
				endCol = userInput.nextInt();
			}
			if (endCol > 7 || endCol < 0)
			{
				System.out.println("The column must be between 0 and 7.  Try again.");
				endRow = userInput.nextInt();
				endCol = userInput.nextInt();
			}
		}

		// This object takes in the rows and columns
		KnightsPath path = new KnightsPath(startRow, startCol, endRow, endCol);

		// Calls the getShortestPath method that returns the path
		System.out.println(path.getShortestPath(startRow, startCol, endRow, endCol));

	}

}
