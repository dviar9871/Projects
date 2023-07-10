#ifndef AVLTREE_H
#define AVLTREE_H
#include <string>
// A node in the AVL tree that stores a double
class Node {
public:
    double key;
    int height = 0;
    Node* left = nullptr;
    Node* right = nullptr;
    Node* parent = nullptr;

    Node(double nodeKey) {
        key = nodeKey;
    }
};

// An AVL tree that can insert with and without rebalancing
class AVLTree
{
public:
    AVLTree() {};
    ~AVLTree();

    // Insert a new element and rebalance the tree
    void insert(Node* node);
    // Insert a new element without rebalancing the tree
    void insertWithoutRebalance(Node* node);
    void Rebalance(Node* node);
    bool ReplaceChild(Node* parent, Node* currentChild, Node* newChild);
    int GetBalance(Node* node);
    void UpdateHeight(Node* node);
    bool SetChild(Node* parent, std::string whichChild, Node* child);
    void AVLTreeRotateRight(Node* node);
    void AVLTreeRotateLeft(Node* node);
    Node* getRoot();

private:
    Node* root = nullptr;
    void deleteTree(Node* node);
};

#endif // !AVLTREE_H