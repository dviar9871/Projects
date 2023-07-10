/*Keeping Track Of Videos
 *EECS 2500
 *Dr.Heuring
 *Author: Daniel Viar 
 * 
 * This project works as a movie database utilizing a hash table. Movies can be added, removed, listed, 
 * and so on. This class is the Linked List that holds the movies. 
*/
package TrackVideo;

import java.util.ArrayList;
import java.util.Queue;

// A simple Java program for traversal of a linked list 
public class LinkedList
{
	//Instance variables for head and tail of list
	private Node head;
	private Node tail;
	//Constructor
	public LinkedList()
	{
		head = null;
		tail = null;

	}
	//Node class that holds data and pointer
	static class Node
	{
		//instance variables
		Movie data;
		Node next;
		//constructor
		Node(Movie data)
		{
			this.data = data;
			next = null;

		}
		//Setter and Getter methods for data and pointer
		public Movie getData()
		{
			return data;
		}

		public void setData(Movie data)
		{

			this.data = data;
		}

		public Node getNext()
		{
			return next;
		}

		public void setNext(Node next)
		{
			this.next = next;
		}

	}

	public String toString()
	{

		String sum = "[";
		if (head == null)
			return null;
		Node newNode = head;
		while (newNode != null)
		{
			if (newNode.getData() != null)
				sum += newNode.getData() + ", ";
			newNode = newNode.next;
		}
		sum = sum.substring(0, sum.length() - 2);
		return sum + "]";

	}
	//Adds a node to the tail linked list
	public void add(Node currNode)
	{	
		//adds if there is nothing in the list
		if (head == null)
		{

			head = currNode;

		} else
		{
			//runs through list until it finds the tail
			Node newNode = head;
			while (newNode.next != null)
			{

				newNode = newNode.next;
			}
			//adds to the end by changing pointer
			newNode.next = currNode;
			tail = currNode;
		}
	}
	//Finds if list is empty
	public boolean isEmpty()
	{

		if (head == null)
			return false;

		return true;
	}
	//Removes a specific node from the list
	public void remove(Node nodeToRemove)
	{
		Node newNode = head;
		Node holdNode = null;
		//if list is empty removes nothing
		if (head == null)
			return;
		else
		{
			//removes the only item
			if (head == tail)
				head = null;
			//removes the tail when there is only 2 nodes left
			if (nodeToRemove == tail)
			{
				remove();
				return;
				//removes the head when there is only 2 nodes left
			} else if (nodeToRemove == newNode)
			{

				head = newNode.next;
				return;
			}
			//General case for removal
			//runs until it finds the node to remove
			while (newNode.getData() != nodeToRemove.getData())
			{	
				//gets the node before the one to remove
				if (newNode.next.getData() == nodeToRemove.getData())
				{
					holdNode = newNode;
					break;
				}

				newNode = newNode.next;
			}
			//gets node after the one wanted to remove
			Node patchNode = newNode.next.next;
			//sets the pointer of the one to remove to null and patches the two together
			newNode = newNode.next;
			newNode.setNext(null);
			holdNode.setNext(patchNode);

		}
	}
	//Returns an arrayList of nodes
	public ArrayList<Node> listOfNodes()
	{
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		Node newNode = head;
		
		if (head == null)
			return null;
		//adds to list while there are nodes left
		while (newNode != null)
		{
			listOfNodes.add(newNode);
			newNode = newNode.next;
		}

		return listOfNodes;
	}
	//Removes the last item of the list
	public void remove()
	{
		Node newNode = head;
		//removes nothing if nothing is in the list
		if (head == null)
			return;
		else
		{
			//removes node if there is only one
			if (head == tail)
				head = null;
			//runs through whole list until the tail
			while (newNode != tail)
			{
				if (newNode.next == tail)
					break;
				newNode = newNode.next;
			}
			//removes last item
			newNode.setNext(null);
			tail = newNode;
		}

	}

	public void addToFront(Node currNode)
	{

		if (head == null)
		{
			head = currNode;
			return;
		} else
		{
			currNode.next = head;
			head = currNode;
		}

	}

}
