public class Employee {

    private String name;
    private int employeeId;
    private double basicSalary;
    private int performanceRating;

    public Employee(String name, int employeeId, double basicSalary) {
        this.name = name;
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.performanceRating = 1;
    }

    public void setPerformanceRating(int rating) {

        // Write your code here

    }

    public double calculateBonus() {

        // Write your code here

        return 0;
    }

    public double calculateFinalSalary() {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) {

        // Test Case 1
        Employee emp1 =
                new Employee("Rahul", 101, 50000);

        emp1.setPerformanceRating(4);

        System.out.println(
                "Bonus: " + emp1.calculateBonus()
        );

        System.out.println(
                "Final Salary: " + emp1.calculateFinalSalary()
        );

        // Test Case 2
        Employee emp2 =
                new Employee("Priya", 102, 40000);

        emp2.setPerformanceRating(5);

        System.out.println(
                "Bonus: " + emp2.calculateBonus()
        );

        System.out.println(
                "Final Salary: " + emp2.calculateFinalSalary()
        );

        // Test Case 3
        Employee emp3 =
                new Employee("Amit", 103, 60000);

        emp3.setPerformanceRating(2);

        System.out.println(
                "Bonus: " + emp3.calculateBonus()
        );

        System.out.println(
                "Final Salary: " + emp3.calculateFinalSalary()
        );
    }
}