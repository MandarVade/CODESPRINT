# Q8 (Easy): Inorder Traversal of a Binary Tree

## Problem Statement

Given the root of a binary tree, return the elements of the tree in inorder traversal.

In inorder traversal, nodes must be visited in the following order:

Left → Root → Right

Return the traversal as an `ArrayList<Integer>`.

## Example 1

**Input:**

```
        1
       / \
      2   3
     / \
    4   5
```

**Output:**

```
[4, 2, 5, 1, 3]
```

## Example 2

**Input:**

```
        10
       /  \
      5    15
          /  \
         12   20
```

**Output:**

```
[5, 10, 12, 15, 20]
```

## Example 3

**Input:**

```
        7
       /
      3
     /
    1
```

**Output:**

```
[1, 3, 7]
```

## Method

```java
static ArrayList<Integer> inorder(Node root)
```

## Constraints

- The number of nodes is between 0 and 1000.
- Each node contains an integer value.
- The tree may be balanced or unbalanced.
- Duplicate values are allowed.
- The root may be `null`.