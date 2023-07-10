package KnightsPath;

import java.util.LinkedList;

public class Queue<T>
{
	LinkedList<T> list = new LinkedList<T>();

	public Queue()
	{

	}

	public void add(T x)
	{
		list.add(x);

	}
	
	public int size() {
		return list.size();
	}
	//Retrieves, but does not remove, the head of this queue.
	public T element()
	{

		return list.peekFirst();
	}

	public void remove()
	{

		list.removeFirst();
	}
	//Retrieves and removes the head of this queue, or returns null if this queue is empty.
	public T poll()
	{
		T head = list.peekFirst();
		list.removeFirst();
		return head;
	}

	public String toString() 
	{
		String output = "[";
		int count = 0;
		Object [] array = new Object[list.size()];
		array = list.toArray(new Object[0]);
		for(Object runner: array) {
			output += runner + ",";
			
			
		}
		output = output.substring(0, output.length() - 1);
		return output + "]";
		
	}
}
