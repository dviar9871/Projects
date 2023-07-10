/**
 * Airport.cs
 * Contains the class Airport which holds all data pertaining to an Airport type
*/
using System;

namespace Air3550;

class Airport
{
    public String airportCode;

    public String cityName;

    public String state;

    public double longitude;

    public double latitude;

    public Airport(String airportCode, String cityName, String state, double longitude, double latitude)
    {
        this.airportCode = airportCode;
        this.cityName = cityName;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void printAirport()
    {
        //Print airport information in correct format
        Console.WriteLine(String.Format("| {0, 12} | {1, 19} | {2, 14} | {3, 13} | {4, 11} |", airportCode, cityName, state, longitude.ToString("0.###"), latitude.ToString("0.###")));
    }
}