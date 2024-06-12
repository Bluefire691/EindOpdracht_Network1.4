public class BSTTest {
    public static void main(String[] args) {
        // Creëer een nieuwe BST voor integers
        BST<Integer> bst = new BST<>();

        // Voeg enkele elementen toe aan de BST
        bst.insert(10);
        bst.insert(20);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);

        // Test of bepaalde elementen in de BST zitten
        System.out.println("Contains 10: " + bst.contains(10)); // Output: true
        System.out.println("Contains 15: " + bst.contains(15)); // Output: true
        System.out.println("Contains 7: " + bst.contains(7));   // Output: false

        // Test een inorder traversal
        System.out.print("Inorder traversal: ");
        bst.inorder(); // Output: 3 5 10 15 20

        // Test een preorder traversal
        System.out.print("\nPreorder traversal: ");
        bst.preorder(); // Output: 10 5 3 20 15

        // Test een postorder traversal
        System.out.print("\nPostorder traversal: ");
        bst.postorder(); // Output: 3 5 15 20 10
    }
}
