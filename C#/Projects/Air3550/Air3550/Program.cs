/**
 * Program.cs
 * Displays the home page of the Air 3550 program and provides the user 
 * with the option to create an account, log in, or exit the program
*/
using System;
using System.Collections.Generic;

namespace Air3550;

class Program
{
    public static object? DatabaseAPI { get; private set; }

    static void Main(String[] args)
    {
        Database db = new Database();

        //update the database
        db.update();

        //initialize menuStack to allow backtracking while on homepage
        Stack<string> menuStack = new Stack<string>();
        string? choice;

        while (true)
        {
            Console.Clear(); // Clear console window
            Console.WriteLine("-----------------------------------");
            Console.WriteLine(" Welcome to the Air3550 Flight App ");
            Console.WriteLine("-----------------------------------");
            Console.WriteLine("1- Log In");
            Console.WriteLine("2- Create an Account");
            Console.WriteLine("0- Exit");
            choice = Console.ReadLine();

            switch (choice)
            {
                case "1": //Login
                    Login login = new Login();
                    login.LoginSequence();
                    break;
                case "2": //Create account 
                    CreateAccount createAccount = new CreateAccount();
                    createAccount.CreateAccountSequence();
                    menuStack.Push("create account");
                    break;

                case "0": //Exit program
                    Console.WriteLine("Goodbye!");
                    return;

                default: //Invalid choice
                    Console.WriteLine("Invalid choice.");
                    Console.Write("Press any key to continue");
                    Console.ReadKey(false);
                    break;
            }
        }
    }
}
