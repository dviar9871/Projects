#include <iostream>
using namespace std;

/* Franks carpet cleaning service charges $30 per room, sales tax is 6%, estimates are valid for 30 days
 * 
 * Prompt user for the number of rooms they would like cleaned and provide an estimate
 * 
 */



int main(){
    const double price_per_room {30};
    
    const float sales_tax {.06};
    
    const int estimate_expiry {30};
    
    cout << "Welcome to Frank's carpet cleaning service. How many rooms do you want cleaned? " << endl;
    
    int rooms_to_be_cleaned {0};
    
    cin >> rooms_to_be_cleaned;
    
    cout << "Your estimate is " << (rooms_to_be_cleaned * price_per_room) + (rooms_to_be_cleaned * price_per_room * sales_tax )<< " This is valid for "<< estimate_expiry<<" days" << endl;
    
    
    
    
    
    
    cout << endl;
    return 0;
}