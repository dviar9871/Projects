
#include <iostream>
#include "RedBlackTree.h"
#include <vector>
#include <chrono>
#include <algorithm>
#include "AVLTree.h"
using namespace std::chrono;
using std::partial_sort;

using std::cout;
using std::endl;
using std::vector;

int main() {

	int unsorted_height;
	int partially_sorted_height;
	int sorted_height;
	int avl_unsorted_height;
	int avl_partially_sorted_height = 0;
	int avl_sorted_height = 0;



	int values_of_ten[] = { 100,1000,10000,100000 };

	vector<double> unsorted_vector;
	vector<double> partially_sorted_vector;
	vector<double> sorted_vector;
	//vector<double> avl_vector;

	int unsorted_time = 0;
	int partially_sorted_time = 0;
	int sorted_time = 0;

	int avl_unsorted_time = 0;
	int avl_partially_sorted_time= 0;
	int avl_sorted_time = 0;

	RedBlackTree unsorted_tree;
	RedBlackTree partially_sorted_tree;
	RedBlackTree sorted_tree;

	AVLTree avl_unsorted;
	AVLTree avl_partially_sorted;
	AVLTree avl_sorted;


	for (int i = 0; i < 4; i++) {
		unsorted_time = 0;
		partially_sorted_time = 0;
		sorted_time = 0;

		avl_unsorted_time = 0;
		avl_partially_sorted_time = 0;
		avl_sorted_time = 0;

		unsorted_vector.clear();
		partially_sorted_vector.clear();
		sorted_vector.clear();

		unsorted_height = 0;
		partially_sorted_height = 0;
		sorted_height = 0;

		avl_unsorted_height = 0;
		avl_partially_sorted_height = 0;
		avl_sorted_height = 0;

		cout << "Tree Size: " << values_of_ten[i] << endl;
		for (int j = 0; j < 10; j++) {
			
			for (int arr_limit = 0; arr_limit < values_of_ten[i]; arr_limit++) {
				double rand_num = double((rand()) / RAND_MAX);
				unsorted_vector.push_back(rand_num);
			}
		
			partially_sorted_vector = unsorted_vector;
			partial_sort(partially_sorted_vector.begin() + (int)partially_sorted_vector.size() * .25, partially_sorted_vector.begin() + (int)partially_sorted_vector.size() * .75, partially_sorted_vector.end());
			sorted_vector = unsorted_vector;
			partial_sort(sorted_vector.begin(), sorted_vector.end(), sorted_vector.end());
			
			auto start = high_resolution_clock::now();

			for (int arr_limit = 0; arr_limit < unsorted_vector.size(); arr_limit++) {

				RBTNode* node = new RBTNode(unsorted_vector.at(arr_limit));
				unsorted_tree.insert(node);

			}

			auto stop = high_resolution_clock::now();
			long duration = duration_cast<milliseconds>(stop - start).count();
			unsorted_height += unsorted_tree.calcHeight(unsorted_tree.getRoot());
			unsorted_time += duration;

			start = high_resolution_clock::now();

			for (int arr_limit = 0; arr_limit < unsorted_vector.size(); arr_limit++) {

				Node* node = new Node(unsorted_vector.at(arr_limit));
				avl_unsorted.insert(node);

			}

			stop = high_resolution_clock::now();
			duration = duration_cast<milliseconds>(stop - start).count();
			avl_unsorted_height += avl_unsorted.getRoot()->height;
			avl_unsorted_time += duration;

			start = high_resolution_clock::now();

			for (int arr_limit = 0; arr_limit < partially_sorted_vector.size(); arr_limit++) {

				RBTNode* node = new RBTNode(partially_sorted_vector.at(arr_limit));
				partially_sorted_tree.insert(node);

			}

			stop = high_resolution_clock::now();

			duration = duration_cast<milliseconds>(stop - start).count();
			partially_sorted_height += partially_sorted_tree.calcHeight(partially_sorted_tree.getRoot());
			partially_sorted_time += duration;

			start = high_resolution_clock::now();

			for (int arr_limit = 0; arr_limit < unsorted_vector.size(); arr_limit++) {

				Node* node = new Node(unsorted_vector.at(arr_limit));
				avl_partially_sorted.insert(node);

			}

			stop = high_resolution_clock::now();
		
			duration = duration_cast<milliseconds>(stop - start).count();
			avl_partially_sorted_height += avl_partially_sorted.getRoot()->height;
			avl_partially_sorted_time += duration;

			start = high_resolution_clock::now();

			for (int arr_limit = 0; arr_limit < sorted_vector.size(); arr_limit++) {

				RBTNode* node = new RBTNode(sorted_vector.at(arr_limit));
				sorted_tree.insert(node);

			}

			stop = high_resolution_clock::now();
			duration = duration_cast<milliseconds>(stop - start).count();
			sorted_height += sorted_tree.calcHeight(sorted_tree.getRoot());
			sorted_time += duration;

			start = high_resolution_clock::now();

			for (int arr_limit = 0; arr_limit < unsorted_vector.size(); arr_limit++) {

				Node* node = new Node(unsorted_vector.at(arr_limit));
				avl_sorted.insert(node);

			}

			stop = high_resolution_clock::now();
			duration = duration_cast<milliseconds>(stop - start).count();
			avl_sorted_height += avl_sorted.getRoot()->height;
			avl_sorted_time += duration;
		}
		





		cout << "Unsorted Total time: " << (unsorted_time) / 10.0 << " Milliseconds" << "		Avl Total time: " << (avl_unsorted_time) / 10.0 << " Miliseconds" << endl;
		cout << "Unsorted Heights: " << (unsorted_height / 10.0) << "				Avl Heights: " << (avl_unsorted_height / 10.0) << endl;
		
		cout << endl << endl;

		cout << "Partially sorted average: " << (partially_sorted_time / 10.0) << " Milliseconds" << "	Avl Total time: " << (avl_partially_sorted_time) / 10.0 << " Miliseconds" << endl;
		cout << "Partially sorted Heights: " << (partially_sorted_height / 10.0) << "			Avl Heights: " << (avl_partially_sorted_height / 10.0) << endl;
		cout << endl << endl;

		cout << "Sorted average: " << (sorted_time / 10.0) << " Milliseconds" << "		Avl Total time: " << (avl_sorted_time) / 10.0 << " Miliseconds" << endl;
		cout << "Sorted Heights: " << (sorted_height / 10.0) << "			        Avl Heights: " << (avl_sorted_height / 10.0)  <<endl;
		
		cout << endl<< endl;

	}
	cout << endl << "Completed" << endl;


	return 0;
}
