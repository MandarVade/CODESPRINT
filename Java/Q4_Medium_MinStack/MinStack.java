import java.util.Stack;

public class MinStack {

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    public void push(int x) {

        // Write your code here

    }

    public int pop() {

        // Write your code here

        return -1;
    }

    public int top() {

        // Write your code here

        return -1;
    }

    public int getMin() {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) {

        // Test Case 1
        MinStack s1 = new MinStack();

        s1.push(5);
        s1.push(3);
        s1.push(7);
        s1.push(2);

        System.out.println(s1.getMin());

        s1.pop();

        System.out.println(s1.getMin());
        System.out.println(s1.top());

        // Test Case 2
        MinStack s2 = new MinStack();

        s2.push(10);
        s2.push(4);
        s2.push(6);

        System.out.println(s2.getMin());

        s2.pop();

        System.out.println(s2.getMin());

        s2.pop();

        System.out.println(s2.getMin());

        // Test Case 3
        MinStack s3 = new MinStack();

        s3.push(8);
        s3.push(8);
        s3.push(3);
        s3.push(5);

        System.out.println(s3.getMin());

        s3.pop();

        System.out.println(s3.getMin());
    }
}