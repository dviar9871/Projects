using System;

namespace CS_Logic
{
    internal class Program
    {
        static void Main(string[] args)
        {


            if (false)
            {

            }else if (false)
            {

            }
            else
            {

            }




            // Try-Parse method - convert strings into numeric data types like ints

            string number_word = "123";
            int parsed_word;

            bool parsed_word_check = int.TryParse(number_word, out parsed_word);  // out -> output being parsed word  

            if (parsed_word_check)
                Console.WriteLine(number_word);
            int switch_case = 0;
            switch (switch_case)
            {

                case 0: Console.WriteLine(0); break;
                default: Console.WriteLine("defaulted"); break;

            }

        }
    }
}
