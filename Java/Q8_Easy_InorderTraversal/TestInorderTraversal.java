import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestInorderTraversal {

    private static int passed = 0;
    private static int failed = 0;

    private static Class<?> solutionClass;
    private static Class<?> nodeClass;

    private static Constructor<?> nodeConstructor;
    private static Method inorderMethod;

    private static Field leftField;
    private static Field rightField;


    public static void main(String[] args) {

        System.out.println("=========================================================");
        System.out.println("  Q8 Inorder Traversal — Test Results");
        System.out.println("=========================================================");


        if (!compileStudentCode()) {
            System.exit(1);
        }


        if (!loadStudentClasses()) {
            System.exit(1);
        }


        runTest(
                "test_01_basic_binary_tree",
                new int[]{4,2,5,1,3},
                new int[]{1,2,3,4,5},
                new int[][]{
                        {0,1,0},
                        {0,2,1},
                        {1,3,0},
                        {1,4,1}
                }
        );


        runTest(
                "test_02_right_subtree",
                new int[]{5,10,12,15,20},
                new int[]{10,5,15,12,20},
                new int[][]{
                        {0,1,0},
                        {0,2,1},
                        {2,3,0},
                        {2,4,1}
                }
        );


        runTest(
                "test_03_left_skewed_tree",
                new int[]{1,3,7},
                new int[]{7,3,1},
                new int[][]{
                        {0,1,0},
                        {1,2,0}
                }
        );


        runTest(
                "test_04_empty_tree",
                new int[]{},
                new int[]{},
                new int[][]{}
        );


        runTest(
                "test_05_single_node",
                new int[]{42},
                new int[]{42},
                new int[][]{}
        );


        runTest(
                "test_06_right_skewed_tree",
                new int[]{1,2,3,4,5},
                new int[]{1,2,3,4,5},
                new int[][]{
                        {0,1,1},
                        {1,2,1},
                        {2,3,1},
                        {3,4,1}
                }
        );


        runTest(
                "test_07_duplicate_values",
                new int[]{5,5,5,5,5},
                new int[]{5,5,5,5,5},
                new int[][]{
                        {0,1,0},
                        {0,2,1},
                        {1,3,0},
                        {1,4,1}
                }
        );


        runTest(
                "test_08_negative_and_zero_values",
                new int[]{-10,-5,0,5,10},
                new int[]{0,-5,10,-10,5},
                new int[][]{
                        {0,1,0},
                        {0,2,1},
                        {1,3,0},
                        {2,4,0}
                }
        );


        runTest(
                "test_09_balanced_tree",
                new int[]{1,2,3,4,5,6,7},
                new int[]{4,2,6,1,3,5,7},
                new int[][]{
                        {0,1,0},
                        {0,2,1},
                        {1,3,0},
                        {1,4,1},
                        {2,5,0},
                        {2,6,1}
                }
        );


        runTest(
                "test_10_mixed_values",
                new int[]{-5,-5,0,2,2,8},
                new int[]{2,-5,8,-5,0,2},
                new int[][]{
                        {0,1,0},
                        {0,2,1},
                        {1,3,0},
                        {1,4,1},
                        {2,5,0}
                }
        );


        System.out.println("---------------------------------------------------------");
        System.out.println("Score: " + passed + "/10");
        System.out.println("Marks: " + (passed * 10) + "/100");
        System.out.println("=========================================================");

        System.exit(failed > 0 ? 1 : 0);
    }



    private static boolean compileStudentCode() {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.out.println("Java compiler unavailable.");
            return false;
        }

        return compiler.run(
                null,
                null,
                null,
                "InorderTraversal.java"
        ) == 0;
    }



    private static boolean loadStudentClasses() {

        try {

            solutionClass = Class.forName("InorderTraversal");
            nodeClass = Class.forName("Node");

            nodeConstructor =
                    nodeClass.getDeclaredConstructor(int.class);

            nodeConstructor.setAccessible(true);


            inorderMethod =
                    solutionClass.getDeclaredMethod(
                            "inorder",
                            nodeClass
                    );


            inorderMethod.setAccessible(true);


            leftField =
                    nodeClass.getDeclaredField("left");

            rightField =
                    nodeClass.getDeclaredField("right");


            leftField.setAccessible(true);
            rightField.setAccessible(true);


            return true;


        } catch(Exception e){

            System.out.println(e.getMessage());
            return false;
        }
    }



    private static void runTest(
            String name,
            int[] expected,
            int[] values,
            int[][] connections
    ){

        try{

            Object root = createTree(values, connections);


            ArrayList<?> result =
                    (ArrayList<?>) inorderMethod.invoke(null, root);


            int[] actual = new int[result.size()];


            for(int i=0;i<result.size();i++){

                actual[i] =
                        ((Number)result.get(i)).intValue();

            }


            if(arrayEqual(expected, actual)){

                System.out.println("✓ PASS [" + name + "]");
                passed++;

            }else{

                System.out.println("✗ FAIL [" + name + "]");
                System.out.println("Expected: " + arrayToString(expected));
                System.out.println("Got:      " + arrayToString(actual));

                failed++;
            }


        }catch(Exception e){

            System.out.println("✗ FAIL ["+name+"]");
            failed++;
        }
    }



    private static Object createTree(
            int[] values,
            int[][] connections
    ) throws Exception{


        if(values.length==0)
            return null;


        Object[] nodes = new Object[values.length];


        for(int i=0;i<values.length;i++){

            nodes[i] =
                    nodeConstructor.newInstance(values[i]);

        }


        for(int[] c: connections){

            int parent = c[0];
            int child = c[1];
            int direction = c[2];


            if(direction==0){

                leftField.set(
                        nodes[parent],
                        nodes[child]
                );

            }else{

                rightField.set(
                        nodes[parent],
                        nodes[child]
                );
            }
        }


        return nodes[0];
    }



    private static boolean arrayEqual(
            int[] a,
            int[] b
    ){

        if(a.length!=b.length)
            return false;


        for(int i=0;i<a.length;i++){

            if(a[i]!=b[i])
                return false;
        }


        return true;
    }



    private static String arrayToString(int[] arr){

        StringBuilder sb = new StringBuilder("[");

        for(int i=0;i<arr.length;i++){

            sb.append(arr[i]);

            if(i<arr.length-1)
                sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }
}
