import java.lang.reflect.Method;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestKthLargest {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> solutionClass;
    private static Method kthLargestMethod;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q6 Kth Largest Element — Test Results");
        System.out.println("=========================================================");

        if (!compileStudentCode()) {
            System.out.println();
            System.out.println("Student code could not be compiled.");
            System.out.println("Tests cannot be executed.");
            System.exit(1);
        }

        if (!loadStudentClass()) {
            System.out.println();
            System.out.println("Could not load the required class/method.");
            System.out.println("Make sure the class and method names match the problem.");
            System.exit(1);
        }

        // Test 1 — Example 1
        runTest(
                "test_01_basic_example",
                new int[]{7, 10, 4, 3, 20, 15},
                3,
                10
        );

        // Test 2 — Example 2
        runTest(
                "test_02_second_largest",
                new int[]{3, 2, 1, 5, 6, 4},
                2,
                5
        );

        // Test 3 — Example 3
        runTest(
                "test_03_duplicate_scores",
                new int[]{5, 5, 3, 2, 8, 1},
                4,
                3
        );

        // Test 4 — k = 1, largest element
        runTest(
                "test_04_largest_element",
                new int[]{12, 45, 7, 89, 34, 23},
                1,
                89
        );

        // Test 5 — k = array length, smallest element
        runTest(
                "test_05_smallest_element",
                new int[]{12, 45, 7, 89, 34, 23},
                6,
                7
        );

        // Test 6 — Negative values
        runTest(
                "test_06_negative_values",
                new int[]{-10, -3, -7, -1, -20, -5},
                3,
                -5
        );

        // Test 7 — Mixed positive, zero and negative values
        runTest(
                "test_07_mixed_values",
                new int[]{-5, 0, 12, -2, 8, 3, -10},
                4,
                0
        );

        // Test 8 — All values are equal
        runTest(
                "test_08_all_duplicate_values",
                new int[]{9, 9, 9, 9, 9, 9},
                4,
                9
        );

        // Test 9 — Many duplicate values
        runTest(
                "test_09_many_duplicates",
                new int[]{10, 10, 8, 8, 8, 5, 5, 2},
                5,
                8
        );

        // Test 10 — Larger array
        runTest(
                "test_10_larger_array",
                new int[]{
                    41, 7, 93, 18, 56,
                    2, 77, 34, 65, 11,
                    88, 29, 50, 73, 6
                },
                7,
                50
        );

        System.out.println("---------------------------------------------------------");
        System.out.println("  Score: " + passed + "/10");
        System.out.println("  Marks: " + (passed * 10) + "/100");
        System.out.println("=========================================================");

        System.exit(failed > 0 ? 1 : 0);
    }

    private static boolean compileStudentCode() {

        try {

            JavaCompiler compiler =
                    ToolProvider.getSystemJavaCompiler();

            if (compiler == null) {

                System.out.println(
                        "Java compiler is not available."
                );

                System.out.println(
                        "Make sure a JDK is installed."
                );

                return false;
            }

            int result = compiler.run(
                    null,
                    null,
                    null,
                    "KthLargest.java"
            );

            return result == 0;

        } catch (Exception e) {

            System.out.println(
                    "Compilation error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    private static boolean loadStudentClass() {

        try {

            solutionClass =
                    Class.forName("KthLargest");

            kthLargestMethod =
                    solutionClass.getDeclaredMethod(
                            "kthLargest",
                            int[].class,
                            int.class
                    );

            kthLargestMethod.setAccessible(true);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error loading student code:"
            );

            System.out.println(
                    e.getMessage()
            );

            return false;
        }
    }

    private static void runTest(
            String testName,
            int[] scores,
            int k,
            int expected
    ) {

        try {

            Object result =
                    kthLargestMethod.invoke(
                            null,
                            scores,
                            k
                    );

            int actual =
                    ((Number) result).intValue();

            check(
                    testName,
                    expected,
                    actual
            );

        } catch (Exception e) {

            System.out.println(
                    "  ✗ FAIL  [" + testName + "]"
            );

            Throwable cause = e.getCause();

            if (cause != null) {

                System.out.println(
                        "           "
                        + cause.getClass().getSimpleName()
                        + ": "
                        + cause.getMessage()
                );

            } else {

                System.out.println(
                        "           "
                        + e.getMessage()
                );
            }

            failed++;
        }
    }

    private static void check(
            String name,
            int expected,
            int actual
    ) {

        if (expected == actual) {

            System.out.println(
                    "  ✓ PASS  [" + name + "]"
            );

            passed++;

        } else {

            System.out.println(
                    "  ✗ FAIL  [" + name + "]"
            );

            System.out.println(
                    "           expected: " + expected
            );

            System.out.println(
                    "           got:      " + actual
            );

            failed++;
        }
    }
}