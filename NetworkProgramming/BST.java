public class BST<T extends Comparable<T>> {
    private class Node {
        T data;
        Node left, right;

        public Node(T data) {
            this.data = data;
            left = right = null;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, T data) {
        if (root == null) {
            return new Node(data);
        }
        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, data);
        }
        // Als data gelijk is aan de huidige knoopdata, voegen we niet opnieuw in
        return root;
    }

    public boolean contains(T data) {
        return containsRec(root, data);
    }

    private boolean containsRec(Node root, T data) {
        if (root == null) {
            return false;
        }
        if (data.compareTo(root.data) == 0) {
            return true;
        }
        if (data.compareTo(root.data) < 0) {
            return containsRec(root.left, data);
        }
        return containsRec(root.right, data);
    }

    // Inorder traversal
    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    // Preorder traversal
    public void preorder() {
        preorderRec(root);
    }

    private void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    // Postorder traversal
    public void postorder() {
        postorderRec(root);
    }

    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }
    public void remove(T data) {
        root = removeRec(root, data);
    }

    private Node removeRec(Node root, T data) {
        if (root == null) {
            return null;
        }

        // Zoek het te verwijderen element in de linker- of rechtersubboom
        if (data.compareTo(root.data) < 0) {
            root.left = removeRec(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = removeRec(root.right, data);
        } else {
            // Gevonden element om te verwijderen

            // Geval 1: Geen kinderen of slechts één kind
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Geval 2: Twee kinderen
            // Vervang het te verwijderen element door de in-order opvolger (het kleinste element in de rechter subboom)
            root.data = minValue(root.right);

            // Verwijder de in-order opvolger uit de rechter subboom
            root.right = removeRec(root.right, root.data);
        }

        return root;
    }

    private T minValue(Node root) {
        T minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }
}
