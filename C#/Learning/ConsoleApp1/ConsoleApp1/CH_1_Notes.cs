using System;

namespace ConsoleApp1
{
    internal class CH_1_Notes
    {


        /// <summary>
        /// This creates a XML document that is shown when hovering over method
        /// </summary>
        static void Main(string[] args) // Use camel case instead for c# standards
        {

         
            void print()
            {

                Console.Write("press enter"); // no next line
                Console.WriteLine(); // next line
                Console.Read(); // gets ascii value of next char
                Console.ReadLine();// gets string or int and saves it as declared value

            }
            int x; // default is 0
            sbyte b = 10; // signed byte
            float f = 9.99f; // f needed to call it float instead of double
            double d = 9.99; // double the digits of float
            decimal dec = 9.99M; // used for real world money \
            string s = "9";
            char c = 'a';
            // casting is the typical (datatype) value
            // Strings have .toDatatype() methods for casting

            int casted = Int32.Parse(s); // string to int -> use class definition
            string s2 = "20";
            Console.WriteLine("My num is {0}", s2); // allows for string formatting

            print();
            // String contencation is with +
            // strings are immutable
            // there is toLower(), trim() - removes whitespace, indexOf, substring, isNullOrWhiteSpace

            string s3 = "Mychar is {0}";
            s3 = string.Format(s3, c.ToString());
            Console.WriteLine(s3);
        }
    }
}
