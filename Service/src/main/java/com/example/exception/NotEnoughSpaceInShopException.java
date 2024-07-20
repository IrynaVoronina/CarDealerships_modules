package com.example.exception;

public class NotEnoughSpaceInShopException extends RuntimeException {

    public NotEnoughSpaceInShopException(String message) {
        super(message);
    }
}
