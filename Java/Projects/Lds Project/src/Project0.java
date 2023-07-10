/*Mail Sorter
 * EECS 2500
 * Professor: Dr.Heuring
 * By: Daniel Viar
 * 
 * 
 * 
 * This project sorts a list of addresses that are obtained from a file and sorts them by
 * zip code using a radix sort and then outputs it to a separate file. This specific class implements
 * the interface, calls the methods that fill the ArrayList, the sort, and outputs the file. 
 * 
 */

//Importing all of the classes I need 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Project0 implements MailAddressInterface // implementing the interface
{
	// Declaring all of my variables that coincide with my info from the text file
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private int zipcode;

	// Default constructor
	public Project0()
	{

	}

	// Constructor that gives values to all of my instance variables
	public Project0(String name, String addressLine1, String addressLine2, String city, String state, int zipcode)
	{

		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}

	// This is the main which calls all of my methods that take in the file, sorts
	// it, and outputs it
	public static void main(String[] args) throws FileNotFoundException
	{
		// Declaring and instantiating objects to use the coded methods
		AddressReader arObject = new AddressReader();
		Project0Helper helper = new Project0Helper();
		Scanner userInput = new Scanner(System.in);

		// allows the user to input a file name, takes in the file, sorts the file
		System.out.println("Enter input file name: ");
		
		arObject.addressFiller(userInput.nextLine()); // takes in addresses from file
		//makes an array for the original list
		Project0 [] beforeList = new Project0[arObject.getList().size()];
		//fills array with sorted array info
		for(int i = 0; i < arObject.getList().size(); i++) {
			beforeList[i] = arObject.getList().get(i);
		}
				
		arObject.radixSort(); // sorts entries

		// array to be used for 
		Project0[] afterList = new Project0[arObject.radixSort().size()];
		//fills the sorted array
		for (int i = 0; i < arObject.radixSort().size(); i++)
		{
			afterList[i] = arObject.radixSort().get(i);
		}
		// calls to project0helper to check if the radix sort works correctly
		helper.checkStartingOrder(beforeList);
		helper.checkFinalOrder(afterList);

		// creates an object to write to the file and gives the user naming control for
		// the output file
		PrintWriter writer = null;
		System.out.println("Enter output file name: ");
		String outputFileName = userInput.nextLine();

		// Instantiating the PrintWriter
		try
		{
			writer = new PrintWriter(outputFileName);

		} catch (FileNotFoundException e) // in case the file is not found
		{

			e.printStackTrace();
		}

		// Printing to the file
		for (int i = 0; i < arObject.radixSort().size(); i++)
		{
			writer.println(arObject.radixSort().get(i));
		}
		// closing the PrintWriter
		writer.close();
		userInput.close();
	}

	// toString() method for project0 objects
	public String toString()
	{

		return "Name: " + name + " \nAddressLine 1: " + addressLine1 + " \nAddressLine 2: " + addressLine2 + " \nCity: "
				+ city + " \nState: " + state + " \nZipCode: " + zipcode + "\n";

	}

	@Override
	// Returns name from selected project0 object
	public String getName()
	{

		return name;
	}

	@Override
	// Returns addressLine1 from selected project0 object
	public String getAddressLine1()
	{

		return addressLine1;
	}

	@Override
	// Returns addressLine2 from selected project0 object
	public String getAddressLine2()
	{

		return addressLine2;
	}

	@Override
	// Returns city from selected project0 object
	public String getCity()
	{

		return city;
	}

	@Override
	// Returns state from selected project0 object
	public String getState()
	{

		return state;

	}

	@Override
	// Returns zipcode from selected project0 object
	public int getZipCode()
	{

		return zipcode;
	}

	@Override
	/*
	 * returns a certain digit of the zipcode depending on the inputed parameter Ex:
	 * digit = 0 it will return the first digit of the zipcode
	 */
	public int getZipCodeDigit(int digit)
	{

		switch (digit)
		{
		case 0: // first digit

			return this.getZipCode() % 10;
		case 1: // second digit
			return this.getZipCode() % 100 / 10;
		case 2: // third digit
			return this.getZipCode() % 1000 / 100;
		case 3: // fourth digit
			return this.getZipCode() % 10000 / 1000;
		case 4: // fifth digit
			return this.getZipCode() % 100000 / 10000;
		case 5: // sixth digit
			return this.getZipCode() % 1000000 / 100000;
		}

		return 0; // Default case
	}

}
