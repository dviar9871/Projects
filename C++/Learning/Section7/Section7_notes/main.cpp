#include <iostream>
#include <vector> // Vectors need to be included and imported

using namespace std;
int main(){
    // Arrays - first index [0], last index [size-1]
    // Declaration and intialization
    //int arr [5] {0,1,2,3,4}; // fill in all values
    
    int arr [10] {0};
    arr[0] = 100;
    arr[(sizeof(arr) / sizeof(arr[0])) - 1] = 1000;
    
    
    int arr2 [5] {0,1}; // fill first 2 with 0 and 1
    
    int arr3 [5] {0}; // fill all to zero
    
    int arr4 [] {1,2,3}; // size is automatically calculated
    
    // Out of bounds indexes will give you random values in memory not an out of bounds error or just crash
    
    // Array name will give memory location
    
    // to get proper size of list use sizeof(arr) / sizeof(arr[0])
    for(int i = 0; i < sizeof(arr) / sizeof(arr[0]); i++){
        cout << arr[i] << endl;
        
    }
    cout << arr2[0] << endl;
    cout << arr3[0] << endl;
    cout << arr4 << endl;
    
    // Can use cin to change element without assignment operator
    
    cout << "Enter new int for array: ";
    cin >> arr3[0];
    cout << arr3[0] << endl;;
    
    //****************************************************
    // Multidimensional arrays
    
    int multi_arr [2][2] {{1,2},{3,4}};
    
    //****************************************************
    // Vectors - dynamic arrays part of the STL - exceptions will be thrown when out of bounds
    // Can have bounds checking and various methods like sort, reverse, etc...
    
    // Declaration 
    
    vector <int> vector1 (5); // empty
    
    vector <int> vector2 {1,2,3}; // fill in values
    
    vector <int> vector3 (5, 0); // makes all 5 entries second parameter
    
    // Can acsess two ways
    
    cout << vector2[0] << endl;
    
    cout << vector3.at(0) << endl;
    
    // Add to the end of vector
    
    vector2.push_back(4);
    
    // Getting size of vector
    
    cout << vector2.size() << endl;
    
    return 0;
}

