/**
 * LoadEngineer.cs
 * Contains the class LoadEngineer which is responsible for managing the Load Engineer's UI and data access
*/
using System;

namespace Air3550;

class LoadEngineer
{
    Database db;

    public LoadEngineer()
    {
        //init database
        db = new Database();
    }

    //Load engineer start page will show all of the options for the load engineer and call methods to complete functions 
    public void LoadEngineerStartPage()
    {
        string? userInput = "";
        string? input = "";
        while (userInput != "0")
        {
            //Present the user with their options as Load Engineer 
            Console.Clear();
            Console.WriteLine("---------------------------");
            Console.WriteLine("  Load Engineer Home Page  ");
            Console.WriteLine("---------------------------");
            Console.WriteLine("1- List flights");
            Console.WriteLine("2- Add flight");
            Console.WriteLine("3- Delete flight");
            Console.WriteLine("4- List airports");
            Console.WriteLine("5- Add airport");
            Console.WriteLine("6- Delete airport");
            Console.WriteLine("0- Exit");

            //read the action the user wants to perform
            userInput = Console.ReadLine();

            //switch case based on userInput
            switch (userInput)
            {
                case "0":
                    return;
                case "1": //list flights
                    listFlights(); //go to method for listing flights
                    break;
                case "2": //add flight
                    addFlight(); //method for adding flights
                    break;
                case "3": //delete flight
                    Console.Clear();
                    Console.WriteLine("----------------------------------------------");
                    Console.WriteLine("                 Delete Flight              \n");
                    Console.WriteLine(" Please enter information of flight to delete ");
                    Console.WriteLine("----------------------------------------------");
                    Console.Write("Flight number: ");
                    //try catch block to cancel any exceptions there may be , invalid input and no flight entered
                    try
                    {
                        input = Console.ReadLine();
                        int flightNumber = Int32.Parse(input == null ? "" : input);
                        deleteFlight(flightNumber);
                    }
                    catch (Exception e)
                    {
                        switch (e)
                        {
                            case ArgumentNullException:
                                Console.WriteLine("No flight entered");
                                break;
                            default:
                                Console.WriteLine("Invalid input.");
                                break;
                        }
                    }
                    break;
                case "4": //list airports serviced 
                    Console.Clear();
                    listAirports(); //method for listing all airports serviced
                    break;
                case "5": //add airport 
                    addAirport(); //method for adding new airports
                    break;
                case "6": //delete airport
                    deleteAirport(); //method for removing airports from serviced airports list
                    break;
                default: //if the user puts in an option that is not defined it is invalid 
                    Console.WriteLine("Invalid input");
                    break;
            }
            Console.Write("Press any key to continue");
            Console.ReadKey(false);
        }
    }

    /* listFlights() gives the user the option to list previous flights, upcoming flights, or all flights (past and future).
    The user is prompted to choose which list they would like and based on the input they are provided with the desired list*/
    void listFlights()
    {
        //ask for all, past, or upcoming flights
        Console.Clear();
        Console.WriteLine("-----------------------");
        Console.WriteLine(" Flight List Selection ");
        Console.WriteLine("-----------------------");
        Console.WriteLine("1- Previous flights");
        Console.WriteLine("2- Future flights");
        Console.WriteLine("3- All flights");
        Console.WriteLine("0- Exit");

        //read in user input
        string? userInput = Console.ReadLine();

        //switch case based on user input 
        switch (userInput)
        {
            case "0":
                return;
            case "1": //list previous flights 
                Console.Clear();
                //get the list of past flights from the db
                Console.WriteLine("Getting Flights...");
                Flight[] flights = db.getPastFlightList();
                //formatting for UI
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                Console.WriteLine("                                       Past Flights                                          ");
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                //for every flight that has passed we want to print the flight details 
                foreach (Flight flight in flights)
                {
                    flight.printFlight();
                }
                break;
            case "2": //list Future flights
                Console.Clear();
                Console.WriteLine("Getting Flights...");
                //get all future flights from db 
                Flight[] flights2 = db.getUpcomingFlightList();
                //formatting for UI
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                Console.WriteLine("                                      Future Flights                                         ");
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                //for every flight in the past flights list print the flight information
                foreach (Flight flight in flights2)
                    flight.printFlight();
                break;
            case "3": //list all flights 
                Console.Clear();
                Console.WriteLine("Getting Flights...");
                Flight[] flights3 = db.getFullFlightList();
                //formatting for UI
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                Console.WriteLine("                                       All Flights                                           ");
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
                Console.WriteLine("---------------------------------------------------------------------------------------------");
                //get full list of flights 
                //for every flight in the list of all flights print out information
                foreach (Flight flight in flights3)
                    flight.printFlight();
                break;
            default:
                Console.WriteLine("Invalid Choice");
                break;
        }
    }

    /* This method will assign a flight between two serviced airports. The LE will have to input the origin city airport code and the destination
    city origin code. The program will then ask for the date and time of the flight as well. The program will then check if the flight is already scheduled,
    if it is not then add it to the list */
    void addFlight()
    {
        //formatting for UI
        Console.Clear();
        Console.WriteLine("---------------------------------------");
        Console.WriteLine("               Add Flight            \n");
        Console.WriteLine("  Please enter new flight information  ");
        Console.WriteLine("---------------------------------------");

        Console.Write("Origin airport code: ");
        String? origin = Console.ReadLine(); //read in the origin airport code
        //check to see if the origin was entered or if the airport is in the system
        if ((origin == null) || (db.getAirportInfo(origin) == null))
        {
            Console.WriteLine("Entered city does not exist.");
            return;
        }

        Console.Write("Destination airport code: ");
        String? destination = Console.ReadLine();//read in the destination airport code
        //check to see if the destination was entered or if the airport is in the system
        if ((destination == null) || (db.getAirportInfo(destination) == null))
        {
            Console.WriteLine("Entered city does not exist.");
            return;
        }

        if (destination == origin)
        {
            Console.WriteLine("Invalid input. Destination is the same as origin.");
            return;
        }

        if (destination == origin)
        {
            Console.WriteLine("Invalid input. Destination is the same as origin.");
            return;
        }

        //check to see if this flight already exists in the upcoming flights list 
        Flight[] flights = db.getUpcomingFlightList();
        foreach (var curflight in flights)
        {
            if ((curflight.origin == origin) && (curflight.destination == destination))
            {
                Console.WriteLine("This flight has already been added.");
                return;
            }
        }

        string? curInput;
        //read in the date and time of the flight and write to db
        //try catch block is to catch any exceptions 
        try
        {
            Console.WriteLine("Month (mm): ");
            curInput = Console.ReadLine();
            int month = Int32.Parse(curInput == null ? "" : curInput);

            Console.WriteLine("Day (dd): ");
            curInput = Console.ReadLine();
            int day = Int32.Parse(curInput == null ? "" : curInput);

            Console.WriteLine("Year (yyyy):");
            curInput = Console.ReadLine();
            int year = Int32.Parse(curInput == null ? "" : curInput);

            Console.Write("Hour (0-23): ");
            curInput = Console.ReadLine();
            int hour = Int32.Parse(curInput == null ? "" : curInput);

            Console.Write("Minute (0 - 59): ");
            curInput = Console.ReadLine();
            int minute = Int32.Parse(curInput == null ? "" : curInput);

            //generate random 6 digit flight number
            Random rnd = new Random();
            int flightNum = rnd.Next() % (int)10e6;

            Flight flight = new Flight(flightNum, origin, destination, new DateTime(year, month, day, hour, minute, 0), false, 0, 0, 0, new List<string>());

            db.writeFlightInfo(flight);

            Console.WriteLine("New flight added");
        }
        catch (Exception e)
        {
            switch (e)
            {
                case ArgumentOutOfRangeException:
                    Console.WriteLine("Invalid date time entered.");
                    break;
                default:
                    Console.WriteLine("Invalid entry");
                    break;
            }
        }

    }

    /*deleteFlight() deletes the flight with the given flightNumber. This method goes through the flight's passenger list and cancels their flights.
    The program then goes through and deletes the flight from the db
    */
    void deleteFlight(int flightNumber)
    {
        //looks for fligh in db, if it is not found will return an error 
        Flight? flight = db.getFlightInfo(flightNumber);
        if (flight == null)
        {
            Console.WriteLine("Flight not found.");
            return;
        }

        //go through list of customers and cancel their flight
        List<string> names = flight.passengerList;

        //for every customer in the list provide a refund and cancel their flight
        foreach (string name in names)
        {
            Customer? customer = db.getCustomerInfo(name);
            if (customer != null)
            {
                customer.cancelFlight(flightNumber);
            }
        }

        //deletes flight, if it fails to delete returns error message
        if (db.deleteFlight(flight.FlightNumber))
        {
            Console.WriteLine("Successfully deleted flight.");
        }
        else
        {
            Console.WriteLine("Failed to delete flight.");
        }
    }
    /*addAirport() adds an airport to the list of serviced airport. LE is asked for airport code, state, city, longitude, and latitude.
    This airport is then added with all of the provided information and the airport is added if not in the db*/
    void addAirport()
    {
        //formatting for UI
        Console.Clear();
        Console.WriteLine("-----------------------------------------");
        Console.WriteLine("               Add Airport               ");
        Console.WriteLine("   Please enter new airport information  ");
        Console.WriteLine("-----------------------------------------");

        Airport city = new Airport(" ", " ", "", 0, 0);

        Console.Write("Airport code: ");
        String? input = Console.ReadLine();

        //looks to see if airport is already added to db
        if (input == null)
        {
            Console.WriteLine("Invalid entry.");
            return;
        }
        else if (db.getAirportInfo(input) != null)
        {
            Console.WriteLine("This airport has already been added.");
            return;
        }

        Console.Write("State: ");
        input = Console.ReadLine();
        //checks to see if input is valid 
        if ((input == null) || (input == ""))
        {
            Console.WriteLine("Invalid input.");
            return;
        }
        city.state = input;

        Console.Write("City name: ");
        input = Console.ReadLine();
        //checks to see if input is valid 
        if ((input == null) || (input == ""))
        {
            Console.WriteLine("Invalid input.");
            return;
        }
        city.cityName = input;

        //try catch block ensures that all exceptions are caught
        //longitude and latitude are used to find straight line distance 
        try
        {
            Console.Write("Longitude: ");
            input = Console.ReadLine();
            double longitude = Convert.ToDouble(input == null ? "" : input); ;
            city.longitude = longitude;

            Console.Write("Latitude: ");
            input = Console.ReadLine();
            double latitude = Convert.ToDouble(input == null ? "" : input); ;
            city.latitude = latitude;

            db.writeAirportInfo(city);

            Console.WriteLine("Successfully created city.");
        }
        catch (Exception e)
        {
            switch (e)
            {
                case NullReferenceException:
                    Console.WriteLine("Invalid input.");
                    break;
                default:
                    Console.WriteLine("Invalid input.");
                    break;
            }
        }
    }

    /*deleteAirport() deletes the airport from the serviced list of airports. This method asks for the airport code and goes thorugh the list
    of upcoming flights and deletes all flights leaving from or coming to the airport*/
    void deleteAirport()
    {
        //formatting for UI
        Console.Clear();
        Console.WriteLine("-------------------------------------------------");
        Console.WriteLine("                  Delete Airport                 ");
        Console.WriteLine("  Please enter information of airport to delete  ");
        Console.WriteLine("-------------------------------------------------");

        Console.WriteLine("Airport code: ");
        string? airportCode = Console.ReadLine();
        //checks to see if input is valid
        if (airportCode == null || airportCode == "")
        {
            Console.WriteLine("Invalid input.");
            return;
        }

        //checks to see if airport is in the list
        if (db.getAirportInfo(airportCode) == null)
        {
            Console.WriteLine("Airport does not exist.");
            return;
        }
        Flight[] routes = db.getUpcomingFlightList();

        /*looks through each upcoming flight and checks to see if airport is the origin or destination. If the airport is in the flight, fligth is 
        cancelled*/
        foreach (Flight flight in routes)
        {
            if (flight.origin == airportCode || flight.destination == airportCode)
            {
                Console.WriteLine("Could not delete airport because it has currently scheduled flights.");
                return;
            }
        }

        //checks to see if airport is properly deleted
        if (db.deleteAirport(airportCode))
        {
            Console.WriteLine("Successfully deleted airport.");
        }
        else
        {
            Console.WriteLine("Failed to delete airport.");
        }
    }

    //listAirports() lists all of the serviced airports. 
    void listAirports()
    {
        Console.WriteLine("-------------------------------------------------------------------------------------");
        Console.WriteLine("                                Serviced Airports                                    ");
        Console.WriteLine("-------------------------------------------------------------------------------------");
        //goes through list of all serviced airports and prints information
        Airport[] cities = db.getAirportList();
        Console.WriteLine("| Airport Code |      City Name      |      State     |   Longitude   |   Latitude  |");
        foreach (Airport city in cities)
        {
            city.printAirport();
        }
    }

}


