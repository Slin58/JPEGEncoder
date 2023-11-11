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

    public static <T> int getMinDepth(HuffmanNode<T> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 1;
        if (node.getLeft() == null) return getMinDepth(node.getLeft());
        if (node.getRight() == null) return getMinDepth(node.getRight());
        return Math.min(getMinDepth(node.getLeft()), getMinDepth(node.getRight())) + 1;
    }

    public static <T> int getMaxDepth(HuffmanNode<T> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 1;
        if (node.getLeft() == null) return getMaxDepth(node.getLeft());
        if (node.getRight() == null) return getMaxDepth(node.getRight());
        return Math.max(getMaxDepth(node.getLeft()), getMaxDepth(node.getRight())) + 1;
    }

    public T getValue() {
        return value;
    }

    public HuffmanNode<T> getLeft() {
        return left;
    }

    private void setLeft(HuffmanNode<T> left) {
        this.left = left;
    }

    public HuffmanNode<T> getRight() {
        return right;
    }

    private void setRight(HuffmanNode<T> right) {
        this.right = right;
    }

    public void setNode(HuffmanNode<T> node) {
        if (this.getLeft() == null) {
            this.left = node;
        } else if (this.getRight() == null) {
            this.right = node;
        } else {
            throw new RuntimeException("something went wrong while writing node");
        }
        if (this.getLeft() != null && this.getRight() != null) {
            if (getMaxDepth(this.getLeft()) > getMinDepth(this.getRight())) {
                final HuffmanNode<T> rightLeft = this.getRight().getLeft();
                this.getRight().setLeft(this.getLeft().getRight());
                this.getLeft().setRight(rightLeft);
            }
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
