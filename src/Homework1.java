
import javax.swing.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;
public class Homework1 extends JPanel
        implements TreeSelectionListener {
    private JEditorPane htmlPane;
    private JTree tree;
    private static Node root;
    private static String get;
    private static boolean useleaf = false;

    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";

    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;

    public Homework1() {
        super(new GridLayout(1,0));

        //Create the nodes.
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode(root);
        CreateNodes(top,root);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(splitPane);

        ImageIcon leafIcon = createImageIcon("middle.gif");
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer =
                    new DefaultTreeCellRenderer();
            renderer.setClosedIcon(leafIcon);
            renderer.setOpenIcon(leafIcon);
            tree.setCellRenderer(renderer);
        }
    }
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Homework1.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null) return;
        useleaf=node.isLeaf();
        Object nodeInfo = node.getUserObject();
        DisplayNode((Node)nodeInfo);

    }


    public static String number;

    public static void main(String[] args) {


        number = args[0];
//        number = "251-*32*+";
        char[] letter = number.toCharArray();

         root = Stack(letter);
         get = infix(root);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }

    private static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }

        //Create and set up the window.
        JFrame frame = new JFrame("Jtree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new Homework1());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    public void DisplayNode(Node n)
    {
        get = infix(n);
        if(!useleaf)
        {
            get=get+"="+calculate(n);
        }
        htmlPane.setText(get);
    }
    public void  CreateNodes(DefaultMutableTreeNode top,Node n)
    {
        if(n.left!=null)
        {
            DefaultMutableTreeNode left = new DefaultMutableTreeNode(n.left);
            top.add(left);
            CreateNodes(left,n.left);
        }
        if(n.right!=null)
        {
            DefaultMutableTreeNode Right=new DefaultMutableTreeNode(n.right);
            top.add(Right);
            CreateNodes(Right,n.right);
        }
    }

    public static Node Stack(char letter[]) {

        Stack<Node> data = new Stack();
        Node node, num1, num2;

        for (int i = 0; i < letter.length; i++) {

            if (!is_operater(letter[i])) {

                node = new Node(letter[i]);
                data.push(node);

            } else {

                node = new Node(letter[i]);
                num2 = data.pop();
                num1 = data.pop();
                node.left = num1;
                node.right = num2;

                data.push(node);
            }

        }

        node = data.peek();
        return node;


    }

    public static String infix(Node node) {

        StringBuilder infix = new StringBuilder();
        inorder(node, infix);

        infix.deleteCharAt(0);
        infix.deleteCharAt(infix.length() - 1);
        for (int i = 1; i < infix.length() - 1; i++) {

            if (is_number(infix.charAt(i))) {
                infix.deleteCharAt(i - 1);

                if (i + 1 <= infix.length()) {
                    infix.deleteCharAt(i);
                }

            }


        }
//			System.out.println(infix.toString());


        return infix.toString();
    }

    public static void inorder(Node node, StringBuilder infix) {


        if (node != null) {
            infix.append('(');
            if (node.letter == '+') {
                inorder(node.left, infix);
                infix.append('+');
                inorder(node.right, infix);
            } else if (node.letter == '-') {
                inorder(node.left, infix);
                infix.append('-');
                inorder(node.right, infix);
            } else if (node.letter == '*') {
                inorder(node.left, infix);
                infix.append('*');
                inorder(node.right, infix);
            } else if (node.letter == '/') {
                inorder(node.left, infix);
                infix.append('/');
                inorder(node.right, infix);
            } else infix.append(node.letter);
            infix.append(')');


        }
    }

    public static boolean is_operater(char letter) {

        if (letter == '+' || letter == '-' || letter == '*' || letter == '/') {

            return true;
        }
        return false;

    }

    public static boolean is_number(char letter) {

        if (letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9' || letter == '0') {

            return true;
        }
        return false;

    }





    public static int calculate(Node node) {

        if (node != null) {
            if (node.letter == '+') {
                return calculate(node.left) + calculate(node.right);
            } else if (node.letter == '-') {
                return calculate(node.left) - calculate(node.right);
            } else if (node.letter == '*') {
                return calculate(node.left) * calculate(node.right);
            } else if (node.letter == '/') {
                return calculate(node.left) / calculate(node.right);
            } else return Integer.parseInt((String.valueOf(node.letter)));

        }
        return 0;


    }


}

