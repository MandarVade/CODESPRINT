# Q9 (Easy): Latest 2020 Login

## Problem Statement

You are given a `Logins` table containing login records for users.

Write a MySQL query to find the **latest login timestamp for each user during the year 2020**.

Only login records whose timestamp falls in the year 2020 should be considered. If a user has multiple logins during 2020, return only their latest login.

Each user should appear at most once in the result.

## Table Schema

```sql
Logins (
    user_id INT,
    time_stamp DATETIME
)
```

## Required Output

Return the following columns:

```text
user_id | time_stamp
```

The result must be ordered by `user_id` in ascending order.

## Example

### Input

```text
user_id | time_stamp
--------|-------------------
1       | 2020-01-01 12:00:00
1       | 2020-02-10 09:30:00
2       | 2020-03-15 18:20:00
1       | 2021-01-05 10:00:00
```

### Expected Output

```text
user_id | time_stamp
--------|-------------------
1       | 2020-02-10 09:30:00
2       | 2020-03-15 18:20:00
```

The 2021 login for user `1` must not be considered.

## Constraints

* `user_id` is a positive integer.
* `time_stamp` is a valid MySQL `DATETIME` value.
* A user may have multiple login records.
* Login records may exist outside the year 2020.

## Requirements

* Use MySQL syntax.
* Return one row for each user who logged in during 2020.
* Return that user's latest 2020 login timestamp.
* Order the final result by `user_id` ascending.
* Do not modify the database or test data.
* Submit only the required SQL query.
