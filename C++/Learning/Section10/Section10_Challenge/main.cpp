#include <iostream>
#include <string>
using namespace std;
int main(){

    
    string alphabet = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    string key = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
    
    string message; 
    string encrypted_message;
    string decrypted_message;
    cout << "Type in a message to be encrpted: ";
    getline(cin, message);
    size_t index_of_letter;
    for(auto letter: message){
        index_of_letter = alphabet.find(letter);
        
        encrypted_message += key[index_of_letter];
        
    }
    
    
    cout << encrypted_message << endl;
    
    for(auto letter: encrypted_message){
        index_of_letter = key.find(letter);
        
        decrypted_message += alphabet[index_of_letter];
        
    }
    cout << decrypted_message << endl;
    
    return 0;
}