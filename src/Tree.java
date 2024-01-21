import java.util.*;

public class Tree {

    public Node root;
    public Tree(){
        root=null;
    }

    //INSERT PART: Here, I find the place where the new Node to be added should be in the Tree and add it to the Tree,
    // and I put my Tree in AVL order with my Balance method.
    // Meanwhile, I also write the approval message of the newly added member to the file by the superiors.
    public void insert(String name,double GMS){
        root=insert(name, GMS,root);
    }
    public Node insert(String name,double GMS, Node node){
        if(node==null){
            return new Node(name,GMS,null,null);
        }
        else if(GMS>node.getGMS()){
            System.out.println(node.getName()+" welcomed "+name);
            node.setRight(insert(name,GMS,node.getRight()));
        }
        else if(GMS<node.getGMS()){
            System.out.println(node.getName()+" welcomed "+name);
            node.setLeft(insert(name,GMS,node.getLeft()));
        }
        return Balance(node);
    }


    //INTEL DIVIDE PART:Here I calculate the maximum number of independent members my Tree can have,
    // without being above or below each other.
    public int intelDivide(Dictionary<Double, Integer> visitedDictionary) {
        return intelDivide(root,visitedDictionary);
    }

    private int intelDivide(Node node,Dictionary<Double,Integer> visitedDictionary) {
        //First I check whether the node is empty or not
        if (node == null) {
            return 0;
        }
        //Then, if the node is a leaf, I do not process this node separately and give it the value "1"
        // because the result is clear and my code runs faster.
        else if(node.getRight()==null && node.getRight()==null){
            return 1;
        }

        //Here, during the IntelDivide process, I check whether the number of independent members of this node has been calculated before,
        // so I do not need to process it again for this node.
        // I also use the "Dictionary" data structure,
        // which uses the Hash algorithm, to reset the information of the independent member numbers of my nodes every IntelDivide
        // and to quickly access that data and add new information to the data field.
        else if(visitedDictionary.get(node.getGMS())!=null){
            return visitedDictionary.get(node.getGMS());
        }

        //In this part, where I do the main operation in IntelDivide,
        // I first look at which children my node has and direct it accordingly,
        // and here I calculate two possibilities,
        // where I include the node (parent) we have
        // and where I include its children without including the node.
        // Whichever is greater, I return it since we are trying to find the maximum independent member.
        else if(node.getLeft()!=null && node.getRight()!=null){
            int withChildrenSequence = intelDivide(node.getLeft(),visitedDictionary) + intelDivide(node.getRight(),visitedDictionary);
            int withParentSequence =1+intelDivide(node.getLeft().getLeft(),visitedDictionary) + intelDivide(node.getLeft().getRight(),visitedDictionary)+intelDivide(node.getRight().getLeft(),visitedDictionary) + intelDivide(node.getRight().getRight(),visitedDictionary);
            visitedDictionary.put(node.getGMS(),Math.max(withChildrenSequence, withParentSequence));
            return Math.max(withChildrenSequence, withParentSequence);
        }
        else if (node.getRight() == null) {
            int withChildrenSequence = intelDivide(node.getLeft(),visitedDictionary) + intelDivide(node.getRight(),visitedDictionary);
            int withParentSequence = 1+intelDivide(node.getLeft().getLeft(),visitedDictionary) + intelDivide(node.getLeft().getRight(),visitedDictionary);
            visitedDictionary.put(node.getGMS(),Math.max(withParentSequence, withChildrenSequence));
            return Math.max(withParentSequence, withChildrenSequence);
        }
        else  {
            int withChildrenSequence = intelDivide(node.getLeft(),visitedDictionary) + intelDivide(node.getRight(),visitedDictionary);
            int withParentSequence = 1+intelDivide(node.getRight().getLeft(),visitedDictionary) + intelDivide(node.getRight().getRight(),visitedDictionary);
            visitedDictionary.put(node.getGMS(),Math.max(withParentSequence, withChildrenSequence));
            return Math.max(withParentSequence, withChildrenSequence);
        }
    }


    //INTEL RANK PART:First, I calculate the distance of the given member to the root using the "findDistanceToRoot" method.
    //Then, since we are asked to sort the members with the same distance in a sorted manner,
    //I use the preOrder traversal technique, that is, by going from left to right,
    //I print the members whose distance is the same as the member given to us,
    //thus I also print them in a sorted manner.
    public void preOrder(int targetDistance,int myDistance,Node node){
        if(node==null){
            return;
        }
        if(targetDistance==myDistance){
            String GMStarget=String.format("%.3f",node.getGMS());
            GMStarget=GMStarget.replace(",",".");
            System.out.print(" "+node.getName()+" "+GMStarget);
        }
        preOrder(targetDistance,myDistance+1,node.getLeft());
        preOrder(targetDistance,myDistance+1,node.getRight());
    }
    public int findDistanceToRoot(double GMS, Node node, int distance){
        if(GMS>node.getGMS()){
            return findDistanceToRoot(GMS,node.getRight(),distance+1);
        }
        else if(GMS<node.getGMS()){
            return findDistanceToRoot(GMS,node.getLeft(),distance+1);
        }
        else {
            return distance;
        }
    }


    // REMOVE PART:In this section, I find the location of the node that I want to remove,
    // then if that node has two children, I replace the lowest member from its right subtree with the member I removed
    // and use the "findMin" method to find it. If there is a child, I replace the child with the node I removed.
    // If there is no child, I turn it to null.
    // Finally, I ensure that my tree remains as an AVL tree again with my "Balance" method.
    // Additionally, after I perform the extraction process, I print this information to the file and use the "controlWrited" variable
    // to prevent multiple printing operations.
    public Node findMin(Node node) {
        if (node.getLeft()==null){
            return node;
        }
        else {
            return findMin(node.getLeft());
        }

    }
    public void remove(String name, double GMS){
        root=remove(name,GMS,root,0);
    }

    public Node remove(String name, double GMS, Node node,int controlWrited) {
        if (node == null) {
            return node;
        }
        if (GMS > node.getGMS()) {
            node.setRight(remove(name, GMS, node.getRight(),controlWrited));
        }
        else if (GMS < node.getGMS()) {
            node.setLeft(remove(name, GMS, node.getLeft(),controlWrited));
        }
        else if (node.getLeft() != null && node.getRight() != null) {
            Node temp = findMin(node.getRight());
            if(controlWrited==0) {
                System.out.println(name + " left the family, replaced by " + temp.getName());
            }
            node.setName(temp.getName());
            node.setGMS(temp.getGMS());
            node.setRight(remove(name, node.getGMS(), node.getRight(),1));
        }
        else if (node.getLeft() != null) {
            node = node.getLeft();
            if(controlWrited==0) {
                System.out.println(name + " left the family, replaced by " + node.getName());
            }
        }
        else if (node.getRight()!=null){
            node = node.getRight();
            if(controlWrited==0) {
                System.out.println(name + " left the family, replaced by " + node.getName());
            }
        }
        else if (node.getRight()==null && node.getLeft()==null){
            node=null;
            if(controlWrited==0){
                System.out.println(name+" left the family, replaced by nobody");
            }
        }
        return Balance(node);
    }


    // BALANCE PART:In this section, after inserting and removing operations,
    // I take the necessary steps to bring my AVL tree back to the appropriate state.
    // I decide which one to do in a total of 4 cases,
    // 2 involving Single Rotation and 2 involving Double Rotation,
    // by examining the height status of my nodes, and lastly after completing the operations,
    // I update the new height status of my node.
    public Node rightRotation(Node k2){
        Node k1=k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        if(height(k2.getLeft())>height(k2.getRight())){
            k2.setHeight(height(k2.getLeft())+1);
        }
        else {
            k2.setHeight(height(k2.getRight())+1);
        }
        if (height(k1.getLeft())>height(k1.getRight())){
            k1.setHeight(height(k1.getLeft())+1);
        }
        else {
            k1.setHeight(height(k1.getRight())+1);
        }
        return k1;
    }
    public Node leftRotation(Node k1){
        Node k2=k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);
        if(height(k2.getLeft())>height(k2.getRight())){
            k2.setHeight(height(k2.getLeft())+1);
        }
        else {
            k2.setHeight(height(k2.getRight())+1);
        }
        if (height(k1.getLeft())>height(k1.getRight())){
            k1.setHeight(height(k1.getLeft())+1);
        }
        else {
            k1.setHeight(height(k1.getRight())+1);
        }
        return k2;
    }
    public Node leftRightRotation(Node k1){
        k1.setRight(rightRotation(k1.getRight()));
        return leftRotation(k1);
    }
    public Node rightLeftRotation(Node k3){
        k3.setLeft(leftRotation(k3.getLeft()));
        return rightRotation(k3);
    }
    public Node Balance(Node node) {
        if (node == null) {
            return node;
        } else if ((height(node.getLeft()) - height(node.getRight())) > 1) {
            if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
                node = rightRotation(node);
            } else {
                node = rightLeftRotation(node);
            }
        } else if ((height(node.getRight()) - height(node.getLeft())) > 1) {
            if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
                node = leftRotation(node);
            } else {
                node = leftRightRotation(node);
            }

        }
        if (height(node.getLeft()) > height(node.getRight())) {
            node.setHeight(height(node.getLeft()) + 1);
        } else {
            node.setHeight(height(node.getRight()) + 1);
        }
        return node;
    }
    private int height(Node n){
        if(n==null){
            return -1;
        }
        else {
            return n.getHeight();
        }

    }

    // INTEL TARGET PART: In this part, I start searching from the root to
    // find the lowest ranking member that is the superior of both of these two member which are given to me.
    // When these two nodes' GMS value are not greater or less than the current node
    // I find the lowest ranking member that is the superior of both of these two member
    public Node target(double GMSfirst,double GMSsecond, Node node){
        if(GMSfirst>node.getGMS() && GMSsecond>node.getGMS()){
            return target(GMSfirst,GMSsecond,node.getRight());
        }
        else if(GMSfirst<node.getGMS() && GMSsecond<node.getGMS()){
            return target(GMSfirst,GMSsecond,node.getLeft());
        }
        else {
            return node;
        }
    }

}
