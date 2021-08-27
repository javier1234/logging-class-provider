package com.despegar.jav;

import java.util.Random;

public class ConnectorDummy implements Connector {

    private Random random;
    private int hits;
    private String user;
    private String pass;

    private ConnectorDummy(String host, int port, String user, String pass) {
        this.pass = pass;
        this.user = user;
        random = new Random(42);
        hits = 0;
    }

    public static Connector getInstance(String host, int port, String user, String pass) {
        return new ConnectorDummy(host, port, user, pass);
    }

    public Response search(Request request) {
        try {
            if (random.nextFloat() < 0.5)
                throw new SearchException("Failed to search");
            Thread.sleep(random.nextInt() % 1000 + 1000);
            if (hits != 0)
                throw new SearchException("You can't fire 2 searches");
            hits = hits + 1;
            return new Response(request.limit(), false, createDataResponseDummy());
        } catch (InterruptedException e) {
            throw new SearchException("Failed to sleep", e);
        }
    }

    public Response scroll(Request request) {
        try {
            if (random.nextFloat() < 0.5)
                throw new SearchException("Failed to scroll");
            Thread.sleep(random.nextInt() % 1000 + 1000);
            hits = hits + 1;
            if (hits < 15)
                return new Response(request.limit(), false, createDataResponseDummy());
            else if (hits == 15)
                return new Response(request.limit() / 2, true, createDataResponseDummy());
            else
                throw new SearchException("Empty scroll");
        } catch (InterruptedException e) {
            throw new SearchException("Failed to sleep", e);
        }
    }

    public void clearScroll() {
        try {
            if (random.nextFloat() < 0.5)
                throw new SearchException("Failed to clear scroll");
            boolean end = hits == 15;
            hits = 0;
            Thread.sleep(random.nextInt() % 1000 + 1000);
            if (!end)
                throw new SearchException("Cannot clear scroll. More data is available.");
        } catch (InterruptedException e) {
            throw new SearchException("Failed to sleep", e);
        }
    }

    private String createDataResponseDummy() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(100000000)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}