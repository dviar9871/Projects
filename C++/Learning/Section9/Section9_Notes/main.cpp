#include <iostream>
#include <vector>
#include <iomanip> // Allows programmer to manipulate input outputs
using namespace std;
int main(){
    // Conditional
    if(1){ // 0 and 1 are true or false in c++ true being 1 and 0 being false
        cout << "True !" << endl;

    }
    if (0){
        
    }else{
        cout << "False !" << endl;
    }
    
    // Switch statement **************************************************************************8
    int switch_selection = 0;
    switch(switch_selection){
        case 0: cout << "0" << endl;; break;
        case 1: cout << "1" << endl;; break;
        default: cout << "All cases passed" << endl; break;
    
    }
    
    /*Enum, which is also known as enumeration, is a user-defined data type that enables you to create a new data type that has a fixed range of possible values, 
     * and the variable can select one value from the set of values. For example, suppose you are the owner of an ice cream shop, 
     * and you sell a limited range of ice cream flavors. So you want the customers to select only from that collection of ice creams in your shop. 
     * This becomes an enumeration with ice cream as the name of enumeration and different flavors of ice creams as its elements.
     */
    
    enum Direction{   
        left,right,up,down
    }; // <---------------------------
    
    Direction heading {left};
    //Direction heading {right};
    //Direction heading {up};
    //Direction heading {down};
    
    switch(heading){
        case left: cout << "Left"; break;
        case right: cout << "Right"; break;
        case up: cout << "Up"; break;
        case down: cout << "Down"; break;
    }
    cout << endl;
    // Conditional operator *********************************************************************************
    
    int result = (10 > 5) ? 10 : 5; 
    // translation
    if (10 > 5){
        result = 10;
    }else{
        result = 5;
    }
    int list [] {1,2,3};
    
    for(int x: list){  // for each loop
        cout << x << endl;
    }
    // Can use auto to figure out the type
    for(auto x: list){
        cout << x << endl;
    }
    
    // Can use range based for loop to iterate over a string cause it is a collection
    
    for(auto letter: "Daniel"){
        cout << letter << endl;
        
    }
    double num_to_be_manip = 9.5595;
    cout << "Before Manipulation: " << num_to_be_manip << endl;
    cout << fixed << setprecision(1); // rounds decimal place to 1 
    cout << "After Manipulation: " <<  num_to_be_manip << endl;
    // 2d array traversal
    int grid [5][4] {};
    
    for(int i = 0; i < 5; i++){
        
        for(int j = 0; j < 4; j++){
            cout << grid[i][j] << " ";
        }
        cout << endl;
    }
    
    vector <vector<int>> vec_2d {
        {1,2,3},
        {4,5,6,7,8},
        {9,10,11,12,13,14}
        };
    // Using range based for loop with auto to loop through different sized 2d vectors
    for(auto vect: vec_2d){
        for(auto val: vect){
            cout << val << " ";
        }
        cout << endl; 
    }
    
    
    return 0;
}