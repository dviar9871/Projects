using System;
namespace Class_Notes
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Car x = new Car(3); 
            Console.WriteLine(x.get_num_wheels());
            x.Var = 10;
            Console.WriteLine(x.Var);
        }
    }
}
