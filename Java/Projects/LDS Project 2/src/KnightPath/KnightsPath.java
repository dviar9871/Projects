/*Knights path
 * EECS 2500
 * Dr.Heuring
 * 
 * Author: Daniel Viar
 * 
 * This project takes in starting and ending coordinates between 0 and 7 and calculates
 * the shortest path between them that a knight could move. This class contains the methods that actually
 * find the paths and returns the shortest one
 */

package KnightPath;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KnightsPath
{
	// Declares queue and arraylist for path finding
	protected Queue<Position> queue = new Queue<Position>();
	protected ArrayList<LinkedList<Position>> list = new ArrayList<LinkedList<Position>>();

	// Adds all positions to the queue and calls methods to generate paths and find
	// the shortest path
	public KnightsPath(int sRow, int sCol, int fRow, int fCol)
	{
		// creates position variables and adds starting position to queue
		Position placeHolder = new Position();
		Position startPos = new Position(sRow, sCol);
		Position endPos = new Position(fRow, fCol);
		queue.add(startPos);
		// generates paths until the goal is found
		for (int i = 0; i < queue.size(); i++)
		{
			// checks for goal position then breaks
			if (queue.element().getRow() == fRow && queue.element().getColumn() == fCol)
			{
				break;
			}
			// takes in the element about to be removed
			placeHolder.setRow(queue.element().getRow());
			placeHolder.setColumn(queue.element().getColumn());
			// generates paths from that point
			generatePosition(queue.poll());
			// adds the element taken out back in
			queue.add(placeHolder);
		}

	}

	// This method finds the shortest path of all of the possible paths using
	// backtracking
	public String getShortestPath(int sRow, int sCol, int fRow, int fCol)
	{
		// declaring count and boolean variables
		int count = 0;
		boolean valid = false;
		// This arraylist will hold the shortest path
		ArrayList<Position> foundPath = new ArrayList<Position>();
		// Position objects that have the start and end in them
		Position startPos = new Position(sRow, sCol);
		Position endPos = new Position(fRow, fCol);
		// Loop runs through ArrayList of LinkedList
		for (LinkedList<Position> linkedList : list)
		{// Increments each time we go through a new linked list to know which list holds
			// the first goal position
			count++;   
			System.out.println(linkedList.toString());
			for (Position position : linkedList)
			{// Goes through the LinkedList to look at the positions
				// checks for goal position
				if (position.getRow() == fRow && position.getColumn() == fCol)
				{
					// adds goal and the head of the LinkedList to arraylist
					foundPath.add(position);
					foundPath.add(linkedList.getFirst());
					// updates goal position and breaks loop
					fRow = linkedList.getFirst().getRow();
					fCol = linkedList.getFirst().getColumn();
					valid = true;
					break;
				}
			} // breaks loop after LL head is found
			if (valid)
				break;
		}
		// resets boolean and makes counter variable
		valid = false;
		int i = -1;
		// Loop backtracks and adds goal path to list
		while (i <= count)
		{
			i++;
			// runs through each position in the list
			for (Position position : list.get(i))
			{
				// checks for goal position
				if (position.getRow() == fRow && position.getColumn() == fCol)
				{
					// adds position if head of LL is found elsewhere
					if (i == 0)
					{
						valid = true;
						foundPath.add(startPos);

						break;
					}
					// finds head of LL
					foundPath.add(list.get(i).getFirst());
					fRow = list.get(i).getFirst().getRow();
					fCol = list.get(i).getFirst().getColumn();
					i = -1;

				}

			}
			// breaks if position is added
			if (valid)
				break;
		}
		// returns formated list of the path
		String sum = "Found Path \n";
		for (int j = foundPath.size() - 1; j >= 0; j--)
		{
			sum += foundPath.get(j) + "\n";
		}

		return sum;
	}

	// This creates all possible positions from a taken in point
	public void generatePosition(Position x)
	{
		// List all the positions are added too
		LinkedList<Position> linkedList = new LinkedList<Position>();
		// adds starting position
		linkedList.add(x);
		// **Generates all the positions for all the cases and adds them to the queue
		// and the LinkedList
		// case a
		if ((x.getRow() - 2 >= 0 && x.getRow() - 2 < 8) && (x.getColumn() - 1 >= 0 && x.getColumn() - 1 < 8))
		{
			Position positionA = new Position(x.getRow() - 2, x.getColumn() - 1);

			linkedList.add(positionA);

			queue.add(positionA);
		}
		// case b
		if ((x.getRow() - 2 >= 0 && x.getRow() - 2 < 8) && (x.getColumn() + 1 >= 0 && x.getColumn() + 1 < 8))
		{
			Position positionB = new Position(x.getRow() - 2, x.getColumn() + 1);
			linkedList.add(positionB);

			queue.add(positionB);
		}
		// case c
		if ((x.getRow() - 1 >= 0 && x.getRow() - 1 < 8) && (x.getColumn() + 2 >= 0 && x.getColumn() + 2 < 8))
		{
			Position positionC = new Position(x.getRow() - 1, x.getColumn() + 2);
			linkedList.add(positionC);

			queue.add(positionC);
		}
		// case d
		if ((x.getRow() + 1 >= 0 && x.getRow() + 1 < 8) && (x.getColumn() + 2 >= 0 && x.getColumn() + 2 < 8))
		{
			Position positionD = new Position(x.getRow() + 1, x.getColumn() + 2);
			linkedList.add(positionD);

			queue.add(positionD);
		}
		// case e
		if ((x.getRow() + 2 >= 0 && x.getRow() + 2 < 8) && (x.getColumn() + 1 >= 0 && x.getColumn() + 1 < 8))
		{
			Position positionE = new Position(x.getRow() + 2, x.getColumn() + 1);
			linkedList.add(positionE);

			queue.add(positionE);
		}
		// case f
		if ((x.getRow() - 1 >= 0 && x.getRow() - 1 < 8) && (x.getColumn() + 2 >= 0 && x.getColumn() + 2 < 8))
		{
			Position positionF = new Position(x.getRow() - 1, x.getColumn() + 2);
			linkedList.add(positionF);

			queue.add(positionF);
		}
		// case g
		if ((x.getRow() + 1 >= 0 && x.getRow() + 1 < 8) && (x.getColumn() - 2 >= 0 && x.getColumn() - 2 < 8))
		{
			Position positionG = new Position(x.getRow() + 1, x.getColumn() - 2);
			linkedList.add(positionG);

			queue.add(positionG);
		}
		// case h
		if ((x.getRow() - 1 >= 0 && x.getRow() - 1 < 8) && (x.getColumn() - 2 >= 0 && x.getColumn() - 2 < 8))
		{
			Position positionH = new Position(x.getRow() - 1, x.getColumn() - 2);
			linkedList.add(positionH);

			queue.add(positionH);
		}
		// adds LinkedList to arrayList
		list.add(linkedList);
	}

}
