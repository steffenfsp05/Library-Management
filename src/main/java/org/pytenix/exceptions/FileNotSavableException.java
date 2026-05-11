package org.pytenix.exceptions;

/**
 * Thrown when the application fails to write the current memory state to the disk.
 */
public class FileNotSavableException extends RuntimeException {

    public FileNotSavableException(String fileName, Exception e) {
        System.out.println("Could not save file: " + fileName + " Info: " + e.getMessage());
    }
}
