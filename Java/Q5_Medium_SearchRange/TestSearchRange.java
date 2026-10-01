import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestSearchRange {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        if (!compileStudentCode()) {
            System.out.println("Compilation failed.");
            System.exit(1);
        }

        try {
            Class<?> solutionClass = Class.forName("SearchRange");

            Method searchRange = solutionClass.getDeclaredMethod(
                    "searchRange",
                    int[].class,
                    int.class
            );

            searchRange.setAccessible(true);

            // Test 1 - Example 1
            runTest(
                    searchRange,
                    new int[]{1, 2, 2, 2, 3, 4, 5},
                    2,
                    new int[]{1, 3}
            );

            // Test 2 - Example 2
            runTest(
                    searchRange,
                    new int[]{1, 2, 3, 4, 5},
                    6,
                    new int[]{-1, -1}
            );

            // Test 3 - Example 3
            runTest(
                    searchRange,
                    new int[]{2, 2, 2, 2, 2},
                    2,
                    new int[]{0, 4}
            );

            // Test 4 - Target occurs once
            runTest(
                    searchRange,
                    new int[]{1, 3, 5, 7, 9},
                    5,
                    new int[]{2, 2}
            );

            // Test 5 - Target occurs at the beginning
            runTest(
                    searchRange,
                    new int[]{2, 2, 2, 3, 4, 5},
                    2,
                    new int[]{0, 2}
            );

            // Test 6 - Target occurs at the end
            runTest(
                    searchRange,
                    new int[]{1, 2, 3, 7, 7, 7},
                    7,
                    new int[]{3, 5}
            );

            // Test 7 - Negative values
            runTest(
                    searchRange,
                    new int[]{-10, -5, -5, -5, 0, 4, 8},
                    -5,
                    new int[]{1, 3}
            );

            // Test 8 - Zero appears multiple times
            runTest(
                    searchRange,
                    new int[]{-3, -1, 0, 0, 0, 2, 5},
                    0,
                    new int[]{2, 4}
            );

            // Test 9 - Single-element array
            runTest(
                    searchRange,
                    new int[]{42},
                    42,
                    new int[]{0, 0}
            );

            // Test 10 - Large repeated range
            runTest(
                    searchRange,
                    new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 4, 5, 5, 5},
                    2,
                    new int[]{4, 8}
            );

            System.out.println();
            System.out.println("Score: " + passed + "/10");
            System.out.println("Marks: " + (passed * 10) + "/100");

            System.exit(failed > 0 ? 1 : 0);

        } catch (NoSuchMethodException e) {
            System.out.println("Required method not found.");
            System.out.println("Expected method:");
            System.out.println("public static int[] searchRange(int[] arr, int target)");
            System.exit(1);

        } catch (ClassNotFoundException e) {
            System.out.println("SearchRange class not found.");
            System.exit(1);

        } catch (Exception e) {
            System.out.println("Unexpected error:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static boolean compileStudentCode() {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.out.println("Java compiler is not available.");
            System.out.println("Please make sure a JDK is installed.");
            return false;
        }

        int result = compiler.run(
                null,
                null,
                null,
                "SearchRange.java"
        );

        return result == 0;
    }

    private static void runTest(
            Method method,
            int[] arr,
            int target,
            int[] expected
    ) {

        try {

            Object result = method.invoke(
                    null,
                    arr,
                    target
            );

            int[] actual = (int[]) result;

            if (Arrays.equals(actual, expected)) {

                passed++;

                System.out.println(
                        "Test " + (passed + failed)
                                + ": PASS"
                );

            } else {

                failed++;

                System.out.println(
                        "Test " + (passed + failed)
                                + ": FAIL"
                );

                System.out.println(
                        "Expected: " + Arrays.toString(expected)
                );

                System.out.println(
                        "Got:      " + Arrays.toString(actual)
                );
            }

        } catch (InvocationTargetException e) {

            failed++;

            System.out.println(
                    "Test " + (passed + failed) + ": FAIL"
            );

            System.out.println(
                    "Runtime error: " + e.getCause()
            );

        } catch (Exception e) {

            failed++;

            System.out.println(
                    "Test " + (passed + failed) + ": FAIL"
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}