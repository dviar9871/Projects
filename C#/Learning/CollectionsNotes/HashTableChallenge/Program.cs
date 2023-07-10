using System;
using System.Collections;
namespace HashTableChallenge
{
    internal class Program
    {

        class Student
        {
            public int Id { get; set; }
            public string Name { get; set; }

            public float GPA { get; set; }

            public Student(int Id, string Name, float GPA)
            {
                this.GPA = GPA;
                this.Id = Id;
                this.Name = Name;
            }
        }

        static void Main(string[] args)
        {
            Student[] studArray = new Student[10];
            for (int i = 0; i < studArray.Length - 1; i++)
            {
                Student temp = new Student(i, "Student "+ i, 4.0F);
                studArray[i] = temp;
            }
            Student lastStud = new Student(8, "Student " + 8, 4.0F);
            studArray[9] = lastStud;

            Hashtable table = new Hashtable();


            for(int i = 0; i < studArray.Length; i++)
            {
                if (table.ContainsKey(studArray[i].Id))
                {
                    Console.WriteLine("Sorry already have student");
                }
                else
                {
                    table.Add(studArray[i].Id, studArray[i]);
                }

            }
            foreach (Student entry in table.Values)
            {
                Console.WriteLine("Id: {0}, Name: {1}, GPA{2}: ", entry.Id, entry.Name, entry.GPA);
            }


        }
    }
}
