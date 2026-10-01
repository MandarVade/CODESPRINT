public class RotatedArraySearch {

    public static int search(int[] arr, int target) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) {

        // Test Case 1
        int[] arr1 = {6, 7, 8, 1, 2, 3, 4, 5};

        System.out.println(
                search(arr1, 3)
        );

        // Test Case 2
        int[] arr2 = {4, 5, 6, 7, 0, 1, 2};

        System.out.println(
                search(arr2, 0)
        );
        // Test Case 3
        int[] arr3 = {6, 7, 8, 1, 2, 3, 4, 5};

        System.out.println(
                search(arr3, 9)
        );
    }
}