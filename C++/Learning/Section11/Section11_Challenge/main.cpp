#include <iostream>
#include <vector>
using namespace std;

char print_menu();

char input_char();

void print_numbers(const vector<int>& list_of_numbers);

void add_numbers(vector<int>& list_of_numbers);

void mean_of_numbers(const vector<int>& list_of_numbers);

void max_of_numbers(const vector<int>& list_of_numbers);

void min_of_numbers(const vector<int>& list_of_numbers);

int main()
{
    char input;
    vector<int> list_of_numbers {};
    do {
        input = print_menu();

        if(input == 'P' or input == 'p') {
            print_numbers(list_of_numbers);
        } else if(input == 'a' or input == 'A') {
            add_numbers(list_of_numbers);
        } else if(input == 'm' or input == 'M') {
            mean_of_numbers(list_of_numbers);
        }
        else if(input == 's' or input == 'S') {
            min_of_numbers(list_of_numbers);
        }
        else if(input == 'l' or input == 'L') {
            max_of_numbers(list_of_numbers);
        }
    } while(input != 'q' && input != 'Q');

    return 0;
}

char print_menu()
{

    cout << "P - Print numbers" << endl
         << "A - Add a number" << endl
         << "M - Display mean of numbers" << endl
         << "S - Display smallest number" << endl;
    cout << "L - Display Largest number" << endl << "Q - Quit" << endl;
    char input = input_char();

    if(input != 'p' && input != 'P' && input != 'a' && input != 'A' && input != 'm' && input != 'M' && input != 's' &&
        input != 'S' && input != 'l' && input != 'L' && input != 'q' && input != 'Q') {
        cout << "Invalid selection. Try again" << endl;
    } else {

        return input;
    }
}

char input_char()
{
    char input;
    cin >> input;
    return input;
}

void print_numbers(const vector<int>& list_of_numbers)
{
    if(list_of_numbers.size() == 0) {
        cout << "List is empty" << endl;
    } else {
        cout << "[ ";
        for(auto cursor : list_of_numbers) {
            cout << cursor << " ";
        }
        cout << "]" << endl;
    }
}

void add_numbers(vector<int>& list_of_numbers)
{
    cout << "Enter a number to add" << endl;
    int val_to_add;
    cin >> val_to_add;
    list_of_numbers.push_back(val_to_add);
}

void mean_of_numbers(const vector<int>& list_of_numbers)
{
    int sum;
    if(list_of_numbers.size() == 0) {
        cout << "No data" << endl;
    } else {
        sum = 0;
        for(auto cursor : list_of_numbers) {
            sum += cursor;
        }
        sum = double(sum) / list_of_numbers.size();
        cout << "The mean is: " << sum << endl;
    }
}

void max_of_numbers(const vector<int>& list_of_numbers)
{
    int max;
    if(list_of_numbers.size() == 0) {
        cout << "The list empty" << endl;
    } else {
        max = list_of_numbers.at(0);
        for(auto cursor : list_of_numbers) {
            if(cursor > max) {
                max = cursor;
            }
        }
        cout << "The max of the list is: " << max << endl;
    }
}

void min_of_numbers(const vector<int>& list_of_numbers)
{
    int min;
    if(list_of_numbers.size() == 0) {
        cout << "The list empty" << endl;
    } else {
        min = list_of_numbers.at(0);
        for(auto cursor : list_of_numbers) {
            if(cursor < min) {
                min = cursor;
            }
        }
        cout << "The min of the list is: " << min << endl;
    }
}