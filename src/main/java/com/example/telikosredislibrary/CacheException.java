package com.example.telikosredislibrary;

public class CacheException extends RuntimeException {
    String message;

    public CacheException(String message) {
        super(message);
        this.message = message;
    }
}
