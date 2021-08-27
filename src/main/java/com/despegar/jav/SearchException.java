package com.despegar.jav;

public class SearchException extends RuntimeException {

    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Exception e) {
        super(msg, e);
    }

}
