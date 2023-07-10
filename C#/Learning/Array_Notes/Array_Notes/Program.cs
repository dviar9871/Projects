using System;

namespace Array_Notes
{
    internal class Program
    {


        public int[] arr(params int[] arr) // passing arr as parameter params allows as many parameters as needed to be passed
        {
            return arr;
        }



        public static int min_overload(params int[] list) // finds min of a whole group of nums without passing them as an array
        {

            int min = int.MaxValue;
         
            for (int i = 0; i < list.Length; i++)
            {
                if (list[i] < min)
                    min = list[i];
            }
            return min;
        }
        static void Main(string[] args)
        {

            Console.WriteLine(min_overload(1, 2, 3, 4, 5, 6, 7, 8) + "\n");

            //Declaring array datatype [] arrayName;
            int[] list = { 1, 2, 3, 4, 5, 6, 7 };
            int length = list.Length; // total ammount of entries
            int[] list2 = new int[10];

            foreach (int runner in list) // for each loop almost pythonic tbh
            {
                Console.WriteLine(runner);
            }
            Console.WriteLine();
            // 2d arrays - comma dictates how many dimensions

            int[,] matrix = new int[,]{
                { 1, 2, 3 },
                { 4, 5, 6 }
            };

            for (int i = 0; i < matrix.GetLength(0); i++) // param in getLength is based on dimension of that list (only returns that dimension) 
            {
                for (int j = 0; j < matrix.GetLength(1); j++)
                {
                    Console.Write(matrix[i, j]);
                }
                Console.WriteLine();
            }

            // 3d arrays
            string[,,] vs = new string[3, 3, 3]; // 3 by 3 by 3



            // Jagged arrays (storing arrays within array)
            int[][] jagged = new int[3][];
            int[] jagged1 = new int[5];
            int[] jagged2 = new int[2];
            int[] jagged3 = new int[1];
            jagged[0] = new int[] { 1, 2, 3, 4, 5 };
            jagged[1] = new int[] { 3, 2 };
            jagged[2] = new int[] { 8 };
            for (int i = 0; i < jagged.Length; i++) // param in getLength is based on dimension of that list (only returns that dimension) 
            {
                for (int j = 0; j < jagged[i].Length; j++)
                {
                    Console.WriteLine(jagged[i][j]);

                }
                Console.WriteLine();

                
            }
        }
    }
}
