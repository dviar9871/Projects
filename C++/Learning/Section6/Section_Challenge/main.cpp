#include <iostream>
using namespace std;
int main(){
    const double small_room_cost {25};
    
    const double large_room_cost {35};
    
    const float sales_tax {.06};
    
    const int estimate_days_of_validity {30};
    
    cout << "How many large rooms would you like cleaned? ";
    
    int number_of_large_rooms {0};
    
    cin >> number_of_large_rooms;
    
    cout << "How many small rooms would you like cleaned? ";
    
    int number_of_small_rooms {0};
    
    cin >> number_of_small_rooms;
    
    double cost {small_room_cost * number_of_small_rooms + large_room_cost * number_of_large_rooms};
    
    double tax {cost * sales_tax};
    
    double total {tax + cost};
    
    cout << "The estimate for the carpet cleaning service: \nNumber of large rooms: " << number_of_large_rooms << " \nNumber of small rooms: " << number_of_small_rooms<< endl;
    
    cout << "The cost of a large room: " << large_room_cost << "\nThe cost of a small room" << small_room_cost << "\nCost: " << cost << endl;
    
    cout << "Sales tax: " << tax << "\n**********************\nTotal cost: " << total << "\nThis estimate is valid for " << estimate_days_of_validity << " days" << endl;
    
    
    
    
    
    
    
    
    
    return 0;
}