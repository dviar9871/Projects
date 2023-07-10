/**
 * Login.cs
 * Contains the class Login which handles login for various accounts and calls the respective user class
*/
using System;

namespace Air3550;

public class Login
{
    public void LoginSequence()
    {
        Database db = new Database();

        Console.Clear();
        Console.WriteLine("--------------");
        Console.WriteLine("  Login Page  ");
        Console.WriteLine("--------------");

        Console.Write("Username: ");
        string? username = Console.ReadLine();

        //check if username exists
        if ((username == null) || !db.checkUserExists(username))
        {
            Console.WriteLine("Username not found.");
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
            return;
        }

        //password
        Console.Write("Password: ");
        string password = "";
        ConsoleKeyInfo key;

        //hide password while typing
        do
        {
            key = Console.ReadKey(true);

            if (key.Key != ConsoleKey.Backspace && key.Key != ConsoleKey.Enter)
            {
                password += key.KeyChar;
                Console.Write("*");
            }
            else if (key.Key == ConsoleKey.Backspace && password.Length > 0)
            {
                password = password.Substring(0, password.Length - 1);
                Console.Write("\b \b");
            }
        } while (key.Key != ConsoleKey.Enter);

        Console.WriteLine();

        //check if password matches
        if (!db.checkPasswordMatches(username, password))
        {
            Console.WriteLine("Incorrect password.");
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
            return;
        }


        Console.Clear();
        Console.WriteLine("Login successful!");


        string? userID = db.getUserID(username);
        if (userID == null)
        {
            Console.WriteLine("Error accessing database.");
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
            return;
        }

        Customer? accounts = db.getCustomerInfo(userID);
        if (accounts == null)
        {
            Console.WriteLine("Error accessing database.");
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
            return;
        }

        //changing homepage based on account type
        switch (accounts.userType)
        {
            case "Load Engineer":
                LoadEngineer loadEngineer = new LoadEngineer();
                loadEngineer.LoadEngineerStartPage();
                break;
            case "Market Manager":
                MarketManager marketManager = new MarketManager();
                marketManager.MarketManagerPage();
                break;
            case "Customer":
                Customer customer = accounts;
                customer.CustomerStartPage();
                break;
            case "Accountant":
                Accountant accountant = new Accountant();
                accountant.AccountantPage();
                break;
            case "Flight Manager":
                FlightManager flightManager = new FlightManager();
                flightManager.FlightManagerPage();
                break;
            default:
                Console.WriteLine("Invalid user type.");
                break;
        }
    }
}
