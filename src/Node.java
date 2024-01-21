public class Node {

    //This class constitutes my node class that I inserted into my AVL tree.
    // I create fields where information about the node is stored and place them with the constructor.
    private String name;
    private double GMS;
    private Node left;
    private  Node right;
    private int height;

    Node(String name, double GMS, Node left, Node right){
        this.name=name;
        this.GMS=GMS;
        this.left=left;
        this.right=right;
        this.height=0;
    }

    // I create the getter and setter methods for accessing and modifying node properties
    public double getGMS() {
        return GMS;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public void setGMS(double GMS) {
        this.GMS = GMS;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}
