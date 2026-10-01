# Q4 (Medium): Design a Stack Supporting Minimum Element

## Problem Statement

A monitoring system needs a stack that supports normal stack operations while also being able to determine the minimum value currently present in the stack.

Implement the following operations:

```
push(x)
pop()
top()
getMin()
```

All operations must maintain normal LIFO (Last In, First Out) behavior.

The `getMin()` operation should return the minimum element currently present in the stack.

## Example 1

**Operations:**

```
push(5)
push(3)
push(7)
push(2)

getMin() → 2

pop()

getMin() → 3

top() → 7
```

## Example 2

**Operations:**

```
push(10)
push(4)
push(6)

getMin() → 4

pop()

getMin() → 4

pop()

getMin() → 10
```

## Example 3

**Operations:**

```
push(8)
push(8)
push(3)
push(5)

getMin() → 3

pop()

getMin() → 3
```

## Required Methods

```java
public void push(int x)

public int pop()

public int top()

public int getMin()
```

## Constraints

- The stack can contain positive, zero, or negative integers.
- `push()` adds an element to the top of the stack.
- `pop()` removes and returns the top element.
- `top()` returns the top element without removing it.
- `getMin()` returns the minimum element currently present.
- Duplicate values are allowed.
- `pop()`, `top()`, and `getMin()` will only be called when the stack is non-empty.
- Aim for O(1) time complexity for all four operations.