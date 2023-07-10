/*Keeping Track Of Videos
 *EECS 2500
 *Dr.Heuring
 *Author: Daniel Viar 
 * 
 * This project works as a movie database utilizing a hash table. Movies can be added, removed, listed, 
 * and so on. This class holds the main and calls all of the methods that makes the project run. It also contains
 * two methods to fill the HashTable and a separate list of movies
 */

package TrackVideo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class project3
{

	public static void main(String[] args)
	{

		// Declare object proj3 object and scanner
		project3 proj3 = new project3();

		Scanner userInput = new Scanner(System.in);
		String command = "";

		// Declare lists and instantiate lists
		ArrayList<Movie> listOfMovies = proj3.listOfMovies();
		HashTable<Movie> tableOfMovies = proj3.movieScanner();
		
		// declare counter variables
		int getCommand = 0;
		int findCommand = 0;

		// This loop runs while the user does not type Q
		do
		{
			// Gets command for switch case
			System.out.println("\n" + "Please enter command");

			command = userInput.next().toUpperCase();
			// Determines which action is done based on command result
			switch (command)
			{
			case "A": // Adds a movie to the HashTable
				// Gathering Object info
				System.out.println("Put in the title of the movie");
				userInput.nextLine();
				String title = userInput.nextLine();

				System.out.println("Put in the year the movie released");
				int released = userInput.nextInt();

				System.out.println("Put in the runtime in minutes");
				int runTime = userInput.nextInt();
				// Add to lists
				Movie addedMovie = new Movie(title, released, runTime);
				tableOfMovies.put(addedMovie.getHashKey(), addedMovie);
				listOfMovies.add(addedMovie);
				System.out.println(title + " was added");

				break;
			case "G": // Removes movie from HashTable when user supplies a title
				System.out.println("What movie would you like to get?");
				userInput.nextLine();

				String getMovie = userInput.nextLine();

				tableOfMovies.remove(getMovie);

				getCommand++;
				break;
			case "L":// Lists all movies available
				System.out.println("Here are all of the Movies available: ");
				System.out.println(tableOfMovies.toString());
				break;
			case "F":// Finds movie in hashTable
				System.out.println("What movie do you want to search for?");
				userInput.nextLine();
				String foundMovie = userInput.nextLine();

				if (tableOfMovies.find(foundMovie) != null)
					System.out.println(tableOfMovies.find(foundMovie));

				findCommand++;
				break;
			case "O": // Returns occupancy of HashTable

				double occ = (double) listOfMovies.size() / (double) tableOfMovies.size();
				System.out.println("The Occupancy is: " + occ);
				break;
			case "Q": // Quits loop and outputs data from the methods called

				System.out.println("Get Commands: " + getCommand);
				System.out.println("Find Commands: " + findCommand);
				System.out.println("Total Movies Visited: " + tableOfMovies.movieVist);
			default: // In the case an incorrect letter is selected
				System.out.println("Incorrect Selection. Try again");
				break;
			}

		} while (!(command.equals("Q")));
		userInput.close();

	}

	// Reads in the movies into the hashTable and returns it
	protected HashTable<Movie> movieScanner()
	{
		// Declare Scanner and assign value to ArrayList
		Scanner input = new Scanner(System.in);
		HashTable<Movie> tableOfMovies;
		ArrayList<Movie> listOfMovies = listOfMovies();
		// Gets size of table
		System.out.println("Please declare size of the HashTable. It must be equal to or greater than one");
		int sizeOfTable = input.nextInt();
		// Makes sure user input is a valid size
		while (sizeOfTable < 1)
		{
			System.out.println("Please declare size of the HashTable. It must be equal to or greater than one");
			sizeOfTable = input.nextInt();
		}
		// Gives size to table
		tableOfMovies = new HashTable<Movie>(sizeOfTable);
		// Puts movies into the table from ArrayList
		for (int i = 0; i < listOfMovies.size(); i++)
		{

			tableOfMovies.put(listOfMovies.get(i).getHashKey(), listOfMovies.get(i));
		}
		
		return tableOfMovies;
	}

	// Returns an ArrayList of Movies and reads in movies from the file
	public ArrayList<Movie> listOfMovies()
	{
		// Declare the list and the file being looked through
		ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
		File movieList = new File("Movies.txt");

		try
		{
			Movie movie;
			Scanner input = new Scanner(movieList);
			while (input.hasNext()) // Runs while file still has lines left in the file
			{
				// Adds the object to the list
				String title = input.nextLine();
				int year = input.nextInt();
				input.nextLine();
				int runTime = input.nextInt();
				input.nextLine();
				listOfMovies.add(new Movie(title, year, runTime));

			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return listOfMovies;
	}
}
