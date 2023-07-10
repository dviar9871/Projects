/**
 * DatabaseAPI.cs
 * Contains the class Database which is responsible for accessing and writing to the excel database
*/
using System;
using ClosedXML.Excel;
using System.Security.Cryptography;
using System.Text;

namespace Air3550;

class Database
{

    static byte[] byteArray = new byte[512];
    static byte[] outputArray = new byte[512];

    public XLWorkbook data;

    public Database()
    {
        try
        {
            //load database reference from file
            data = new XLWorkbook("Software Database.xlsx");
        }
        catch (Exception e)
        {
            //if file could not be opened, display an error message then exit the program
            Console.WriteLine("Error: Could not connect to the database.");
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
            Console.WriteLine("Ending program...");
            Environment.Exit(1);
        }
    }

    //checks the time fields on the database to see if a flight should be marked as completed and allocates points to all customers on board
    public void update()
    {
        Flight? curFlight;
        Customer? curCustomer;
        var flightTable = data.Worksheet("Flight List").Table(0);

        //loop through each flight
        for (int i = 2; i < flightTable.RowCount(); i++)
        {
            //check if flight is completed
            if (!flightTable.Cell(i, 5).GetValue<bool>())
            {
                //check if the flight date has passed
                if (flightTable.Cell(i, 4).GetValue<DateTime>() < DateTime.Now)
                {
                    //get the current flight as it is not marked completed and has passed
                    curFlight = getFlightInfo(flightTable.Cell(i, 1).GetValue<int>());
                    if (curFlight != null)
                    {
                        //loop through each passenger listed
                        foreach (string customer in curFlight.passengerList)
                        {
                            //get the current customer information
                            curCustomer = getCustomerInfo(customer);
                            //loop through account history list
                            for (int j = 0; (curCustomer != null) && (j < curCustomer.accountHistory.Count); j++)
                            {
                                //remove flight from customer's flight list
                                curCustomer.flightList.Remove(curFlight.FlightNumber);

                                //check if the flight is present at that location in the list
                                if (curCustomer.accountHistory[j].Contains(curFlight.FlightNumber.ToString()))
                                {
                                    string[] splitHistory = curCustomer.accountHistory[j].Split(',');
                                    //check if the flight was paid using points
                                    if (splitHistory[2].Equals("False"))
                                    {
                                        //it wasn't paid using points increment the points accordingly
                                        curCustomer.points += ((int)double.Parse(splitHistory[1]) * 10);
                                        //write updated information to the database
                                        writeCustomerInfo(curCustomer);
                                    }
                                }
                            }

                        }

                        //update flight info and write to database
                        curFlight.completed = true;
                        writeFlightInfo(curFlight);
                    }
                }
            }
        }
    }

    //retrieves customer info from the database that matches the given ID
    public Customer? getCustomerInfo(string customerID)
    {

        //load account table refrence into program
        var accountsTable = data.Worksheet("Accounts").Table(0);

        //search rows for given customerID
        for (int row = 2; row <= accountsTable.RowCount(); row++)
        {
            if (accountsTable.Cell(row, 1).GetValue<string>().Equals(customerID))
            {
                //if the customer ID has been found, parse its data and return a new Customer object

                //get flight list from formatted string in database
                string flightString = accountsTable.Cell(row, 9).GetValue<string>();
                List<string> flightListString = flightString.Split(',').ToList();

                //convert list of strings to list of int after checking that the list of strings is not empty
                List<int> flightList = new List<int>();
                if (flightListString.ElementAt(0) != "")
                    flightList = flightListString.Select(int.Parse).ToList();

                //get customer history from formatted string
                string historyString = accountsTable.Cell(row, 14).GetValue<string>();
                List<string> accountHistory = new List<string>();
                if (!(historyString.Equals("")))
                    accountHistory = historyString.Split('(', ')').ToList();
                accountHistory.RemoveAll(s => s == ""); //clear all empty strings from list

                //create new customer object from gathered data
                return new Customer(
                    accountsTable.Cell(row, 1).GetValue<string>(),
                    accountsTable.Cell(row, 2).GetValue<string>(),
                    accountsTable.Cell(row, 3).GetValue<string>(),
                    accountsTable.Cell(row, 4).GetValue<string>(),
                    accountsTable.Cell(row, 5).GetValue<string>(),
                    accountsTable.Cell(row, 6).GetValue<string>(),
                    accountsTable.Cell(row, 7).GetValue<int>(),
                    accountsTable.Cell(row, 8).GetValue<double>(),
                    flightList,
                    accountsTable.Cell(row, 10).GetValue<string>(),
                    accountsTable.Cell(row, 11).GetValue<string>(),
                    accountsTable.Cell(row, 12).GetValue<string>(),
                    accountsTable.Cell(row, 13).GetValue<string>(),
                    accountHistory
                );
            }
        }

        //if customer ID could not be found, return null
        return null;
    }

    //get the full list of flights from the database
    public Flight[] getFullFlightList()
    {
        //load flight list from database
        List<Flight> flightList = new List<Flight>();
        var flightTable = data.Worksheet("Flight List").Table(0);

        //loop through all flights in database
        for (int i = 2; i <= flightTable.RowCount(); i++)
        {
            //get flight info and add to list
            Flight? curFlight = getFlightInfo(flightTable.Cell(i, 1).GetValue<int>());
            if (curFlight != null)
            {
                flightList.Add(curFlight);
            }
        }

        //return list with all flights
        return flightList.ToArray();
    }

    //get the list of all flights that have not been marked as completed
    public Flight[] getUpcomingFlightList()
    {
        //load flight list from database
        List<Flight> flightList = new List<Flight>();
        var flightTable = data.Worksheet("Flight List").Table(0);

        //loop through all flights
        for (int i = 2; i <= flightTable.RowCount(); i++)
        {
            //get flight info, if flight is not completed then add to list
            Flight? curFlight = getFlightInfo(flightTable.Cell(i, 1).GetValue<int>());
            if ((curFlight != null) && !curFlight.completed)
            {
                flightList.Add(curFlight);
            }
        }

        //return list with all upcoming flights
        return flightList.ToArray();
    }

    //get the list of all flights that have been marked as completed
    public Flight[] getPastFlightList()
    {
        //load flight list from database
        List<Flight> flightList = new List<Flight>();
        var flightTable = data.Worksheet("Flight List").Table(0);

        //loop through all flights
        for (int i = 2; i <= flightTable.RowCount(); i++)
        {
            //get flight info, if flight is completed then add to list
            Flight? curFlight = getFlightInfo(flightTable.Cell(i, 1).GetValue<int>());
            if (curFlight != null && curFlight.completed)
            {
                flightList.Add(curFlight);
            }
        }

        //return list with all completed flights
        return flightList.ToArray();
    }

    //get a list of all airports in database
    public Airport[] getAirportList()
    {
        //load airport list from database
        List<Airport> airportList = new List<Airport>();
        var airportTable = data.Worksheet("Airport Table").Table(0);

        //loop through all airports 
        for (int i = 2; i <= airportTable.RowCount(); i++)
        {
            //get airport info and add to list
            Airport? curAirport = getAirportInfo(airportTable.Cell(i, 1).GetValue<string>());
            if (curAirport != null)
            {
                airportList.Add(curAirport);
            }
        }

        //return list with all airports
        return airportList.ToArray();
    }


    //get flight info of given flightID
    public Flight? getFlightInfo(int flightID)
    {
        //load flight list from database
        var flightTable = data.Worksheet("Flight List").Table(0);

        //loop through all flights
        for (int row = 2; row <= flightTable.RowCount(); row++)
        {
            //check if flight matches ID
            if (flightTable.Cell(row, 1).GetValue<int>() == flightID)
            {
                //if flight matches ID get all flight info and build new flight object

                //parse passengerList string into a list of strings
                string passengerString = flightTable.Cell(row, 9).GetValue<string>();
                List<string> passengerList = passengerString.Split(',').ToList();
                passengerList.RemoveAll(s => s == "");

                return new Flight(
                    flightTable.Cell(row, 1).GetValue<int>(),
                    flightTable.Cell(row, 2).GetValue<string>(),
                    flightTable.Cell(row, 3).GetValue<string>(),
                    flightTable.Cell(row, 4).GetValue<DateTime>(),
                    flightTable.Cell(row, 5).GetValue<bool>(),
                    flightTable.Cell(row, 6).GetValue<int>(),
                    flightTable.Cell(row, 7).GetValue<int>(),
                    flightTable.Cell(row, 8).GetValue<double>(),
                    passengerList
                );
            }
        }

        //if flight number could not be found, return null
        return null;
    }

    //get info for an airport matching the given airport code
    public Airport? getAirportInfo(String airportCode)
    {
        //load airport table from database
        var airportTable = data.Worksheet("Airport Table").Table(0);

        //loop through all airports in table
        for (int row = 2; row <= airportTable.RowCount(); row++)
        {
            //check if airport code matches the argument
            if (airportTable.Cell(row, 1).GetValue<String>() == airportCode)
            {
                //return a new airport object with the retrieved data
                return new Airport(
                    airportTable.Cell(row, 1).GetValue<string>(),
                    airportTable.Cell(row, 2).GetValue<string>(),
                    airportTable.Cell(row, 3).GetValue<string>(),
                    airportTable.Cell(row, 4).GetValue<double>(),
                    airportTable.Cell(row, 5).GetValue<double>()
                );
            }
        }

        return null;
    }

    //get info for a plane matching the given plane type
    public Plane? getPlaneInfo(int planeType)
    {
        //load plane list from db
        Plane? ret = null;
        var planeTable = data.Worksheet("Plane List").Table(0);
        int i;
        for (i = 2; i <= planeTable.RowCount(); i++)
        {
            //check if planetype matches the argument
            if (planeTable.Cell(i, 1).GetValue<int>() == planeType)
            {
                //return new plane object with found data
                ret = new Plane(planeType, planeTable.Cell(i, 2).GetValue<int>(), planeTable.Cell(i, 3).GetValue<int>());
                break;
            }
        }
        return ret;
    }

    //write airport info to database given an airport object
    public void writeAirportInfo(Airport airport)
    {
        //load airport table from database
        bool found = false;
        var airportTable = data.Worksheet("Airport Table").Table(0);
        int i;

        //loop through all airports in table to see if  the entry already exists
        for (i = 2; i <= airportTable.RowCount(); i++)
        {
            if (airportTable.Cell(i, 1).GetValue<String>() == airport.airportCode)
            {
                //if entry already exists, break from loop and keep track of index to overwrite entry
                found = true;
                break;
            }
        }
        if (!found)
        {
            //if entry did not already exist, add a row below
            airportTable.InsertRowsBelow(1);
        }

        //write info from airport object into selected index of table
        airportTable.Cell(i, 1).SetValue(airport.airportCode);
        airportTable.Cell(i, 2).SetValue(airport.cityName);
        airportTable.Cell(i, 3).SetValue(airport.state);
        airportTable.Cell(i, 4).SetValue(airport.longitude);
        airportTable.Cell(i, 5).SetValue(airport.latitude);
        data.Save();
    }

    //write plane info to database given a plane object
    public void writePlaneInfo(Plane plane)
    {
        //load plane list from database
        bool found = false;
        var planeTable = data.Worksheet("Plane List").Table(0);
        int i;

        //loop through all planes to see if entry already exists
        for (i = 2; i <= planeTable.RowCount(); i++)
        {
            if (planeTable.Cell(i, 1).GetValue<int>() == plane.planeType)
            {
                //if entry already exists, break from loop and keep track of index to overwrite entry
                found = true;
                break;
            }
        }
        if (!found)
        {
            //if entry did not already exist, add a row below
            planeTable.InsertRowsBelow(1);
        }

        //write plane info at selected index
        planeTable.Cell(i, 1).SetValue(plane.planeType);
        planeTable.Cell(i, 2).SetValue(plane.capacity);
        planeTable.Cell(i, 3).SetValue(plane.range);
        data.Save();
    }

    /*createAccount() is given a customer and creates the account in the accounts table in the db. The customer's password is encrypted here and all
    of their information is written to the db. 
    */
    public void createAccountInfo(Customer customer)
    {
        //Create a table object
        var accountsTable = data.Worksheet("Accounts").Table(0);

        //insert a row in the account table
        accountsTable.InsertRowsBelow(1);

        string accountHistoryString = "";
        //create an account history string
        foreach (string str in customer.accountHistory)
        {
            accountHistoryString += "(" + str + ")";
        }

        //Create the SHA512 hash structure
        SHA512 hashCrypt = SHA512.Create();
        //create a byte array of all of the characters in the password string
        byte[] inputString = new byte[customer.password.Length];
        inputString = Encoding.UTF8.GetBytes(customer.password);
        //Create a byte array of the computed hash from the password
        byte[] outputArray = hashCrypt.ComputeHash(inputString);

        //create a string of the hashed password by converting the byte array to a hex string
        string password = Convert.ToHexString(outputArray);

        //dispose the resource of the hash structure
        hashCrypt.Dispose();

        //Store all of the account information in the database
        accountsTable.Cell(accountsTable.RowCount(), 1).SetValue(customer.userID);
        accountsTable.Cell(accountsTable.RowCount(), 2).SetValue(customer.userType);
        accountsTable.Cell(accountsTable.RowCount(), 3).SetValue(password);
        accountsTable.Cell(accountsTable.RowCount(), 4).SetValue(customer.username);
        accountsTable.Cell(accountsTable.RowCount(), 5).SetValue(customer.address);
        accountsTable.Cell(accountsTable.RowCount(), 6).SetValue(customer.billingInfo);
        accountsTable.Cell(accountsTable.RowCount(), 7).SetValue(customer.age);
        accountsTable.Cell(accountsTable.RowCount(), 8).SetValue(customer.points);
        accountsTable.Cell(accountsTable.RowCount(), 9).SetValue(customer.flightList == null ? "" : string.Join(',', customer.flightList.ToArray()));
        accountsTable.Cell(accountsTable.RowCount(), 10).SetValue(customer.firstName);
        accountsTable.Cell(accountsTable.RowCount(), 11).SetValue(customer.lastName);
        accountsTable.Cell(accountsTable.RowCount(), 12).SetValue(customer.emailAddress);
        accountsTable.Cell(accountsTable.RowCount(), 13).SetValue(accountHistoryString);
        //Save the database
        data.Save();
    }

    /*writeCustomerInfo() is given a customer and checks to see if the customer already exists in the db. If the customer exists the method returns,
    if not the customer's info is written to the db. 
    */
    public bool writeCustomerInfo(Customer customer)
    {
        bool found = false;
        var accountsTable = data.Worksheet("Accounts").Table(0);
        int i;
        for (i = 2; i <= accountsTable.RowCount(); i++)
        {
            if (accountsTable.Cell(i, 1).GetValue<String>() == customer.userID)
            {
                found = true;
                break;
            }
        }
        if (!found)
        {
            return false;
        }

        string accountHistoryString = "";
        foreach (string str in customer.accountHistory)
        {
            accountHistoryString += "(" + str + ")";
        }

        accountsTable.Cell(i, 1).SetValue(customer.userID);
        accountsTable.Cell(i, 2).SetValue(customer.userType);
        accountsTable.Cell(i, 3).SetValue(customer.password);
        accountsTable.Cell(i, 4).SetValue(customer.username);
        accountsTable.Cell(i, 5).SetValue(customer.address);
        accountsTable.Cell(i, 6).SetValue(customer.billingInfo);
        accountsTable.Cell(i, 7).SetValue(customer.age);
        accountsTable.Cell(i, 8).SetValue(customer.points);
        accountsTable.Cell(i, 9).SetValue(customer.flightList == null ? "" : string.Join(',', customer.flightList.ToArray()));
        accountsTable.Cell(i, 10).SetValue(customer.firstName);
        accountsTable.Cell(i, 11).SetValue(customer.lastName);
        accountsTable.Cell(i, 12).SetValue(customer.emailAddress);
        accountsTable.Cell(i, 13).SetValue(customer.phoneNum);
        accountsTable.Cell(i, 14).SetValue(accountHistoryString);

        data.Save();

        return true;
    }

    /* writeFlightInfo() looks for the flight it is passed and if it does not exist writes the flight's information to the db
    */
    public void writeFlightInfo(Flight flight)
    {
        bool found = false;
        var flightTable = data.Worksheet("Flight List").Table(0);
        int i;
        for (i = 2; i <= flightTable.RowCount(); i++)
        {
            if (flightTable.Cell(i, 1).GetValue<int>() == flight.FlightNumber)
            {
                found = true;
                break;
            }
        }
        if (!found)
        {
            flightTable.InsertRowsBelow(1);
        }

        flight.passengerList.RemoveAll(s => s == "");

        flightTable.Cell(i, 1).SetValue(flight.FlightNumber);
        flightTable.Cell(i, 2).SetValue(flight.origin);
        flightTable.Cell(i, 3).SetValue(flight.destination);
        flightTable.Cell(i, 4).SetValue(flight.dateTime);
        flightTable.Cell(i, 5).SetValue(flight.completed);
        flightTable.Cell(i, 6).SetValue(flight.planeType);
        flightTable.Cell(i, 7).SetValue(flight.numPassengers);
        flightTable.Cell(i, 8).SetValue(flight.ticketRevenue);
        flightTable.Cell(i, 9).SetValue(flight.passengerList == null ? "" : string.Join(',', flight.passengerList.ToArray()));
        data.Save();
    }

    /* writePaymentHistory() writes a customer's information to the db along with their payment history. 
    */
    public void writePaymentHistory(string firstName, string lastName, string creditcardNum, string cost)
    {
        var paymentHistoryTable = data.Worksheet("Payment History").Table(0);

        paymentHistoryTable.InsertRowsBelow(1);

        int row = paymentHistoryTable.RowCount();
        paymentHistoryTable.Cell(row, 1).SetValue(firstName);
        paymentHistoryTable.Cell(row, 2).SetValue(lastName);
        paymentHistoryTable.Cell(row, 3).SetValue(creditcardNum);
        paymentHistoryTable.Cell(row, 4).SetValue(cost);
    }

    /* getPlaneList() gets the list of all planes being used and returns an array with all of the planes' information
    */
    public Plane[] getPlaneList()
    {
        List<Plane> ret = new List<Plane>();
        var planeTable = data.Worksheet("Plane List").Table(0);
        int i;
        for (i = 2; i <= planeTable.RowCount(); i++)
        {
            Plane? plane = getPlaneInfo(planeTable.Cell(i, 1).GetValue<int>());
            if (plane != null)
            {
                ret.Add(plane);
            }
        }
        return ret.ToArray();
    }

    /*deleteFlight() takes in a flight number, finds it in the Flight List in the db and then deletes the row containing it. After the row
    is deleted the flight no longer exists. 
    */
    public bool deleteFlight(int flightNumber)
    {
        var flightTable = data.Worksheet("Flight List").Table(0);
        int i;
        for (i = 2; i <= flightTable.RowCount(); i++)
        {
            if (flightTable.Cell(i, 1).GetValue<int>() == flightNumber)
            {
                flightTable.Row(i).Delete();
                data.Save();
                return true;
            }
        }
        return false;
    }

    /*deleteAirport() takes in an airportCode and looks for this airport in the Airport table in the db. Once the airport is found the row the
    information is held in is deleted and this airport is no longer serviced. 
    */
    public bool deleteAirport(string airportCode)
    {
        var airportTable = data.Worksheet("Airport Table").Table(0);
        int i;
        for (i = 2; i <= airportTable.RowCount(); i++)
        {
            if (airportTable.Cell(i, 1).GetValue<String>() == airportCode)
            {
                airportTable.Row(i).Delete();
                data.Save();
                return true;
            }
        }
        return false;
    }

    /* deletePlane() takes a plane type and searches for it in the Plane List held in the db. The row the plane's information is held in is then 
    deleted from the db and this plane is no longer used.*/
    public bool deletePlane(int planeType)
    {
        var planeTable = data.Worksheet("Plane List").Table(0);
        for (int i = 2; i <= planeTable.RowCount(); i++)
        {
            if (planeTable.Cell(i, 1).GetValue<int>() == planeType)
            {
                planeTable.Row(i).Delete();
                data.Save();
                return true;
            }
        }
        return false;
    }

    /* deleteCustomer() takes a customer id and looks for the customer's information in the db. The row the information is stored in is then
    deleted.*/
    public bool deleteCustomer(string customerID)
    {

        var accountsTable = data.Worksheet("Accounts").Table(0);

        for (int i = 2; i <= accountsTable.RowCount(); i++)
        {
            if (accountsTable.Cell(i, 1).GetValue<int>().Equals(customerID))
            {
                accountsTable.Row(i).Delete();
                data.Save();
                return true;
            }
        }

        return false;
    }

    /*checkUserExists() traverses through the accounts table in the db and checks to see if the username belongs to an existing
    user */
    public bool checkUserExists(string UserName)
    {
        var accountsTable = data.Worksheet("Accounts").Table(0);

        for (int row = 2; row <= accountsTable.RowCount(); row++)
        {
            if (accountsTable.Cell(row, 4).GetValue<string>() == UserName)
            {
                return true;
            }
        }

        return false;
    }

    /*checkPasswordMatches takes a user's username and password and checks to see if they match the combination stored in the database.
    The password is encoded with SHA12 encryption and compared to what is stored in the db.
    */
    public bool checkPasswordMatches(string username, string password)
    {
        var accountsTable = data.Worksheet("Accounts").Table(0);

        for (int row = 2; row <= accountsTable.RowCount(); row++)
        {
            if (accountsTable.Cell(row, 4).GetValue<string>() == username)
            {
                /*Cryptography here*/
                SHA512 hashCrypt = SHA512.Create();
                byte[] inputString = new byte[password.Length];
                inputString = Encoding.UTF8.GetBytes(password);
                byte[] outputArray = hashCrypt.ComputeHash(inputString);

                string encryptPassword = Convert.ToHexString(outputArray);

                hashCrypt.Dispose();

                if (accountsTable.Cell(row, 3).GetValue<string>() == encryptPassword)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        // If username not found, return false
        return false;
    }

    /*This method retrieves the userID given the username of the customer
    The method traverses the customertable and compares the username to each entry
    and returns the username once found. 
    */
    public string? getUserID(string username)
    {
        var customerTable = data.Worksheet("Accounts").Table(0);

        for (int row = 2; row <= customerTable.RowCount(); row++)
        {
            if (customerTable.Cell(row, 4).GetValue<string>() == username)
            {
                return customerTable.Cell(row, 1).GetValue<string>();
            }
        }

        return null;
    }
}