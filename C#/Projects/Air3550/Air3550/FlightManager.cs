/**
 * FlightManager.cs
 * Contains the class FlightManager which holds all functionality of the Flight Manager
*/
using System;
using ClosedXML.Excel;

namespace Air3550;

class FlightManager
{
    XLWorkbook data = new XLWorkbook("Software Database.xlsx");
    Database db;

    public FlightManager()
    {

        //init database
        db = new Database();


    }

    public void FlightManagerPage()
    {
        Console.WriteLine("You have signed in as a Flight Manager");
        Console.WriteLine(" ");

        string? choice = "1";
        while (choice != "0")
        {
            Console.Clear();
            Console.WriteLine("----------------------------");
            Console.WriteLine(" Flight Manager Home Screen ");
            Console.WriteLine("----------------------------");
            Console.WriteLine("1- Print FLight Manifest");
            Console.WriteLine("0- Exit");

            choice = Console.ReadLine();

            switch (choice)
            {
                case "0":
                    return;
                case "1":
                    int flightNumberChoice = 1;

                    Flight[] flights = db.getFullFlightList();

                    Console.WriteLine("---------------------------------------------------------------------------------------------");
                    Console.WriteLine("                                       Flights                                               ");
                    Console.WriteLine("---------------------------------------------------------------------------------------------");
                    Console.WriteLine("| FlightNumber | Origin >> Destination |       Date Time       | Plane Type | numPassengers |");
                    Console.WriteLine("---------------------------------------------------------------------------------------------");
                    foreach (Flight flight in flights)
                    {
                        flight.printFlight();
                    }
                    Console.WriteLine("");
                    Console.Write("Please enter the flight number you wish to print a flight manifest for: ");
                    try
                    {

                        flightNumberChoice = Convert.ToInt32(Console.ReadLine());
                        Flight? selectedFlight = db.getFlightInfo(flightNumberChoice);
                        if (selectedFlight != null)
                        {
                            Console.Clear();
                            printFlightManifest(selectedFlight);
                        }
                        else
                        {
                            Console.WriteLine("Error printing flight manifest: Invalid Flight Selected");
                        }
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("Invalid flight number");

                    }
                    break;
                default:
                    Console.WriteLine("Invalid Choice");
                    break;
            }
            Console.Write("Press any key to continue");
            Console.ReadKey(false);

            Console.Clear();
        }
    }


    void printFlightManifest(Flight flight)
    {
        Console.WriteLine("Flight manifest for flight " + flight.FlightNumber.ToString());
        Console.WriteLine("---------------------------------------");
        foreach (string customerID in flight.passengerList)
        {
            Console.WriteLine(customerID);
        }

        Console.WriteLine("---------------------------------------");
        Console.WriteLine("");
        Console.Write("Press any key to continue");

        Console.ReadKey(false);

        Console.Clear();
    }


}