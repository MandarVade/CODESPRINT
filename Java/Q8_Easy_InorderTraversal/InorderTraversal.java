import java.util.ArrayList;

public class InorderTraversal {

    public static ArrayList<Integer> inorder(Node root) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) {

        // Test Case 1
        Node root1 = new Node(1);
        root1.left = new Node(2);
        root1.right = new Node(3);
        root1.left.left = new Node(4);
        root1.left.right = new Node(5);

        System.out.println(inorder(root1));
        // Expected: [4, 2, 5, 1, 3]

        // Test Case 2
        Node root2 = new Node(10);
        root2.left = new Node(5);
        root2.right = new Node(15);
        root2.right.left = new Node(12);
        root2.right.right = new Node(20);

        System.out.println(inorder(root2));
        // Expected: [5, 10, 12, 15, 20]

        // Test Case 3
        Node root3 = new Node(7);
        root3.left = new Node(3);
        root3.left.left = new Node(1);

        System.out.println(inorder(root3));
        // Expected: [1, 3, 7]
    }
}

class Node {

    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}