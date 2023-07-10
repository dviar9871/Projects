﻿/**
 * Customer.cs
 * Contains the class Customer which is responsible for managing the customer's UI and data access
*/
using System;

namespace Air3550;

//Class responsible for managing the customer's UI and data access
class Customer
{
    public String userID;
    public String userType;
    public String password;
    public String username;
    public String address;
    public String billingInfo;
    public int age;
    public double points;
    public List<int> flightList;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String phoneNum;
    public List<string> accountHistory;
    private Database db = new Database();


    public Customer(string userID, string userType, string password, string username, string address, string billingInfo, int age, double points, List<int>? flightList, string firstName, string lastName, string emailAddress, string phoneNum, List<string>? accountHistory)
    {
        this.userID = userID;
        this.userType = userType;
        this.password = password;
        this.username = username;
        this.address = address;
        this.billingInfo = billingInfo;
        this.age = age;
        this.points = points;
        this.flightList = flightList == null ? new List<int>() : flightList;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNum = phoneNum;
        this.accountHistory = accountHistory == null ? new List<string>() : accountHistory;
    }

    //Method to be called after a customer logs in, displays all options
    public void CustomerStartPage()
    {
        string? userInput;
        //Customer home page loop start
        while (true)
        {
            Console.Clear();

            //display options
            Console.Clear();
            Console.WriteLine("----------------------");
            Console.WriteLine("  Customer Home Page  ");
            Console.WriteLine("----------------------");
            Console.WriteLine("1- Book flight");
            Console.WriteLine("2- Cancel flight");
            Console.WriteLine("3- View account");
            Console.WriteLine("4- Print boarding pass");
            Console.WriteLine("0- Exit");
            //Read in the input
            userInput = Console.ReadLine();

            //Check the selection made by the user

            switch (userInput)
            {
                case "0": //exit customer page
                    return;
                case "1": //book a flight
                    bookFlight(); //method used to book a flight
                    break;
                case "2": //cancel a flight
                    //Print cancel flight page heading
                    Console.WriteLine("--------------------------------------------------------------------------------------------");
                    Console.WriteLine("                                    Cancel Flight                                           ");
                    Console.WriteLine("--------------------------------------------------------------------------------------------");
                    //Prompt user for the ID of the flight they want to cancel
                    Console.Write("Enter the ID of the flight you wish to cancel: ");

                    //read in the user's input
                    string? input = Console.ReadLine();
                    int flightNumber;

                    //try to convert the input into a integer
                    try
                    {
                        flightNumber = Convert.ToInt32(input); //reads in flight number for flight to be cancelled
                        cancelFlight(flightNumber); //method to cancel flight
                    }
                    //catch the exception and show the user there is an invalid input
                    catch (Exception e)
                    {
                        Console.WriteLine("Invalid input.");
                    }

                    break;
                case "3": //view account  
                    Console.Clear();
                    viewAccount();
                    break;
                case "4": //print boarding pass
                    printBoardingPass();
                    break;
                default: //Invalid input detected
                    Console.WriteLine("Invalid input");
                    break;
            }

            //pause here to ensure user has time to read any UI element before returning back to home page
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
        }
    }

    //Provides the customer with options to view their account details
    void viewAccount()
    {
        string? userInput;
        //Provide options for the customer to view their account details
        Console.WriteLine("-------------------------------------");
        Console.WriteLine(" Choose one of the following options ");
        Console.WriteLine("-------------------------------------");
        Console.WriteLine("1- View future flights");
        Console.WriteLine("2- View past flights");
        Console.WriteLine("3- View points");
        Console.WriteLine("4- View Account Info");
        Console.WriteLine("0- Exit");

        userInput = Console.ReadLine();

        switch (userInput)
        {
            case "1": //look at future flights
                viewFlights();
                break;
            case "2": //cancel a flight
                Console.Clear();
                viewAccountHistory();
                break;
            case "3": //view points  
                Console.Clear();
                Console.WriteLine("--------------------------");
                Console.WriteLine("          Points          ");
                Console.WriteLine("--------------------------");
                Console.WriteLine("Point Balance: " + points);
                break;
            case "4": //view account info
                Console.Clear();
                Console.WriteLine("---------------------");
                Console.WriteLine(" Account Information ");
                Console.WriteLine("---------------------");
                Console.WriteLine("Name: " + firstName + " " + lastName);
                Console.WriteLine("Age: " + age);
                Console.WriteLine("Address: " + address);
                Console.WriteLine("Phone Number: " + phoneNum);
                Console.WriteLine("Account Number: " + userID);
                Console.WriteLine("");
                break;
            case "0":
                return;
            default:
                Console.WriteLine("Invalid input");
                break;

        }
    }

    //determines if a point transaction is possible, then removes the points from the customer's acount
    //allocatePoints() removes points from the cutomer's total points once they are used to pay for a flights
    bool allocatePoints(int pointCost)
    {
        //remove the given amount of points from the customer's account and return true if the balance was successfully charged
        if (pointCost <= points)
        {
            points -= pointCost;
            return true;
        }

        return false;
    }

    /* provideRefund() gives refunds to the customer when their flight is cancelled, 
    flight number is passed in for calculation
    */
    //Checks account history for a given flight number, gets the amount paid for the flight, refunds the amount to the customer's point balance
    bool provideRefund(int flightNumber)
    {
        //Find the given flight number in customer's account history, find the cost of the flight and whether they paid with points and refund points or to credit card on account
        if (accountHistory != null)
        {
            foreach (string flightHistory in accountHistory)
            {
                string[] infoArray = flightHistory.Split(',');
                if (int.Parse(infoArray[0]) == flightNumber)
                {
                    if (infoArray[2] == "true")
                    {
                        points += int.Parse(infoArray[1]) * 10;
                        /*Show points refund*/
                    }
                    else
                    {
                        /*Remove cost of flight from flight revenue*/
                        double cost = double.Parse(infoArray[1]);
                        Flight curFlight = db.getFlightInfo(flightNumber);
                        curFlight.ticketRevenue -= double.Parse(infoArray[1]);

                    }
                    return true;
                }
            }
        }

        return false;
    }

    /*cancelFlight() cancels the flight indicated by the flightNumber. Removes flight from the customer's flight list and takes customer
    out of flight's passenger list. The customer is then refunded for the flight. 
    */
    public bool cancelFlight(int flightNumber)
    {
        //need to remove flight from customer flight list and remove customer from flight's passenger list 
        foreach (var trip in flightList)
        {
            if (trip == flightNumber)
            {
                //remove from the list 
                if (DateTime.Now < db.getFlightInfo(flightNumber).dateTime.AddHours(-1))
                {
                    provideRefund(flightNumber);
                    flightList.Remove(flightNumber);
                }
                else
                {
                    Console.WriteLine("Cannot cancel a flight 1 hour before departure.");
                    return false;
                }

                break;
            }
        }
        //to cancel their flight they need to be taken out of the array and refunded

        //found the flight to be deleted
        Flight? flight = db.getFlightInfo(flightNumber);

        if (flight != null)
        {
            //look through the list and take out customer's information 
            List<string> names = flight.passengerList;
            names.Remove(this.userID);
            flight.numPassengers--;
            flight.passengerList = names;
            db.writeFlightInfo(flight);
            return true;
        }
        else
        {
            Console.WriteLine("Invalid flight entered.");
        }

        return false;
    }

    void printBoardingPass()
    {
        Console.Clear();

        // display list of flights
        Console.WriteLine("-------------------------");
        Console.WriteLine("   Print Boarding Pass   ");
        Console.WriteLine("-------------------------");

        if (!viewFlights())
        {
            Console.Write("Press any key to continue");
            Console.ReadKey(false);


            return;
        }

        Console.Write("Flight number to print boarding pass: ");
        string? input = Console.ReadLine();
        int flightNumber;

        try
        {
            if (input != null)
            {
                flightNumber = Convert.ToInt32(input);
            }
            else
            {
                Console.WriteLine("Invalid input.");
                return;
            }
        }
        catch (Exception e)
        {
            Console.WriteLine("Invalid input.");
            return;
        }

        // get flight information
        Flight? flight = db.getFlightInfo(flightNumber);

        // check if flight is less than 24 hours away from departure
        if (flight != null)
        {
            DateTime departureTime = flight.dateTime;
            TimeSpan timeUntilDeparture = departureTime - DateTime.Now;
            if (timeUntilDeparture.TotalHours < 24)
            {
                Console.WriteLine("Boarding pass information:");
                Console.WriteLine("Flight Number: " + flight.FlightNumber);
                Console.WriteLine("Name: " + firstName + " " + lastName);
                Console.WriteLine("From: " + flight.origin);
                Console.WriteLine("To: " + flight.destination);
                Console.WriteLine("Departure Time: " + flight.dateTime);
                Console.WriteLine("Arrival Time: " + flight.calculateArrivalTime(flight.distance));
                Console.WriteLine("Account Number: " + userID);
            }
            else
            {
                Console.WriteLine("Sorry, boarding pass information is not available for flights more than 24 hours away from departure.");
            }
        }
        else
        {
            Console.WriteLine("Invalid flight entered.");
        }

    }

    //viewFlights() lists all of the customer's upcoming flights
    bool viewFlights()
    {
        //get flightList from the current customer
        List<int> flightList = this.flightList;
        //check to see if the customer has any scheduled flights
        if (flightList.Count == 0)
        {
            Console.WriteLine("No flights scheduled");
            return false;

        }
        else //if the customer has scheduled flights
        {
            //formatting for UI
            Console.WriteLine("Below is a list of all of your future flights");
            Console.WriteLine("---------------------------------------------------------------------------------------------");
            Console.WriteLine("                                      Future Flights                                         ");
            Console.WriteLine("---------------------------------------------------------------------------------------------");
            //print a formatted list of flights with a header
            Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
            Console.WriteLine("---------------------------------------------------------------------------------------------");
            //prints every flight in the customer's flight list 
            foreach (var flightNum in flightList)
            {
                Flight? flight = db.getFlightInfo(flightNum);
                if (flight != null)
                {
                    flight.printFlight();
                }
            }
        }
        return true;
    }

    //Asks user for information about the flight they wish to book, searches the available flights from the database, and proposes a flight matching the criteria
    public bool bookFlight()
    {
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("                                      Book Flight                                            ");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        //Ask for origin and destination
        Console.Write("Enter your origin airport code: ");
        string? origin = Console.ReadLine();

        if (origin == null || origin == "" || db.getAirportInfo(origin) == null)
        {
            Console.WriteLine("Invalid origin.");
            return false;
        }

        Console.Write("Enter your destination airport code: ");
        string? destination = Console.ReadLine();
        if (destination == null || destination == "" || db.getAirportInfo(destination) == null)
        {
            Console.WriteLine("Invalid destination.");
            return false;
        }

        if (origin == destination)
        {
            Console.WriteLine("The same destination and origin was entered.");
            return false;
        }

        //ask for return date
        Console.Write("Enter your depart date (MM/DD/YYYY): ");
        DateTime departureDate;
        string? departureDateString = Console.ReadLine();
        try
        {
            departureDate = DateTime.Parse(departureDateString == null ? "" : departureDateString);
        }
        catch (Exception e)
        {
            Console.WriteLine("Invalid input.");
            return false;
        }

        //if date is more than 6 months out: reject request
        if (departureDate.Month >= (DateTime.Now.Month + 6))
        {
            Console.Write("Date entered is more then 6 months away.");
            return false;
        }

        //Ask for round trip
        Console.Write("Round trip? (Y/N): ");
        string? input = Console.ReadLine();
        input = input == null ? "" : input;
        bool roundTrip = (input.ToString().CompareTo("Y") == 0 ? true : false);

        string? returnDateString = "";
        DateTime returnDate = new DateTime();
        if (roundTrip)
        {
            //ask for return date
            Console.Write("Enter your return date (MM/DD/YYYY): ");
            returnDateString = Console.ReadLine();
            returnDateString = returnDateString == null ? "" : returnDateString;
            try
            {
                returnDate = DateTime.Parse(returnDateString);
            }
            catch (Exception e)
            {
                Console.WriteLine("Invalid input.");
                return false;
            }

            //if date is more than 6 months out: reject request
            if (returnDate.Month >= (DateTime.Now.Month + 6))
            {
                Console.WriteLine("ERROR: Date must be within 6 months");
                return false;
            }
        }

        //search origin airport for a direct flight to the destination
        Flight[] allFlights = db.getUpcomingFlightList();
        List<List<Flight>> outgoingFlights = new List<List<Flight>>(), returningFlights = new List<List<Flight>>(), chosenFlights = new List<List<Flight>>();

        //Search for a direct flight to destination
        foreach (Flight flight in allFlights)
        {
            //check if date, origin and destination match the input
            if (flight.dateTime.Date.Equals(departureDate.Date) && flight.origin == origin && flight.destination == destination)
            {
                Plane? planeInfo = db.getPlaneInfo(flight.planeType);
                //check that the found flight is not at max capacity
                if ((planeInfo) != null && (planeInfo.capacity > flight.numPassengers))
                {
                    //add flight to outgoing flights list
                    List<Flight> list = new List<Flight>();
                    list.Add(flight);
                    outgoingFlights.Add(list);
                }
            }
        }

        //if no flight has been found in first search, look for a connecting airport
        if (outgoingFlights.Count == 0)
        {
            outgoingFlights.AddRange(getConnectingFlights(departureDate, origin, destination, allFlights));
        }

        //if a flight has still not been found, display that no flights have been found and return
        if (outgoingFlights.Count == 0)
        {
            Console.WriteLine("No flight found.");
            return false;
        }

        //if a round trip was selected, find the matching returning flight and display
        if (roundTrip)
        {
            string? returnOrigin = destination, returnDestination = origin;

            //search origin airport for a direct flight to the destination
            foreach (Flight flight in allFlights)
            {
                //check if date, origin, and destination match the input
                if (flight.dateTime.Date.Equals(returnDate.Date) && flight.origin == returnOrigin && flight.destination == returnDestination)
                {
                    Plane? curPlane = db.getPlaneInfo(flight.planeType);
                    //check that the found flight is not at max capacity
                    if (curPlane != null && curPlane.capacity > flight.numPassengers)
                    {
                        //add flight to returning flight list
                        List<Flight> list = new List<Flight>();
                        list.Add(flight);
                        returningFlights.Add(list);
                    }
                }
            }

            //if no flight has been found in first search, look for a connecting airport
            if (returningFlights.Count == 0)
            {
                returningFlights.AddRange(getConnectingFlights(returnDate, returnOrigin, returnDestination, allFlights));
            }

            //if a flight has still not been found, display that no flights have been found and return
            if (returningFlights.Count == 0)
            {
                Console.WriteLine("No returning flight found");
                return false;
            }
        }

        //display outgoing flights
        int optionNumber = 1;
        Console.WriteLine("Outgoing Flights: ");
        foreach (List<Flight> offeredFlight in outgoingFlights)
        {
            Console.WriteLine("Option Number: {0}", optionNumber++);
            printOfferedFlight(offeredFlight);
        }

        //prompt user to select desired flight or press 0 to exit
        Console.Write("Enter desired option number or press 0 to exit: ");
        input = Console.ReadLine();
        int outgoingFlightChoice;
        try
        {
            if (input != null)
            {
                outgoingFlightChoice = Convert.ToInt32(input) - 1;
                if (outgoingFlightChoice == -1)
                {
                    return false;
                }
            }
            else
            {
                Console.WriteLine("Invalid input.");
                return false;
            }
        }
        catch (Exception e)
        {
            Console.WriteLine("Invalid input.");
            return false;
        }

        //add chosen flight to list
        chosenFlights.Add(outgoingFlights.ElementAt(outgoingFlightChoice));

        //display returning flights if needed
        int returningFlightChoice = 0;
        if (roundTrip)
        {
            optionNumber = 1;
            Console.WriteLine("Returning Flights: ");
            foreach (List<Flight> offeredFlight in returningFlights)
            {
                Console.WriteLine("Option Number: {0}", optionNumber++);
                printOfferedFlight(offeredFlight);
            }

            //prompt user to select desired flight or press 0 to exit
            Console.Write("Enter desired option number or press 0 to exit: ");
            input = Console.ReadLine();
            try
            {
                if (input != null)
                {
                    returningFlightChoice = Convert.ToInt32(input) - 1;
                    if (returningFlightChoice == -1)
                    {
                        return false;
                    }
                }
                else
                {
                    Console.WriteLine("Invalid input.");
                    return false;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Invalid input.");
                return false;
            }

            chosenFlights.Add(returningFlights.ElementAt(returningFlightChoice));
        }

        //display summary of flights chosen and total cost
        double totalCost = 0;
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("                                        Flights                                              ");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        foreach (List<Flight> trip in chosenFlights)
        {
            foreach (Flight flight in trip)
            {
                flight.printFlight();
                totalCost += flight.calculateTicketCost();
            }

            //Add layover fee for each connecting flight
            totalCost += 8 * (trip.Count - 1);
        }

        Console.WriteLine("Total Cost: {0: 0.00}", totalCost);
        Console.WriteLine("Point Balance: {0: 0.00}", points);

        //enter payment screen
        //check if account has enough points to use to pay for flight
        bool usedPoints = false;
        if (points >= totalCost)
        {
            Console.Write("Would you like to pay with points? (Y/N): ");
            input = Console.ReadLine();
            input = input == null ? "" : input;
            usedPoints = (input.ToString().CompareTo("Y") == 0 ? true : false);
            points -= totalCost;
        }

        //if customer does not want to pay with points, charge to card on file and record transaction to database
        if (!usedPoints)
        {
            Console.WriteLine("Charging to card#: {0}", billingInfo);
            db.writePaymentHistory(firstName, lastName, billingInfo, Convert.ToString(totalCost));
        }

        Console.Write("Confirm Payment (Y to proceed, N to cancel): ");
        input = Console.ReadLine();
        input = input == null ? "" : input;
        if (input.ToString().CompareTo("Y") != 0)
        {
            return false;
        }

        //save flight and amount paid to account history and update flight info for chosen flights
        foreach (List<Flight> trip in chosenFlights)
        {
            foreach (Flight flight in trip)
            {
                //update customer info
                this.flightList.Add(flight.FlightNumber);
                this.accountHistory.Add(String.Format("{0},{1},{2}", flight.FlightNumber, flight.calculateTicketCost(), usedPoints));

                //update flight info and save it to database
                flight.numPassengers++;
                flight.passengerList.Add(String.Format("{0},", this.userID));
                flight.ticketRevenue += flight.calculateTicketCost();
                db.writeFlightInfo(flight);
            }
        }

        //save customer info to database
        db.writeCustomerInfo(this);
        return true;
    }

    //finds a sequence of 2 flights that connect the given date, origin, and destination
    List<List<Flight>> getConnectingFlights(DateTime date, string origin, string destination, Flight[] allFlights)
    {
        //Finds a list of all flights that connect the given destinations on the given date

        List<Flight> flight1 = new List<Flight>(), flight2 = new List<Flight>();
        List<List<Flight>> validFlights = new List<List<Flight>>();
        Plane? curPlane;

        //find all destinations from starting airport
        foreach (Flight flight in allFlights)
        {
            if (flight.dateTime.Date.Equals(date.Date) && flight.origin == origin)
            {
                curPlane = db.getPlaneInfo(flight.planeType);
                if ((curPlane != null) && (curPlane.capacity > flight.numPassengers))
                {
                    flight1.Add(flight);
                }
            }
        }

        //find all flights going in to destination
        foreach (Flight flight in allFlights)
        {
            if (flight.dateTime.Date.Equals(date.Date) && flight.destination == destination)
            {
                curPlane = db.getPlaneInfo(flight.planeType);
                if (curPlane != null && curPlane.capacity > flight.numPassengers)
                {
                    flight2.Add(flight);
                }
            }
        }

        //find all flights with a connecting airport for flight1 and flight2
        foreach (Flight leg1 in flight1)
            foreach (Flight leg2 in flight2)
                if (leg1.destination == leg2.origin)
                {
                    //check if leg1 will arrive at least 40 minutes before leg2 leaves
                    if (leg1.calculateArrivalTime(leg1.distance).AddMinutes(40) <= leg2.dateTime)
                    {
                        List<Flight> list = new List<Flight>();
                        list.Add(leg1);
                        list.Add(leg2);
                        validFlights.Add(list);
                    }
                }

        return validFlights;
    }

    //prints a formatted flight with a header and total cost
    private void printOfferedFlight(List<Flight> list)
    {
        //prints a formatted list of a set of flights and displays their total cost

        double totalCost = 0;
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("                                     Offered Flights                                         ");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        foreach (Flight flight in list)
        {
            flight.printFlight();
            totalCost += flight.calculateTicketCost();
        }

        //Add layover fee for each connecting flight
        totalCost += 8 * (list.Count - 1);
        Console.WriteLine("Cost: {0: 0.00}\n", totalCost);
    }

    //retrieves and displays the user's account history
    public void viewAccountHistory()
    {
        Console.WriteLine("Flight number | Amount paid | Paid with points");
        foreach (string entry in accountHistory)
        {
            string[] elements = entry.Split(",");
            Console.WriteLine("{0, 13} | ${1, 10} | {2, 17}", elements[0], elements[1], elements[2]);
        }
    }

}

