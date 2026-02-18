static {
    try {
        HikariConfig config = new HikariConfig();

        String rawUrl = System.getenv("DATABASE_URL");

        // Convert Railway mysql:// → jdbc:mysql://
        if (rawUrl != null && rawUrl.startsWith("mysql://")) {
            rawUrl = rawUrl.replace("mysql://", "jdbc:mysql://");
        }

        config.setJdbcUrl(rawUrl);
        config.setUsername(System.getenv("DATABASE_USERNAME"));
        config.setPassword(System.getenv("DATABASE_PASSWORD"));
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Recommended for Railway MySQL
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("allowPublicKeyRetrieval", "true");
        config.addDataSourceProperty("serverTimezone", "UTC");

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);

        System.out.println("✅ HikariCP initialized successfully");
        System.out.println("🔗 DB URL: " + rawUrl);

    } catch (Exception e) {
        System.err.println("❌ Failed to initialize HikariCP: " + e.getMessage());
    }
}