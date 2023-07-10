package KnightsPath;

import java.util.Scanner;

public class project2
{
	public static void main(String[] args)
	{

		Scanner userInput = new Scanner(System.in);

		System.out.println("Input the starting row and column separated by a space:");
		int startRow = userInput.nextInt();
		int startCol = userInput.nextInt();
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
		System.out.println("Input the final row and column separated by a space:");
		int endRow = userInput.nextInt();
		int endCol = userInput.nextInt();

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

		KnightsPath path = new KnightsPath(startRow, startCol, endRow, endCol);

		userInput.close();
		System.exit(0);
	}

}
