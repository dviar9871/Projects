package recursion;

public class recursion
{

	public recursion()
	{

	}

	public static int sum(int n)
	{

		if (n == 1)
			return n;
		else if (n > 1)
			return n + sum(n - 1);
		return n;
	}

	public static void main(String[] args)
	{

		System.out.println(sum(10));
		
		
	}

}
