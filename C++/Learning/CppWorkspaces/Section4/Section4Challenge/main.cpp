#include <iostream>

int main(){
    int favorite_number;
    std::cout <<"What is your favorite number? ";
    
    std::cin >> favorite_number;
    std::cout << "Amazing that is my favorite number too!" << std::endl;
        
    std::cout << "No really! " << favorite_number << " is my favorite number too !" << std::endl; // Can use carrots to contencate?
 
    
    
    /*
    std::cout << "No really " ;  Less Efficient
    std::cout << favorite_number;
    std::cout << " is my favorite number too!" << std::endl;
    */
    
    return 0;
}