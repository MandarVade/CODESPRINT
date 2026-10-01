# Q10 (Medium): Most Frequently Ordered Product(s) for Each Customer

## Problem Statement

You are given an `Orders` table containing customer orders.

Write a MySQL query to find the **most frequently ordered product or products for each customer**.

For every customer, count how many times each product was ordered. Return the product or products having the highest order frequency for that customer.

If two or more products have the same highest frequency for a customer, **return all tied products**.

## Table Schema

```sql
Orders (
    order_id INT,
    customer_id INT,
    product_id INT
)
```

## Required Output

Return the following columns:

```text
customer_id | product_id | frequency
```

The result must be ordered by `customer_id` and then `product_id`, both in ascending order.

## Example

### Input

```text
order_id | customer_id | product_id
---------|-------------|-----------
101      | 1           | 10
102      | 1           | 10
103      | 1           | 20
104      | 1           | 20
105      | 1           | 30
106      | 2           | 40
107      | 2           | 40
108      | 2           | 50
```

### Expected Output

```text
customer_id | product_id | frequency
------------|------------|----------
1           | 10         | 2
1           | 20         | 2
2           | 40         | 2
```

For customer `1`, products `10` and `20` are tied for the highest frequency, so both must be returned.

## Constraints

* `order_id` is unique.
* `customer_id` and `product_id` are positive integers.
* A customer may order the same product multiple times.
* A customer may order multiple different products.

## Requirements

* Use MySQL syntax.
* Count orders separately for each customer and product.
* Return every product tied for the highest frequency for its customer.
* Return the frequency as the third column.
* Order the final result by `customer_id`, then `product_id`, ascending.
* Do not modify the database or test data.
* Submit only the required SQL query.
