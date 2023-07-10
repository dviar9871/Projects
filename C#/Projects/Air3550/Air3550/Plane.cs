/**
 * Plane.cs
 * Contains the class Plane which holds all data pertaining to a Plane type
*/
using System;

namespace Air3550;

class Plane
{

    public int planeType;

    public int capacity;

    public int range;

    public Plane(int PlaneType, int Capacity, int Range)
    {
        this.planeType = PlaneType;
        this.capacity = Capacity;
        this.range = Range;
    }
}