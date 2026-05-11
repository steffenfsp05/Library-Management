package org.pytenix.exceptions;

/**
 * Thrown when the application fails to read or parse the JSON repository files
 * during startup.
 */
public class FileNotLoadableException extends RuntimeException {

    public FileNotLoadableException(String fileName, Exception e) {
        System.out.println("Could not load file: " + fileName + " Info: " + e.getMessage());
    }

}
