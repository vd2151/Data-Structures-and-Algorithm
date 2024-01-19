package project4;
import java.io.*;
import java.util.*;

/**
 * This class extends the File class and represents a file on the disk with its functionality.
 * @Author Vedant_Desai
 * @version 11-09-2023  
 */

class FileOnDisk extends File{

    private File directory;
    private long size;
    private String path; 
    private ArrayList<FileOnDisk> pathSizeAndName;
    private FileOnDiskComparatorBySize f_size;

    /**
     * Constructs a FileOnDisk object with the given directory path.
     * @param pathname the directory path for the FileOnDisk
     * @throws NullPointerException if the directory path is null
     */

    public FileOnDisk(String pathname) throws NullPointerException{
        super(pathname);
        if(pathname==null){
            throw new NullPointerException("Pathname cannot be null");
        } 
        try{
            directory = new File(pathname);
            path = directory.getCanonicalPath();
            f_size = new FileOnDiskComparatorBySize();
            size = directory.length();
            pathSizeAndName = new ArrayList<FileOnDisk>();
            if(directory.isFile()){
                size = directory.length();
            }
            else{
                size = getTotalSize();
            }
        }
        catch(Exception ex){
            System.err.println();
        }
    }

    /**
     * Retrieves the path of the FileOnDisk.
     * @return a String representing the path of the FileOnDisk
     */

    public String path(){
        return path;
    }

    /**
     * Computes the total size of files within the specified directory and its sub_directories.
     * @param document an array of File objects representing files and directories
     * @param count the current count of files processed within the array
     * @param len the length of the File array
     * @return the cumulative size of files within the specified directory.
     */

    public long size(File document[], int len, int count){
        if(count>=len){
            return 0;
        }
        else if(document[count].isFile()){
            long l_1 = document[count].length();
            try{
                pathSizeAndName.add(new FileOnDisk(document[count].getAbsolutePath()));
            }
            catch(Exception e){
                System.err.println();
            }
            if(count-1==len){
                return l_1;
            }
            else{
                return l_1 + size(document,len,count+1);
            }
        }
        else{
            File f[] = document[count].listFiles();
            if(count-1==len){
                return size(f,f.length,0);
            }
            else{
                return size(document[count].listFiles(),f.length,0) + size(document,len,count+1);
            }
        }
    }

     /**
     * Recursively sorts the FileOnDisk objects based on their sizes using a custom comparison method.
     * @param i the starting index for sorting
     * @param j the ending index for sorting
     * @param size the size of the list
     * @param numOfFiles the number of files to sort
     * @return a List of sorted FileOnDisk objects based on their sizes
     */

    private List <FileOnDisk> sortFiles(int i,int j,int size,int numOfFiles){
        if(i==numOfFiles)
            return pathSizeAndName.subList(0, numOfFiles);
        else{
            if(j==i){
                j= size-1;
                i+=1;
            }
            else{
                if(f_size.compare(pathSizeAndName.get(j-1), pathSizeAndName.get(j))>0){
                    Collections.swap(pathSizeAndName,j-1,j);
            }
            j-=1;
        }
            return sortFiles(i, j,size,numOfFiles);
        }
    }

    /**
     * Retrieves the total size of files within the directory and its subdirectories.
     *
     * @return the total size of files within the directory and its subdirectories
     */

    public long getTotalSize(){
        if(directory==null){
            return -1;
        }
        else if(directory.isFile()){
            return directory.length();
        }
        File arr[] = directory.listFiles();
        if(arr==null){
            return -1; 
        }
        return size(arr,arr.length,0);
    }

    /**
     * Retrieves a list of the largest files within the directory.
     * @param numOfFiles the number of largest files to retrieve
     * @return a List of FileOnDisk objects representing the largest files
     */

    public List<FileOnDisk> getLargestFiles(int numOfFiles){
        if(directory.isFile()){
            return null; 
        }
        int length = pathSizeAndName.size();
        if(length<numOfFiles){
            numOfFiles = length;
        }
        return sortFiles(0,length-1,length,numOfFiles);
    }

     /**
     * Generates a string representation of the FileOnDisk, displaying size, unit, and path information.
     * @return a formatted string representing the size, unit, and path of the FileOnDisk
     */

    @Override
    public String toString(){
        String unit = "bytes";
        double actualSize = (double)size;
        while(actualSize>=1024 && !unit.equals("GB")){
            actualSize=actualSize/1024;
            if(unit.equals("bytes")){
                unit = "KB";
            }
            else if(unit.equals("KB")){
                unit = "MB";
            }
            else{
                unit = "GB";
            }
        }
        if(unit.equals("bytes")){
            return String.format("%8.2f %s  %s", actualSize, unit, path);
        }
        else{
            return String.format("%8.2f %s     %s", actualSize, unit, path);
        }
    }
}