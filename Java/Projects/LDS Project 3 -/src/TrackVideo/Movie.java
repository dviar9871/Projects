/*Keeping Track Of Videos
 *EECS 2500
 *Dr.Heuring
 *Author: Daniel Viar 
 * 
 * This project works as a movie database utilizing a hash table. Movies can be added, removed, listed, 
 * and so on. This class holds the object being stored and manipulated, the Movie object. Its most
 * important function is the getHashKey() method 
 */



package TrackVideo;

import java.io.*;
import java.util.*;

public class Movie
{
	//Instance variables
	private String title;
	private int yearReleased;
	private int runningTime;
	//Constructors
	public Movie()
	{
		this("", 0, 0);
	}

	public Movie(String title, int yearReleased, int runningTime)
	{
		this.title = title.trim();
		this.yearReleased = yearReleased;
		this.runningTime = runningTime;
	}

	public Movie(Movie m)
	{
		title = m.title.trim();
		yearReleased = m.yearReleased;
		runningTime = m.runningTime;
	}
	//Getter methods for instance variables
	public int getYearReleased()
	{
		return yearReleased;
	}

	public int getRunningTime()
	{
		return runningTime;
	}

	public String getTitle()
	{
		return title;
	}
	//This method takes a title and gets the first letter of each title and returns the character value in an array
	public int[] getFirstLetterCodes()
	{
		String[] words;
		int[] result;
		words = title.split(" ");
		result = new int[words.length];
		for (int wordNbr = 0; wordNbr < words.length; wordNbr++)
		{
			if (words.length != 0 && words[wordNbr].length() > 0)
			{
				result[wordNbr] = Character.valueOf(words[wordNbr].charAt(0));
			}
		}
		;
		return result;
	}
	/*This method generates the hash key from the first letter codes gathered from the title
	 *  using the equation (first letter code) * 128 + next letter code .....
	 */
	public long getHashKey()
	{
		long hashKey = 0;
		int[] code = getFirstLetterCodes(); // getting array of the first letter digits
		hashKey = code[0];
		for (int i = 0; i < code.length - 1; i++)
		{
			hashKey = hashKey * 128 + code[i + 1]; //creating the key
		}

		return hashKey;
	}

	public String toString()
	{
		return "\"" + title + "\" Released in : " + yearReleased + " Running Time : " + runningTime + " minutes";
	}

	public static void main(String[] arguments)
	{

		Movie m1, m2, m3, m4;
		m1 = new Movie("Die Hard", 1990, 105);
		m2 = new Movie("D H W Y S", 0, 0);
		m3 = new Movie(m1);
		m4 = new Movie("Brave", 2000, 99);
		int[] codes;
		System.out.println(m1.getHashKey());
		System.out.println(m1.getFirstLetterCodes());
		System.out.println(m1);
		codes = m1.getFirstLetterCodes();
		for (int k : codes)
			System.out.println(k);
		System.out.println(m2);
		codes = m2.getFirstLetterCodes();
		for (int k : codes)
			System.out.println(k);
		System.out.println(m3);
		codes = m3.getFirstLetterCodes();
		for (int k : codes)
			System.out.println(k);
		System.out.println(m4);
		codes = m4.getFirstLetterCodes();
		for (int k : codes)
			System.out.println(k);

		System.exit(0);
	}
}