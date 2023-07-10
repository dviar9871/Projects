/*Mail Sorter
 * EECS 2500
 * Professor: Dr.Heuring
 * By: Daniel Viar
 * 
 * 
 * This project sorts a list of addresses that are obtained from a file and sorts them by
 * zip code using a radix sort and then outputs it to a separate file. This specific class holds
 * the methods that are being called by project0. It holds the methods that fills an ArrayList 
 * with addresses and sorts them.
 */

//Importing needed classes
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AddressReader
{
	//list to hold all the project0 object
	private ArrayList<Project0> list = new ArrayList<Project0>();

	// Default constructor
	public AddressReader()
	{

	}
	//getter method for the list
	public ArrayList<Project0> getList(){
		
		return list;
	}
	//This method fills the ArrayList with project0 objects from the file
	public void addressFiller(String fileName) throws FileNotFoundException
	{
		//taking in the file
		File file = new File(fileName);
		//scanner class is used for the file input
		Scanner input = new Scanner(file);
		//loop will run while the file still has a next line to read in
		while (input.hasNextLine())
		{
			//adds each line of the text file and holds the value
			String name = input.nextLine();
			String addressLine1 = input.nextLine();
			String addressLine2 = input.nextLine();
			String city = input.nextLine();
			String state = input.nextLine();
			int zipcode = input.nextInt();
			input.nextLine();
			//adds held values to an object
			list.add(new Project0(name, addressLine1, addressLine2, city, state, zipcode));
		}

		input.close();

	}
	/*This method is the RadixSort. It sorts by looking at each digit and puts it into bins or buckets.
	 * It takes those bins from 0 - 9 and puts it back into the list from 0  to 9 order. Then it returns 
	 * the sorted array.
	 */
	public ArrayList<Project0> radixSort()
	{
		//2-D array to hold all of the digits
		Project0[][] buckets = new Project0[10][list.size() + 1];
		//nested loops to access each element of the buckets 2-D array
		for (int i = 0; i < 5; i++)
		{
			int[] index = new int[10]; // Array of ints that hold how many occurrences of said digit
			for (int j = 0; j < list.size(); j++)
			{
				// This condition gets the digit from the jth place
				switch ((list.get(j)).getZipCodeDigit(i))
				{
				case 0: //if the digit is 0
					buckets[0][index[0]] = list.get(j); // assigns digit to spot in 2-D array
					index[0]++; //increments the occurrence
					break;
				case 1: //if the digit is 1
					buckets[1][index[1]] = list.get(j); // assigns digit to spot in 2-D array
					index[1]++; //increments the occurrence
					break;
				case 2: //if the digit is 2
					buckets[2][index[2]] = list.get(j); // assigns digit to spot in 2-D array
					index[2]++; //increments the occurrence
					break;
				case 3: //if the digit is 3
					buckets[3][index[3]] = list.get(j); // assigns digit to spot in 2-D array
					index[3]++; //increments the occurrence
					break;
				case 4: //if the digit is 4
					buckets[4][index[4]] = list.get(j); // assigns digit to spot in 2-D array
					index[4]++; //increments the occurrence
					break;
				case 5: //if the digit is 5
					buckets[5][index[5]] = list.get(j); // assigns digit to spot in 2-D array
					index[5]++; //increments the occurrence
					break;
				case 6: //if the digit is 6
					buckets[6][index[6]] = list.get(j); // assigns digit to spot in 2-D array
					index[6]++; //increments the occurrence
					break;
				case 7: //if the digit is 7
					buckets[7][index[7]] = list.get(j); // assigns digit to spot in 2-D array
					index[7]++; //increments the occurrence
					break;
				case 8: //if the digit is 8
					buckets[8][index[8]] = list.get(j); // assigns digit to spot in 2-D array
					index[8]++; //increments the occurrence
					break;
				case 9: //if the digit is 9
					buckets[9][index[9]] = list.get(j); // assigns digit to spot in 2-D array
					index[9]++; //increments the occurrence
					break;
				}
			}

			//keeps track of where to put the element in the array
			int place = 0; 

			for (int k = 0; k < index[0]; k++)// puts bucket 0 back into the list
			{
				list.set(place, buckets[0][k]);
				place++;
			}
			for (int k = 0; k < index[1]; k++)// puts bucket 1 back into the list
			{
				list.set(place, buckets[1][k]);
				place++;
			}
			for (int k = 0; k < index[2]; k++)// puts bucket 2 back into the list
			{
				list.set(place, buckets[2][k]);
				place++;
			}
			for (int k = 0; k < index[3]; k++)// puts bucket 3 back into the list
			{
				list.set(place, buckets[3][k]);
				place++;
			}
			for (int k = 0; k < index[4]; k++)// puts bucket 4 back into the list
			{
				list.set(place, buckets[4][k]);
				place++;
			}
			for (int k = 0; k < index[5]; k++)// puts bucket 5 back into the list
			{
				list.set(place, buckets[5][k]);
				place++;
			}
			for (int k = 0; k < index[6]; k++)// puts bucket 6 back into the list
			{
				list.set(place, buckets[6][k]);
				place++;
			}
			for (int k = 0; k < index[7]; k++)// puts bucket 7 back into the list
			{
				list.set(place, buckets[7][k]);
				place++;
			}
			for (int k = 0; k < index[8]; k++)// puts bucket 8 back into the list
			{
				list.set(place, buckets[8][k]);
				place++;
			}
			for (int k = 0; k < index[9]; k++)// puts bucket 9 back into the list
			{
				list.set(place, buckets[9][k]);
				place++;
			}

		}
		//returns the list
		return list;
	}

}
