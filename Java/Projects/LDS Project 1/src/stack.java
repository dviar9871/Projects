/*Decimal and Binary Pallindrome checker
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * The stack class that was written for the project
 */

import java.util.ArrayList;

public class stack implements StackInterface
{
	//The arrayList is being used because of its ability to expand and the simplicity of .add()
	private ArrayList<Integer> stackList = new ArrayList<Integer>();
	//default constructor
	public stack()
	{

	}
	//checks if stack is empty
	public boolean isEmpty()
	{
		return getStackList().isEmpty();
	}

	//Clears the stack of all values
	public void makeEmpty()
	{
		getStackList().clear();
	}

	@Override
	//removes the top element of the stack
	public int pop()
	{
		return getStackList().remove(0);

	}

	@Override
	//Pushes element onto the stack
	public void push(int value)
	{
		getStackList().add(0, value);

	}

	@Override
	//removes an element and shows what was removed
	public int topAndPop()
	{
		int top = getStackList().remove(0);
		return top;
	}

	@Override
	//Shows the value at the top of the stack
	public int top()
	{

		return getStackList().get(0);
	}

	@Override
	//Getter method for stackList
	public ArrayList<Integer> getStackList()
	{

		return stackList;
	}

	@Override
	//returns size of the stack
	public int size()
	{

		return getStackList().size();
	}

	@Override
	//A toString method for the class
	public String toString()
	{
		String stackReturn = "This is the Stack: ";
		for (int i = 0; i < getStackList().size(); i++)
		{
			stackReturn += getStackList().get(i) + " ";
		}
		return stackReturn;
	}

}
