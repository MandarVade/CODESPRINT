# Rotate Array by K Positions

## Problem Statement

Given an integer array `nums`, rotate the array to the right by `k` positions.

A right rotation by one position moves the last element to the first position, while all other elements shift one position to the right.

You must modify the array in-place.

## Input

- An integer array `nums`.
- An integer `k`, representing the number of positions to rotate the array.

## Output

Modify `nums` in-place so that it represents the array after rotating it to the right by `k` positions.

Do not return a new array.

## Examples

### Example 1

**Input:**

```
nums = [1,2,3,4,5,6,7]
k = 3
```

**Output:**

```
[5,6,7,1,2,3,4]
```

**Explanation:**

```
After 1 rotation: [7,1,2,3,4,5,6]
After 2 rotations: [6,7,1,2,3,4,5]
After 3 rotations: [5,6,7,1,2,3,4]
```

### Example 2

**Input:**

```
nums = [-1,-100,3,99]
k = 2
```

**Output:**

```
[3,99,-1,-100]
```

## Important Cases

- If `k` is greater than the array length, rotations should effectively repeat.

  For example:

  **Input:**

```
  nums = [1,2,3,4,5]
  k = 7
```

  **Output:**

```
  [4,5,1,2,3]
```

  Because:

```
  7 % 5 = 2
```

  So rotating by 7 positions is equivalent to rotating by 2 positions.

- If `k = 0`, the array remains unchanged.

## Constraints

```
1 <= nums.length <= 10^5
-10^9 <= nums[i] <= 10^9
0 <= k <= 10^9
```
