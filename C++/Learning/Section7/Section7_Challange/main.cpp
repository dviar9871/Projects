#include <iostream>
#include <vector>

using namespace std;
int main(){
    vector <int> vector1;
    vector <int> vector2;
    
    vector1.push_back(10);
    vector1.push_back(20);
    
    cout << "Vector1 spot 0: "<< vector1.at(0) << " and vector1 spot 1: " << vector1.at(1) << " and size of vector1: " << vector1.size() <<endl;
    
    vector2.push_back(100);
    vector2.push_back(200);
    
    cout << "Vector2 spot 0: "<< vector2.at(0) << " and vector2 spot 1: " << vector2.at(1) << " and size of vector2: " << vector2.size() <<endl;
    
    vector <vector<int>> vector_2d {};
    
    vector_2d.push_back(vector1);
    vector_2d.push_back(vector2);
    
    cout << "Vector_2d spot 0: " << vector_2d.at(0).at(0) <<","<< vector_2d.at(0).at(1) << " and " << "Vector_2d spot 0: " << vector_2d.at(1).at(0) <<","<< vector_2d.at(1).at(1)<<endl;
    
    vector1.at(0) = 1000;
    cout << "Vector1 spot 0: "<< vector1.at(0) << " and vector1 spot 1: " << vector1.at(1) << " and size of vector1: " << vector1.size() <<endl;
    
    
    cout << "Vector_2d spot 0: " << vector_2d.at(0).at(0) <<","<< vector_2d.at(0).at(1) << " and " << "Vector_2d spot 0: " << vector_2d.at(1).at(0) <<","<< vector_2d.at(1).at(1)<<endl;
    
    
    
    
    
    
    return 0;
}