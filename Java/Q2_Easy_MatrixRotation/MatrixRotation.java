public class MatrixRotation {

    public static void rotate(int[][] matrix) {
        // Write your code here
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        rotate(matrix1);
        printMatrix(matrix1);

        System.out.println();

        int[][] matrix2 = {
            {1, 2},
            {3, 4}
        };

        rotate(matrix2);
        printMatrix(matrix2);

        System.out.println();

        int[][] matrix3 = {
            {5}
        };

        rotate(matrix3);
        printMatrix(matrix3);
    }
}
