package org.pytenix.exceptions;

public class FileNotSaveableException  extends RuntimeException {

    public FileNotSaveableException(String fileName,Exception e)
    {
        System.out.println("Could not save file: "+ fileName + " Info: " + e.getMessage());
    }
}
