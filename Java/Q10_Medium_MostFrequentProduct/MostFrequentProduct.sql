-- Write your SQL query here
SELECT
    customer_id,
    product_id,
    COUNT(*) AS frequency
FROM Orders
GROUP BY customer_id, product_id
HAVING COUNT(*) = (
    SELECT MAX(product_count)
    FROM (
        SELECT COUNT(*) AS product_count
        FROM Orders o2
        WHERE o2.customer_id = Orders.customer_id
        GROUP BY o2.customer_id, o2.product_id
    ) AS counts
)
ORDER BY customer_id, product_id;