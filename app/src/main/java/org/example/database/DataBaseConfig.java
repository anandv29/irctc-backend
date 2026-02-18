package org.example.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseConfig {
    private static HikariDataSource dataSource;

    static {
        try {
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String db   = System.getenv("MYSQLDATABASE");
            String user = System.getenv("MYSQLUSER");
            String pass = System.getenv("MYSQLPASSWORD");

            // DEBUG: Print what we're reading
            System.out.println("🔍 DEBUG - Environment Variables:");
            System.out.println("  MYSQLHOST: " + (host != null ? "✓ Set" : "❌ NULL"));
            System.out.println("  MYSQLPORT: " + (port != null ? "✓ Set" : "❌ NULL"));
            System.out.println("  MYSQLDATABASE: " + (db != null ? "✓ Set" : "❌ NULL"));
            System.out.println("  MYSQLUSER: " + (user != null ? "✓ Set" : "❌ NULL"));
            System.out.println("  MYSQLPASSWORD: " + (pass != null ? "✓ Set" : "❌ NULL"));

            // Validate all required values are present
            if (host == null || port == null || db == null || user == null || pass == null) {
                throw new IllegalArgumentException("One or more required MySQL environment variables are missing!");
            }

            String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + db +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            System.out.println("🔌 JDBC URL: " + jdbcUrl);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(user);
            config.setPassword(pass);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            config.setMaximumPoolSize(5);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);

            dataSource = new HikariDataSource(config);

            System.out.println("✅ HikariCP successfully connected to Railway MySQL!");

        } catch (Exception e) {
            System.err.println("❌ Failed to initialize HikariCP: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized!");
        }
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}