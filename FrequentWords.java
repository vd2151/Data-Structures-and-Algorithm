package project5;

/***********************************************************************/
/*                    DO  NOT  MODIFY  THIS  FILE                      */
/***********************************************************************/


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The FrequentWords program parses an input text file and uses different data
 * structures to compute the frequency of words that appear in the input file.
 * It produces a list of most frequent words.
 *
 * Usage: The program is called from the command line and expects three
 * parameters: java FrequentWords inputFile cutOffValue outputFile
 *
 * inputFile is a text file containing words to be counted cutOfValue is a
 * positive integer, only the words whose count is equal to or grater than the
 * cutOfValue are reported on and printed to the output file outputFile is a
 * text file containing results of the computations, it prints all the words
 * that occur at least cutOfValue many times in the input file in alphabetical
 * order, one per line together with their counts
 *
 * @author Joanna Klukowska
 * @version November 17, 2023
 *
 */
public class FrequentWords {

    /*
     * @param args command line arguments as described above
     */
    public static void main(String[] args) {
        // timers used for performance evaluation of different data structures
        long start, end;

        // verify number of command line arguments
        if (args.length < 3) {
            System.err.printf("\nERROR : missing parameters. \n\n");
            System.exit(1);
        }

        // make sure that the output file argument is valid
        File outFile = new File(args[2]);
        PrintWriter out = null;

        if (outFile.canWrite() || !outFile.exists()) {
            try {
                out = new PrintWriter(outFile);
            } catch (FileNotFoundException e) {
                System.err.println("ERROR: problem encountered opening output file.");
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        // initial storage of all words from the input file
        ArrayList<String> allWords = new ArrayList<String>();

        // FileParser object processes the input file and produces a list
        // containing all words found in the input file
        FileParser fp = null;
        try {
            fp = new FileParser(args[0]);
        } catch (IOException e) {
            System.err.println("ERROR: problem encountered reading file:");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // process the input file and display information about processing time
        // and number of words read
        start = System.nanoTime();
        allWords = fp.getAllWords();
        end = System.nanoTime();
        System.out.printf("INFO: Reading file took %d ms (~ %7.3f seconds).\n", (end - start) / 1000,
                          (end - start) / 1000000000.0);

        System.out.printf("INFO: %d words read. \n", allWords.size());

        // wordList can be instantiated to different data structures
        // as long as they implement the Index interface;
        // it is used for keeping words and their frequencies
        Index wordList1 = null;
        Index wordList2 = null;

        // process the words using a sorted linked list
        wordList1 = new SortedLinkedList();
        runTest(wordList1, allWords, System.out, Integer.valueOf(args[1]));

        // process the words using iterative bst
        wordList2 = new BSTIndex();
        runTest(wordList2, allWords, System.out, Integer.valueOf(args[1]));

        // verify that the two objects are the same
        if (!wordList1.equals(wordList2)) {
            System.out.printf("ERROR: two word lists are not the same\n\n");

            Iterator<Word> itr1 = wordList1.iterator();
            Iterator<Word> itr2 = wordList2.iterator();

            System.out.printf("%-20s%-20s\n", "wordList1:", "wordList2:");
            while (itr1.hasNext() && itr2.hasNext()) {
                System.out.printf("%-20s%-20s\n", itr1.next(), itr2.next());
            }
            while (itr1.hasNext()) {
                System.out.printf("%-20s%-20s\n", itr1.next(), "");
            }
            while (itr2.hasNext()) {
                System.out.printf("%-20s%-20s\n", "", itr2.next());
            }
        }

        // write data to the output file
        for (Word w : wordList1 ) {
            out.println( w );
        }
        // close the output stream
        out.close();
    }

    /**
     * Produces counts of each words in allWords list using index data structure and
     * retains only the words whose count is at least minCount. The information
     * about time used for processing and number of words remaining in the index
     * structure are printed to the out stream.
     *
     * @param index    data structure used for storing unique words and their counts
     * @param allWords list of all the words (contains multiple occurrences of
     *                 words)
     * @param out      output stream to which statistics should be printed
     * @param minCount smallest counter for words that should remain in the data
     *                 structure
     */
    public static void runTest(Index index, ArrayList<String> allWords,
                               PrintStream out, int minCount) {

        long start, end;
        String className = null;
        // print the information about data structure used
        if (index instanceof SortedLinkedList)
            className = "Sorted Linked List";
        else if (index instanceof BSTIndex)
            className = "Recursive BST";
        else
            className = " Index ";
        out.printf("\nProcessing using %s\n", className);

        // add words from allWords to the data structure referenced by index
        // and print the information
        start = System.nanoTime();
        populateIndex(index, allWords);
        end = System.nanoTime();
        out.printf("INFO: Creating index took %d ms (~ %7.3f seconds).\n",
                   (end - start) / 1000,
                   (end - start) / 1000000000.0);
        out.printf("INFO: %d words stored in index.\n", index.size());

        // prune the index by removing words whose count is smaller than minCount
        // and print the information
        start = System.nanoTime();
        pruneIndex(index, minCount);
        end = System.nanoTime();
        out.printf("INFO: Pruning index took %d ms (~ %7.3f seconds).\n",
                   (end - start) / 1000,
                   (end - start) / 1000000000.0);
        out.printf("INFO: %d words remaining after pruning.\n", index.size());

    }

    /**
     * Populates index data structure with words from allWords list. index stores
     * unique words and their counts.
     *
     * @param index    data structure to store unique words and their counts
     * @param allWords list of all the words (contains multiple occurrences of
     *                 words)
     */
    public static void populateIndex(Index index, ArrayList<String> allWords) {

        // for each word in allWords list
        for (String currentString : allWords) {
            index.add(currentString);
        }
    }

    /**
     * Prunes (removes) all words whose count is smaller than minCount.
     *
     * @param index    data structure to store unique words and their counts
     * @param minCount smallest counter for words that should remain in the data
     *                 structure
     */
    public static void pruneIndex(Index index, int minCount) {
        Iterator<Word> it = index.iterator();
        // get the first Word object from index
        Word tmp = null;
        // as long as we are not at the end of the list
        while (it.hasNext()) {
            tmp = it.next();
            // if the current Word object's count is smaller
            // than minCount, remove it from index
            if (tmp.getCount() < minCount) {
                it.remove();
            }
        }
    }
}