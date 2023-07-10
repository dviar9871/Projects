#include <iostream>
#include <string>
#include <vector>
// Pointers - a variable that stores the address of another variable or function

// Pointers can be used to access data that is out of scope of the function, opperate on arrays, and to allocate memory
// dynamically from the heap or free store
using namespace std;



void double_data(int *intptr);



int main()
{   
    
    
    int* int_ptr = nullptr; //  intialized to prevent garbage data and always intialize pointers
    // double* double_ptr = nullptr;
    // char *char_ptr = nullptr;
    // string *string_ptr = nullptr;

    // The & can be used to give the address of a variable and the type of pointer has to match the type of variable

    // Size of variable has nothing to do with the size of the pointer address

    // Getting a pointer for num variable
    int num = 10;
    int_ptr = &num;
    cout << num << endl;
    // Referencing num indirectly using the pointer with the dereferencing operator *variable_name
    *int_ptr = 20;
    cout << num << endl;

    // Dynamically assigning data from heap
    double* double_ptr = nullptr;
    double_ptr = new double;     // allocate double onto heap
    cout << double_ptr << endl;  // points to address in heap
    cout << *double_ptr << endl; // dereference it to access pointer
    *double_ptr = 100.0;
    cout << *double_ptr << endl; // if you loose this pointer, you loose access to the allocated data and you will have
                                 // a memory leak. When done with this data deallocate it

    delete double_ptr;

    // Allocate memory for array
    // If pointer is lost cannot access this array and you will cause a memory leak
    int* array_ptr = nullptr;
    size_t size;

    cout << "Enter size of array: ";
    cin >> size;
    // address of first element is stored in pointer
    array_ptr = new int[size];
    // deallocate memory
    delete[] array_ptr;

    // You can access arrays directly by dereferencing them

    int array[] { 1, 2, 3 };

    cout << array << endl;          // prints address (pointer)
    cout << *array << endl << endl; // prints first element in array cause we dereferenced the pointer

    // we can also use array subscripting on pointers

    array_ptr = array; // No need to dereference the array because it gives pointer by default

    cout << array_ptr[0] << endl;
    cout << array_ptr[1] << endl;
    cout << array_ptr[2] << endl << endl;

    // Can also access them using pointer arithmatic

    cout << *(array_ptr) << endl;
    cout << *(array_ptr + 1) << endl;
    cout << *(array_ptr + 2) << endl;

    // Can also do this with array names cause array is pointer to first element in memory

    cout << *(array) << endl;
    cout << *(array + 1) << endl;
    cout << *(array + 2) << endl << endl;
    
    
    // Pointer arithmatic only makes sense with raw arrays and is machine specific cause adding and subtracting from them is based on sizes of ints
    array_ptr++; // can be used to look at next array element same with --
    array_ptr--;
    // If you subtract two pointers that point to the same place you get the difference in space between the two spots between the pointers
    while(*array_ptr != 3){
        cout << *(array_ptr) << endl;
        array_ptr++;
    }
    
    int high_score = 100;
    int low_score = 20;
    const int *ptr = &high_score; // I can change what the pointer is pointing to but not the value that that pointer is pointing to
    ptr = &low_score;
    
    // To make the pointer constant and not be able to change where it is pointing
    int *const ptr2 = nullptr;
    
    // Make it so the pointer and what it is pointing to cannot be changed
    
    const int *const ptr3 = nullptr;
    
    // Function call with pointer use & to give variable address
    
    int to_be_doubled = 2;
    
    double_data(&to_be_doubled);
    
    
    // References - alias for a variable, must be intialized when declared, cannot be null
    // Without refrence
    vector<string> stooges = {"Bob","Glob","Slob"};
    
    for(auto str: stooges){ // Will not change because we are referencing a copy of vector
        str = "Funny";
    }
    
    for(auto str: stooges){
        cout << str << endl;
    }
    
    
    for(auto &str: stooges){ // Will change because we are referencing the actual vector address with & operator
        str = "Funny";
    }
    
    for(auto str: stooges){
        cout << str << endl;
    }
    
}







void double_data(int *intptr){
    *intptr *= 2;
}