using System;
using System.Collections; // imports some collections into our program (ArrList, Hashtable)
using System.Collections.Generic; // imports the rest of the collections(List)
namespace CollectionsNotes
 
{
    internal class Program
    {

        class Student
        {
            public int Id { get; set; }
            public string Name { get; set; }

            public float GPA { get; set;  }

            public Student(int Id, string Name, float GPA)
            {
                this.GPA = GPA;
                this.Id = Id;
                this.Name = Name;   
            }
        }


        // Little list test
        public static List<int> Solution()
        {
            // TODO: write your solution here
            List<int> list = new List<int>();
            for (int i = 100; i <= 170; i += 2)
            {
                list.Add(i);
            }
            return list;
        }
        static void Main(string[] args)
        {

         //******************************************************************************************
         // ArrayList
            ArrayList list = new ArrayList(); // can put any kind of object into ArrayList
            list.Add(1);
            list.Add("2");



            foreach (object obj in list) // Arraylist values are objects so in for each loop they must be called as such
            {
                Console.WriteLine(obj);
                // can check for data type to determine operations
                if(obj is int) // Pythonic <3
                {
                    Console.WriteLine("INTEGER");
                }


            }
            //****************************************************************************************
            //Lists
            var numbers = new List<int>();
            numbers.Add(1);
            numbers.Add(2);
            numbers.Add(3);
            numbers.Remove(1); // removes entry
            numbers.RemoveAt(0); // removes at an index
            int num_from_list = numbers[0]; // gets data entry from list
            int length = numbers.Count; // length of list property
            numbers.Clear();

            var numbers2 = new List<int> { 1,2,3,4,};
            //**************************************************************************************
            //Hashtables

            Hashtable table = new Hashtable();

            Student stud1 = new Student(1, "stud1", 98);
            Student stud2 = new Student(2, "stud2", 70);
            Student stud3 = new Student(3, "stud3", 50);
            Student stud4 = new Student(4, "stud4", 10);

            table.Add(stud1.Id, stud1); // using student Id as key 
            table.Add(stud2.Id, stud2);
            table.Add(stud3.Id, stud3);
            table.Add(stud4.Id, stud4);

            // retrieve individual item with know Id
            Student storedStudent = (Student)table[stud1.Id]; // have to cast to student so it fits in object
            // Get all entries from HashTable
            foreach (DictionaryEntry entry in table)
            {
                Student temp = (Student)entry.Value; // has to be casted so we can access identifiers
                Console.WriteLine("Student Id: {0}, Name: {1}, GPA: {2}", temp.Id, temp.Name, temp.GPA);
            }
            Console.WriteLine();
            foreach (Student value in table.Values) // gets rid of need for casting
            {
                Console.WriteLine("Student Id: {0}, Name: {1}, GPA: {2}", value.Id, value.Name, value.GPA);
            }


            //Console.WriteLine("Student Id: {0}, Name: {1}, GPA: {2}", storedStudent.Id, storedStudent.Name, storedStudent.GPA);

        }

    }
}
