#include <iostream>
#include <cctype> // functions for testing characters and converting character case
#include <cstdlib> // functions to convert c-style strings into other types like int, float, etc...
#include <cstring> // gives cstyle string functions
#include <string> // Needed to use strings and part of the std namespace
using namespace std;

int main(){
   // CCType has a lot of isSomething boolean checks for characters
   /*
    isalnum Check if character is alphanumeric (function )

    isalpha Check if character is alphabetic (function )

    isblank Check if character is blank (function )

    iscntrl Check if character is a control character (function )

    isdigit Check if character is decimal digit (function )

    isgraph Check if character has graphical representation (function )

    islower Check if character is lowercase letter (function )

    isprint Check if character is printable (function )

    ispunct Check if character is a punctuation character (function )

    isspace Check if character is a white-space (function )

    isupper Check if character is uppercase letter (function )

    isxdigit Check if character is hexadecimal digit (function )
     * 
     * tolower Convert uppercase letter to lowercase (function )
     *
     *   toupper Convert lowercase letter to uppercase (function )
     * 
     */
    
    /*          C-Style Strings - will be full of garbage if they are not initialized
     * Sequence of characters that is contigous in memory and implemented as an array of char. They are terminated by null char value put at the end "C++ is fun\0"
     */
  
    
   
    char my_name [] {"Frank"}; // 5 chars but of length 6 because null char value
    // my_name [5] = 'y'; will get rid of null char value so it is a problem
    char my_name_2 [20] {"Frank"};
    my_name_2[5] = 'y'; // this is ok cause we allocated more space for the string
    
    // char my_name [10]; my_name = "daniel"; <---- will not work causes compiler error
    // strpy(my_name, "Daniel"); <---- This will copy the c-style string into the char array
    
    // To Concatenate 
    strcat(my_name_2, " the man");
    
    // Get length
    cout << strlen(my_name) << endl;
    
    // size_t will return a numerical value depending on the system
    char name [50] {};
    cout << "Put in name" << endl;
    cin.getline(name, 50); // reads 50 char and assigns it given c-style string
    
    
    // C++ Style strings
    // contigous in memory, dynamic size, works with I/O streams, can be converted to c-style strings, safer
    string string0; //empty no garbage values
    string string1 {"Hi I am a blob"};
    string string2 {string1};
    string string3 {"Daniel", 3}; // Dan - cuts off after specified length
    string string4 {"Daniel", 0 , 2}; // Da - works like substring
    string string5 (3, 'X');  // XXX
    
    // Can use [] and .at in c++ strings
    cout << string1[0] << " or " << string1.at(0) << endl;
    // Substring method exists
    cout << string1.substr(0,4) << endl;
    // .find() returns index of string -  can start at a later index to prevent known location issues. If string is not found, returns string::npos
    
    cout << string1.find("am") << endl;
    // there is also .erase and .length
    
    // When using strings with cin operator, it will stop when it sees white space so 
    cout << "type in a name" << endl;
    getline(cin, string0); // will allow anything the user types to be included
    
    // concatenation
    // string string6 {strcat("Hi ", "There") + string1};
    //cout << string6 << endl;
    
    // Can loop through strings cause they are char arrays
      string str = "abcdef";
    str = str.insert(3, " ");
    cout<< str << endl;
    
    return 0;
}