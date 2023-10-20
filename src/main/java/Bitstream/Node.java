package Bitstream;

public class Node <T> {
    public Node<T> left;
    public Node<T> right;
    public T t;

    public void addToTree(String a, T t) {

        if (a.charAt(0) == '0') {
            if (a.length() == 1) {
                this.left = new Node<T>();
                this.left.t = t;
            }
            else {
                if (this.left == null) {
                    this.left = new Node<T>();
                }
                this.left.addToTree(a.substring(1), t);
            }
        }
        else {
            if (a.length() == 1) {
                this.right = new Node<T>();
                this.right.t = t;
            }
            else {
                if (this.right == null) {
                    this.right = new Node<T>();
                }
                this.right.addToTree(a.substring(1), t);
            }
        }
    }


    public Node<T> getNode(char c, boolean addNode) {

        if (c == '0') {

            if (this.left == null) {
                if (addNode) {
                    this.left = new Node<T>();
                }
                else {
                    return this;
                }
            }
            return this.left;

        }
        else {
            if (this.right == null) {
                if (addNode) {
                    this.right = new Node<T>();
                }
                else {
                    return this;
                }
            }
            return this.right;

        }
    }
}

