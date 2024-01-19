package project5;

/***********************************************************************/
/*                    DO  NOT  MODIFY  THIS  FILE                      */
/***********************************************************************/

/**
 * An interface for container classes used for storing Word objects
 * in sorted order.
 *
 * @author Joanna Klukowska
 * @version Nov. 19, 2023
 */
public interface Index extends Iterable<Word> {

    /**
     * Adds an item to the index in sorted order. If the Word object
     * with the same string as item already exists, its count should be
     * incremented by one and no new Word objects should be created.
     *
     * @param item new item to be added
     * @throws IllegalArgumentException when item is null
     */
    void add(String item);

    /**
     * Removes an item from the index if it exists, otherwise the index remains
     * unchanged. This operation should remove the Word object matching the
     * item regardless of what the count is.
     *
     * @param item item to be removed
     */
    void remove(String item);

    /**
     * Returns the count of the Word object associated with the item, or -1
     * if such a Word object does not exits.
     *
     * @param item item whose count should be returned
     * @return the count associated with the item, or -1 if the item does not exist
     */
    int get(String item);

    /**
     * Returns number of unique words stored in the index.
     * NOTE: this counts each word only once even it the count associated
     * with a word is larger than one.
     *
     * @return number of items stored in the index.
     */
    int size();

}