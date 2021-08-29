package com.despegar.jav;

import java.util.Arrays;
import java.util.Optional;

public enum DBEnvironment {
    STABLE("dbUserDespegar.com", 0.05),
    UN_STABLE("dbUserOld.com", 0.5);

    private final double errorPercentage;
    private final String urlDB;

    DBEnvironment(String urlDB, double errorPercentage) {
        this.errorPercentage = errorPercentage;
        this.urlDB = urlDB;
    }

    public static DBEnvironment fromUrl(String urlDB) {
        return Arrays.stream(DBEnvironment.values())
                .filter(dbEnvironment -> dbEnvironment.urlDB.equals(urlDB))
                .findAny()
                .orElseThrow(() -> new RuntimeException("not found server in " + urlDB));
    }

    public double getErrorPercentage() {
        return errorPercentage;
    }

    public String getUrlDB() {
        return urlDB;
    }
}
