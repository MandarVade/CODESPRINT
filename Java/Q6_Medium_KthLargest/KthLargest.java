import java.util.PriorityQueue;

public class KthLargest {

    public static int kthLargest(int[] scores, int k) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) {

        // Test Case 1
        int[] scores1 = {
            7, 10, 4, 3, 20, 15
        };

        System.out.println(
                kthLargest(scores1, 3)
        );

        // Expected: 10


        // Test Case 2
        int[] scores2 = {
            3, 2, 1, 5, 6, 4
        };

        System.out.println(
                kthLargest(scores2, 2)
        );

        // Expected: 5


        // Test Case 3
        int[] scores3 = {
            5, 5, 3, 2, 8, 1
        };

        System.out.println(
                kthLargest(scores3, 4)
        );

        // Expected: 3
    }
}