/**
 * CreateAccount.cs
 * Contains the class CreateAccount which manages the creation of a customer account
*/
namespace Air3550;
using System;
using System.Collections.Generic;

using System.Linq;
public class CreateAccount
{

    public static object? DatabaseAPI { get; private set; }

    public void CreateAccountSequence()

    //Handle customer account sequence menu
    {
        Database db = new Database();

        Console.Clear();
        Console.WriteLine("----------------------------------------");
        Console.WriteLine("            Account Creation          \n");
        Console.WriteLine(" Please enter the following information ");
        Console.WriteLine("----------------------------------------");

        string userType = "Customer";
        string? firstName, lastName, emailAddress, address, username, input, userID, billingInfo, phoneNum;
        int age;

        //Basic Information
        //Each variable necessary to create a new customer in the system is read in 
        //and checked to confirm that there is a value to read
        //it loops until a valid entry is provided
        while (true)
        {
            Console.Write("First Name: ");
            firstName = Console.ReadLine();
            if (firstName == null || firstName == "")
            {
                Console.WriteLine("You must enter your first name. Try again.");
            }
            else
            {
                break;
            }
        }
        while (true)
        {
            Console.Write("Last Name: ");
            lastName = Console.ReadLine();
            if (lastName == null || lastName == "")
            {
                Console.WriteLine("You must enter your last name. Try again.");
            }
            else
            {
                break;
            }
        }
        while (true)
        {
            Console.Write("Email Address: ");
            emailAddress = Console.ReadLine();
            if (emailAddress == null || emailAddress == "")
            {
                Console.WriteLine("You must enter your email address. Try again.");
            }
            else
            {
                break;
            }
        }

        //Username
        username = "";
        while (true)
        {
            Console.Write("Username: ");
            username = Console.ReadLine();

            if ((username == "") || (username == null))
            {
                Console.WriteLine("Must input a username. Try again.");
            }
            else if (db.checkUserExists(username))
            {
                Console.WriteLine("Username already exists. Try again.");
            }
            else
            {
                //username is unique, break out of the loop
                break;
            }
        }
        //Password
        string password = "";
        while (true)
        {
            Console.Write("Password: ");
            ConsoleKeyInfo key;

            //hide password from user while typing
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

            if (password.Length > 0)
            {
                break;
            }
            else
            {
                Console.WriteLine("You must enter a password. Try again.");
            }
        }

        Console.WriteLine("");
        //address information
        while (true)
        {
            Console.Write("Address: ");
            address = Console.ReadLine();
            if (address == null || address == "")
            {
                Console.WriteLine("You must enter an address. Try again.");
            }
            else
            {
                break;
            }
        }
        //phone number information 
        while (true)
        {
            Console.Write("Phone Number (10 digits no dashes): ");
            input = Console.ReadLine();
            input = input == null ? "" : input;
            try
            {
                phoneNum = input;
                if (input.Length != 10)
                {
                    int testPhoneNum = int.Parse(input);
                    break;
                }
                else
                {
                    Console.WriteLine("Invalid input. Try again.");
                }
            }
            catch (Exception e)
            {
                switch (e)
                {
                    case FormatException:
                        Console.WriteLine("Invalid input. Try again.");
                        break;
                    default:
                        Console.WriteLine("Invalid input. Try again.");
                        break;
                }
            }
        }

        //age information 
        while (true)
        {
            Console.Write("Age: ");
            input = Console.ReadLine();
            input = input == null ? "" : input;
            try
            {
                age = Convert.ToInt32(input);
                break;
            }
            catch (Exception e)
            {
                switch (e)
                {
                    case FormatException:
                        Console.WriteLine("Invalid input. Try again.");
                        break;
                    default:
                        Console.WriteLine("Invalid input. Try again.");
                        break;
                }
            }
        }

        //randomly generate user ID
        userID = GenerateRandomString(6);

        while (true)
        {
            Console.Write("Credit Card Number (16 digits): ");
            billingInfo = Console.ReadLine();
            if (billingInfo == null || billingInfo == "" || billingInfo.Length != 16)
            {
                Console.WriteLine("You must enter your credit card number. Try again.");
            }
            else
            {
                try
                {
                    UInt64.Parse(billingInfo);
                    break;
                }
                catch (Exception e)
                {
                    Console.WriteLine("You must enter your credit card number. Try again.");
                }
            }
        }
        //add customer with entered information to db
        Customer newCustomer = new Customer(userID, userType, password, username, address, billingInfo, age, 0, new List<int>(), firstName, lastName, emailAddress, phoneNum, new List<string>());
        db.createAccountInfo(newCustomer);

        Console.WriteLine("Account created successfully.");

        Console.Write("Press any key to continue");
        Console.ReadKey(false);

    }

    //generate random string method
    private static readonly Random random = new Random();
    private static readonly object syncLock = new object();

    //Generates new user ID for the customer
    public static string GenerateRandomString(int length)
    {
        const string chars = "123456789";
        lock (syncLock)
        {
            return new string(Enumerable.Repeat(chars, length)
              .Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }


}

