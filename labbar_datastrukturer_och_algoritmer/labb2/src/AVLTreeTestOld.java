public class AVLTreeTestOld {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        //TEST CASE 1
        System.out.println("Test case 1");
        tree.insert(6);
        tree.insert(4);
        tree.insert(10);
        tree.insert(5);

        System.out.println("Tree after insertion of nodes: 6, 4, 10, 5");
        tree.print();

        System.out.println("\nTree after removal of node 10");
        tree.remove(10);
        tree.print();

        tree.clear();

        //TEST CASE 2
        System.out.println("\nTest case 2");
        tree.insert(6);
        tree.insert(4);
        tree.insert(10);
        tree.insert(15);

        System.out.println("Tree after insertion of nodes: 6, 4, 10, 15");
        tree.print();

        System.out.println("\nTree after removal of node 4");
        tree.remove(4);
        tree.print();

        tree.clear();

        //TEST CASE 3
        System.out.println("\nTest case 3");
        tree.insert(1);
        tree.insert(9);
        tree.insert(1);
        tree.insert(4);
        tree.insert(1);
        tree.insert(11);
        tree.insert(1);
        tree.insert(2);
        tree.insert(1);
        tree.insert(6);
        tree.insert(1);
        tree.insert(10);
        tree.insert(1);
        tree.insert(12);
        tree.insert(1);
        tree.insert(1);
        tree.insert(1);
        tree.insert(3);
        tree.insert(1);
        tree.insert(5);
        tree.insert(1);
        tree.insert(7);
        tree.insert(1);
        tree.insert(13);
        tree.insert(1);
        tree.insert(8);

        System.out.println("Tree after insertion of nodes: 1, 9, 1, 4, 1, 11, 1, 2, 1, 6, 1, 10, 1, 12, 1, 1, 1, 3, 1, 7, 1, 13, 1, 8");
        tree.print();


        System.out.println("\nTree after removal of node 9");
        tree.remove(9);
        tree.print();
        tree.clear();

        //TEST CASE 4
        AVLTree<Character> charTree = new AVLTree<>();
        System.out.println("Test case 4");

        System.out.println("Tree after insertion of nodes: M, N, O");
        //LEFT ROTATION
        charTree.insert('M');
        charTree.insert('N');
        charTree.insert('O');

        charTree.print();

        System.out.println("\nTree after insertion of nodes: L, K");
        //RIGHT ROTATION
        charTree.insert('L');
        charTree.insert('K');

        charTree.print();

        System.out.println("\nTree after insertion of nodes: Q, P");
        //RIGHT LEFT ROTATION
        charTree.insert('Q');
        charTree.insert('P');

        charTree.print();

        System.out.println("\nTree after insertion of nodes: H, I");
        //LEFT RIGHT ROTATION
        charTree.insert('H');
        charTree.insert('I');

        charTree.print();

        System.out.println("\nTree after removal of node N");
        charTree.remove('N');

        charTree.print();

        System.out.println("\nTree after insertion of node R");
        charTree.insert('R');

        charTree.print();
    }
}
