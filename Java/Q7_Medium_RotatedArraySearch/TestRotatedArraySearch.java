import java.lang.reflect.Method;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestRotatedArraySearch {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> solutionClass;
    private static Method searchMethod;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q7 Rotated Sorted Array Search — Test Results");
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
                "test_01_basic_rotated_array",
                new int[]{6, 7, 8, 1, 2, 3, 4, 5},
                3,
                5
        );

        // Test 2 — Example 2
        runTest(
                "test_02_target_after_rotation",
                new int[]{4, 5, 6, 7, 0, 1, 2},
                0,
                4
        );

        // Test 3 — Example 3
        runTest(
                "test_03_target_not_found",
                new int[]{6, 7, 8, 1, 2, 3, 4, 5},
                9,
                -1
        );

        // Test 4 — Unrotated sorted array
        runTest(
                "test_04_unrotated_array",
                new int[]{1, 2, 3, 4, 5, 6, 7},
                6,
                5
        );

        // Test 5 — Single element, target present
        runTest(
                "test_05_single_element_present",
                new int[]{10},
                10,
                0
        );

        // Test 6 — Single element, target absent
        runTest(
                "test_06_single_element_absent",
                new int[]{10},
                5,
                -1
        );

        // Test 7 — Two-element rotated array
        runTest(
                "test_07_two_element_rotated",
                new int[]{2, 1},
                1,
                1
        );

        // Test 8 — Negative and zero values
        runTest(
                "test_08_negative_and_zero_values",
                new int[]{0, 1, 2, -4, -3, -2, -1},
                -3,
                4
        );

        // Test 9 — Target at rotation boundary
        runTest(
                "test_09_rotation_boundary",
                new int[]{5, 6, 7, 8, 1, 2, 3, 4},
                1,
                4
        );

        // Test 10 — Larger rotated array
        runTest(
                "test_10_large_rotated_array",
                new int[]{
                        50, 60, 70, 80, 90,
                        100, 110, 10, 20, 30,
                        40
                },
                20,
                8
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
                    "RotatedArraySearch.java"
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
                    Class.forName("RotatedArraySearch");

            searchMethod =
                    solutionClass.getDeclaredMethod(
                            "search",
                            int[].class,
                            int.class
                    );

            searchMethod.setAccessible(true);

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
            int[] arr,
            int target,
            int expected
    ) {

        try {

            Object result =
                    searchMethod.invoke(
                            null,
                            arr,
                            target
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