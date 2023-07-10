#include <iostream>

int main()
{
    int favorite_number;
    
    std::cout << "Enter your favorite number between 1 - 100: "; // Console print using std::cout<< ""; without endl acts like print (no ln)
    
    std::cin >> favorite_number; // Take in values from console using cin
    std::cout << "Amazing that is my favorite number too" << std::endl; // Use endl to push to next line (println)
    
    return 0; 
}