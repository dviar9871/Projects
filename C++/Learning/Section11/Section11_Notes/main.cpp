#include <iostream>
#include <cmath> // c++ math library
#include <ctime> // time library
#include <cstdlib> // rand included
using namespace std;
// Functions
// generic typing
template <typename T>

T add_num(T x, T y){
    return x + y;
}


// Function prototyping - not good for big projects
int function_name(); // knows that it will be named later

// Default arguements - will give a specified arguement unless told otherwise

int func1(int x, int y = 10);

int overloading(int x );
double overloading(double x);
string test_of_proto_rules(string arr [], size_t size);
// int* is an int array
int* array_pass(int arr [], size_t size); // When passing an array it is a pointer to the first location and it loses it's size info so size needs to be passed

int pass_by_reference(int &num); // & - makes pass by reference applies to primitives and objects

/* Static local variables remember there value for the lifetime of the program but is only visible in scope. Initialized just once
 */



int main(){
    
    cout << function_name() << endl;
    
    cout << func1(10) << " using default param" << endl;
    cout << func1(10,5) << " not using default param" << endl;
    
    return 0;
}


int function_name(){ // can be called in any order
    return 0;
}


int func1(int x, int y ){
    return x + y;
}

int overloading(int x ){
    return x;
}
double overloading(double x){
    return x;
}

int* array_pass(int arr [], size_t size){ // to prevent array from being changed have the array be called as const
    return arr;
}

int pass_by_reference(int &num){
    
    return num;
}

string test_of_proto_rules(string arr [], size_t size)
{
    
    return "";
}

void static_example(){
    static int num = 100; // will be 100 for the first call but will remember the value fro subsequent calls so next time it will be 110
    num += 10;
}

#include <iostream>
#include <iomanip>
using namespace std;

int function_activation_count {0};

//----DO NOT MODIFY THE CODE ABOVE THIS LINE----
//----WRITE THE FUNCTION PROTOTYPE BELOW THIS LINE----

double a_penny_doubled_everyday(int, double start_amount = .01);//----WRITE THE FUNCTION PARAMETER LIST WITHIN THE PARENTHESES

//----WRITE THE FUNCTION PROTOTYPE ABOVE THIS LINE----
//----DO NOT MODIFY THE CODE BELOW THIS LINE----

void amount_accumulated() {
    
//----DO NOT MODIFY THE CODE ABOVE THIS LINE----
//----WRITE THE FUNCTION CALL BELOW THIS LINE----

    double total_amount = a_penny_doubled_everyday(25);
    
//----WRITE THE FUNCTION CALL ABOVE THIS LINE----
//----DO NOT MODIFY THE CODE BELOW THIS LINE----
    //cout &lt;&lt;  "If I start with a penny and doubled it every day for 25 days, I will have $" &lt;&lt; setprecision(10) &lt;&lt; total_amount;
}

//----DO NOT MODIFY THE CODE ABOVE THIS LINE----
//----WRITE THE FUNCTION DEFINITION BELOW THIS LINE----

double a_penny_doubled_everyday(int n, double start_amount) {//----WRITE THE FUNCTION PARAMETER LIST WITHIN THE PARENTHESES
    function_activation_count++;
    //----DO NOT MODIFY THE CODE ABOVE THIS LINE----
    //----WRITE THE FUNCTION BODY BELOW THIS LINE----
    if(n <= 1){
        return start_amount;
    }
    
    return a_penny_doubled_everyday(n - 1, start_amount * 2);
    
    
    //----WRITE THE FUNCTION BODY ABOVE THIS LINE----
    //DO NOT MODIFY THE CODE BELOW THIS LINE----
}

int test_function_activation_count() {
    return function_activation_count;
}