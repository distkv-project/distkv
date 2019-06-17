package org.dst.core.exception;

public class NotImplementException extends RuntimeException {

    public NotImplementException() {
    }

    /**
     * If the method don't be implemented, this method
     * will show a messgae
     *
     * @param message the exception message to display
     */
    public NotImplementException(String message) {
        super(message);
    }

}
