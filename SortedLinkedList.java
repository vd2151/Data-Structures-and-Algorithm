package project5;
import java.util.*;

/**
 * This class represents a sorted doubly-linked list.
 * All elements in the list are maintained in alphabetical order.
 * This list does not allow null or duplicate elements.
 * Elements are of generic type E which extends Comparable to ensure they can be compared.
 * @param <E> the type of elements maintained by this list
 * @author Vedant_Desai
 * @version December 03, 2023
 */
public class SortedLinkedList<E extends Comparable<E>> implements Index {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new, empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in a sorted order or increases the count 
     * of the existing word if it is a duplicate.
     * @param element The string to be added to the list.
     * @throws IllegalArgumentException if the input element is null.
     */
    @Override
    public void add(String element) {
        
        if(element == null) throw new IllegalArgumentException("Wrong input"); 

        Node newNode = new Node(element);         
        if(head == null){ 
            head = newNode;
            tail = newNode; 
            size++;
        }
        else if(head.data.compareTo(newNode.data) > 0){ //smaller than head
            newNode.next = head; 
            head.prev = newNode; 
            head = newNode; 
            size++;
        }
        else if(newNode.data.compareTo(head.data) == 0){ 
            head.data.incrementCount(); 
        }
        else {
            Node current = head;
            while (current.next != null && newNode.data.compareTo(current.next.data) > 0) { 
                current = current.next;
            }
            if(current.next!=null && current.next.data.getWord().equals(newNode.data.getWord())){ 
                current.next.data.incrementCount();
            }
            else if(current == tail){ 
                tail.next = newNode; 
                newNode.prev = tail; 
                tail = newNode; 
                size++;
              }
            else{
                newNode.prev = current;
                newNode.next = current.next;
                current.next = newNode;
                newNode.next.prev = newNode;
                size++;
            }
        }
    }
   

    /**
     * Returns the count of the given element in the list.
     * Returns -1 if the element is not found.
     * @param item The element to search for.
     * @return The count of the element, or -1 if not found.
     * @throws NullPointerException if the item is null.
     */
    public int get(String item) throws NullPointerException{
        if(item==null)
            throw new NullPointerException("Null value cannot be searched!");
        Node current = head;
        while(current!=null)
        {
            if(current.data.getWord().equals(item))
                return current.data.getCount();
            current = current.next;
        }
        return -1;
    }

    /**
     * Removes the specified element from the list.
     * @param item The element to be removed.
     * @throws NullPointerException if the input item or list head is null.
     */
    public void remove(String item) throws NullPointerException{
        if (item == null || head == null)
            throw new NullPointerException("Null object found!");

        Node n = new Node(item);
        Node current = head;
        while (current != null) {
            if (n.data.compareTo(current.data) == 0) 
            {
                if (current.prev != null)
                {
                    current.prev.next = current.next;
                }
                else
                {
                    head = current.next;
                    head.prev = null;
                }
                if (current.next != null)
                {
                    current.next.prev = current.prev;
                }
                else
                {
                    tail = current.prev;
                    tail.next = null;
                }
                size--;
                size--;
            }
            current = current.next;
        }
    }

    /**
     * Returns the size of the list.
     * @return The number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Provides an iterator over the elements in the list.
     * @return An iterator for traversing the list.
     */
    public Iterator<Word> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     * Two lists are considered equal if they contain the same elements in the same order.
     * @param o The object to be compared with this list.
     * @return True if the specified object is equal to this list, false otherwise.
     */
    public boolean equals(Object o) {
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
     *  Returns a string representation of the list.
     *  The string representation consists of a list of the lists's elements in
     *  alphabetical order, enclosed in square brackets ("[]").
     *  Adjacent elements are separated by the characters ", " (comma and space).
     * @return a string representation of the list
     */
    public String toString() {
        String result = "[";
        Node current = head;
        while (current != null) {
            result+= current.data.toString();
            if (current.next != null)
                result+= ", ";
            current = current.next;
        }
        return result+"]";
    }

    /**
     * Inner class representing nodes of the list.
     * Each node contains a Word data element and references to the next and previous nodes.
     */
    private class Node implements Comparable<Node> {
    	Word data; // The data element of the node
        Node next; // Reference to the next node
        Node prev; // Reference to the previous node
        /**
         * Constructs a new Node with the specified string element.
         * Initializes the data field to a new Word object with the element and sets next and prev to null.
         * Throws a NullPointerException if the element is null.
         * @param element The string element to store in the node.
         * @throws NullPointerException if the element is null.
         */
        Node(String element) {
            if (element == null ) throw new NullPointerException ("Invalid value!");
            data = new Word(element);
            next = null;
            prev = null;
        }
        /**
         * Constructs a Node with the specified string element, next node, and previous node.
         * Utilizes the Node(String element) constructor for initial setup and then sets the next and prev nodes.
         *
         * @param element The string element to store in the node.
         * @param next The next Node in the list.
         * @param prev The previous Node in the list.
         */
        Node (String element, Node next, Node prev) {
            this(element);
            this.next = next;
            this.prev = prev;
        }
        /**
         * Compares this node with another node for order.
         * Comparison is based on the string values of the Word objects stored in the nodes.
         *
         * @param n The node to be compared with this node.
         * @return A negative integer, zero, or a positive integer as this node
         *         is less than, equal to, or greater than the specified node.
         */
        @Override
        public int compareTo( Node n ) {
            return this.data.getWord().compareTo(n.data.getWord());
        }
    }

    /* Inner class implementing a basic forward iterator for the sorted list. */
    private class ListIterator implements Iterator<Word> {

        Node nextToReturn = head;
        Node prevToReturn = null;

        /**
         * Checks if there is a next element in the list to return.

         * @return true if there is a next element, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        /**
         * Returns the next Word object in the list.
         * Updates the nextToReturn and prevToReturn references for subsequent calls.
         * @return The next Word object in the list, or null if there are no more elements.
         */
        @Override
        public Word next(){
            if (nextToReturn == null )
                return null;
            Word tmp = nextToReturn.data;
            prevToReturn = nextToReturn;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

        /**
         * Removes the last element returned by this iterator from the list.
         * This method updates the list's structure by handling different cases such as
         * removing the only element, the head, the tail, or a middle element. It also updates the size of the list.
         */
        public void remove() {
            if (prevToReturn == null) {
                // If no previous element was returned by the iterator, there's nothing to remove.
                return;
            }

            // Decrements the size of the list to account for the removed element.
            size--;

            if (prevToReturn.prev == null && prevToReturn.next == null) {
                // Case: The list has only one element, which is being removed.
                head = null; // Sets the head of the list to null.
                tail = null; // Sets the tail of the list to null.
            } else {
                // Case: The list contains more than one element.

                if (prevToReturn.prev == null) {
                    // Removing the head element.
                    prevToReturn.next.prev = null; // Sets the previous node of the next element to null.
                    head = head.next; // Updates the head of the list to the next element.
                } else {
                    // Linking the previous node of the current element to the next node of the current element.
                    prevToReturn.prev.next = prevToReturn.next;
                }

                if (prevToReturn.next == null) {
                    // Removing the tail element.
                    if (prevToReturn.prev != null) {
                        prevToReturn.prev.next = null; // Ensures the previous node points to null.
                    }
                    tail = prevToReturn.prev; // Updates the tail of the list.
                } else {
                    // Linking the next node of the current element to the previous node of the current element.
                    prevToReturn.next.prev = prevToReturn.prev;
                }
            }

            // Updating the prevToReturn to point to the previous node of the removed element.
            prevToReturn = prevToReturn.prev;
        }
       
    }
}
