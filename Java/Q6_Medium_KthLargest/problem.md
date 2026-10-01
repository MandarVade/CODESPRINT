# Q6 (Medium): Find the Kth Largest Element

## Problem Statement

A leaderboard system stores the scores of all participants in an integer array.

The system needs to find the participant score that ranks `k`th when the scores are arranged from highest to lowest.

Given an integer array and `k`, return the kth largest element.

Use a `PriorityQueue` to solve the problem efficiently without fully sorting the array.

Duplicate values should be counted as separate elements.

## Example 1

**Input:**

```
Scores = [7, 10, 4, 3, 20, 15]
k = 3
```

**Output:**

```
10
```

The three largest scores are: 20, 15, 10

## Example 2

**Input:**

```
Scores = [3, 2, 1, 5, 6, 4]
k = 2
```

**Output:**

```
5
```

The two largest scores are: 6, 5

## Example 3

**Input:**

```
Scores = [5, 5, 3, 2, 8, 1]
k = 4
```

**Output:**

```
3
```

The four largest scores are: 8, 5, 5, 3

## Method

```java
static int kthLargest(int[] scores, int k)
```

## Constraints

- The array contains at least one element.
- `1 <= k <= scores.length`.
- Scores may be positive, zero, or negative.
- Duplicate scores are allowed.
- Duplicate values count as separate elements.
- Do not fully sort the array.
- Use a `PriorityQueue`.
- The expected approach should maintain only the necessary elements rather than sorting the complete array.
