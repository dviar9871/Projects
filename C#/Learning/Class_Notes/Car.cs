using System;

public class Car
{
	public int num_wheels = 4;
	private int x;
	public Car(int x)
	{
		this.x = x;	
	}
	public void set_x(int x)
    {
		this.x = x;
    }
	public int get_num_wheels()
    {
		return num_wheels;	
    }
}
