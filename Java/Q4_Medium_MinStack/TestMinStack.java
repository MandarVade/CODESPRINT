import java.lang.reflect.Method;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestMinStack {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> stackClass;

    private static Method pushMethod;
    private static Method popMethod;
    private static Method topMethod;
    private static Method getMinMethod;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q4 Min Stack — Test Results");
        System.out.println("=========================================================");

        if (!compileStudentCode()) {
            System.out.println();
            System.out.println("Student code could not be compiled.");
            System.out.println("Tests cannot be executed.");
            System.exit(1);
        }

        if (!loadStudentClass()) {
            System.out.println();
            System.out.println("Could not load the required class/methods.");
            System.out.println("Make sure the class and method names match the problem.");
            System.exit(1);
        }

        // Test 1 — Example 1
        runTest01();

        // Test 2 — Example 2
        runTest02();

        // Test 3 — Example 3
        runTest03();

        // Test 4 — Negative values
        runTest04();

        // Test 5 — Minimum changes after pop
        runTest05();

        // Test 6 — Duplicate minimum values
        runTest06();

        // Test 7 — Top must not remove element
        runTest07();

        // Test 8 — Minimum at bottom
        runTest08();

        // Test 9 — Minimum at top
        runTest09();

        // Test 10 — Mixed values and multiple minimum changes
        runTest10();

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
                    "MinStack.java"
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

            stackClass =
                    Class.forName("MinStack");

            pushMethod =
                    stackClass.getDeclaredMethod(
                            "push",
                            int.class
                    );

            popMethod =
                    stackClass.getDeclaredMethod(
                            "pop"
                    );

            topMethod =
                    stackClass.getDeclaredMethod(
                            "top"
                    );

            getMinMethod =
                    stackClass.getDeclaredMethod(
                            "getMin"
                    );

            pushMethod.setAccessible(true);
            popMethod.setAccessible(true);
            topMethod.setAccessible(true);
            getMinMethod.setAccessible(true);

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

    private static Object createStack()
            throws Exception {

        return stackClass.getDeclaredConstructor()
                .newInstance();
    }

    private static void push(
            Object stack,
            int value
    ) throws Exception {

        pushMethod.invoke(
                stack,
                value
        );
    }

    private static int pop(
            Object stack
    ) throws Exception {

        return ((Number) popMethod.invoke(
                stack
        )).intValue();
    }

    private static int top(
            Object stack
    ) throws Exception {

        return ((Number) topMethod.invoke(
                stack
        )).intValue();
    }

    private static int getMin(
            Object stack
    ) throws Exception {

        return ((Number) getMinMethod.invoke(
                stack
        )).intValue();
    }

    private static void check(
            String testName,
            boolean condition,
            String expected,
            String actual
    ) {

        if (condition) {

            System.out.println(
                    "  PASS  [" + testName + "]"
            );

            passed++;

        } else {

            System.out.println(
                    "  FAIL  [" + testName + "]"
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

    private static void runTest01() {

        try {

            Object s = createStack();

            push(s, 5);
            push(s, 3);
            push(s, 7);
            push(s, 2);

            int min1 = getMin(s);

            int popped = pop(s);

            int min2 = getMin(s);

            int top = top(s);

            boolean correct =
                    min1 == 2
                    && popped == 2
                    && min2 == 3
                    && top == 7;

            check(
                    "test_01_basic_min_stack",
                    correct,
                    "min=2, pop=2, min=3, top=7",
                    "min=" + min1
                            + ", pop=" + popped
                            + ", min=" + min2
                            + ", top=" + top
            );

        } catch (Exception e) {

            failWithException(
                    "test_01_basic_min_stack",
                    e
            );
        }
    }

    private static void runTest02() {

        try {

            Object s = createStack();

            push(s, 10);
            push(s, 4);
            push(s, 6);

            int min1 = getMin(s);

            pop(s);

            int min2 = getMin(s);

            pop(s);

            int min3 = getMin(s);

            boolean correct =
                    min1 == 4
                    && min2 == 4
                    && min3 == 10;

            check(
                    "test_02_minimum_after_pops",
                    correct,
                    "4, 4, 10",
                    min1 + ", " + min2 + ", " + min3
            );

        } catch (Exception e) {

            failWithException(
                    "test_02_minimum_after_pops",
                    e
            );
        }
    }

    private static void runTest03() {

        try {

            Object s = createStack();

            push(s, 8);
            push(s, 8);
            push(s, 3);
            push(s, 5);

            int min1 = getMin(s);

            pop(s);

            int min2 = getMin(s);

            boolean correct =
                    min1 == 3
                    && min2 == 3;

            check(
                    "test_03_duplicate_values",
                    correct,
                    "3, 3",
                    min1 + ", " + min2
            );

        } catch (Exception e) {

            failWithException(
                    "test_03_duplicate_values",
                    e
            );
        }
    }

    private static void runTest04() {

        try {

            Object s = createStack();

            push(s, -5);
            push(s, -10);
            push(s, 0);
            push(s, -20);

            int min = getMin(s);

            boolean correct =
                    min == -20;

            check(
                    "test_04_negative_values",
                    correct,
                    "-20",
                    String.valueOf(min)
            );

        } catch (Exception e) {

            failWithException(
                    "test_04_negative_values",
                    e
            );
        }
    }

    private static void runTest05() {

        try {

            Object s = createStack();

            push(s, 10);
            push(s, 5);
            push(s, 1);
            push(s, 7);

            int min1 = getMin(s);

            pop(s);

            int min2 = getMin(s);

            pop(s);

            int min3 = getMin(s);

            boolean correct =
                    min1 == 1
                    && min2 == 1
                    && min3 == 5;

            check(
                    "test_05_minimum_changes_after_pop",
                    correct,
                    "1, 1, 5",
                    min1 + ", " + min2 + ", " + min3
            );

        } catch (Exception e) {

            failWithException(
                    "test_05_minimum_changes_after_pop",
                    e
            );
        }
    }

    private static void runTest06() {

        try {

            Object s = createStack();

            push(s, 4);
            push(s, 2);
            push(s, 2);
            push(s, 5);

            int min1 = getMin(s);

            pop(s);

            int min2 = getMin(s);

            pop(s);

            int min3 = getMin(s);

            boolean correct =
                    min1 == 2
                    && min2 == 2
                    && min3 == 2;

            check(
                    "test_06_duplicate_minimums",
                    correct,
                    "2, 2, 2",
                    min1 + ", " + min2 + ", " + min3
            );

        } catch (Exception e) {

            failWithException(
                    "test_06_duplicate_minimums",
                    e
            );
        }
    }

    private static void runTest07() {

        try {

            Object s = createStack();

            push(s, 8);
            push(s, 3);
            push(s, 6);

            int top1 = top(s);
            int top2 = top(s);
            int min = getMin(s);

            check(
                    "test_07_top_does_not_remove",
                    top1 == 6 && top2 == 6 && min == 3,
                    "top=6, top=6, min=3",
                    "top=" + top1
                            + ", top=" + top2
                            + ", min=" + min
            );

        } catch (Exception e) {

            failWithException(
                    "test_07_top_does_not_remove",
                    e
            );
        }
    }

    private static void runTest08() {

        try {

            Object s = createStack();

            push(s, -10);
            push(s, 5);
            push(s, 20);
            push(s, 30);

            int min1 = getMin(s);

            pop(s);
            pop(s);
            pop(s);

            int min2 = getMin(s);

            boolean correct =
                    min1 == -10
                    && min2 == -10;

            check(
                    "test_08_minimum_at_bottom",
                    correct,
                    "-10, -10",
                    min1 + ", " + min2
            );

        } catch (Exception e) {

            failWithException(
                    "test_08_minimum_at_bottom",
                    e
            );
        }
    }

    private static void runTest09() {

        try {

            Object s = createStack();

            push(s, 20);
            push(s, 15);
            push(s, 10);
            push(s, 1);

            int min1 = getMin(s);

            int popped = pop(s);

            int min2 = getMin(s);

            boolean correct =
                    min1 == 1
                    && popped == 1
                    && min2 == 10;

            check(
                    "test_09_minimum_at_top",
                    correct,
                    "min=1, pop=1, min=10",
                    "min=" + min1
                            + ", pop=" + popped
                            + ", min=" + min2
            );

        } catch (Exception e) {

            failWithException(
                    "test_09_minimum_at_top",
                    e
            );
        }
    }

    private static void runTest10() {

        try {

            Object s = createStack();

            push(s, 12);
            push(s, -3);
            push(s, 7);
            push(s, -3);
            push(s, 15);
            push(s, -10);
            push(s, 4);

            int min1 = getMin(s);

            pop(s);
            pop(s);

            int min2 = getMin(s);

            pop(s);

            int min3 = getMin(s);

            push(s, -20);

            int min4 = getMin(s);

            boolean correct =
                    min1 == -10
                    && min2 == -3
                    && min3 == -3
                    && min4 == -20;

            check(
                    "test_10_mixed_minimum_changes",
                    correct,
                    "-10, -3, -3, -20",
                    min1 + ", "
                            + min2 + ", "
                            + min3 + ", "
                            + min4
            );

        } catch (Exception e) {

            failWithException(
                    "test_10_mixed_minimum_changes",
                    e
            );
        }
    }

    private static void failWithException(
            String testName,
            Exception e
    ) {

        System.out.println(
                "  FAIL  [" + testName + "]"
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