# Q5 — Find First and Last Position of an Element

## Problem Statement

A sorted database contains repeated values. Given a sorted integer array and a target value, find the first and last position at which the target occurs.

The solution should use binary search rather than scanning the entire array.

Return the result as an array:

```
[ firstPosition, lastPosition ]
```

If the target does not exist, return:

```
[ -1, -1 ]
```

## Input

- A sorted integer array `arr`.
- An integer `target`.

## Output

Return an integer array containing:

- The first position where `target` occurs.
- The last position where `target` occurs.

If the target is not present, return `[-1, -1]`.

## Required Method

```java
public static int[] searchRange(int[] arr, int target)
```

## Example 1

**Input:**

```
arr = [1, 2, 2, 2, 3, 4, 5]
target = 2
```

**Output:**

```
[1, 3]
```

## Example 2

**Input:**

```
arr = [1, 2, 3, 4, 5]
target = 6
```

**Output:**

```
[-1, -1]
```

## Example 3

**Input:**

```
arr = [2, 2, 2, 2, 2]
target = 2
```

**Output:**

```
[0, 4]
```

## Constraints

- `1 <= arr.length <= 100000`
- The array is sorted in non-decreasing order.
- Array elements may be negative, zero, or positive.
- Duplicate values are allowed.
- The target may or may not exist in the array.
- The solution should use binary search.
- Avoid scanning the entire array.