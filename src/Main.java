import java.io.*;
import java.util.*;

/**
 * This program was written to meet the needs of the Tattaglia Crime Family.
 * The names and GMS values of the members of the Tattaglia Crime Family were created as "Node" with the help of the "Node" class and
 * the nodes are stored in a structure very similar to AVL Tree with the help of the "Tree" class.
 * Kerem Bozkurt 2020400177
 * */
public class Main {
    public static void main(String[] args) throws IOException {
        //In this part, I read the "input" file from the console and write it to the "output" file.
        PrintStream out = new PrintStream(new FileOutputStream(args[1]));
        System.setOut(out);
        File file =new File(args[0]);
        Scanner myScanner = new Scanner(file);

        //Here, I create a "tree" structure where I will keep the structure, pull the data that will form my first "root" and enter the tree as root.
        String[] temp=myScanner.nextLine().split(" ");
        Tree myTree=new Tree();
        myTree.insert(temp[0],Double.parseDouble(temp[1]));

        //In this part, I read the commands from the input file, give them what is requested and organize my tree.
        //Note: I make detailed explanations about operations in the comment section of the Tree class.
        while (myScanner.hasNextLine()){
            String[] temp2=myScanner.nextLine().split(" ");
            if(temp2[0].equals("MEMBER_IN")){
                //In this section, I add a new member to the tree.
                myTree.insert(temp2[1],Double.parseDouble(temp2[2]));
            }
            else if(temp2[0].equals("MEMBER_OUT")){
                //In this section, I remove a  member from the tree.
                myTree.remove(temp2[1],Double.parseDouble(temp2[2]));
            }
            else if(temp2[0].equals("INTEL_DIVIDE")){
                // Since the IntelDivide feature is requested in this section, I do this with the help of tree.
                Dictionary<Double,Integer> myDictionary=new Hashtable<>();
                System.out.println("Division Analysis Result: "+myTree.intelDivide(myDictionary));
            }
            else if(temp2[0].equals("INTEL_TARGET")){
                //Since the IntelTarget feature is requested in this section, I do this with the help of tree.
                double GMSfirst=Double.parseDouble(temp2[2]);
                double GMSsecond=Double.parseDouble(temp2[4]);
                Node target=myTree.target(GMSfirst,GMSsecond,myTree.root);
                String GMStarget=String.format("%.3f",target.getGMS()); //Here I use String format to get the result in the desired format.
                GMStarget=GMStarget.replace(",",".");
                System.out.println("Target Analysis Result: "+target.getName()+" "+GMStarget);
            }
            else if(temp2[0].equals("INTEL_RANK")){
                //Since the IntelRank feature is requested in this section, I do this with the help of tree.
                int distance= myTree.findDistanceToRoot(Double.parseDouble(temp2[2]),myTree.root,0); //
                System.out.print("Rank Analysis Result:");
                myTree.preOrder(distance,0,myTree.root);
                System.out.println("");
            }
        }

    }
}