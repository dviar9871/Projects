package QuestionNine;

public class dynamicArray
{

	protected String[] myList;

	protected int numberInList;

	public dynamicArray()
	{

		myList = new String[10];

		numberInList = 0;

	}

	private void resize()
	{
		String[] copyOfArray = new String[myList.length + 10];
		for(int i  = 0; i < myList.length; i++) {
			copyOfArray[i] = myList[i];
		}
		myList = copyOfArray;
	
	}

	public void addItem(String name)
	{

		if (numberInList == myList.length)
		{
			
			resize();

		}

		myList[numberInList] = name;

		numberInList++;
		
	}

	public void clear()
	{

		for (int i = 0; i < numberInList; i++)
		{

			myList[i] = null;

		}

		numberInList = 0;

	}

}