# Q3 (Medium): Remove the Nth Node from the End

## Problem Statement

A linked list stores customer records in the order in which they were created. You are given the head of the list and an integer `n`.

Remove the `n`th node from the end of the linked list and return the updated head.

The solution should traverse the linked list efficiently without first calculating its complete length separately.

## Required Method

```java
public static Node removeNthFromEnd(Node head, int n)
```

## Example 1

**Input:**

```
1 → 2 → 3 → 4 → 5 → null
n = 2
```

**Output:**

```
1 → 2 → 3 → 5 → null
```

## Example 2

**Input:**

```
10 → 20 → 30 → null
n = 3
```

**Output:**

```
20 → 30 → null
```

## Example 3

**Input:**

```
7 → 14 → 21 → 28 → null
n = 1
```

**Output:**

```
7 → 14 → 21 → null
```

## Constraints

- The number of nodes is between `1` and `1000`.
- `1 <= n <= number of nodes`.
- Each node contains an integer value.
- The list may contain duplicate values.
- If `n` is equal to the length of the list, the head node must be removed.