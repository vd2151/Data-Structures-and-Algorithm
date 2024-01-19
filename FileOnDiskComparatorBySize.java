package project4;
import java.util.*;
/**
 * The FileOnDiskComparatorBySize class implements the Comparator interface
 * Compares instances of FileOnDisk based on their sizes.
 * @Author Vedant_Desai
 * @version 11-09-2023 
 */
class FileOnDiskComparatorBySize implements Comparator<FileOnDisk>{ 
    /**
     * Default constructor for FileOnDiskComparatorBySize.
     */
    public FileOnDiskComparatorBySize(){
    ;    
    }

    /**
     * Compares two FileOnDisk objects based on their sizes.
     * @param file_1 the first FileOnDisk object to be compared
     * @param file_2 the second FileOnDisk object to be compared
     * @return an integer value that indicates the comparison result
     *         - a positive integer if file_1 is larger than file_2
     *         - a negative integer if file_1 is smaller than file_1
     *          - zero if file_1 and file_2 have the same size
     * @throws NullPointerException if either file_1 or file_2 is null
     * @throws ClassCastException if either file_1 or file_2 is not an instance of FileOnDisk
     */

    public int compare(FileOnDisk file_1, FileOnDisk file_2) throws NullPointerException, ClassCastException{
        if(file_1 == null || file_2 == null){
            throw new NullPointerException("Objects that have null references could not be compared");
        }
        if(file_1==file_2){
            return 0;
        }
        if(!(file_1 instanceof FileOnDisk) || !(file_2 instanceof FileOnDisk)){
            throw new ClassCastException("One or more objects are not the instance of FileOnDisk");
        }
        long size1 = file_1.length();
        long size2 = file_2.length();
        long sizeDifference = size2 - size1;

        if(size1==size2){
            return (int)Math.signum(file_1.getPath().compareTo(file_2.getPath()));
        }
            return (int)Math.signum((int)sizeDifference);
    }
}
