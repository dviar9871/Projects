#include <iostream>
#include <vector>
using namespace std;
int main(){
    char input = ' ';
    double sum;
    int min;
    int max;
    vector <int> list_of_numbers {};
    do{
        cout << "P - Print numbers" << endl << "A - Add a number" << endl << "M - Display mean of numbers" << endl << "S - Display smallest number" << endl; 
        cout << "L - Display Largest number" << endl << "Q - Quit" << endl;
        
        cin >> input;

        if (input != 'p' && input != 'P' && input != 'a' && input != 'A' && input != 'm' && input != 'M' && input != 's' && input != 'S' && input != 'l' && 
            input != 'L' && input != 'q' && input != 'Q')
        {
                cout << "Invalid selection. Try again" << endl;
                continue;
            
        }
        
        
        if(input == 'P' or input == 'p'){
            
            if(list_of_numbers.size() == 0){
                cout << "List is empty" << endl;
            }else{
                cout << "[ ";
                for(auto cursor: list_of_numbers){
                    cout << cursor << " " << endl;
                }
                cout << " ]";
            }
            
            
        }
        
        if(input == 'a' or input == 'A'){
            
            cout << "Enter a number to add" << endl;
            int val_to_add;
            cin >> val_to_add;
            list_of_numbers.push_back(val_to_add);
        }
        
        if(input == 'm' or input == 'M'){
            if(list_of_numbers.size() == 0){
                cout << "No data" << endl;
            }else{
                sum = 0;
                for(auto cursor: list_of_numbers){
                    sum += cursor;
                }
                sum = double(sum) / list_of_numbers.size();
                cout << "The mean is: " << sum << endl; 
                
            }
            
        }
        
        if(input == 's' or input == 'S'){
            if(list_of_numbers.size() == 0){
                cout << "The list empty" << endl;
            }else{
                min = list_of_numbers.at(0);
                for(auto cursor: list_of_numbers){
                    if (cursor < min){
                        min = cursor;
                    }
                }
                cout << "The min of the list is: " << min << endl;
            }
            
            
            
        }
        
        if(input == 'l' or input == 'L'){
            if(list_of_numbers.size() == 0){
                cout << "The list empty" << endl;
            }else{
                max = list_of_numbers.at(0);
                for(auto cursor: list_of_numbers){
                    if (cursor > max){
                        max = cursor;
                    }
                }
                cout << "The max of the list is: " << max << endl;
            }
            
            
            
        }
 
    }while(input != 'q' && input != 'Q');
    
    

    return 0;
}