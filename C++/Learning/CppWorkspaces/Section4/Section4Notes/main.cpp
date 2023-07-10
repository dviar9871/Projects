#include <iostream>

//using namespace std;  allows the programmer to not have to type std:: before using and std functions Ex: cout, cin, endl 

using std::cout; // This allows to call specific functions from the namespace rather than doing all of them to reduce mixups on larger programs
using std::cin;
using std::endl;


int main(){
    /*The main() function acts as the sort of public static void main(String [] args) for c++. If the main returns 0 it is a successful execution
     * and it is the starting point of program execution.
     * There are two versions of main()
     * 
     * Version One:
     * int main() 
     * {
     * // code
     * return 0 
     * }
     * 
     * program.exe
     * 
     * Version Two: - common for command line operations
     * int main(int argc, char *argv[])
     * {
     * //code
     * return 0
     * }
     * 
     * program.exe argument1 arguement2
     * /
    
    
    
    /* << - This is called a chevron - stream insertion operator. This inserts it into the stream and prints it into the console.
     * Console output or input cannot be done without including the iostream (output stream)
     * *** Can be chained Ex: cout << "Number is: " << number;
     * 
     * >> - This is the string extraction operator. It takes something from the console and stores it to a variable(input streams) (Input or Scanner Class)
     * It will give a data type based on the variable it is going into. It will fail if it cannot determine the value. *** Spaces will be ignored
     * *** Can be chained cin >> data1 >> data2;
     * 
     * :: - This is the scope resolution operator which resolves which namespace we are using for a function.  std::cout vs otherprogrammer::cout
     * 
     * cout - Standard output stream, console
     * cin - Standard input stream, keyboard
     */
   
    
    /* Anything that starts with a pound is taken in by the C++ preprocessor. Before this it strips code of comments and replaces it with a space. 
     * Then it looks for preprocessor directors which is anything that begins with a pound sign (#include, #if, etc...). Replaces all directors with 
     * files. Include statements adds things from the STL or other libraries. If statements can be used to check OS to load specific file libraries.
     */
     
    /* Namespaces are used to help reduce the odd programmers name functions the same thing. Two programers can name something cout but the compiler gets
     * confused on which one to use if there is no namespace ie std::cout vs otherprogrammer::cout. This allows the compiler to tell the difference. 
     */
     
     /*
      */
    
    
  
    return 0;
}