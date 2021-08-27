package com.despegar.jav;

public class Request {

    private int limit;

    public Request(int limit) {
        this.limit = limit;
    }

    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return "Request{" +
                "limit=" + limit +
                '}';
    }
}
