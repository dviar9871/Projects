/*Decimal and Binary Pallindrome checker
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * Implements the pallindrome checking methods as well as the binary conversion. 
 */

//Importing util class for scanner and arrayList
import java.util.*;

public class project1
{

	public static void main(String[] args)
	{
		//declaring all objects being used
		Scanner userInput = new Scanner(System.in);
		project1 proj1 = new project1();
		ArrayList<Integer> pallindromeDec = new ArrayList<Integer>();
		ArrayList<Integer> pallindromeBinary = new ArrayList<Integer>();
		// declaring starting and ending ranges
		int range1;
		int range2;
		//taking in the input range
		System.out.println("Input Range ->");
		range1 = userInput.nextInt();
		range2 = userInput.nextInt();
		//Conditionals to make sure input is valid
		while (range1 > range2 || range1 <= 0 || range2 <= 0)
		{//Loop will run until inputs are valid
			if (range1 > range2)
			{//Checks size condition
				System.out.println("Error:  First number is larger than second number.   Try Again.");
				range1 = userInput.nextInt();
				range2 = userInput.nextInt();
			} else if (range1 <= 0 || range2 <= 0)
			{//checks negative or zero condition
				System.out.println("Error:  Invalid value entered.  All values must be greater than zero.  Try Again.");
				range1 = userInput.nextInt();
				range2 = userInput.nextInt();
			}
		}
		//counter variable to use to go between range 1 and range 2
		int count = 0;
		//This loop dictates what is put into the pallindrome method
		for (int i = range1; i <= range2; i++)
		{
			//Checks if the number at the specific range is a decimal pallindrome
			if (proj1.pallindromeDec(range1 + count))
			{//Converts the decimal number to binary and then checks if it is a pallindrome
				if (proj1.pallindromeBinary(proj1.convertToBinary(range1 + count)))
				{//If both are pallindromes, they are added to respective lists
					pallindromeDec.add(i);
					pallindromeBinary.add(proj1.convertToBinary(i));
				}
			}
			count++;
		}
		//Prints result
		for (int i = 0; i < pallindromeDec.size(); i++)
		{
			System.out.println("Base 10               Binary \n" + pallindromeDec.get(i) + "                     "
					+ pallindromeBinary.get(i));
		}
		//closes scanner
		userInput.close();
	}
	/*This method checks whether a decimal number is a pallindrome using a stack. It does so
	 * by taking an individual digit off of the decimal pushing onto a stack as well as putting
	 * it onto a list. Then the numbers are compared to determine if it is a pallindrome.
	 */
	private boolean pallindromeDec(int value)
	{
		//Declaring list, stack, and variable to hold argument
		ArrayList<Integer> originalNum = new ArrayList<Integer>();
		stack list = new stack();
		int individualVal = value;
		//if the value is less than ten, then it is already a pallindrome
		if (value < 10)
			return true;
		//Loop will run until individualVal has nothing left in it to check
		while ((double) individualVal > 0)
		{
			//adds single digit to stack and list
			originalNum.add(individualVal % 10);
			list.push(individualVal % 10);
			//removes last digit off of the int
			individualVal /= 10;

		}
		//Variable to compare the size of list to correct number of digits
		int truthChecker = 0;
		//compares the digits on the stack and in the list to see if it is a pallindrome
		for (int i = 0; i < originalNum.size(); i++)
		{
			if (list.pop() == originalNum.get(i))
				truthChecker++;

		}
		
		if (truthChecker == originalNum.size())
			return true;
		else
			return false;
	}
	
	/*This method checks whether a decimal number is a pallindrome using a stack. It does so
	 * by taking an individual digit off of the decimal pushing onto a stack as well as putting
	 * it onto a list. Then the numbers are compared to determine if it is a pallindrome.
	 */
	private boolean pallindromeBinary(int value)
	{
		//Declaring list, stack, and variable to hold argument
		stack list = new stack();
		ArrayList<Integer> originalNum = new ArrayList<Integer>();
		int individualVal = value;
		//Loop will run until individualVal has nothing left in it to check
		while ((double) individualVal > 0)
		{
			//adds single digit to stack and list
			originalNum.add(individualVal % 10);
			list.push(individualVal % 10);
			//removes last digit off of the int
			individualVal /= 10;
		}
		//Variable to compare the size of list to correct number of digits
		int truthChecker = 0;
		//compares the digits on the stack and in the list to see if it is a pallindrome
		for (int i = 0; i < originalNum.size(); i++)
		{
			if (list.pop() == originalNum.get(i))
				truthChecker++;

		}

		if (truthChecker == originalNum.size())
			return true;
		else
			return false;
	}
	/*This method converts a given integer in base 10 into a binary integer by taking in a 
	 * single digit on a stack, cutting off the digit just looked at, then putting the values
	 * in the stack into a string to flip the value around. It is then parsed to an int
	 * then returned
	 */
	private int convertToBinary(int userInput)
	{
		//declaring variables to hold and objects
		int UIHold = userInput;
		stack list = new stack();
		String binString = "";
		int binHold = 0;
		//loop that converts the decimal to binary
		while (UIHold > 0)
		{
			//gets last digit
			binHold = UIHold % 2;
			//pushes digit on stack
			list.push(binHold);
			//gets rid of the digit that was just looked at
			UIHold /= 2;
		}
		//puts stack values in string to flip it the right way
		while (list.size() > 0)
		{

			binString += Integer.toString(list.pop());
		}
		//changes the string that holds the binary digits to an int
		binHold = (int) Double.parseDouble(binString);
		return binHold;

	}

}
