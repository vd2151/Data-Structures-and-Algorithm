package project5;

/**
 * This class represents a Word object, designed to store a string and its frequency (count).
 * It provides functionality to manage and access the word and its count.
 * This class also implements the Comparable interface to enable sorting based on the word's lexicographical order.
 * 
 * @author Vedant_Desai
 * @version December 03, 2023
 */
class Word implements Comparable<Word> {

    private String word; // The word string
    private int count;   // The frequency count of the word

    /**
     * Constructs a Word object with the specified string.
     * Initializes the word to the given string and count to 1.
     * Throws NullPointerException if the word is null, IllegalArgumentException if the word is empty.
     * @param word The string to be stored in the Word object.
     * @throws NullPointerException if the word is null.
     * @throws IllegalArgumentException if the word is an empty string.
     */
    public Word(String word) throws NullPointerException, IllegalArgumentException {
        if (word == null) 
            throw new NullPointerException("Word cannot be null!");
        if (word.isEmpty()) 
            throw new IllegalArgumentException("Word cannot be empty!");
        this.word = word;
        count = 1;
    }

    /**
     * Increments the count of the word by 1 and returns the new count.
     * @return The incremented count of the word.
     */
    public int incrementCount() {
        count += 1;
        return count;
    }

    /**
     * Returns the word string stored in the object.
     * @return The word string.
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the count of the word.
     * @return The count of the word.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns a string representation of the Word object.
     * The format includes the count, followed by the word string.
     * @return A formatted string representing the Word object.
     */
    public String toString() {
        String output = String.format("%5d  %s", getCount(), getWord());
        return output;
    }

    /**
     * Checks whether two Word objects are equal.
     * Two Word objects are considered equal if their strings are equal (ignoring case) and their counts are the same.
     * @param o The object to compare with this Word object.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Word))
            return false;
        if (this == o)
            return true;
        Word obj = (Word) o;
        return this.getWord().equalsIgnoreCase(obj.getWord()) && this.getCount() == obj.getCount();
    }

    /**
     * Compares this Word object with another Word object lexicographically.
     * @param ob The Word object to be compared.
     * @return A negative integer, zero, or a positive integer as this Word object
     *         is less than, equal to, or greater than the specified Word object.
     */
    @Override
    public int compareTo(Word ob) {
        return this.getWord().compareTo(ob.getWord());
    }
}
