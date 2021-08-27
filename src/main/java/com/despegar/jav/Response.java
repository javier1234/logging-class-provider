package com.despegar.jav;

public class Response {

    private final int read;
    private final boolean end;
    private final String data;

    public Response(int read, boolean end, String data) {
        this.read = read;
        this.end = end;
        this.data = data;
    }

    public int read() {
        return this.read;
    }

    public boolean end() {
        return this.end;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "read=" + read +
                ", end=" + end +
                ", data='" + data + '\'' +
                '}';
    }
}
