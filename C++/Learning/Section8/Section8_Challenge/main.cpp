#include <iostream>
using namespace std;
// Change finder
int main(){
   /* Using the division operator I can take each input and divide it by that respective amount and subtract the value from the total
    * will round down because we are using ints
    *
    *  Ex: 30 cents
    * 
    *   30 / 100 = 0 and 30 - 0 = 0 or 30 / 100 and 30 % 0
    *   30 / 25 = 1 and 30 - 25 = 5 One quarter
    *   5 / 10 = 0 and 5 - 10 = 0
    *   5 / 5 = 1 and 5 - 5 = 0 one nickel
    * 
    * 
    */
    
    cout << "Enter an amount of money in cents you would like to be broken into change: ";
    
    int starting_amount = 0; 
    
    cin >> starting_amount;
    
    int dollars = 0;
    
    int quarters = 0;
    
    int dimes = 0;
    
    int nickels = 0; 
    
    int pennies = 0;
    
    dollars = starting_amount / 100; 
    
    starting_amount -= dollars * 100;
    
    quarters = starting_amount / 25;
    
    starting_amount -= quarters * 25;
    
    dimes = starting_amount / 10;
    
    starting_amount -= dimes * 10;
    
    nickels = starting_amount / 5;
    
    starting_amount -= nickels * 5;
    
    pennies = starting_amount / 1;
    
    starting_amount -= pennies * 1;
    
    cout << "Dollars: " << dollars << endl;
    
    cout << "Quarters: " << quarters << endl;
    
    cout << "Dimes: " << dimes << endl;
    
    cout << "Nickels " << nickels << endl;
    
    cout << "Pennies " << pennies << endl;
    
    
    
    
    
    return 0;
}