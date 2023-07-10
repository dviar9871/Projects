#include <iostream>
using namespace std;

// The program will calculate the area of a room in square feet
int main(){
    
    cout << "Enter the width of the room: ";
    double width {0};
    
    cin >> width;
    
    cout << "Enter the length of the room: ";
        
    double length {0};
    
    cin >> length;
    
    cout << "The area of the room is " << length * width << " square feet" << endl; 
    
    return 0;
}