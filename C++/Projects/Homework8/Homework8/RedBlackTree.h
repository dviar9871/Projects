#ifndef REDBLACKTREE_H
#define REDBLACKTREE_H
#include <string>

// A node in the red-black tree that stores a double
class RBTNode {
public:
    double key;
    // Whether the node is red (false denotes black)
    bool red = true;
    RBTNode* left = nullptr;
    RBTNode* right = nullptr;
    RBTNode* parent = nullptr;
    int height = 0;
    RBTNode(double nodeKey) {
        key = nodeKey;
    }
};

// A red-black tree that can only insert
class RedBlackTree
{
public:
    RedBlackTree() {};
    ~RedBlackTree();
    bool RBTreeSetChild(RBTNode* parent, std::string whichChild, RBTNode* child);
    bool  RBTreeReplaceChild(RBTNode* parent, RBTNode* currentChild, RBTNode* newChild);
    void RBTreeRotateLeft(RBTNode* node);
    void RBTreeRotateRight(RBTNode* node);
    void BSTInsert(RBTNode* node);
    RBTNode* getRoot();
    RBTNode* RBTreeGetGrandparent(RBTNode* node);
    RBTNode* RBTreeGetUncle(RBTNode* node);
    void RBTreeBalance(RBTNode* node);

    // Insert a new element and rebalance the tree
    void insert(RBTNode* node);
    int calcHeight(RBTNode* node);
private:
    RBTNode* root = nullptr;
    void deleteTree(RBTNode* node);
};

#endif // !REDBLACKTREE_H