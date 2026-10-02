import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestLatest2020Login {

    private static int passed = 0;
    private static int failed = 0;

    private static SqlTestConfig config;

    private static final int TIMEOUT_SECONDS = 5;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q9 Latest 2020 Login — Test Results");
        System.out.println("=========================================================");

        try {
            config = SqlTestConfig.load();
        } catch (Exception e) {
            System.out.println();
            System.out.println("CodeSprint SQL database is not configured.");
            System.out.println("Run the organizer setup script once on this PC:");
            System.out.println("  setup\\setup-windows.ps1  (Windows)");
            System.out.println("  setup/setup-linux.sh       (Linux/macOS)");
            System.out.println();
            System.out.println("Details: " + e.getMessage());
            System.exit(1);
        }

        String studentSql;

        try {
            studentSql = Files.readString(
                    Path.of("Latest2020Login.sql"),
                    StandardCharsets.UTF_8
            ).trim();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not read Latest2020Login.sql.");
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
            return;
        }

        if (studentSql.isBlank() || studentSql.equals("-- Write your SQL query here")) {
            System.out.println();
            System.out.println("Student SQL query is empty.");
            System.exit(1);
        }

        TestCase[] tests = new TestCase[] {
            new TestCase(
                "test_01_basic_multiple_users",
                "INSERT INTO Logins VALUES (1,'2020-01-01 12:00:00'),(1,'2020-02-10 09:30:00'),(2,'2020-03-15 18:20:00'),(1,'2021-01-05 10:00:00');",
                "1\t2020-02-10 09:30:00\n2\t2020-03-15 18:20:00"
            ),
            new TestCase(
                "test_02_multiple_logins_same_user",
                "INSERT INTO Logins VALUES (1,'2020-01-01 08:00:00'),(1,'2020-04-01 08:00:00'),(1,'2020-12-31 23:59:59');",
                "1\t2020-12-31 23:59:59"
            ),
            new TestCase(
                "test_03_ignore_2019",
                "INSERT INTO Logins VALUES (1,'2019-12-31 23:59:59'),(2,'2019-06-01 10:00:00'),(2,'2020-01-02 10:00:00');",
                "2\t2020-01-02 10:00:00"
            ),
            new TestCase(
                "test_04_ignore_2021",
                "INSERT INTO Logins VALUES (1,'2020-12-31 23:59:59'),(1,'2021-01-01 00:00:00'),(2,'2021-05-05 12:00:00');",
                "1\t2020-12-31 23:59:59"
            ),
            new TestCase(
                "test_05_single_login",
                "INSERT INTO Logins VALUES (7,'2020-07-15 14:45:00');",
                "7\t2020-07-15 14:45:00"
            ),
            new TestCase(
                "test_06_different_latest_dates",
                "INSERT INTO Logins VALUES (1,'2020-05-01 10:00:00'),(1,'2020-05-20 10:00:00'),(2,'2020-02-01 09:00:00'),(2,'2020-09-01 09:00:00'),(3,'2020-11-11 11:11:11');",
                "1\t2020-05-20 10:00:00\n2\t2020-09-01 09:00:00\n3\t2020-11-11 11:11:11"
            ),
            new TestCase(
                "test_07_many_logins",
                "INSERT INTO Logins VALUES (4,'2020-01-01 00:00:00'),(4,'2020-02-01 00:00:00'),(4,'2020-03-01 00:00:00'),(4,'2020-04-01 00:00:00'),(4,'2020-05-01 00:00:00');",
                "4\t2020-05-01 00:00:00"
            ),
            new TestCase(
                "test_08_mixed_years",
                "INSERT INTO Logins VALUES (1,'2018-01-01 00:00:00'),(1,'2020-06-10 12:00:00'),(1,'2022-01-01 00:00:00'),(2,'2019-01-01 00:00:00'),(2,'2020-08-10 12:00:00'),(2,'2021-01-01 00:00:00'),(3,'2020-09-09 09:09:09');",
                "1\t2020-06-10 12:00:00\n2\t2020-08-10 12:00:00\n3\t2020-09-09 09:09:09"
            ),
            new TestCase(
                "test_09_same_date_different_times",
                "INSERT INTO Logins VALUES (1,'2020-10-10 08:00:00'),(1,'2020-10-10 12:00:00'),(1,'2020-10-10 18:00:00'),(2,'2020-10-10 00:01:00');",
                "1\t2020-10-10 18:00:00\n2\t2020-10-10 00:01:00"
            ),
            new TestCase(
                "test_10_larger_mixed_dataset",
                "INSERT INTO Logins VALUES (1,'2019-01-01 00:00:00'),(1,'2020-01-15 10:00:00'),(1,'2020-06-20 20:00:00'),(2,'2020-03-03 03:03:03'),(2,'2021-03-03 03:03:03'),(3,'2020-12-01 01:01:01'),(3,'2020-12-31 23:59:59'),(4,'2018-05-05 05:05:05'),(5,'2020-02-29 12:00:00'),(5,'2020-12-30 12:00:00');",
                "1\t2020-06-20 20:00:00\n2\t2020-03-03 03:03:03\n3\t2020-12-31 23:59:59\n5\t2020-12-30 12:00:00"
            )
        };

        for (TestCase test : tests) {
            runTest(test, studentSql);
        }

        System.out.println("---------------------------------------------------------");
        System.out.println("  Score: " + passed + "/10");
        System.out.println("=========================================================");

        System.exit(failed > 0 ? 1 : 0);
    }

    private static void runTest(TestCase test, String studentSql) {
        try {
            String setup =
                    "DROP TABLE IF EXISTS Logins;"
                    + "CREATE TABLE Logins (user_id INT, time_stamp DATETIME);"
                    + test.insertSql;

            CommandResult setupResult = executeMysql(setup);

            if (!setupResult.success) {
                fail(test.name, "Could not prepare test data: " + setupResult.output);
                return;
            }

            CommandResult queryResult = executeMysql(studentSql);

            if (!queryResult.success) {
                fail(test.name, "SQL error: " + queryResult.output);
                return;
            }

            String actual = normalize(queryResult.output);
            String expected = normalize(test.expected);

            if (actual.equals(expected)) {
                System.out.println("  PASS  [" + test.name + "]");
                passed++;
            } else {
                System.out.println("  FAIL  [" + test.name + "]");
                System.out.println("           Expected:");
                printIndented(expected);
                System.out.println("           Got:");
                printIndented(actual);
                failed++;
            }
        } catch (Exception e) {
            fail(test.name, "Error: " + e.getMessage());
        }
    }

    private static CommandResult executeMysql(String sql) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(config.mysqlClient);
        command.add("--batch");
        command.add("--skip-column-names");
        command.add("--raw");
        command.add("--protocol=TCP");
        command.add("-h");
        command.add(config.host);
        command.add("-P");
        command.add(config.port);
        command.add("-u");
        command.add(config.user);
        command.add(config.database);

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);

        builder.environment().put("MYSQL_PWD", config.password);

        Process process = builder.start();

        try (OutputStream input = process.getOutputStream()) {
            input.write(sql.getBytes(StandardCharsets.UTF_8));
            input.write('\n');
        }

        boolean finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);

        if (!finished) {
            process.destroyForcibly();
            return new CommandResult(false,
                    "Query timed out after " + TIMEOUT_SECONDS + " seconds.");
        }

        String output = readAll(process.getInputStream());

        return new CommandResult(
                process.exitValue() == 0,
                output.trim()
        );
    }

    private static String readAll(InputStream input) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int count;

        while ((count = input.read(buffer)) != -1) {
            output.write(buffer, 0, count);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    private static String normalize(String value) {
        String normalized = value == null
                ? ""
                : value.replace("\r\n", "\n")
                       .replace("\r", "\n")
                       .trim();

        if (normalized.isEmpty()) {
            return "";
        }

        String[] lines = normalized.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String cleaned = line.trim();
            if (cleaned.isEmpty()) {
                continue;
            }
            if (result.length() > 0) {
                result.append('\n');
            }
            result.append(cleaned);
        }

        return result.toString();
    }

    private static void printIndented(String value) {
        if (value.isEmpty()) {
            System.out.println("           <empty>");
            return;
        }

        for (String line : value.split("\n")) {
            System.out.println("           " + line);
        }
    }

    private static void fail(String testName, String message) {
        System.out.println("  FAIL  [" + testName + "]");
        System.out.println("           " + message);
        failed++;
    }

    private static class TestCase {
        final String name;
        final String insertSql;
        final String expected;

        TestCase(String name, String insertSql, String expected) {
            this.name = name;
            this.insertSql = insertSql;
            this.expected = expected;
        }
    }

    private static class CommandResult {
        final boolean success;
        final String output;

        CommandResult(boolean success, String output) {
            this.success = success;
            this.output = output;
        }
    }
}
