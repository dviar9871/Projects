/**
 * Flight.cs
 * Holds all data members and methods needed to store a flight's information from the database 
 * and perform simple display and cost operations
*/
using System;
namespace Air3550;

class Flight
{

    public int FlightNumber;

    public string destination;

    public string origin;

    public DateTime dateTime;

    public bool completed;

    public int planeType;

    public int numPassengers;

    public double ticketRevenue;

    public double distance;

    public List<string> passengerList;
    private Database db = new Database();

    public Flight(int FlightNumber, string origin, string destination, DateTime dateTime, bool completed, int planeType, int numPassengers, double ticketCost, List<string> passengerList)
    {
        this.FlightNumber = FlightNumber;
        this.destination = destination;
        this.origin = origin;
        this.dateTime = dateTime;
        this.completed = completed;
        this.planeType = planeType;
        this.numPassengers = numPassengers;
        this.ticketRevenue = ticketCost;
        this.passengerList = passengerList;

        Airport? depart = db.getAirportInfo(origin), arrive = db.getAirportInfo(destination);
        distance = calculateDistance(depart, arrive);
    }

    //print a formatted version of a flight object to the console
    public void printFlight()
    {
        //display flight info with separators to be used with a header label
        Console.WriteLine(String.Format("| {0, 12} | {1, 6} >> {2,-11} | {3, 21} | {4, 10} | {5, 13} |", FlightNumber, origin, destination, dateTime, planeType, numPassengers));

    }

    //calculate the cost of a ticket using the formula: 50+.12(miles)+8(per segment)-.1(cost){IF LEAVING BEFORE 8 AND AFTER 7} -.2(cost)[IF LEAVING OR ARRIVING BETWEEN MIDNIGHt AN 5]
    public double calculateTicketCost()
    {
        double cost = 50;

        //calculate distance 
        //get lat and long for origin
        Airport? originInfo = db.getAirportInfo(origin);
        Airport? destinationInfo = db.getAirportInfo(destination);

        if (originInfo == null || destinationInfo == null)
        {
            return 0.0;
        }

        double dist = calculateDistance(originInfo, destinationInfo);

        cost += (.12) * (dist);

        int arrivalHour = calculateArrivalTime(dist).Hour;

        //dateTime objects use a 24-hour clock
        if (arrivalHour > 24)
        {
            arrivalHour -= 24;
        }

        if (dateTime.Hour < 8 || dateTime.Hour > 18)
        {
            cost = cost - (.1) * cost;
        }

        if ((dateTime.Hour < 5 && dateTime.Hour >= 0) || arrivalHour < 5 && arrivalHour >= 0)
        {
            cost = cost - (.2) * cost;
        }

        return Math.Round(cost, 2);
    }

    //calculates distance using trigonometry and the latitiude and longitude of the given origin and destination airports
    public double calculateDistance(Airport? originInfo, Airport? destinationInfo)
    {

        //if airport info is null, return 0
        if (originInfo == null || destinationInfo == null)
        {
            return 0.0;
        }

        //get the coordinate info of the given airports
        double lat1 = originInfo.latitude;
        double lat2 = destinationInfo.latitude;
        double lon1 = originInfo.longitude;
        double lon2 = destinationInfo.longitude;

        double rlat1 = Math.PI * lat1 / 180;
        double rlat2 = Math.PI * lat2 / 180;

        double theta = lon1 - lon2;
        double rtheta = Math.PI * theta / 180;

        double dist =
        Math.Sin(rlat1) * Math.Sin(rlat2) + Math.Cos(rlat1) *
        Math.Cos(rlat2) * Math.Cos(rtheta);
        dist = Math.Acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.1515;

        return dist;
    }

    //calculates arrival time based on distance
    public DateTime calculateArrivalTime(double distance)
    {
        //assumming flights fly at max speed of 500 mph
        double flightTime = distance / 500;
        flightTime += 60; //time to and from the terminal
        DateTime arrivalDate = new DateTime(dateTime.Year, dateTime.Month, dateTime.Day, dateTime.Hour, dateTime.Minute, dateTime.Second);
        arrivalDate.AddHours(flightTime);
        return arrivalDate;
    }
}