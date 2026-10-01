import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestRemoveNthNode {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> solutionClass;
    private static Class<?> nodeClass;
    private static Constructor<?> nodeConstructor;
    private static Method removeMethod;
    private static Field dataField;
    private static Field nextField;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q3 Remove Nth Node from the End — Test Results");
        System.out.println("=========================================================");

        if (!compileStudentCode()) {
            System.out.println();
            System.out.println("Student code could not be compiled.");
            System.out.println("Tests cannot be executed.");
            System.exit(1);
        }

        if (!loadStudentClasses()) {
            System.out.println();
            System.out.println("Could not load the required classes/method.");
            System.out.println("Make sure the class and method names match the problem.");
            System.exit(1);
        }

        runTest(
                "test_01_remove_second_from_end",
                new int[]{1, 2, 3, 5},
                new int[]{1, 2, 3, 4, 5},
                2
        );

        runTest(
                "test_02_remove_head",
                new int[]{20, 30},
                new int[]{10, 20, 30},
                3
        );

        runTest(
                "test_03_remove_last_node",
                new int[]{7, 14, 21},
                new int[]{7, 14, 21, 28},
                1
        );

        runTest(
                "test_04_single_node",
                new int[]{},
                new int[]{42},
                1
        );

        runTest(
                "test_05_two_nodes_remove_head",
                new int[]{200},
                new int[]{100, 200},
                2
        );

        runTest(
                "test_06_two_nodes_remove_tail",
                new int[]{100},
                new int[]{100, 200},
                1
        );

        runTest(
                "test_07_remove_middle_node",
                new int[]{1, 2, 3, 5, 6, 7},
                new int[]{1, 2, 3, 4, 5, 6, 7},
                4
        );

        runTest(
                "test_08_duplicate_values",
                new int[]{5, 5, 5, 5},
                new int[]{5, 5, 5, 5, 5},
                3
        );

        runTest(
                "test_09_negative_and_zero_values",
                new int[]{-10, 0, 10, 20},
                new int[]{-10, 0, 10, -20, 20},
                2
        );

        runTest(
                "test_10_larger_list",
                new int[]{10, 20, 30, 50, 60, 70, 80, 90, 100},
                new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100},
                7
        );

        System.out.println("---------------------------------------------------------");
        System.out.println("  Score: " + passed + "/10");
        System.out.println("  Marks: " + (passed * 10) + "/100");
        System.out.println("=========================================================");

        System.exit(failed > 0 ? 1 : 0);
    }

    private static boolean compileStudentCode() {

        try {

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            if (compiler == null) {
                System.out.println("Java compiler is not available.");
                System.out.println("Make sure a JDK is installed.");
                return false;
            }

            int result = compiler.run(
                    null,
                    null,
                    null,
                    "RemoveNthNode.java"
            );

            return result == 0;

        } catch (Exception e) {

            System.out.println("Compilation error: " + e.getMessage());
            return false;
        }
    }

    private static boolean loadStudentClasses() {

        try {

            solutionClass = Class.forName("RemoveNthNode");
            nodeClass = Class.forName("Node");

            nodeConstructor =
                    nodeClass.getDeclaredConstructor(int.class);

            nodeConstructor.setAccessible(true);

            removeMethod =
                    solutionClass.getDeclaredMethod(
                            "removeNthFromEnd",
                            nodeClass,
                            int.class
                    );

            removeMethod.setAccessible(true);

            dataField =
                    nodeClass.getDeclaredField("data");

            dataField.setAccessible(true);

            nextField =
                    nodeClass.getDeclaredField("next");

            nextField.setAccessible(true);

            return true;

        } catch (Exception e) {

            System.out.println("Error loading student code:");
            System.out.println(e.getMessage());

            return false;
        }
    }

    private static void runTest(
            String testName,
            int[] expected,
            int[] input,
            int n
    ) {

        try {

            Object head = createList(input);

            Object result =
                    removeMethod.invoke(null, head, n);

            int[] actual = toArray(result);

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

    private static Object createList(int... values)
            throws Exception {

        if (values.length == 0) {
            return null;
        }

        Object head =
                nodeConstructor.newInstance(values[0]);

        Object current = head;

        for (int i = 1; i < values.length; i++) {

            Object newNode =
                    nodeConstructor.newInstance(values[i]);

            nextField.set(current, newNode);

            current = newNode;
        }

        return head;
    }

    private static int[] toArray(Object head)
            throws Exception {

        int count = 0;
        Object current = head;

        while (current != null) {

            count++;

            current = nextField.get(current);
        }

        int[] result = new int[count];

        current = head;

        for (int i = 0; i < count; i++) {

            result[i] =
                    dataField.getInt(current);

            current =
                    nextField.get(current);
        }

        return result;
    }

    private static void check(
            String name,
            int[] expected,
            int[] actual
    ) {

        if (arraysEqual(expected, actual)) {

            System.out.println(
                    "  ✓ PASS  [" + name + "]"
            );

            passed++;

        } else {

            System.out.println(
                    "  ✗ FAIL  [" + name + "]"
            );

            System.out.print(
                    "           expected: "
            );

            printArray(expected);

            System.out.print(
                    "           got:      "
            );

            printArray(actual);

            failed++;
        }
    }

    private static boolean arraysEqual(
            int[] a,
            int[] b
    ) {

        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {

            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    private static void printArray(int[] arr) {

        System.out.print("[");

        for (int i = 0; i < arr.length; i++) {

            System.out.print(arr[i]);

            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}