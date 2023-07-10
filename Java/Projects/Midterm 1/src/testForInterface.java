
public class testForInterface implements TestInterface
{
	public Integer[] list;

	public testForInterface(int size)
	{
		list = new Integer[size];
	}

	public static void main(String[] args) throws StorageOverflowException
	{

		testForInterface test = new testForInterface(10);
		
		
		System.out.println(test.isEmpty());
		System.out.println(test.isFull());
		System.out.println(test.sumOfValues());
		test.removeValue(7);

		for (int i = 0; i < test.list.length; i++)
		{
			System.out.println(test.list[i]);
		}
		test.addValue(100);
	}

	@Override
	public void addValue(int value) throws StorageOverflowException
	{
		// TODO Auto-generated method stub
		for(int i =0; i < list.length; i++) {
			if(value == list[i]) {
				
				return;
			}
		}
		
		if(isFull()) {
			throw new StorageOverflowException();
			
		}
			for(int i = 0; i < list.length; i++) {
				if(list[i] == null) {
					list[i] = value;
				break;	
				}
			}
		
			
		
	}

	@Override
	public void removeValue(int value)
	{

		int instancesOfVal = 0;
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] == value)
			{
				list[i] = null;
				instancesOfVal++;
			}

		}
		Integer[] newList = new Integer[list.length - instancesOfVal];
		int count = 0;
		for (int i = 0; i < list.length; i++)
		{

			if (list[i] != null)
			{

				newList[count] = list[i];
				count++;
			}

		}

		list = newList;

	}

	@Override
	public int sumOfValues()
	{
		int sum = 0;
		for (int i = 0; i < list.length; i++)
		{
			sum += list[i];
		}
		return sum;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < list.length; i++)
		{
			list[i] = null;
		}

	}

	@Override
	public boolean isFull()
	{
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] == null)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty()
	{
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] != null)
			{
				return false;
			}

		}
		return true;
	}
}
