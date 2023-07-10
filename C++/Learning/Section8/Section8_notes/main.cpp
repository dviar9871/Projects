#include <iostream>
using namespace std;
int main(){
    // increment operators
    
    // prefix ++num - incremented before it is used or assigned
    // suffix num++ - incremented after it is used or assigned
    
    // Preincrement
    int counter {10};
    int result {0};
    
    cout << "Counter: " << counter << endl;
    
    result = ++counter; // will be 11 cause it is incremented before it is assigned
    
    cout << "Result: " << result << endl; // 11
    cout << "Counter: " << counter << endl; // 11
    
    // Post Increment
    counter = 10;
    result = 0;
    
    cout << "Counter: " << counter << endl;
    
    result = counter++; // will be 10 cause it is incremented after it is assigned
    
    cout << "Result: " << result << endl; // 10
    cout << "Counter: " << counter << endl; // 11
    //***********************************************************************
    
    // Coersion is when you convert from one type to another - int to string
    // Promotion is when you convert a lower type into a higher type - (short, char to int) always converted to int, int to long
    // Demotion is when you convert from a higher type to a lower type
    
    // When doing operations with doubles, ints will be promoted to doubles for that instance EX: 2 * 5.2, The 2 becomes 2.0
    // Assiging a double or a float type number to an int will demote it to an int
    
    // Casting
    
    // Cast Int to Double 
    int num1 {28};
    int num2 {5};
    double average {0.0};
    
    average = num1 / num2; // will demote to int because cause we are dividing with 2 ints

    average = static_cast<double>(num1)/num2; // casts result to double instead of int, any type can be put inbetween the carrots to cast (numerical only probably)
    cout << average << endl; // Only one needs to be casted to a double to promote the other num to an int
    
    average = (double)(num1)/num2; // old style DON'T USE doesn't check to make sure it can be converted the other cast does
    cout << average << endl;
     
    // ************************************************************************************************************************
    
    // Equality
    
    bool test1 = 0 == 0; // equal
    bool test2 = 1 != 0; // not equal
    
    // Try not to use == for doubles that are really close because it is an approx. and can lead to false true values
    
    // Logical Operators - can use symbol or keyword (use symbol)
    
    // And - && or and
    // Not - ! or not
    // Or - || or or
    
    
    
    
    return 0;
}