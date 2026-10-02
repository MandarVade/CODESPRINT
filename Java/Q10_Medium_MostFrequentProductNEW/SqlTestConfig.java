import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

/**
 * Loads CodeSprint SQL judge configuration.
 *
 * Configuration is created once by the organizer setup script at:
 *   ~/.codesprint/db.properties
 *
 * Environment variables remain supported as optional overrides for debugging.
 */
final class SqlTestConfig {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_USER = "codesprint";
    private static final String DEFAULT_DATABASE = "codesprint";
    private static final String DEFAULT_MYSQL_CLIENT = "mysql";

    final String host;
    final String port;
    final String user;
    final String database;
    final String password;
    final String mysqlClient;

    private SqlTestConfig(String host, String port, String user, String database,
                          String password, String mysqlClient) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.database = database;
        this.password = password;
        this.mysqlClient = mysqlClient;
    }

    static SqlTestConfig load() throws IOException {
        Properties file = new Properties();
        Path configPath = Path.of(System.getProperty("user.home"), ".codesprint", "db.properties");

        if (Files.exists(configPath)) {
            try (InputStream input = Files.newInputStream(configPath)) {
                file.load(input);
            }
        }

        Map<String, String> env = System.getenv();

        String host = value(env, "CODESPRINT_DB_HOST", file, "host", DEFAULT_HOST);
        String port = value(env, "CODESPRINT_DB_PORT", file, "port", DEFAULT_PORT);
        String user = value(env, "CODESPRINT_DB_USER", file, "user", DEFAULT_USER);
        String database = value(env, "CODESPRINT_DB_NAME", file, "database", DEFAULT_DATABASE);
        String password = value(env, "CODESPRINT_DB_PASSWORD", file, "password", null);
        String mysqlClient = value(env, "MYSQL_CLIENT", file, "mysql-client", DEFAULT_MYSQL_CLIENT);

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "CodeSprint database is not configured. Run the organizer setup script first: "
                    + "setup\\setup-windows.ps1 (Windows) or setup/setup-linux.sh (Linux/macOS)."
            );
        }

        return new SqlTestConfig(host, port, user, database, password, mysqlClient);
    }

    private static String value(Map<String, String> env, String envName,
                                Properties file, String fileName, String fallback) {
        String envValue = env.get(envName);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String fileValue = file.getProperty(fileName);
        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue.trim();
        }

        return fallback;
    }
}
