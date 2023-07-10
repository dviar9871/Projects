/*Knights path
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * This project takes in starting and ending coordinates between 0 and 7 and calculates
 * the shortest path between them that a knight could move. This is the queue made of linked list
 */


package KnightPath;

import java.util.LinkedList;

public class Queue<T>
{
	//Declare LinkedList for queue
	LinkedList<T> list = new LinkedList<T>();
	//default constructor
	public Queue()
	{

	}
	//adds element to queue
	public void add(T x)
	{
		list.add(x);

	}
	//returns size of queue
	public int size()
	{
		return list.size();
	}

	// Retrieves, but does not remove, the head of this queue.
	public T element()
	{

		return list.peekFirst();
	}
	//removes element from the queue
	public void remove()
	{

		list.removeFirst();
	}

	// Retrieves and removes the head of this queue, or returns null if this queue
	// is empty.
	public T poll()
	{
		T head = list.peekFirst();
		list.removeFirst();
		return head;
	}
	//ToString for the queue
	public String toString()
	{
		String output = "[";
		int count = 0;
		Object[] array = new Object[list.size()];
		array = list.toArray(new Object[0]);
		for (Object runner : array)
		{
			output += runner + ",";

		}
		output = output.substring(0, output.length() - 1);
		return output + "]";

	}
}
