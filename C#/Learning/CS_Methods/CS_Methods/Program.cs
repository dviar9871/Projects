using System;

namespace CS_Methods
{
    internal class Program
    {
        public int add(int x, int y)
        {
            return x + y;
        }
        public void printMe()
        {
            Console.WriteLine("print");
        }
        public static void printMeStatic()
        {
            Console.WriteLine("print Static");
        }
        static void Main(string[] args)
        {
            Program program = new Program(); // Need object to access functions
            program.printMe();

            printMeStatic(); //  static method gets rid of object needed

            try
            {
                Console.WriteLine("Enter a Number to divide by zero: ");
                int x = Int32.Parse(Console.ReadLine()) / 0; // console input needs to be converted from string
            }
            catch (DivideByZeroException ex)
            {
                Console.WriteLine(ex);
                Console.WriteLine("Failed to divide cause it is impossible");
                Console.WriteLine("\n \nThrowing an Error for example");
                throw;
            }
            finally
            {
                Console.WriteLine("\n\nWe are done with errors !!");
            }





        }
    }
}
