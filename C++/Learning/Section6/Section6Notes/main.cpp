#include <iostream>
#include <climits>

using namespace std;
// Global Variables are automatically intialized to zero
int global;
int main(){
    
    // Variables
    int age;
    double decimal;
    char letter {'L'};
    bool flag {false};
    //string word;
    // All valid ways to initialize a variable in c++
    int age1 = 21; // C-like initialization
    
    int age2 (21); // Constructor intitialization
    
    int age3 {21}; // C++11 list initialization syntax - give this a chance
    
    // There are signed and unsigned ints. Signed ints go from negative to positive. Unsigned ints go from 0 to positive
    
    long long big_number {7'000'000'000}; // C++ 14 standard allows tick marks in integers to separate the number and make it easier to read
    
    
    
    // The sizeof(); operator determines the amount of bytes in a variable or type
    
    cout << "Size of age 1 " <<sizeof age1<< endl;
    
    // The climits library has useful constants for different data types
    
    cout << "Minimum values" << endl;
    
    cout << "Char "<< CHAR_MIN << endl;
    
    cout << "Int "<< INT_MIN << endl;
    
    cout << "Short "<< SHRT_MIN << endl; // Works the same for max values
    
    // *******************************************************************************************************************************
    
    // Constant Variables - Once declared their value cannot change
    
    //Literal constants
    
    unsigned int unsigned_int_literal {12U};
    
    cout << sizeof (unsigned_int_literal) << endl;
    
    long long_int_literal {12L};
    
    // const keyword
    
    const int number {10};
    
    // #define - WE DON"T DO THIS ANYMORE makes debugging hard
    
    #define pi {3.14}
    
    
    
    
    
    
    
    
    
    return 0;
}