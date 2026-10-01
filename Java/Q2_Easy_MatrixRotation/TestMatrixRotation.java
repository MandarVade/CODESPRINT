import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestMatrixRotation {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q2 Matrix Rotation — Test Results");
        System.out.println("=========================================================");

        if (!compileStudentCode()) {
            System.out.println();
            System.out.println("Student code could not be compiled.");
            System.out.println("Tests cannot be executed.");
            System.exit(1);
        }

        try {
            Class<?> solutionClass = Class.forName("MatrixRotation");

            Method rotateMethod = solutionClass.getDeclaredMethod(
                    "rotate",
                    int[][].class
            );

            rotateMethod.setAccessible(true);

            // Test 1 - 3x3 matrix
            runTest(
                    rotateMethod,
                    "test_01_3x3_matrix",
                    new int[][]{
                            {1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9}
                    },
                    new int[][]{
                            {7, 4, 1},
                            {8, 5, 2},
                            {9, 6, 3}
                    }
            );

            // Test 2 - 2x2 matrix
            runTest(
                    rotateMethod,
                    "test_02_2x2_matrix",
                    new int[][]{
                            {1, 2},
                            {3, 4}
                    },
                    new int[][]{
                            {3, 1},
                            {4, 2}
                    }
            );

            // Test 3 - Single element
            runTest(
                    rotateMethod,
                    "test_03_single_element",
                    new int[][]{
                            {5}
                    },
                    new int[][]{
                            {5}
                    }
            );

            // Test 4 - 4x4 matrix
            runTest(
                    rotateMethod,
                    "test_04_4x4_matrix",
                    new int[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    },
                    new int[][]{
                            {13, 9, 5, 1},
                            {14, 10, 6, 2},
                            {15, 11, 7, 3},
                            {16, 12, 8, 4}
                    }
            );

            // Test 5 - Matrix containing zeroes
            runTest(
                    rotateMethod,
                    "test_05_matrix_with_zeroes",
                    new int[][]{
                            {0, 1, 0},
                            {2, 0, 3},
                            {0, 4, 5}
                    },
                    new int[][]{
                            {0, 2, 0},
                            {4, 0, 1},
                            {5, 3, 0}
                    }
            );

            // Test 6 - Negative values
            runTest(
                    rotateMethod,
                    "test_06_negative_values",
                    new int[][]{
                            {-1, -2},
                            {-3, -4}
                    },
                    new int[][]{
                            {-3, -1},
                            {-4, -2}
                    }
            );

            // Test 7 - Duplicate values
            runTest(
                    rotateMethod,
                    "test_07_duplicate_values",
                    new int[][]{
                            {5, 5, 5},
                            {5, 1, 5},
                            {5, 5, 5}
                    },
                    new int[][]{
                            {5, 5, 5},
                            {5, 1, 5},
                            {5, 5, 5}
                    }
            );

            // Test 8 - Non-sequential values
            runTest(
                    rotateMethod,
                    "test_08_non_sequential_values",
                    new int[][]{
                            {10, 20, 30},
                            {40, 50, 60},
                            {70, 80, 90}
                    },
                    new int[][]{
                            {70, 40, 10},
                            {80, 50, 20},
                            {90, 60, 30}
                    }
            );

            // Test 9 - 5x5 matrix
            runTest(
                    rotateMethod,
                    "test_09_5x5_matrix",
                    new int[][]{
                            {1, 2, 3, 4, 5},
                            {6, 7, 8, 9, 10},
                            {11, 12, 13, 14, 15},
                            {16, 17, 18, 19, 20},
                            {21, 22, 23, 24, 25}
                    },
                    new int[][]{
                            {21, 16, 11, 6, 1},
                            {22, 17, 12, 7, 2},
                            {23, 18, 13, 8, 3},
                            {24, 19, 14, 9, 4},
                            {25, 20, 15, 10, 5}
                    }
            );

            // Test 10 - Mixed values
            runTest(
                    rotateMethod,
                    "test_10_mixed_values",
                    new int[][]{
                            {-5, 0, 7, 2},
                            {3, -1, 8, 4},
                            {6, 9, 0, -2},
                            {10, 5, -7, 1}
                    },
                    new int[][]{
                            {10, 6, 3, -5},
                            {5, 9, -1, 0},
                            {-7, 0, 8, 7},
                            {1, -2, 4, 2}
                    }
            );

            System.out.println("---------------------------------------------------------");
            System.out.println("  Score: " + passed + "/10");
            System.out.println("  Marks: " + (passed * 10) + "/100");
            System.out.println("=========================================================");

            System.exit(failed > 0 ? 1 : 0);

        } catch (NoSuchMethodException e) {

            System.out.println();
            System.out.println("Required method not found.");
            System.out.println("Expected method:");
            System.out.println("public static void rotate(int[][] matrix)");
            System.exit(1);

        } catch (ClassNotFoundException e) {

            System.out.println();
            System.out.println("MatrixRotation class not found.");
            System.exit(1);

        } catch (Exception e) {

            System.out.println();
            System.out.println("Unexpected error:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static boolean compileStudentCode() {

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
                "MatrixRotation.java"
        );

        return result == 0;
    }

    private static void runTest(
            Method rotateMethod,
            String testName,
            int[][] input,
            int[][] expected
    ) {

        try {

            rotateMethod.invoke(
                    null,
                    (Object) input
            );

            if (matricesEqual(expected, input)) {

                System.out.println(
                        "  PASS  [" + testName + "]"
                );

                passed++;

            } else {

                System.out.println(
                        "  FAIL  [" + testName + "]"
                );

                System.out.println("           Expected:");
                printMatrix(expected);

                System.out.println("           Got:");
                printMatrix(input);

                failed++;
            }

        } catch (InvocationTargetException e) {

            System.out.println(
                    "  FAIL  [" + testName + "]"
            );

            Throwable cause = e.getCause();

            if (cause != null) {
                System.out.println(
                        "           Runtime error: "
                                + cause.getClass().getSimpleName()
                                + ": "
                                + cause.getMessage()
                );
            } else {
                System.out.println(
                        "           Runtime error: "
                                + e.getMessage()
                );
            }

            failed++;

        } catch (Exception e) {

            System.out.println(
                    "  FAIL  [" + testName + "]"
            );

            System.out.println(
                    "           Error: " + e.getMessage()
            );

            failed++;
        }
    }

    private static boolean matricesEqual(
            int[][] a,
            int[][] b
    ) {

        if (a == null || b == null) {
            return a == b;
        }

        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {

            if (a[i].length != b[i].length) {
                return false;
            }

            if (!Arrays.equals(a[i], b[i])) {
                return false;
            }
        }

        return true;
    }

    private static void printMatrix(int[][] matrix) {

        for (int[] row : matrix) {

            System.out.print("           ");

            for (int value : row) {
                System.out.print(value + " ");
            }

            System.out.println();
        }
    }
}