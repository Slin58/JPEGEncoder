package Bitstream;

public class NodeOLD<T> {
    public NodeOLD<T> left;
    public NodeOLD<T> right;
    public T t;

    public void addToTree(String a, T t) {

        if (a.charAt(0) == '0') {
            if (a.length() == 1) {
                this.left = new NodeOLD<T>();
                this.left.t = t;
            }
            else {
                if (this.left == null) {
                    this.left = new NodeOLD<T>();
                }
                this.left.addToTree(a.substring(1), t);
            }
        }
        else {
            if (a.length() == 1) {
                this.right = new NodeOLD<T>();
                this.right.t = t;
            }
            else {
                if (this.right == null) {
                    this.right = new NodeOLD<T>();
                }
                this.right.addToTree(a.substring(1), t);
            }
        }
    }


    public NodeOLD<T> getNode(char c, boolean addNode) {

        if (c == '0') {

            if (this.left == null) {
                if (addNode) {
                    this.left = new NodeOLD<T>();
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
                    this.right = new NodeOLD<T>();
                }
                else {
                    return this;
                }
            }
            return this.right;

        }
    }
}

