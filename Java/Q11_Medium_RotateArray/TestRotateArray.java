import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestRotateArray {

    static int passed = 0;
    static int total = 10;

    public static void main(String[] args) {

        try {
            // Compile the student's RotateArray.java
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            if (compiler == null) {
                System.out.println("ERROR: Java compiler is not available.");
                System.out.println("A JDK is required to run the evaluator.");
                return;
            }

            int result = compiler.run(
                null,
                null,
                null,
                "RotateArray.java"
            );

            if (result != 0) {
                System.out.println("ERROR: RotateArray.java compilation failed.");
                return;
            }

            // Load the compiled RotateArray class
            URLClassLoader classLoader =
                URLClassLoader.newInstance(
                    new URL[]{new File(".").toURI().toURL()}
                );

            Class<?> rotateClass =
                Class.forName("RotateArray", true, classLoader);

            Method rotateMethod =
                rotateClass.getMethod(
                    "rotate",
                    int[].class,
                    int.class
                );

            runTest(
                rotateMethod,
                "Test 1 - Basic rotation",
                new int[]{1, 2, 3, 4, 5, 6, 7},
                3,
                new int[]{5, 6, 7, 1, 2, 3, 4}
            );

            runTest(
                rotateMethod,
                "Test 2 - Rotation by 2",
                new int[]{-1, -100, 3, 99},
                2,
                new int[]{3, 99, -1, -100}
            );

            runTest(
                rotateMethod,
                "Test 3 - k greater than length",
                new int[]{1, 2, 3, 4, 5},
                7,
                new int[]{4, 5, 1, 2, 3}
            );

            runTest(
                rotateMethod,
                "Test 4 - k is zero",
                new int[]{1, 2, 3, 4, 5},
                0,
                new int[]{1, 2, 3, 4, 5}
            );

            runTest(
                rotateMethod,
                "Test 5 - Single element",
                new int[]{10},
                5,
                new int[]{10}
            );

            runTest(
                rotateMethod,
                "Test 6 - k equals array length",
                new int[]{1, 2, 3, 4},
                4,
                new int[]{1, 2, 3, 4}
            );

            runTest(
                rotateMethod,
                "Test 7 - Two elements",
                new int[]{1, 2},
                1,
                new int[]{2, 1}
            );

            runTest(
                rotateMethod,
                "Test 8 - Duplicate values",
                new int[]{1, 1, 2, 2, 3, 3},
                2,
                new int[]{3, 3, 1, 1, 2, 2}
            );

            runTest(
                rotateMethod,
                "Test 9 - Large k",
                new int[]{1, 2, 3, 4, 5, 6},
                14,
                new int[]{5, 6, 1, 2, 3, 4}
            );

            runTest(
                rotateMethod,
                "Test 10 - Negative values",
                new int[]{-5, -4, -3, -2, -1},
                2,
                new int[]{-2, -1, -5, -4, -3}
            );

            System.out.println();
            System.out.println("Score: " + passed + "/" + total);
            System.out.println("Marks: " + (passed * 10) + "/100");

            classLoader.close();

        } catch (Exception e) {
            System.out.println("Evaluator Error: " + e.getMessage());
        }
    }

    static void runTest(
            Method rotateMethod,
            String testName,
            int[] input,
            int k,
            int[] expected) {

        int[] nums = Arrays.copyOf(input, input.length);

        try {
            rotateMethod.invoke(null, nums, k);

            if (Arrays.equals(nums, expected)) {
                passed++;
                System.out.println(testName + " : PASS");
            } else {
                System.out.println(testName + " : FAIL");
                System.out.println(
                    "Expected: " + Arrays.toString(expected)
                );
                System.out.println(
                    "Got:      " + Arrays.toString(nums)
                );
            }

        } catch (Exception e) {
            System.out.println(testName + " : FAIL");
            System.out.println(
                "Runtime Error: " + e.getMessage()
            );
        }
    }
}
