/**
 * Accountant.cs
 * Contains the class Accountant which is responsible for managing the accountant's UI and data access
*/
using System;
using ClosedXML.Excel;

namespace Air3550;


//Class that is responsible for managing the accountant's UI and data access
class Accountant
{

    Database db;

    public Accountant()
    {

        //init database
        db = new Database();
    }

    //Method that wil run after accountant logs in, displays all options 
    public void AccountantPage()
    {

        string? choice = "1";
        while (choice != "0")
        {
            //clear console then display options
            Console.Clear();
            Console.WriteLine("------------------------");
            Console.WriteLine("  Accountant Home Page  ");
            Console.WriteLine("------------------------");
            Console.WriteLine("1- Create Finance Report");
            Console.WriteLine("0- Exit");
            choice = Console.ReadLine();

            switch (choice)
            {
                case "0":
                    //Will exit at the end of loop
                    break;
                case "1":
                    Console.Clear();
                    createFinanceReport();
                    Console.WriteLine("Finance report complete");
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


    //calculates the percent capacity of a given plane and number of passengers
    double calculateCapacity(int numPassengers, int planeType)
    {
        //get plane information
        Plane? plane = db.getPlaneInfo(planeType);
        
        //if a plane was not found from the given inputs, return 0
        if(plane == null) 
        {
            return 0.0;
        }

        //return the capacity of the plane as a percentage
        return (numPassengers / (double)plane.capacity) * 100;
    }

    //will create a csv file that holds income info from all flights 
    public void createFinanceReport()
    {
        Console.WriteLine("Creating finance report...");
        //create and export a csv file of all finance info
        XLWorkbook wb = new XLWorkbook();
        var ws = wb.AddWorksheet("Finance Report");
        wb.ColumnWidth = 15;

        ws.Cell("A1").SetValue("Flight number");
        ws.Cell("B1").SetValue("Percent Capacity");
        ws.Cell("C1").SetValue("Income");

        //store summary info
        double totalIncome = 0;
        List<int> flightNums = new List<int>();
        List<double> capacities = new List<double>();
        List<double> incomes = new List<double>();

        //create csv file and headers for tables

        //get info for all flights
        Flight[] flightList = db.getFullFlightList();


        //get information from all flights and add to lists
        foreach (Flight flight in flightList)
        {
            totalIncome += flight.ticketRevenue;
            flightNums.Add(flight.FlightNumber);
            capacities.Add(calculateCapacity(flight.numPassengers, flight.planeType));
            incomes.Add(flight.ticketRevenue);
        }

        //insert summary data into cells
        ws.Cell("A2").InsertData(flightNums);
        ws.Cell("B2").InsertData(capacities);
        ws.Cell("C2").InsertData(incomes);

        ws.Cell("E1").SetValue("Total Income");
        ws.Cell("E2").SetValue(totalIncome);

        //export file to same file path as executable
        wb.SaveAs("finance report.xlsx");

        Console.WriteLine("Finance report successfully created.");
    }
}
