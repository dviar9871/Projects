/*Keeping Track Of Videos
 *EECS 2500
 *Dr.Heuring
 *Author: Daniel Viar 
 * 
 * This project works as a movie database utilizing a hash table. Movies can be added, removed, listed, 
 * and so on. This class is the HashTable that holds the movies. It is chained with a singly linked list.
 */
package TrackVideo;

import java.util.ArrayList;
import java.util.Arrays;

import TrackVideo.LinkedList.Node;

public class HashTable<T>
{	
	//Instance variables
	private int size;
	protected LinkedList[] table;
	protected int movieVist = 0;
	//Constructor
	@SuppressWarnings("unchecked")
	public HashTable(int size)
	{
		//Gives size to list
		this.size = size;
		table = new LinkedList[size];
		//Instantiates each linked list in the array
		for (int i = 0; i < table.length; i++)
		{
			table[i] = new LinkedList();
		}
	}
	//Puts value into hash table
	@SuppressWarnings("unchecked")
	public void put(long key, Movie value)
	{
		//Create node to put in table holding value
		Node node = new Node(value);
		//get slot
		long slot = key % table.length;
		//adding to the list
		table[(int) slot].addToFront(node);


	}
	//This method finds a specific movie object in the Hash Table
	public Movie find(String title)
	{
		//create list of nodes
		ArrayList<Node> nodes;
		//Traverses all spots in the array
		for (int i = 0; i < table.length; i++)
		{
			//Gets a list to manipulate of the nodes in the Linked List
			nodes = table[i].listOfNodes();

			if (nodes != null)
			{
				//Traverses each spot in list of nodes
				for (int j = 0; j < nodes.size(); j++)
				{
					//Checks each node for value and returns it if it finds it
					movieVist++;
					if (nodes.get(j).getData().getTitle().equals(title))
					{

						return nodes.get(j).getData();

					}
				}
			}
		}
		//If the movie it is not found it returns null
		System.out.println("Movie not found");
		return null;
	}
	//This method removes a specific movie object from the Hash Table
	public void remove(String title)
	{
		//create list of nodes
		ArrayList<Node> nodes;
		//Traverses all spots in the array
		for (int i = 0; i < table.length; i++)
		{
			//Gets a list to manipulate of the nodes in the Linked List
			nodes = table[i].listOfNodes();

			if (nodes != null)
			{
				for (int j = 0; j < nodes.size(); j++)
				{
					movieVist++;
					if (nodes.get(j).getData().getTitle().equals(title))
					{
						//Checks each node for value and removes it if it finds it
						table[i].remove(nodes.get(j));
						System.out.println("Got: " + title);
						return;

					}
				}
			}
		}
		System.out.println("Cannot get movie");
	}
	//Returns size
	public int size()
	{
		return size;
	}


	public String toString()
	{
		String sum = "[";

		for (LinkedList runner : table)
		{

			if (runner.isEmpty())
			{

				sum += runner + "\n";
			}
		}
		return sum + "]";

	}

}
