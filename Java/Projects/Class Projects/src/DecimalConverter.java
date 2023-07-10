/*This program takes in a user input and converts it into binary, octal, and hexadecimal
 *
 * Daniel Viar
 * 2/18/2020
 * EECS 1510
 * Dr.Hobbs
 */

import java.util.Scanner;
public class DecimalConverter
{
	public static void main(String [] args)
	{
		
		Scanner userInput = new Scanner(System.in); //Creating a scanner then taking in the number
		int number = userInput.nextInt(); 
		System.out.println(number);
		//Conversion to binary
		int num = number;
		/* We have to reuse number so we declared a variable to use in the actual calculation. 
		* If we used number in the calculations, the value of number would be zero by the time
		*  we started the octal conversion and the hex conversion
		*/
		String sumForBinary = ""; // Creating a string to hold all the single values 
		int tempForBinary = 0; // Declaring a temporary variable to get moded by 2
		
		for(int i = 0; i < 16; i++) // using a loop to iterate 16 times to get 2 bytes worth of numbers
		{
			tempForBinary = num; // Giving tempForBinary the value of the num

		/*This step divides num by 2 to give us the next value to divide for the next iteration
		 * and mods tempForBinary by 2 to give us the actual conversion to binary
		 * EX: num = 51 -> num / 2 = 25 -> tempForBinary % 2 = 1
		 * 
		 */
			num /= 2;
			tempForBinary %= 2;
			
			sumForBinary += tempForBinary; // Adds the binary digit to the string and holds the string 
			// this string will be flipped from the result we want
		}
		
		String flipForBinary = ""; // Declaring string to hold the correct string 
		
		for(int i = 0; i < 16; i++) // loop goes through the entire string
		{
			flipForBinary += sumForBinary.substring(sumForBinary.length() - 1); // takes in the last digit
			sumForBinary = sumForBinary.substring(0, sumForBinary.length() - 1);// takes away the last digit from the string
		}
		
		System.out.println(flipForBinary);
		
		
		//Conversion to Octal
		num = number; // reseting the value of num for the next conversion
		
		String sumForOctal = ""; // Creating a string to hold all the single values 
		int tempForOctal = 0; // Declaring a temporary variable to get moded by 8
		
		for(int i = 0; i < 6; i++) // using a loop to iterate 16 times to get 2 bytes worth of numbers
		{
			tempForOctal = num;  // Giving tempForOctal the value of the num
			
			/*This step divides num by 8 to give us the next value to divide for the next iteration
			 * and mods tempForOCtal by 8 to give us the actual conversion to Octal
			 * EX: num = 65 -> num / 8 = 8 -> tempForBinary % 8 = 1
			 * 
			 */
			num/= 8;
			tempForOctal %= 8;
			
			sumForOctal += tempForOctal; // Adds the octal digit to the string and holds the string 
			// this string will be flipped from the result we want
		}
		String flipForOctal = ""; // Declaring string to hold the correct string 
		
		for(int i = 0; i < 6; i++) // loop goes through the entire string
		{
			flipForOctal += sumForOctal.substring(sumForOctal.length() - 1); // takes in the last digit
			sumForOctal = sumForOctal.substring(0, sumForOctal.length() - 1);// takes away the last digit from the string
		}
		
		System.out.println(flipForOctal);
		
		
		//Conversion to HexaDecimal
		num = number; // reseting the value of num for the next conversion
		
		String sumForHex = ""; // Creating a string to hold all the single values 
		int tempForHex = 0; // Declaring a temporary variable to get moded by 16
		String upperDigits = "";//Hex uses letters to represent digits so we declare this to hold those
		for(int i = 0; i < 4; i++) // using a loop to iterate 16 times to get 2 bytes worth of numbers
		{
			tempForHex = num;  // Giving tempForHex the value of the num
			
			/*This step divides num by 16 to give us the next value to divide for the next iteration
			 * and mods tempForHex by 16 to give us the actual conversion to Octal
			 * EX: num = 65 -> num / 16 = 4 -> tempForHex % 16 = 1
			 * 
			 */
			num/= 16;
			
			tempForHex %= 16;
			if(tempForHex == 10) {   //this set of if statements determines what alphabetical digit is represented
				upperDigits = "a"; // and adds it to the string (only occurs for returns 10-16)
				sumForHex += upperDigits; 
			}else if(tempForHex == 11) {  
				upperDigits = "b";
				sumForHex += upperDigits;
			}else if(tempForHex == 12) {  
				upperDigits = "c";
				sumForHex += upperDigits;
			}else if(tempForHex == 13) {  
				upperDigits = "d";
				sumForHex += upperDigits;
			}else if(tempForHex == 14) {  
				upperDigits = "e";
				sumForHex += upperDigits;
			}else if(tempForHex == 15) {  
				upperDigits = "f";
				sumForHex += upperDigits;
			}else if(tempForHex == 16) {  
				upperDigits = "g";
				sumForHex += upperDigits;
			}else { //if the hex digit is 0-9 it will just add to this string
				sumForHex += tempForHex; // Adds the hex digit to the string and holds the string 
				// this string will be flipped from the result we want
			}
			
			
		}
		String flipForHex = ""; // Declaring string to hold the correct string 
		
		for(int i = 0; i < 4; i++) // loop goes through the entire string
		{
			flipForHex += sumForHex.substring(sumForHex.length() - 1); // takes in the last digit
			sumForHex = sumForHex.substring(0, sumForHex.length() - 1);// takes away the last digit from the string
		}
		
		System.out.println(flipForHex);
		
	}
}
