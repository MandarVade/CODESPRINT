# Q7 (Medium): Search in a Rotated Sorted Array

## Problem Statement

A sorted array has been rotated at an unknown position.

Given the rotated array and a target value, return the index of the target.

The array contains distinct elements.

The solution must use binary search rather than performing a linear search.

If the target does not exist in the array, return `-1`.

## Example 1

**Input:**

```
arr = [6, 7, 8, 1, 2, 3, 4, 5]
target = 3
```

**Output:**

```
5
```

## Example 2

**Input:**

```
arr = [4, 5, 6, 7, 0, 1, 2]
target = 0
```

**Output:**

```
4
```

## Example 3

**Input:**

```
arr = [6, 7, 8, 1, 2, 3, 4, 5]
target = 9
```

**Output:**

```
-1
```

## Method

```java
static int search(int[] arr, int target)
```

## Constraints

- The array contains distinct integers.
- The array is originally sorted in ascending order.
- The array is rotated at an unknown position.
- The array contains at least one element.
- The target may or may not be present.
- Array values may be positive, zero, or negative.
- The expected time complexity is O(log n).
