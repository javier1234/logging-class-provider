package com.despegar.jav;

import java.util.Random;

/**
 * Simula la conexion a una base de datos a efectos didacticos del ejericio final de la clase de logging
 *
 * Porcentaje de errores en el host = dbUserDespegar.com
 * Porcentaje alto de errores en el host = dbUserOld.com
 *
 * Con el parametro puerto, deteminamos la duracion de una corrida del programa, de la forma port - 3000 = segundos de corrida aprox
 */
public class ConnectorDummy implements Connector {

    private final Random random;
    private int hits;
    private final String user;
    private final String pass;
    private final double percentageError;
    private final int maxHints;

    private ConnectorDummy(String host, int port, String user, String pass) {
        this.pass = pass;
        this.user = user;
        this.random = new Random(42);
        this.hits = 0;
        this.maxHints = port - 3000;
        this.percentageError = DBEnvironment.fromUrl(host).getErrorPercentage();
    }

    public static Connector newInstance(String host, int port, String user, String pass) {
        return new ConnectorDummy(host, port, user, pass);
    }

    public Response search(Request request) {
        try {
            if (random.nextFloat() < this.percentageError)
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
            if (random.nextFloat() < this.percentageError)
                throw new SearchException("Failed to scroll");
            Thread.sleep(random.nextInt() % 1000 + 1000);
            hits = hits + 1;
            if (hits < this.maxHints)
                return new Response(request.limit(), false, createDataResponseDummy());
            else if (hits == this.maxHints)
                return new Response(request.limit() / 2, true, createDataResponseDummy());
            else
                throw new SearchException("Empty scroll");
        } catch (InterruptedException e) {
            throw new SearchException("Failed to sleep", e);
        }
    }

    public void clearScroll() {
        try {
            if (random.nextFloat() < this.percentageError)
                throw new SearchException("Failed to clear scroll");
            boolean end = hits == this.maxHints;
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