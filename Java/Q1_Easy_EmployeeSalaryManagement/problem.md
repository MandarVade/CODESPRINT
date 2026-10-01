# Q1 (Easy): Employee Salary Management — Encapsulation and Object-Oriented Design

## Problem Statement

You are developing a payroll system for a company. Each employee has a name, employee ID, basic salary, and performance rating.

Create an `Employee` class that keeps employee data properly encapsulated. The basic salary and performance rating should not be directly accessible from outside the class.

Implement methods to:

- Update the performance rating.
- Calculate the performance bonus.
- Calculate the final salary after adding the bonus.

Use appropriate access modifiers and class methods to maintain encapsulation.

The performance rating must be between 1 and 5.

The bonus is calculated according to the following rules:

| Performance Rating | Bonus Percentage |
|--------------------:|-----------------:|
| 1 | 0% |
| 2 | 5% |
| 3 | 10% |
| 4 | 20% |
| 5 | 25% |

If an invalid performance rating is provided, the previous rating should remain unchanged.

## Required Methods

```java
public void setPerformanceRating(int rating)

public double calculateBonus()

public double calculateFinalSalary()
```

## Example 1

**Input:**

```
Employee: Rahul
Basic Salary: 50000
Performance Rating: 4
```

**Output:**

```
Bonus: 10000.0
Final Salary: 60000.0
```

## Example 2

**Input:**

```
Employee: Priya
Basic Salary: 40000
Performance Rating: 5
```

**Output:**

```
Bonus: 10000.0
Final Salary: 50000.0
```

## Example 3

**Input:**

```
Employee: Amit
Basic Salary: 60000
Performance Rating: 2
```

**Output:**

```
Bonus: 3000.0
Final Salary: 63000.0
```

## Constraints

- Performance rating is an integer from 1 to 5.
- Basic salary is non-negative.
- Employee ID is a positive integer.
- Invalid ratings outside 1–5 should not change the current rating.
- Salary values may contain decimal values.