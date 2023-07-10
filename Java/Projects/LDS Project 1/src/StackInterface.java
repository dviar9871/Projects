/*Decimal and Binary Pallindrome checker
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * Interface for the stack class
 */



import java.util.ArrayList;

public interface StackInterface
{	//checks if stack is empty
	public boolean isEmpty();
	//clears stack of all values
	public void makeEmpty();
	//removes top element of stack
	public int pop();
	//returns size of stack
	public int size();
	//getter method for stack
	public ArrayList<Integer> getStackList();
	//toString method for stack
	public String toString();
	//adds value to stack
	public void push(int value);
	//view top element of stack
	public int top();
	//removes top element and shows what was removed
	public int topAndPop();
}
