package huffman;

public class HuffmanNode<T> {
    private HuffmanNode<T> left;
    private HuffmanNode<T> right;
    private T value;

    public HuffmanNode(T value) {
        this.value = value;
    }

    public HuffmanNode(){

    }

    public T getValue() {
        if (value == null) {
            throw new RuntimeException("Tried to access value of a branch node without a value");
        }
        return value;
    }

    public HuffmanNode<T> getLeft() {
        return left;
    }

    public HuffmanNode<T> getRight() {
        return right;
    }

    public void setNode(HuffmanNode<T> node){
        if (this.left == null){
            this.left = node;
        } else if (this.right == null){
            this.right = node;
        }else {
            throw new RuntimeException("something went wrong while writing node");
        }
    }

    @Override
    public String toString() {
        if (value == null) {
            return "+";
        }
        return this.value.toString();
    }
}
