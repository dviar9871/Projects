


#include <unordered_map>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <stack>

using namespace std;

vector<int> DFS(int startV,unordered_map<int, vector<int>> table) {
	stack<int> lifo;
	vector<int> visited_set;
	int currentV = startV;
	bool key = 0;
	lifo.push(startV);

	while (!(lifo.empty())) {
		currentV = lifo.top();
		key = 0;
		//cout << lifo.size() << endl;
		lifo.pop();

		for (int i = 0; i < visited_set.size(); i++) {
			if (visited_set.at(i) == currentV) {
				key = 1;
				break;
			}
		}

		if (!key) {
			visited_set.push_back(currentV);
			for (int j = 0; j < table[currentV].size(); j++)
				lifo.push(table[currentV][j]);
		}

	}

	return visited_set;
}

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


		last_user = user;
		

	}
	int vert_over_hundred = 0;
	int total_edges = 0;
	
	for (int i = 0; i < keys.size(); i++) {
		if (outer_list.at(keys.at(i)).size() > 100)
			vert_over_hundred++;
		total_edges += outer_list.at(keys.at(i)).size();

	}
	file.close();
	cout << endl << "Average degree: " << (total_edges / keys.size()) << endl;
	cout << "Number of vertices over 100: " << vert_over_hundred << endl;
	cout << "DFS: This may take a few minutes to run on the full data set" << endl;
	vector<int> search = DFS(6,outer_list);
	
	cout << "DFS found path size: " << search.size() << endl;
	if (search.size() == outer_list.size())
		cout << "Connected" << endl;
	else
		cout << "Unconnected" << endl;
	return 0;
}




