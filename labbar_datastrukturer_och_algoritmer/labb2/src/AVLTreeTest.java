import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * AVLTreeTest uses the code skeleton from VisualizeAVL to visualize
 * a test of the AVL tree's functions. Most of the code, except for
 * main, is written by Magnus Nielsen and Tommy Färnqvist.
 */
public class AVLTreeTest extends JPanel implements ActionListener {

    public static final long serialVersionUID = 3L;
    private AVLTree tree = null;
    private HashMap<AVLTreeNode, Rectangle> nodeLocations = null;
    private HashMap<AVLTreeNode, Dimension> subtreeSizes = null;
    static private boolean dirty = true;
    private int parent2child = 20, child2child = 30;
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    public AVLTreeTest(AVLTree tree) {
        this.tree = tree;
        nodeLocations = new HashMap<AVLTreeNode, Rectangle>();
        subtreeSizes = new HashMap<AVLTreeNode, Dimension>();
    }

    /**
     * Repaints the tree when an action has been performed (insert or
     * remove).
     */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /**
     * Calculates the node locations.
     */
    private void calculateLocations() {
        nodeLocations.clear();
        subtreeSizes.clear();
        AVLTreeNode root = tree.getRoot();
        if (root != null) {
            calculateSubtreeSize(root);
            calculateLocation(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    /**
     * Calculate the size of a subtree rooted at n
     *
     * @param n - root of subtree (AVLTreeNode)
     * @return - returns the dimension of the sub tree (Dimension, Swing lib)
     */
    private Dimension calculateSubtreeSize(AVLTreeNode n) {
        if (n == null)
            return new Dimension(0, 0);
        Dimension ld = calculateSubtreeSize(n.getLeft());
        Dimension rd = calculateSubtreeSize(n.getRight());
        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;
        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);
        return d;
    }

    /**
     * Calculate the location of the nodes in the subtree rooted at n
     *
     * @param n     - root of subtree (AVLTreeNode)
     * @param left  - used to calculate how far to the left to place the sub tree (int)
     * @param right - used to calculate how far to the right to place the sub tree (int)
     * @param top   - used to calculate how far from the top to place the sub tree (int)
     */
    private void calculateLocation(AVLTreeNode n, int left, int right, int top) {
        if (n == null)
            return;
        Dimension ld = (Dimension) subtreeSizes.get(n.getLeft());
        if (ld == null)
            ld = empty;
        Dimension rd = (Dimension) subtreeSizes.get(n.getRight());
        if (rd == null)
            rd = empty;
        int center = 0;
        if (right != Integer.MAX_VALUE)
            center = right - rd.width - child2child / 2;
        else if (left != Integer.MAX_VALUE)
            center = left + ld.width + child2child / 2;
        int width = fm.stringWidth("" + n.getElement());
        Rectangle r = new Rectangle(center - width / 2 - 3, top, width + 6, fm
                .getHeight());
        nodeLocations.put(n, r);
        calculateLocation(n.getLeft(), Integer.MAX_VALUE, center - child2child / 2,
                top + fm.getHeight() + parent2child);
        calculateLocation(n.getRight(), center + child2child / 2, Integer.MAX_VALUE,
                top + fm.getHeight() + parent2child);
    }

    /**
     * Draw the tree using the pre-calculated locations.
     *
     * @param g     - graphics object from Swing library (Graphics2D)
     * @param n     - root of subtree (AVLTreeNode)
     * @param px    - position on the x axis (int)
     * @param py    - position on the y axis (int)
     * @param yoffs - offset on the y axis (int)
     */
    private void drawTree(Graphics2D g, AVLTreeNode n, int px, int py, int yoffs) {
        if (n == null)
            return;
        Rectangle r = (Rectangle) nodeLocations.get(n);
        g.draw(r);
        g.drawString("" + n.getElement(), r.x + 3, r.y + yoffs);
        if (px != Integer.MAX_VALUE)
            g.drawLine(px, py, r.x + r.width / 2, r.y);
        drawTree(g, n.getLeft(), r.x + r.width / 2, r.y + r.height, yoffs);
        drawTree(g, n.getRight(), r.x + r.width / 2, r.y + r.height, yoffs);
    }

    /**
     * Paints the entire tree.
     *
     * @param g - graphics object from Swing library (Graphics2D)
     */
    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();
        // if node locations not calculated
        if (dirty) {
            calculateLocations();
            dirty = false;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        drawTree(g2d, tree.getRoot(), Integer.MAX_VALUE, Integer.MAX_VALUE, fm
                .getLeading()
                + fm.getAscent());
        fm = null;
    }

    /**
     * Get the next int from the scanner.
     *
     * @param in - scanner object linked to a data stream (Scanner, java.util)
     * @return - int containing the first int in the scanner stream.
     */
    static int getInt(Scanner in) {
        return in.nextInt();
    }


    public static void main(String[] args) {
        boolean textMode = false;
        if (args.length == 1 && args[0].equals("--text-mode")) {
            textMode = true;
        }

        AVLTree<Integer> tree = new AVLTree<>();
        AVLTree<Character> charTree = new AVLTree<>();

        JFrame f = new JFrame("AVL");
        f.getContentPane().add(new AVLTreeTest(tree));
        // create and add an event handler for window closing event
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setBounds(50, 50, 600, 400);
        if (!textMode) {
            f.setVisible(true);
        }


        //SCANNER
        int k;
        Scanner in = new Scanner(System.in);


        //TEST 1
        System.out.println("Test 1 - Normal insert");
        tree.insert(6);
        tree.insert(4);
        tree.insert(10);
        tree.insert(5);
        dirty = true;
        f.repaint();
        System.out.println("Tree after insertion of nodes: 6, 4, 10, 5");


        //TEST 2
        System.out.println("\nTest 2 - Removal of key");
        System.out.println("Press 1 to delete node 10");
        k = getInt(in);
        if (k == 1) {
            System.out.println("Tree after removal of node 10");
            tree.remove(10);
        }
        dirty = true;
        f.repaint();

        //goes to next test
        System.out.println("Press 1 to go to next test");
        k = getInt(in);
        if (k == 1) {
            tree.clear();
            dirty = true;
            f.repaint();
        }


        //TEST 3
        System.out.println("\nTest 3 - Duplicate inserts");
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
        dirty = true;
        f.repaint();
        System.out.println("Tree after insertion of nodes: 1, 9, 1, 4, 1, 11, 1, 2, 1, 6, 1, 10, 1, 12, 1, 1, 1, 3, 1, 7, 1, 13, 1, 8");

        System.out.println("Press 1 to remove root");
        k = getInt(in);
        if (k == 1) {
            tree.remove(9);
            dirty=true;
            f.repaint();
        }

        //goes to next test and switches to a character AVLtree
        System.out.println("Press 1 to go to next test");
        k = getInt(in);
        if (k == 1) {
            //Switch to charTree
            f.dispose();
            f = new JFrame("AVL");
            f.getContentPane().add(new AVLTreeTest(charTree));
            // create and add an event handler for window closing event
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            f.setBounds(50, 50, 600, 400);
            if (!textMode) {
                f.setVisible(true);
            }
        }


        //TEST 4
        System.out.println("\nTest 4 - charTree and rotations");
        System.out.println("\nPress 1 to go to next rotation");

        //LEFT ROTATION
        System.out.println("Left rotation:");
        charTree.insert('M');
        charTree.insert('N');
        charTree.insert('O');
        dirty = true;
        f.repaint();
        System.out.println("Tree after insertion of nodes: M, N, O");
        k = getInt(in); //pause til user goes to next rotation

        //RIGHT ROTATION
        System.out.println("Right rotation:");
        charTree.insert('L');
        charTree.insert('K');
        dirty = true;
        f.repaint();
        System.out.println("Tree after insertion of nodes: L, K");
        k = getInt(in);

        //RIGHT LEFT ROTATION
        System.out.println("Right left rotation:");
        charTree.insert('Q');
        charTree.insert('P');
        dirty = true;
        f.repaint();
        System.out.println("Tree after insertion of nodes: Q, P");
        k = getInt(in);

        //LEFT RIGHT ROTATION
        System.out.println("Left right rotation:");
        charTree.insert('H');
        charTree.insert('I');
        dirty = true;
        f.repaint();
        System.out.println("Tree after insertion of nodes: H, I");

        //Removal test
        System.out.println("\nPress 1 to remove: M, O");
        k = getInt(in);
        if (k==1){
            charTree.remove('M');
            charTree.remove('O');
            dirty=true;
            f.repaint();
        }

        System.out.println("Tests complete");

    }
}
