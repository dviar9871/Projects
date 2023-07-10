#include "RedBlackTree.h"
#include <algorithm>
#include <string>
#include <iostream>
RedBlackTree::~RedBlackTree() {
	deleteTree(root);
}

void RedBlackTree::deleteTree(RBTNode* node) {
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



RBTNode* RedBlackTree::getRoot() {
	return this->root;
}


bool RedBlackTree::RBTreeSetChild(RBTNode* parent, std::string whichChild, RBTNode* child) {
	if (whichChild != "left" && whichChild != "right")
		return false;

	if (whichChild == "left")
		parent->left = child;
	else
		parent->right = child;
	if (child != nullptr)
		child->parent = parent;
	return true;
}


bool RedBlackTree::RBTreeReplaceChild(RBTNode* parent, RBTNode* currentChild, RBTNode* newChild) {
	if (parent->left == currentChild)
		return RBTreeSetChild(parent, "left", newChild);
	else if (parent->right == currentChild)
		return RBTreeSetChild(parent, "right", newChild);
	return false;
}


void RedBlackTree::RBTreeRotateLeft(RBTNode* node) {
	RBTNode* rightLeftChild = node->right->left;
	if (node->parent != nullptr)
		RBTreeReplaceChild(node->parent, node, node->right);
	else { // node is root
		this->root = node->right;
		this->root->parent = nullptr;
	}
	RBTreeSetChild(node->right, "left", node);
	RBTreeSetChild(node, "right", rightLeftChild);
}

void RedBlackTree::RBTreeRotateRight(RBTNode* node) {
	RBTNode* leftRightChild = node->left->right;
	if (node->parent != nullptr)
		RBTreeReplaceChild(node->parent, node, node->left);
	else { // node is root
		this->root = node->left;
		this->root->parent = nullptr;
	}
	RBTreeSetChild(node->left, "right", node);
	RBTreeSetChild(node, "left", leftRightChild);
}

void RedBlackTree::BSTInsert(RBTNode* node) {
	if (root == nullptr) {
		root = node;
		node->parent = nullptr;
		return;
	}

	RBTNode* cur = root;
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
}

void updateHeight(RBTNode* node) {
	int leftHeight = -1;
	if (node->left != nullptr)
		leftHeight = node->left->height;

	int rightHeight = -1;
	if (node->right != nullptr)
		rightHeight = node->right->height;

	node->height = std::max(leftHeight, rightHeight) + 1;
}


void RedBlackTree::insert(RBTNode* node) {
	BSTInsert(node);
	node->red = 1;
	RBTreeBalance(node);
	
	
}
int RedBlackTree::calcHeight(RBTNode* node) {
	//increase height for each node iterated, (0 indexed) if node is null, return -1

	if (node == nullptr)
		return -1;

	//calc left height
	int leftHeight = calcHeight(node->left);
	//calc right height
	int rightHeight = calcHeight(node->right);

	return std::max(leftHeight, rightHeight) + 1;
}

RBTNode* RedBlackTree::RBTreeGetGrandparent(RBTNode* node) {
	if (node->parent == nullptr)
		return nullptr;
	return node->parent->parent;
}

RBTNode* RedBlackTree::RBTreeGetUncle(RBTNode* node) {
	RBTNode* grandparent = nullptr;
	if (node->parent != nullptr)
		grandparent = node->parent->parent;
	if (grandparent == nullptr)
		return nullptr;
	if (grandparent->left == node->parent)
		return grandparent->right;
	else
		return grandparent->left;
}


void RedBlackTree::RBTreeBalance(RBTNode* node) {
	if (node->parent == nullptr) {
		node->red = 0;
		return;
	}
	if (node->parent->red == 0)
		return;
	RBTNode* parent = node->parent;
	RBTNode* grandparent = RBTreeGetGrandparent(node);
	RBTNode* uncle = RBTreeGetUncle(node);
	if (uncle != nullptr && uncle->red == 1) {
		parent->red = uncle->red = 0;
		grandparent->red = 1;
		RBTreeBalance(grandparent);
		return;
	}
	if (node == parent->right && parent == grandparent->left) {
		RBTreeRotateLeft(parent);
		node = parent;
		parent = node->parent;
	}
	else if (node == parent->left && parent == grandparent->right) {
		RBTreeRotateRight(parent);
		node = parent;
		parent = node->parent;
	}
	parent->red = 0;
	grandparent->red = 1;
	if (node == parent->left)
		RBTreeRotateRight(grandparent);
	else
		RBTreeRotateLeft(grandparent);
}


