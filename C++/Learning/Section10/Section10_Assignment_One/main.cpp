#include <iostream>
#include <string>
using namespace std;

string reverse_string(string string_to_be_reversed){
    string reversed_string {};
    
    for(int i = string_to_be_reversed.length() - 1; i >= 0; i--){
        reversed_string += string_to_be_reversed[i]; 
    }
    return reversed_string;
}

string whitespace_add(int length){
    string whitespace {};
    for(int i = 0; i < length; i++){
        whitespace += " ";
    }
        
    return whitespace;
}

int main(){
  
    cout << "Please enter the string you want to pascal" << endl;
    
    string origin_message {};
    cin >> origin_message;
   
    for(int i = 1; i < origin_message.length(); ++i){
       
        cout << whitespace_add(origin_message.length() - origin_message.substr(0,i).length()) << origin_message.substr(0,i) << reverse_string(origin_message.substr(0,i - 1)) << endl;
        
        
        
    }
    
    
    
    
    return 0;
}