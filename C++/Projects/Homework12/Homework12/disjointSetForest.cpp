// disjointSetForest.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
#include <vector>
#include<iostream>
#include <chrono>
using std::cout;
using std::endl;
using std::vector;
using namespace std::chrono;
using Duration = std::chrono::microseconds;


class Node {
public:
	int value = 0;
	int rank = 0;
	Node* parent = nullptr;
	Node(int value) { this->value = value; }
};

// Create a set given a single element
void makeSet(Node* element);
// Return root for set given an element, using path compression if
// pathCompression is true
Node* findSet(Node* element, bool pathCompression);
// Take the union of two sets given an element from each set. Both union by
// rank and path compression heuristics can be used or not used.
void unionSet(Node* element1, Node* element2, bool unionByRank,
	bool pathCompression);

void printAllElements(std::vector<Node*> elements);

int main()
{
	/*
	bool unionByRank = true, pathCompression = true;

	// Test for correctness of functionality
	int numElements = 5;
	std::vector<Node*> elements(numElements);
	// Create vector of pointers to elements
	for (int i = 0; i < numElements; i++)
		elements.at(i) = new Node(i);

	// Make all singleton sets
	for (int i = 0; i < numElements; i++) {
		makeSet(elements.at(i));
	}
	printAllElements(elements);

	// Test a few unions ending up with a single set of all vertices
	unionSet(elements.at(0), elements.at(1), unionByRank, pathCompression);
	printAllElements(elements);
	unionSet(elements.at(0), elements.at(2), unionByRank, pathCompression);
	printAllElements(elements);
	unionSet(elements.at(3), elements.at(4), unionByRank, pathCompression);
	printAllElements(elements);
	unionSet(elements.at(0), elements.at(4), unionByRank, pathCompression);
	printAllElements(elements);


	*/
	// Test for correctness of functionality
	
	vector<Node*> elements_worst;
	int worst_time_individual = 0;
	int best_time_individual = 0;
	vector<int> worst_times;
	vector<int> best_times;
	int values_of_ten[] = { 100,1000,10000,0 };
	cout << "Problem B" << endl;
	for (int i = 0; i < 3; i++) {		
		for (int j = 0; j < values_of_ten[i]; j++) {
			elements_worst.push_back(new Node(int(rand())));
		}
		vector<Node*> elements_best = elements_worst;
		for (int j = 0; j < values_of_ten[i]; j++) {
			makeSet(elements_worst.at(j));
			makeSet(elements_best.at(j));
		}
		
		for (int j = 0; j < values_of_ten[i] - 1; j++) {
			auto start = high_resolution_clock::now();
			unionSet(elements_worst.at(j), elements_worst.at(j+1), false, false);
			auto stop = high_resolution_clock::now();
			auto duration = duration_cast<nanoseconds>(stop - start).count();
			worst_time_individual += duration;

			start = high_resolution_clock::now();
			unionSet(elements_best.at(j), elements_best.at(j + 1), true, true);
			stop = high_resolution_clock::now();
			duration = duration_cast<nanoseconds>(stop - start).count();
		
			best_time_individual += duration;
			
		}
		worst_times.push_back(worst_time_individual);
		best_times.push_back(best_time_individual);
		worst_time_individual = 0;
		best_time_individual = 0;
		elements_best.clear();
		elements_worst.clear();
	}
	cout << "No Heruistic, Heruistic" << endl;
	for (int i = 0; i < worst_times.size(); i ++) {

		cout << worst_times.at(i) << " nanoseconds, " << best_times.at(i) << " nanoseconds"<< endl;
	}
	cout << "Problem C" << endl;
	int values_of_ten_c[] = {1000,10000,100000,1000000};
	vector<Node*> set_of_singles_both_heru;
	vector<Node*> set_of_singles_path;
	vector<Node*> set_of_singles_rank;

	uint32_t both_heru = 0;
	uint32_t path_heru = 0;
	uint32_t rank_heru = 0;

	for (int ten_count = 0; ten_count < 4; ten_count++) {
		for (int ten_times = 0; ten_times < 10; ten_times++) {

			for (int i = 0; i < values_of_ten_c[ten_count]; i++) {
				set_of_singles_both_heru.push_back(new Node(i));
				makeSet(set_of_singles_both_heru.at(i));
			}

			set_of_singles_path = set_of_singles_both_heru;
			set_of_singles_rank = set_of_singles_both_heru;


			for (int j = 0; j < values_of_ten_c[ten_count]; j++) {
				auto start = high_resolution_clock::now();
				unionSet(set_of_singles_both_heru.at(int(rand()) % set_of_singles_both_heru.size()), set_of_singles_both_heru.at(int(rand()) % set_of_singles_both_heru.size()), true, true);
				auto stop = high_resolution_clock::now();
				auto duration = duration_cast<microseconds>(stop - start).count();
				both_heru += duration;

				start = high_resolution_clock::now();
				unionSet(set_of_singles_path.at(int(rand()) % set_of_singles_path.size()), set_of_singles_path.at(int(rand()) % set_of_singles_path.size()), false, true);
				stop = high_resolution_clock::now();
				duration = duration_cast<microseconds>(stop - start).count();
				path_heru += duration;

				start = high_resolution_clock::now();
				unionSet(set_of_singles_rank.at(int(rand()) % set_of_singles_rank.size()), set_of_singles_rank.at(int(rand()) % set_of_singles_rank.size()), false, true);
				stop = high_resolution_clock::now();
				duration = duration_cast<microseconds>(stop - start).count();
				rank_heru += duration;
			}
			set_of_singles_both_heru.clear();
			set_of_singles_path.clear();
			set_of_singles_rank.clear();
		}
		cout << values_of_ten_c[ten_count] << endl;
		cout << "Both heruistics: " << (double)both_heru / 10.0 << " microseconds" << endl;
		cout << "Path compression: " << (double)path_heru / 10.0 << " microseconds" <<endl;
		cout << "By Rank: " << (double)rank_heru / 10.0 << " microseconds" <<endl;
		both_heru = 0;
		path_heru = 0;
		rank_heru = 0;
	}
	
	return 0;
}

void printAllElements(std::vector<Node*> elements) {
	std::cout << "Element  Parent  Set Representative" << std::endl;
	for (int i = 0; i < elements.size(); i++) {
		// Print element and its set representative (should be same as element)
		std::cout << elements.at(i)->value << "  "
			<< elements.at(i)->parent->value << "  "
			<< findSet(elements.at(i), false)->value << std::endl;
	}
}

void makeSet(Node* element) {
	element->parent = element;
	element->rank = 0;
}

Node* findSet(Node* element, bool pathCompression) {
	
	if (pathCompression) {
		if (element != element->parent) {
			element->parent = findSet(element->parent, pathCompression);

		}
	}
	else if (element != element->parent) {
		element = findSet(element->parent, pathCompression);
	}
	return element->parent;
}
void link(Node* element1, Node* element2, bool unionByRank) {
	
	if (unionByRank) {
		if (element1->rank > element2->rank)
			element2->parent = element1;
		else
		{
			element1->parent = element2;
			if (element1->rank == element2->rank)
				element2->rank++;
		}
	}
	else {
		element1->parent = element2;
	}
}

void unionSet(Node* element1, Node* element2, bool unionByRank, bool pathCompression) {
	
	link(findSet(element1, pathCompression), findSet(element2, pathCompression), unionByRank);

}
