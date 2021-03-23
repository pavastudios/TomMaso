package com.pavastudios.TomMaso.db;

import java.util.Locale;

public class DatiConnessione {
    private static final String DB_HOST     = "localhost";
    private static final int    DB_PORT     = 3306;
    private static final String DB_NAME     = "tommaso";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    public  static final String JDBC_STRING = String.format(Locale.US,
            "jdbc:mysql://%s:%d/%s?user=%s&pass=%s",
            DB_HOST,DB_PORT,DB_NAME,DB_USERNAME,DB_PASSWORD);
}

