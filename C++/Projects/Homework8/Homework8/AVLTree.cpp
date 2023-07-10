#include "AVLTree.h"
#include <algorithm>

AVLTree::~AVLTree() {
	deleteTree(root);
}

// Insert a new element and rebalance the tree
void AVLTree::insert(Node* node) {
	if (root == nullptr) {
		root = node;
		node->parent = nullptr;
		return;
	}

	Node* cur = root;
	while (cur != nullptr) {
		if (node->key < cur->key) {
			if (cur->left == nullptr) {
				cur->left = node;
				node->parent = cur;
				cur = nullptr;
			}
			else {
				cur = cur->left;
			}
		}
		else {
			if (cur->right == nullptr) {
				cur->right = node;
				node->parent = cur;
				cur = nullptr;
			}
			else
				cur = cur->right;
		}
	}

	node = node->parent;
	while (node != nullptr) {
		Rebalance(node);
		node = node->parent;
	}
}
Node* AVLTree::getRoot() {
	return this->root;
}
// Insert a new element without rebalancing the tree
void AVLTree::insertWithoutRebalance(Node* node) {

	if (this->root == nullptr) {
		this->root = node;
		node->parent = nullptr;
		return;
	}

	Node* cur = this->root;
	while (cur != nullptr) {
		if (node->key < cur->key) {
			if (cur->left == nullptr) {
			
				cur->left = node;
				node->parent = cur;

				cur = nullptr;
			}
			else {
				cur = cur->left;
			}
		}
		else {
			if (cur->right == nullptr) {
				cur->right = node;
				node->parent = cur;
				cur = nullptr;
			}
			else
				cur = cur->right;
		}
	}

	
	while (node->parent != nullptr) {
		UpdateHeight(node);
		node = node->parent;
			
		
	}
	UpdateHeight(node);
}


void AVLTree::Rebalance(Node* node) {
	UpdateHeight(node);
	if (GetBalance(node) == -2) {
		if (GetBalance(node->right) == 1) {
			// Double rotation case.
			AVLTreeRotateRight(node->right);
		}
		return AVLTreeRotateLeft(node);
	}
	else if (GetBalance(node) == 2) {
		if (GetBalance(node->left) == -1) {
			// Double rotation case.
			AVLTreeRotateLeft(node->left);
		}
		return AVLTreeRotateRight(node);
	}
	return;
}

void AVLTree::AVLTreeRotateRight(Node* node) {
	Node* leftRightChild = node->left->right;
	if (node->parent != nullptr)
		ReplaceChild(node->parent, node, node->left);
	else { // node is root
		root = node->left;
		root->parent = nullptr;
	}
	SetChild(node->left, "right", node);
	SetChild(node, "left", leftRightChild);
}

void AVLTree::AVLTreeRotateLeft(Node* node) {
	Node* rightLeftChild = node->right->left;
	if (node->parent != nullptr)
		ReplaceChild(node->parent, node, node->right);
	else { // node is root
		root = node->right;
		root->parent = nullptr;
	}
	SetChild(node->right, "left", node);
	SetChild(node, "right", rightLeftChild);
}


void AVLTree::UpdateHeight(Node* node) {
	int leftHeight = -1;
	if (node->left != nullptr)
		leftHeight = node->left->height;

	int rightHeight = -1;
	if (node->right != nullptr)
		rightHeight = node->right->height;

	node->height = std::max(leftHeight, rightHeight) + 1;
}


bool AVLTree::SetChild(Node* parent, std::string whichChild, Node* child) {
	if (whichChild != "left" && whichChild != "right")
		return false;

	if (whichChild == "left")
		parent->left = child;
	else
		parent->right = child;

	if (child != nullptr)
		child->parent = parent;

	UpdateHeight(parent);
	return true;
}


bool AVLTree::ReplaceChild(Node* parent, Node* currentChild, Node* newChild) {
	if (parent->left == currentChild)
		return SetChild(parent, "left", newChild);
	else if (parent->right == currentChild)
		return SetChild(parent, "right", newChild);
	return false;
}


int AVLTree::GetBalance(Node* node) {
	int leftHeight = -1;
	if (node->left != nullptr)
		leftHeight = node->left->height;
	int rightHeight = -1;
	if (node->right != nullptr)
		rightHeight = node->right->height;
	return leftHeight - rightHeight;
}

void AVLTree::deleteTree(Node* node) {
	// Recursively remove all nodes in the BST (used by the destructor). 
	// By doing this recursively, we start removing nodes from the bottom
	// of the tree (leaf nodes), which is most efficiently because it does
	// not require replacing any nodes.

	if (node == nullptr)
		return;

	deleteTree(node->left);
	deleteTree(node->right);
	delete node;
}
