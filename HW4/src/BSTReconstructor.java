/**
 * Homework 4 - Group 2
 * Nathan Deininger, Will Pond, Zack Sargent
 */

import java.util.List;

public class BSTReconstructor {
    static BST<Integer> originalBST;
    static BST<Integer> preOrderReconstructedBST = new BST<>();
    static BST<Integer> postOrderReconstructedBST = new BST<>();

    static List<Integer> inOrderOutput;
    static List<Integer> preOrderOutput;
    static List<Integer> postOrderOutput;

    static Integer[] input = {35, 25, 65, 30, 15, 20, 95, 45, 40, 55, 32, 60, 75};

    public static void main(String[] args) {
        //	Create BST from input
        originalBST = new BST<>(input);

        // Generate in/pre/post order traversal lists
        generateBSTTraversalLists();

        // Reconstruct BST from traversal lists of original BST
        System.out.println("Reconstructing BSTs from pre and post order traversal lists.");
        preOrderReconstructor(preOrderOutput);
        postOrderReconstructor(postOrderOutput);

        reconstructionVerificationTests();
    }

    // Generate in/pre/post order traversal lists
    private static void generateBSTTraversalLists() {
        inOrderOutput = originalBST.inorder();
        System.out.println("In-order: " + inOrderOutput);

        preOrderOutput = originalBST.preorder();
        System.out.println("Pre-order: " + preOrderOutput);

        postOrderOutput = originalBST.postorder();
        System.out.println("Post-order: " + postOrderOutput);
    }

    private static void reconstructionVerificationTests() {

        System.out.println("preOrderReconstructedBST should be true: " + originalBST.isEqualTo(preOrderReconstructedBST));
        System.out.println("postOrderReconstructedBST should be true: " + originalBST.isEqualTo(postOrderReconstructedBST));

        System.out.println("Inserting and deleting 65 from original BST");
        originalBST.delete(65);
        originalBST.insert(65);

        System.out.println("preOrderReconstructedBST should be false: " + originalBST.isEqualTo(preOrderReconstructedBST));
        System.out.println("postOrderReconstructedBST should be false: " + originalBST.isEqualTo(postOrderReconstructedBST));

        System.out.println("Inserting and deleting 65 from preOrderReconstructed BST");
        preOrderReconstructedBST.delete(65);
        preOrderReconstructedBST.insert(65);

        System.out.println("preOrderReconstructedBST should be true: " + originalBST.isEqualTo(preOrderReconstructedBST));
        System.out.println("postOrderReconstructedBST should be false: " + originalBST.isEqualTo(postOrderReconstructedBST));

        System.out.println("Inserting and deleting 65 from postOrderReconstructed BST");
        postOrderReconstructedBST.delete(65);
        postOrderReconstructedBST.insert(65);

        System.out.println("preOrderReconstructedBST should be true: " + originalBST.isEqualTo(preOrderReconstructedBST));
        System.out.println("postOrderReconstructedBST should be true: " + originalBST.isEqualTo(postOrderReconstructedBST));
    }

    // Method to find the split index in the input array for reconstructing the BST.
    // The split index is the first element greater than the root.
    // This helps identify elements for the left and right subtrees of the root
    private static int findSplitIndex(int start, int stop, Integer root, List<Integer> inputArray) {
        for (int i = start; i < stop; i++) {
            if (inputArray.get(i) > root) {
                return i;
            }
        }
        return stop; // if no such index is found, split at the end of the list.
    }


    // Reconstruct BST from pre-order traversal lists of original BST.
    // This method will take an inputArray of pre-order traversal items
    // and re-create the original BST, and save reconstructed tree in the
    // preOrderReconstructedBST variable.
    private static void preOrderReconstructor(List<Integer> inputArray) {
        if (inputArray.isEmpty()) { // recursive base case
            return;
        }

        // The first element in the current input array is the root.
        Integer root = inputArray.get(0);
        preOrderReconstructedBST.add(root);

        if (inputArray.size() > 1) { // handle special cases
            // Loop from the second element till the end.
            // Stop when the element is greater than the root.
            int splitIndex = findSplitIndex(1, inputArray.size(), root, inputArray);
            // Split the array into two sub-arrays, which implies the elements in the left and right sub-trees.
            // Recursively start from step 1 to reconstruct the left and right subtrees from the two sub-arrays.
            preOrderReconstructor(inputArray.subList(1, splitIndex));
            preOrderReconstructor(inputArray.subList(splitIndex, inputArray.size()));
        }
    }


    // Reconstruct BST from post-order traversal lists of original BST.
    // This method will take an inputArray of post-order traversal items
    // and re-create the original BST, and save reconstructed tree in the
    // postOrderReconstructedBST variable.
    private static void postOrderReconstructor(List<Integer> inputArray) {
        if (inputArray.isEmpty()) { // recursive base case
            return;
        }
        // The last element N in the current input array is the root.
        Integer root = inputArray.get(inputArray.size() - 1);
        postOrderReconstructedBST.add(root);
        // Loop from beginning till N-1.
        // Stop when the element is greater than the root.
        int splitIndex = findSplitIndex(0, inputArray.size() - 1, root, inputArray);
        // Split the array into two sub-arrays, which implies the elements in the left and right sub-trees.
        // Recursively start from step 1 to reconstruct the left and right subtrees from the two sub-arrays.
        List<Integer> left = inputArray.subList(0, splitIndex);
        postOrderReconstructor(left);
        List<Integer> right = inputArray.subList(splitIndex, inputArray.size() - 1);
        postOrderReconstructor(right);
    }
}
