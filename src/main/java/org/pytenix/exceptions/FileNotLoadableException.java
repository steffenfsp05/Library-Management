package org.pytenix.exceptions;

public class FileNotLoadableException extends RuntimeException {

    public FileNotLoadableException(String fileName,Exception e)
    {
        System.out.println("Could not load file: "+ fileName + " Info: " + e.getMessage());
    }

}
