package huffman;

import java.util.Objects;

public class HuffmanNode<T> {
    private HuffmanNode<T> left;
    private HuffmanNode<T> right;
    private T value;

    public HuffmanNode(T value) {
        this.value = value;
    }

    public HuffmanNode() {

    }

    public T getValue() {
        return value;
    }

    public HuffmanNode<T> getLeft() {
        return left;
    }

    public HuffmanNode<T> getRight() {
        return right;
    }

    public void setNode(HuffmanNode<T> node) {
        if (this.left == null) {
            this.left = node;
        } else if (this.right == null) {
            this.right = node;
        } else {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HuffmanNode<?> that)) return false;
        return Objects.equals(getLeft(), that.getLeft()) && Objects.equals(getRight(), that.getRight()) &&
               Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getRight(), getValue());
    }
}
