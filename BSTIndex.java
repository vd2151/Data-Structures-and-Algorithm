package project5;
import java.util.*;

/**
 * This class represents a Binary Search Tree (BST) implementation where elements are stored in alphabetical order.
 * It does not allow null or duplicate elements. The tree sorts its elements according to their natural ordering.
 * Generic type T extends Comparable to ensure that elements can be compared for sorting.
 *
 * @param <T> the type of elements maintained by this tree
 * @author Vedant_Desai
 * @version December 03, 2023
 */
public class BSTIndex <T extends Comparable<T>> implements Index {

    // Root node of the BST
    private Node root;

    // Number of nodes in the BST
    private int size;

    /**
     * Constructs an empty BST.
     * Initializes the root to null and size to 0, indicating an empty tree.
     */
    static String result;
    public BSTIndex() {
        root = null;
        size = 0;
    }

    /**
     * Adds a new element to the BST if it's not already present.
     * If the element is already present, increments its count.
     *
     * @param data The element to add to the tree.
     * @throws NullPointerException if the specified element is null.
     */
    public void add(String data) throws NullPointerException {
        if (data == null) 
            throw new NullPointerException("null value found");
        size++;
        root = add(data, root);
    }

    /**
     * Internal method to add an element to the tree recursively.
     * Finds the correct position for the new element based on BST properties.
     * @param data The element to be added.
     * @param node The node at which the recursive call is made.
     * @return The modified subtree with the new element added.
     */
    private Node add(String data, Node node) {
        if (node == null) {
            return new Node(data);
        }
        int diff = data.compareTo(node.data.getWord());

        // Determine where to insert the new value
        if (diff < 0) {
            node.left = add(data, node.left);
        } else if (diff > 0) {
            node.right = add(data, node.right);
        } else {
            node.data.incrementCount();
            size--;
        }
        return node;
    }

    /**
     * Removes the specified element from the tree.
     * Does nothing if the element or root is null.
     * @param data The element to remove.
     */
    public void remove(String data) {
        if (data == null || root == null)
            return;
        root = remove(data, root);
        // size should only be decremented if a node was actually removed
    }


    /**
     * Internal method to remove an element from the tree recursively.
     * Locates and removes the specified element and returns the modified tree.
     * @param element The element to remove.
     * @param node The node at which the recursive call is made.
     * @return The modified subtree with the specified element removed.
     */
    
    private Node remove(String element, Node node) {
        if (node == null) {
            return null;
        }

        int diff = element.compareTo(node.data.getWord());

        if (diff < 0) {
            node.left = remove(element, node.left);
        } else if (diff > 0) {
            node.right = remove(element, node.right);
        } else {
            // Node with two children: Get the inorder successor
            if (node.left != null && node.right != null) {
                Node temp = findMin(node.right);
                node.data = temp.data;
                node.right = remove(node.data.getWord(), node.right);
            } else {
                node = (node.left != null) ? node.left : node.right;
                size--; // Decrement size only if a node is actually removed
            }
        }
        return node;
    }
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    /**
     * Returns the number of nodes in the BST.
     *
     * @return The number of nodes.
     */
    public int size() {
        return size;
    }

    /**
     * Searches for an element and returns its count.
     *
     * @param item The element to search for.
     * @return The count of the element, or -1 if not found.
     * @throws NullPointerException if the element is null.
     */
    public int get(String item) throws NullPointerException {
        if (item == null)
            throw new NullPointerException("Null value cannot be searched!");
        return countOf(item, root);
    }

    /**
     * Recursively searches for an item in the tree and returns its count.
     * This method traverses the tree starting from a given node and looks for the item.
     * It compares the item with the data at each node and decides the direction of the search
     * (left or right) based on the comparison result.
     *
     * @param item The item whose count is to be determined.
     * @param n    The current node from which the search is conducted. In the initial call,
     *             this should be the root of the tree.
     *
     * @return The count of the item if found, or -1 if the item is not in the tree.
     *         The count represents how many times the item occurs in the tree.
   
     */ 
    public int countOf(String item, Node n) {
        if (n == null) {
            return -1; // Item not found
        }

        int diff = item.compareTo(n.data.getWord());
        if (diff < 0) {
            return countOf(item, n.left);
        } else if (diff > 0) {
            return countOf(item, n.right);
        } else {
            return n.data.getCount();
        }
    }
    
    /**
     * Compares this BST with another object for equality.
     * Two BSTs are considered equal if they have the same size and their elements are equal in the same order.
     * @param o The object to compare with this BST.
     * @return true if the specified object is equal to this BST, false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) 
            return true;
        if (!(o instanceof Index))
            return false;
        Index list2 = (Index) o;
        if (this.size() != list2.size())
            return false;
        Iterator<Word> iter1 = this.iterator();
        Iterator<Word> iter2 = list2.iterator();
        while(iter1.hasNext() && iter2.hasNext())
            if(!iter1.next().equals(iter2.next()))
                return false;
        if(iter1.hasNext() || iter2.hasNext())
            return false;
        return true;
    }

    /**
     * Returns a string representation of the BST.
     * The elements are listed in alphabetical order, enclosed in square brackets, separated by commas.
     * @return A string representation of the BST.
     */
    @Override
    public String toString()
    {
        result = "";
        inorder(root);
        return "["+result+"]";
    }

 // Helper method for inorder traversal to build a string representation
    public void inorder(Node n)
    {
        if(n==null)
            return;
        inorder(n.left);
        if(result.equals(""))
            result += n.data.toString();
        else
            result+= ", "+n.data.toString();
        inorder(n.right);
    }

    /**
     * Provides an iterator over the elements in the BST.
     * @return An iterator for the BST.
     */
    public Iterator<Word> iterator() {
        return new BSTIter();
    }
 // Inner class for implementing the Iterator interface for the BST
    private class BSTIter implements Iterator<Word> {
        ArrayList<Word> values ; // List to hold elements in inorder
        int current; // Current position in the iterator
        // Constructor for BSTIter
        public BSTIter( ) {
            values = new ArrayList<Word>(size) ; 
            inorder(root); 
            current = 0; 
        }
     // Helper method for inorder traversal to populate the iterator
        void inorder (Node node ) {
            if (node == null)
                return;
             
            inorder (node.left); // Traverse left subtree
           
            values.add(node.data) ; // Add the current node
 
            inorder (node.right);// Traverse right subtree

        }
        /**
         * Checks if there are more elements in the iteration.
         * @return true if there are more elements, false otherwise.
         */
        @Override
        public boolean hasNext() {
        	if (current >= values.size() ) 
                return false;
            return true; 
        }


        /**
         * Returns the next element in the iteration.
         * @return The next Word object, or null if no more elements.
         */
        @Override
        public Word next() {
            if (current >= values.size() ) {
                return null; 
            }
            Word val = values.get(current); 
            current++; 
            return val; 
        }
        
        /**
         * Removes the current element from the BST during iteration.
         * This method is part of the Iterator implementation for the BSTIndex class.
         * It allows for the removal of an element from the BST while iterating over its elements.
         *
         **/
        @Override
        public void remove() {
            if (values == null || current > values.size() || current == 0)
                return;

            current--;
            String wordToRemove = values.get(current).getWord();
            BSTIndex.this.remove(wordToRemove); // Use BSTIndex's remove method
            values.remove(current);
            // Size decrementing is handled in the BSTIndex's remove methodS
        }

    }

    /**
     * Node class representing the nodes in the BST.
     * Each node contains a data element of type Word, and references to left and right child nodes.
     */
    private class Node implements Comparable <Node> {

        Word data;// The data element stored in the node
        Node left;// Reference to the left child
        Node right;// Reference to the right child

     // Constructor for Node with just the elementss
        Node(String element) {
            if (element == null ) throw new NullPointerException ("Invalid value!");
            data = new Word(element);
            left = null;
            right = null;
        }

        /**
         * Constructs a Node with the specified element, left child, and right child.
         * 
         * @param element The data element to store in the node.
         * @param left The left child of the node.
         * @param right The right child of the node.
         */
        Node(String element, Node left, Node right) {
            this(element); // Calls the constructor Node(String element) to initialize data
            this.left = left; // Sets the left child of this node
            this.right = right; // Sets the right child of this node
        }

        /**
         * Compares this node with another node for order.
         * Comparison is based on the lexical order of the data elements stored in the nodes.
         * 
         * @param n The node to be compared with this node.
         * @return A negative integer, zero, or a positive integer as this node's data
         *         is less than, equal to, or greater than the specified node's data.
         */
        public int compareTo(Node n) {
            // Compares the data of this node with the data of node n
            // Uses the compareTo method of the Word class
            return this.data.getWord().compareTo(n.data.getWord());
        }
    }
}