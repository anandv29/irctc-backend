package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfig {

    private static final String host = System.getenv("MYSQLHOST");
    private static final String port = System.getenv("MYSQLPORT");
    private static final String db   = System.getenv("MYSQLDATABASE");
    private static final String user = System.getenv("MYSQLUSER");
    private static final String pass = System.getenv("MYSQLPASSWORD");

    private static final String jdbcUrl =
            "jdbc:mysql://" + host + ":" + port + "/" + db +
            "?useSSL=true&requireSSL=true&verifyServerCertificate=false" +
            "&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL Driver Loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, pass);
    }
}