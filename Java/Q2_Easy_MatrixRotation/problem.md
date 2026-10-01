# Q2 (Easy): Rotate a Matrix 90 Degrees Clockwise

## Problem Statement

An image-processing application stores an image as a square matrix. Before displaying the image, the application needs to rotate it by 90 degrees clockwise.

Given a square matrix, rotate the matrix 90 degrees clockwise **in-place**.

You must modify the given matrix directly. Do not create another matrix to store the rotated result.

## Required Method

```java
static void rotate(int[][] matrix)
```

## Constraints

- The matrix is square.
- The number of rows and columns is between 1 and 100.
- The matrix may contain positive, negative, or zero values.
- The rotation must be performed in-place.

## Example 1

**Input:**

```
1 2 3
4 5 6
7 8 9
```

**Output:**

```
7 4 1
8 5 2
9 6 3
```

## Example 2

**Input:**

```
1 2
3 4
```

**Output:**

```
3 1
4 2
```

## Example 3

**Input:**

```
5
```

**Output:**

```
5
```
