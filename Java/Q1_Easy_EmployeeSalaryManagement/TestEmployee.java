import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestEmployee {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> employeeClass;

    private static Constructor<?> employeeConstructor;

    private static Method setRatingMethod;
    private static Method calculateBonusMethod;
    private static Method calculateFinalSalaryMethod;

    private static Field nameField;
    private static Field employeeIdField;
    private static Field basicSalaryField;
    private static Field performanceRatingField;

    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q1 Employee Salary Management — Test Results");
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

        // Test 1 — Example 1 (Rating 4 = 20% bonus)
        runTest(
                "test_01_rating_four",
                "Rahul",
                101,
                50000.0,
                4,
                10000.0,
                60000.0
        );

        // Test 2 — Example 2 (Rating 5 = 25% bonus)
        runTest(
                "test_02_rating_five",
                "Priya",
                102,
                40000.0,
                5,
                10000.0,
                50000.0
        );

        // Test 3 — Example 3
        runTest(
                "test_03_rating_two",
                "Amit",
                103,
                60000.0,
                2,
                3000.0,
                63000.0
        );

        // Test 4 — Rating one, no bonus
        runTest(
                "test_04_rating_one",
                "Neha",
                104,
                50000.0,
                1,
                0.0,
                50000.0
        );

        // Test 5 — Rating three
        runTest(
                "test_05_rating_three",
                "Rohan",
                105,
                70000.0,
                3,
                7000.0,
                77000.0
        );

        // Test 6 — Zero salary
        runTest(
                "test_06_zero_salary",
                "Karan",
                106,
                0.0,
                5,
                0.0,
                0.0
        );

        // Test 7 — Decimal salary

        runTest(
                "test_07_decimal_salary",
                "Sneha",
                107,
                45555.50,
                4,
                9111.10,
                54666.60
        );

        // Test 8 — Invalid high rating
        runInvalidRatingTest(
                "test_08_invalid_high_rating",
                "Vikas",
                108,
                50000.0,
                3,
                10
        );

        // Test 9 — Invalid low rating
        runInvalidRatingTest(
                "test_09_invalid_low_rating",
                "Pooja",
                109,
                60000.0,
                4,
                0
        );

        // Test 10 — Rating update
        runRatingUpdateTest(
                "test_10_rating_update",
                "Arjun",
                110,
                80000.0
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
                    "Employee.java"
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

            employeeClass =
                    Class.forName("Employee");

            employeeConstructor =
                    employeeClass.getDeclaredConstructor(
                            String.class,
                            int.class,
                            double.class
                    );

            employeeConstructor.setAccessible(true);

            setRatingMethod =
                    employeeClass.getDeclaredMethod(
                            "setPerformanceRating",
                            int.class
                    );

            setRatingMethod.setAccessible(true);

            calculateBonusMethod =
                    employeeClass.getDeclaredMethod(
                            "calculateBonus"
                    );

            calculateBonusMethod.setAccessible(true);

            calculateFinalSalaryMethod =
                    employeeClass.getDeclaredMethod(
                            "calculateFinalSalary"
                    );

            calculateFinalSalaryMethod.setAccessible(true);

            nameField =
                    employeeClass.getDeclaredField("name");

            employeeIdField =
                    employeeClass.getDeclaredField("employeeId");

            basicSalaryField =
                    employeeClass.getDeclaredField("basicSalary");

            performanceRatingField =
                    employeeClass.getDeclaredField(
                            "performanceRating"
                    );

            nameField.setAccessible(true);
            employeeIdField.setAccessible(true);
            basicSalaryField.setAccessible(true);
            performanceRatingField.setAccessible(true);

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
            String name,
            int employeeId,
            double salary,
            int rating,
            double expectedBonus,
            double expectedFinalSalary
    ) {

        try {

            Object employee =
                    employeeConstructor.newInstance(
                            name,
                            employeeId,
                            salary
                    );

            setRatingMethod.invoke(
                    employee,
                    rating
            );

            double actualBonus =
                    ((Number) calculateBonusMethod.invoke(
                            employee
                    )).doubleValue();

            double actualFinalSalary =
                    ((Number) calculateFinalSalaryMethod.invoke(
                            employee
                    )).doubleValue();

            boolean bonusCorrect =
                    Math.abs(
                            actualBonus - expectedBonus
                    ) < 0.001;

            boolean salaryCorrect =
                    Math.abs(
                            actualFinalSalary
                                    - expectedFinalSalary
                    ) < 0.001;

            if (bonusCorrect && salaryCorrect) {

                System.out.println(
                        "  ✓ PASS  [" + testName + "]"
                );

                passed++;

            } else {

                System.out.println(
                        "  ✗ FAIL  [" + testName + "]"
                );

                System.out.println(
                        "           Expected Bonus: "
                                + expectedBonus
                );

                System.out.println(
                        "           Got Bonus: "
                                + actualBonus
                );

                System.out.println(
                        "           Expected Final Salary: "
                                + expectedFinalSalary
                );

                System.out.println(
                        "           Got Final Salary: "
                                + actualFinalSalary
                );

                failed++;
            }

        } catch (Exception e) {

            printExecutionError(testName, e);
        }
    }

    private static void runInvalidRatingTest(
            String testName,
            String name,
            int employeeId,
            double salary,
            int initialRating,
            int invalidRating
    ) {

        try {

            Object employee =
                    employeeConstructor.newInstance(
                            name,
                            employeeId,
                            salary
                    );

            setRatingMethod.invoke(
                    employee,
                    initialRating
            );

            setRatingMethod.invoke(
                    employee,
                    invalidRating
            );

            int actualRating =
                    performanceRatingField.getInt(
                            employee
                    );

            double expectedBonus =
                    calculateExpectedBonus(
                            salary,
                            initialRating
                    );

            double actualBonus =
                    ((Number) calculateBonusMethod.invoke(
                            employee
                    )).doubleValue();

            boolean ratingCorrect =
                    actualRating == initialRating;

            boolean bonusCorrect =
                    Math.abs(
                            actualBonus - expectedBonus
                    ) < 0.001;

            if (ratingCorrect && bonusCorrect) {

                System.out.println(
                        "  ✓ PASS  [" + testName + "]"
                );

                passed++;

            } else {

                System.out.println(
                        "  ✗ FAIL  [" + testName + "]"
                );

                System.out.println(
                        "           Expected rating: "
                                + initialRating
                );

                System.out.println(
                        "           Got rating: "
                                + actualRating
                );

                failed++;
            }

        } catch (Exception e) {

            printExecutionError(testName, e);
        }
    }

    private static void runRatingUpdateTest(
            String testName,
            String name,
            int employeeId,
            double salary
    ) {

        try {

            Object employee =
                    employeeConstructor.newInstance(
                            name,
                            employeeId,
                            salary
                    );

            setRatingMethod.invoke(
                    employee,
                    2
            );

            double firstSalary =
                    ((Number) calculateFinalSalaryMethod.invoke(
                            employee
                    )).doubleValue();

            setRatingMethod.invoke(
                    employee,
                    5
            );

            double secondSalary =
                    ((Number) calculateFinalSalaryMethod.invoke(
                            employee
                    )).doubleValue();

            boolean firstCorrect =
                    Math.abs(
                            firstSalary - 84000.0
                    ) < 0.001;

            boolean secondCorrect =
                    Math.abs(
                            secondSalary - 100000.0
                    ) < 0.001;

            if (firstCorrect && secondCorrect) {

                System.out.println(
                        "  ✓ PASS  [" + testName + "]"
                );

                passed++;

            } else {

                System.out.println(
                        "  ✗ FAIL  [" + testName + "]"
                );

                System.out.println(
                        "           Expected salaries: "
                                + "84000.0 → 100000.0"
                );

                System.out.println(
                        "           Got salaries: "
                                + firstSalary
                                + " → "
                                + secondSalary
                );

                failed++;
            }

        } catch (Exception e) {

            printExecutionError(testName, e);
        }
    }

    private static double calculateExpectedBonus(
            double salary,
            int rating
    ) {

        double percentage = 0.0;

        switch (rating) {

            case 1:
                percentage = 0.0;
                break;

            case 2:
                percentage = 0.05;
                break;

            case 3:
                percentage = 0.10;
                break;
            case 4:
                percentage = 0.20;
                break;
            case 5:
                percentage = 0.25;
                break;    
        }

        return salary * percentage;
    }

    private static void printExecutionError(
            String testName,
            Exception e
    ) {

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