/*Knights path
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * This project takes in starting and ending coordinates between 0 and 7 and calculates
 * the shortest path between them that a knight could move. This class contains the definition for the 
 * position object
 */
package KnightPath;

public class Position
{
	//Initializes row and column
	protected int row;
	protected int column;
	//Default constructor
	public Position()
	{

	}
	//Constructor taking in row and column
	public Position(int row, int column)
	{
		this.row = row;
		this.column = column;

	}
	//To String method for position
	public String toString()
	{
		return "[" + row + "," + column + "]";
	}
	//Gets row
	public int getRow()
	{
		return row;
	}
	//sets row
	public void setRow(int x)
	{

		row = x;
	}
	//gets column
	public int getColumn()
	{
		return column;
	}
	//sets column
	public void setColumn(int column)
	{
		this.column = column;
	}

}
