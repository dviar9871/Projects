
#include <algorithm>
#include <iostream>
#include "AVLTree.h"
#include <stdlib.h> 
#include <vector>
using namespace std;
int main() {

	int values_of_ten[] = { 100,1000,10000,100000 };
	vector<double> bst_heights;
	vector<double> avl_heights;
	double bst_min, bst_max, bst_avg = 0;
	double avl_min, avl_max, avl_avg = 0;
	for (int i = 0; i < 4; i++) {

		cout << "Tree Size: " << values_of_ten[i] << endl;
		for (int j = 0; j < 10; j++) {
			avl_avg = 0;
			bst_avg = 0;
			double random = ((double)rand() / RAND_MAX);

			AVLTree bst;
			AVLTree avl;
			bst.insertWithoutRebalance(new Node(random));
			avl.insert(new Node(random));

			for (int arr_limit = 0; arr_limit < values_of_ten[i]; arr_limit++) {
				random = ((double)rand() / RAND_MAX);
				bst.insertWithoutRebalance(new Node(random));
				avl.insert(new Node(random));

			}
			bst_heights.push_back(bst.getRoot()->height);
			avl_heights.push_back(avl.getRoot()->height);

		}
		bst_min = bst_heights.at(0);
		bst_max = bst_heights.at(0);
		for (double height : bst_heights) {
			if (height >= bst_max)
				bst_max = height;
			if (height <= bst_min)
				bst_min = height;
			bst_avg += height;
		}
		bst_avg /= 10;

		avl_min = avl_heights.at(0);
		avl_max = avl_heights.at(0);
		for (double height : avl_heights) {
			if (height >= avl_max)
				avl_max = height;
			if (height <= avl_min)
				avl_min = height;
			avl_avg += height;
		}
		
		avl_avg /= 10;
		bst_heights.clear();
		avl_heights.clear();
		cout << "BST Min Height: " << bst_min << "   BST Max Height: " << bst_max << "      BST Avg Height: " << bst_avg << endl;
		cout << "AVL Min Height: " << avl_min << "   AVL Max Height: " << avl_max << "      AVL Avg Height: " << avl_avg << endl;
	}
	return 0;
}