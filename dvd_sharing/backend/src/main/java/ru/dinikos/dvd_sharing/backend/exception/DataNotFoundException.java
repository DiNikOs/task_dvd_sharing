/**
 * @author Ostrovskiy Dmitriy
 * @created 19.06.2020
 * DataNotFoundException
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
