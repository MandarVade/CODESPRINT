import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestMostFrequentProduct {

    private static int passed = 0;
    private static int failed = 0;

    private static SqlTestConfig config;

    private static final int TIMEOUT_SECONDS = 5;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q10 Most Frequently Ordered Product — Test Results");
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
                    Path.of("MostFrequentProduct.sql"),
                    StandardCharsets.UTF_8
            ).trim();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not read MostFrequentProduct.sql.");
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
                "test_01_single_customer_single_product",
                "INSERT INTO Orders VALUES (101,1,10);",
                "1\t10\t1"
            ),
            new TestCase(
                "test_02_different_frequencies",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,20),(104,1,30),(105,1,30),(106,1,30);",
                "1\t30\t3"
            ),
            new TestCase(
                "test_03_two_way_tie",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,20),(104,1,20),(105,1,30);",
                "1\t10\t2\n1\t20\t2"
            ),
            new TestCase(
                "test_04_three_way_tie",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,20),(104,1,20),(105,1,30),(106,1,30);",
                "1\t10\t2\n1\t20\t2\n1\t30\t2"
            ),
            new TestCase(
                "test_05_multiple_customers",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,20),(104,2,30),(105,2,30),(106,2,40),(107,3,50);",
                "1\t10\t2\n2\t30\t2\n3\t50\t1"
            ),
            new TestCase(
                "test_06_different_winners",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,10),(104,1,20),(105,2,20),(106,2,30),(107,2,30),(108,2,30),(109,2,30);",
                "1\t10\t3\n2\t30\t4"
            ),
            new TestCase(
                "test_07_large_customer",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,10),(104,1,10),(105,1,20),(106,1,20),(107,1,30),(108,1,30),(109,1,30),(110,1,30),(111,1,30);",
                "1\t30\t5"
            ),
            new TestCase(
                "test_08_products_once",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,20),(103,1,30),(104,2,40),(105,2,50);",
                "1\t10\t1\n1\t20\t1\n1\t30\t1\n2\t40\t1\n2\t50\t1"
            ),
            new TestCase(
                "test_09_multiple_customers_with_ties",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,20),(104,1,20),(105,2,30),(106,2,40),(107,2,40),(108,2,50),(109,2,50),(110,3,60),(111,3,70),(112,3,80),(113,3,80);",
                "1\t10\t2\n1\t20\t2\n2\t40\t2\n2\t50\t2\n3\t80\t2"
            ),
            new TestCase(
                "test_10_larger_mixed_dataset",
                "INSERT INTO Orders VALUES (101,1,10),(102,1,10),(103,1,10),(104,1,20),(105,1,20),(106,1,30),(107,2,40),(108,2,40),(109,2,50),(110,2,50),(111,2,60),(112,2,60),(113,2,60),(114,3,70),(115,3,80),(116,3,80),(117,3,90),(118,3,90),(119,3,90),(120,4,100),(121,4,100),(122,4,110);",
                "1\t10\t3\n2\t60\t3\n3\t90\t3\n4\t100\t2"
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
                    "DROP TABLE IF EXISTS Orders;"
                    + "CREATE TABLE Orders (order_id INT, customer_id INT, product_id INT);"
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
