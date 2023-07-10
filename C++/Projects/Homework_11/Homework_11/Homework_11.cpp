


#include <unordered_map>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <stack>

using namespace std;



int main() {
	ifstream file;
	file.open("facebook-links.txt");
	if (!file.is_open()) {
		cout << "Failed to open file" << endl;
		return 1;
	}
	int user;
	int connection;
	int last_user = -1;
	string filler_word;
	unordered_map<int, vector<int>> outer_list;
	vector<int> keys;
	std::vector<int>::iterator it;
	int count = 0;
	while (!file.eof()) {
		/*
		if (count >= 200)
			break;
			*/
		file >> user;
		if (last_user != user)
			keys.push_back(user);
		file >> connection;
		it = std::find(outer_list[user].begin(), outer_list[user].end(), connection);
		if (it != outer_list[user].end()) {
			//cout << "duplicate" << endl;
			file >> filler_word;
			last_user = user;
			continue;
		}
		else {
			outer_list[user].push_back(connection);
			outer_list[connection].push_back(user);

			file >> filler_word;
		}

		count++;
		last_user = user;


	}
	/*
	for (int key : keys) {
		cout << key << ": ";
		for (int value : outer_list[key])
			cout << value << ", ";
		cout << endl;
	}
	*/
	double average_friend = 0.0;
	vector <double> average_friend_list;
	for (int key : keys) {
		average_friend = 0.0;
		for (int value : outer_list[key]) {
			average_friend += outer_list[value].size();
		}
		average_friend /= double(outer_list[key].size());
		average_friend_list.push_back(average_friend);
	}
	double total_friend_average = 0;
	for (double average_friend_value : average_friend_list)
		total_friend_average += average_friend_value;
		//cout << average_friend_value << ", ";
	cout << "Average Friend degree: "  << total_friend_average / average_friend_list.size() << endl;
	return 0;
}




