using System;

public class Car
{
	public int num_wheels = 4;
	private int x;
	private int var;
	public Car(int x)
	{
		this.x = x;
	}
	~Car() // destructor
    {
		Console.WriteLine("Destroying Car Object");
    }
	public int get_num_wheels()
	{
		return num_wheels;
	}
	public int X { get; set; } // new way to write setter and getter for variable
	public int Var // new way to write setter and getter to be more efficient - upper case name
    {
        get
        {
			return this.var; // original case for getter and setter
        }
        set
        {
            this.var = value;
        }

    }

}
