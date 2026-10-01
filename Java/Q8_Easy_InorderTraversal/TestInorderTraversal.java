import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestInorderTraversal {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> solutionClass;
    private static Class<?> nodeClass;

    private static Constructor<?> nodeConstructor;
    private static Method inorderMethod;

    private static Field dataField;
    private static Field leftField;
    private static Field rightField;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q8 Inorder Traversal — Test Results");
        System.out.println("=========================================================");

        if (!compileStudentCode()) {
            System.out.println();
            System.out.println("Student code could not be compiled.");
            System.out.println("Tests cannot be executed.");
            System.exit(1);
        }

        if (!loadStudentClasses()) {
            System.out.println();
            System.out.println("Could not load the required class/method.");
            System.out.println("Make sure the class and method names match the problem.");
            System.exit(1);
        }

        // Test 1 — Example 1
        runTest(
                "test_01_basic_binary_tree",
                new int[]{4, 2, 5, 1, 3},
                new int[]{1, 2, 3, 4, 5},
                new int[][]{
                        {1, 2, 1},
                        {1, 3, 2}
                }
        );

        // Test 2 — Example 2
        runTest(
                "test_02_right_subtree",
                new int[]{5, 10, 12, 15, 20},
                new int[]{10, 5, 15, 12, 20},
                new int[][]{
                        {0, 1, 0},
                        {0, 2, 0},
                        {2, 3, 0},
                        {2, 4, 0}
                }
        );

        // Test 3 — Example 3
        runTest(
                "test_03_left_skewed_tree",
                new int[]{1, 3, 7},
                new int[]{7, 3, 1},
                new int[][]{
                        {0, 1, 0},
                        {1, 2, 0}
                }
        );

        // Test 4 — Empty tree
        runTest(
                "test_04_empty_tree",
                new int[]{},
                new int[]{},
                new int[][]{}
        );

        // Test 5 — Single node
        runTest(
                "test_05_single_node",
                new int[]{42},
                new int[]{42},
                new int[][]{}
        );

        // Test 6 — Right-skewed tree
        runTest(
                "test_06_right_skewed_tree",
                new int[]{1, 2, 3, 4, 5},
                new int[]{1, 2, 3, 4, 5},
                new int[][]{
                        {0, 1, 0},
                        {1, 2, 0},
                        {2, 3, 0},
                        {3, 4, 0}
                }
        );

        // Test 7 — Duplicate values
        runTest(
                "test_07_duplicate_values",
                new int[]{5, 5, 5, 5, 5},
                new int[]{5, 5, 5, 5, 5},
                new int[][]{
                        {0, 1, 0},
                        {0, 2, 0},
                        {1, 3, 0},
                        {1, 4, 0}
                }
        );

        // Test 8 — Negative and zero values
        runTest(
                "test_08_negative_and_zero_values",
                new int[]{-10, -5, 0, 5, 10},
                new int[]{0, -5, 10, -10, 5},
                new int[][]{
                        {0, 1, 0},
                        {0, 2, 1},
                        {1, 3, 0},
                        {2, 4, 0}
                }
        );

        // Test 9 — Balanced larger tree
        runTest(
                "test_09_balanced_tree",
                new int[]{1, 2, 3, 4, 5, 6, 7},
                new int[]{4, 2, 6, 1, 3, 5, 7},
                new int[][]{
                        {0, 1, 0},
                        {0, 2, 1},
                        {1, 3, 0},
                        {1, 4, 0},
                        {2, 5, 0},
                        {2, 6, 0}
                }
        );

        // Test 10 — Mixed positive, negative and duplicate values
        runTest(
                "test_10_mixed_values",
                new int[]{-5, -5, 0, 2, 2, 8},
                new int[]{2, -5, 8, -5, 0, 2},
                new int[][]{
                        {0, 1, 0},
                        {0, 2, 1},
                        {1, 3, 0},
                        {1, 4, 1},
                        {2, 5, 0}
                }
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
                System.out.println("Java compiler is not available.");
                System.out.println("Make sure a JDK is installed.");
                return false;
            }

            int result = compiler.run(
                    null,
                    null,
                    null,
                    "InorderTraversal.java"
            );

            return result == 0;

        } catch (Exception e) {

            System.out.println(
                    "Compilation error: " + e.getMessage()
            );

            return false;
        }
    }

    private static boolean loadStudentClasses() {

        try {

            solutionClass =
                    Class.forName("InorderTraversal");

            nodeClass =
                    Class.forName("Node");

            nodeConstructor =
                    nodeClass.getDeclaredConstructor(int.class);

            nodeConstructor.setAccessible(true);

            inorderMethod =
                    solutionClass.getDeclaredMethod(
                            "inorder",
                            nodeClass
                    );

            inorderMethod.setAccessible(true);

            dataField =
                    nodeClass.getDeclaredField("data");

            dataField.setAccessible(true);

            leftField =
                    nodeClass.getDeclaredField("left");

            leftField.setAccessible(true);

            rightField =
                    nodeClass.getDeclaredField("right");

            rightField.setAccessible(true);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error loading student code:"
            );

            System.out.println(e.getMessage());

            return false;
        }
    }

    private static void runTest(
            String testName,
            int[] expected,
            int[] values,
            int[][] connections
    ) {

        try {

            Object root =
                    createTree(values, connections);

            Object result =
                    inorderMethod.invoke(null, root);

            ArrayList<?> actualList =
                    (ArrayList<?>) result;

            int[] actual =
                    new int[actualList.size()];

            for (int i = 0; i < actualList.size(); i++) {
                actual[i] =
                        ((Number) actualList.get(i)).intValue();
            }

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

    private static Object createTree(
            int[] values,
            int[][] connections
    ) throws Exception {

        if (values.length == 0) {
            return null;
        }

        Object[] nodes =
                new Object[values.length];

        for (int i = 0; i < values.length; i++) {

            nodes[i] =
                    nodeConstructor.newInstance(values[i]);
        }

        for (int[] connection : connections) {

            int parent = connection[0];
            int child = connection[1];
            int direction = connection[2];

            if (direction == 0) {
                leftField.set(
                        nodes[parent],
                        nodes[child]
                );
            } else {
                rightField.set(
                        nodes[parent],
                        nodes[child]
                );
            }
        }

        return nodes[0];
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