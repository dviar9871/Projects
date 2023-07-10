/**
 * MarketManager.cs
 * Contains the class MarketManager which is responsible for managing the Marketing Manager's UI and data access
*/
using System;
using ClosedXML.Excel;

namespace Air3550;

class MarketManager
{
    XLWorkbook data = new XLWorkbook("Software Database.xlsx");

    Database db;

    public MarketManager()
    {

        //init database
        db = new Database();


    }
    //options page to handle calling methods based on user input
    public void MarketManagerPage()
    {

        string? choice = "1";
        while (choice != "0")
        {
            Console.Clear();
            Console.WriteLine("-------------------------------");
            Console.WriteLine(" Marketing Manager Home Screen ");
            Console.WriteLine("-------------------------------");
            Console.WriteLine("1- Swap Plane");
            Console.WriteLine("2- View Upcoming Flights");
            Console.WriteLine("0- Exit");

            choice = Console.ReadLine();

            switch (choice)
            {
                case "0":
                    return;
                case "1":
                    swapPlane();
                    break;

                case "2":
                    viewFlightInfo();
                    Console.WriteLine("Flight Info has been retrieved");
                    break;
                default:
                    Console.WriteLine("Error: Invalid Choice");
                    break;
            }

            Console.Write("Press any key to continue");
            Console.ReadKey(false);
        }
    }
    //method to swap the plane on a flight
    void swapPlane()
    {
        string? enteredFlightNumber;
        int flightNumberChoice = 1;
        int newPlaneType = 1;
        Flight? selectedFlight;

        Flight[] flights = db.getUpcomingFlightList();

        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("                                      Future Flights                                         ");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        foreach (Flight flight in flights)
            flight.printFlight();

        Console.Write("Flight number to edit: ");

        enteredFlightNumber = Console.ReadLine();

        try
        {
            if (enteredFlightNumber != null)
            {
                flightNumberChoice = int.Parse(enteredFlightNumber);
                selectedFlight = db.getFlightInfo(flightNumberChoice);
                if (selectedFlight == null || selectedFlight.completed)
                {
                    Console.WriteLine("Invalid flight selected.");
                    return;
                }
            }
            else
            {
                Console.WriteLine("Invalid flight number.");
                return;
            }

        }
        catch (Exception e)
        {
            Console.WriteLine("Invalid flight number.");
            return;
        }

        try
        {
            Console.Write("New plane type: ");
            newPlaneType = Convert.ToInt32(Console.ReadLine());
            Plane? newPlane = db.getPlaneInfo(newPlaneType);
            if (newPlane == null)
            {
                string planeList = "";
                Plane[] planeArray = db.getPlaneList();
                for (int i = 0; i < planeArray.Length; i++)
                {
                    planeList = planeList + planeArray[i].planeType;
                    if (i != planeArray.Length - 1)
                    {
                        planeList = planeList + ", ";
                    }
                }
                Console.WriteLine("Invalid plane type. Please use one of the following: " + planeList);
                return;
            }
            else
            {

                if (newPlane.capacity >= selectedFlight.numPassengers)
                {
                    selectedFlight.planeType = newPlane.planeType;
                    db.writeFlightInfo(selectedFlight);
                    Console.WriteLine("Successfully swapped plane");
                    return;
                }
                else
                {
                    Console.WriteLine("Error: Selected plane does not have enough capacity to support the flight");
                    return;
                }
            }
        }
        catch (Exception e)
        {
            string planeList = "";
            Plane[] planeArray = db.getPlaneList();
            for (int i = 0; i < planeArray.Length; i++)
            {
                planeList = planeList + planeArray[i].planeType;
                if (i != planeArray.Length - 1)
                {
                    planeList = planeList + ", ";
                }
            }
            Console.WriteLine("Invalid plane type. Please use one of the following: " + planeList);
            return;
        }

    }

    //method to view flight information
    void viewFlightInfo()
    {
        Flight[] flights = db.getUpcomingFlightList();
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("                                      Future Flights                                         ");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
        Console.WriteLine("---------------------------------------------------------------------------------------------");
        foreach (Flight flight in flights)
            flight.printFlight();
    }


}