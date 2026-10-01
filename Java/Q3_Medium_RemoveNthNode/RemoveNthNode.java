public class RemoveNthNode {

    public static Node removeNthFromEnd(Node head, int n) {
        // Write your code here
        return null;
    }

    private static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data);
            if (head.next != null) {
                System.out.print(" -> ");
            }
            head = head.next;
        }
        System.out.println(" -> null");
    }

    public static void main(String[] args) {

        // Test Case 1
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);

        head1 = removeNthFromEnd(head1, 2);
        printList(head1);

        // Test Case 2
        Node head2 = new Node(10);
        head2.next = new Node(20);
        head2.next.next = new Node(30);

        head2 = removeNthFromEnd(head2, 3);
        printList(head2);

        // Test Case 3
        Node head3 = new Node(7);
        head3.next = new Node(14);
        head3.next.next = new Node(21);
        head3.next.next.next = new Node(28);

        head3 = removeNthFromEnd(head3, 1);
        printList(head3);
    }
}

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}