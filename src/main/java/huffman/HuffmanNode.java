package huffman;

import java.io.Serializable;
import java.util.Objects;

public class HuffmanNode<T extends Serializable> {
    private HuffmanNode<T> left;
    private HuffmanNode<T> right;

    private HuffmanNode<T> parent;

    private T value;

    private long weight = 0;

    public HuffmanNode(T value, long weight) {
        this.value = value;
        this.weight = weight;
    }

    public HuffmanNode() {

    }

    public static <T extends Serializable> int getMinDepth(HuffmanNode<T> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 0;
        if (node.getLeft() == null) return getMinDepth(node.getLeft());
        if (node.getRight() == null) return getMinDepth(node.getRight());
        return Math.min(getMinDepth(node.getLeft()), getMinDepth(node.getRight())) + 1;
    }

    public static <T extends Serializable> int getMaxDepth(HuffmanNode<T> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 0;
        if (node.getLeft() == null) return getMaxDepth(node.getLeft());
        if (node.getRight() == null) return getMaxDepth(node.getRight());
        return Math.max(getMaxDepth(node.getLeft()), getMaxDepth(node.getRight())) + 1;
    }

    private static <T extends Serializable> void swap(HuffmanNode<T> tHuffmanNode) {
        if (tHuffmanNode.getLeft() != null && tHuffmanNode.getRight() != null) {
            if (getMinDepth(tHuffmanNode.getRight()) == 1 && getMaxDepth(tHuffmanNode.getLeft()) > 1) {
                HuffmanNode<T> tmp = tHuffmanNode.getLeft();
                tHuffmanNode.setLeft(tHuffmanNode.getRight());
                tHuffmanNode.setRight(tmp);
            }
            if (getMaxDepth(tHuffmanNode.getLeft()) > getMaxDepth(tHuffmanNode.getRight())) {
                final HuffmanNode<T> tmp = tHuffmanNode.getLeft();
                tHuffmanNode.setLeft(tHuffmanNode.getRight());
                tHuffmanNode.setRight(tmp);
            }
            if (getMaxDepth(tHuffmanNode.getLeft()) > getMinDepth(tHuffmanNode.getRight())) {
                final HuffmanNode<T> rightLeft = tHuffmanNode.getRight().getLeft();
                tHuffmanNode.getRight().setLeft(tHuffmanNode.getLeft().getRight());
                tHuffmanNode.getLeft().setRight(rightLeft);
            }

            if (getMaxDepth(tHuffmanNode.getLeft().getRight()) > getMinDepth(tHuffmanNode.getRight().getLeft())) {
                final HuffmanNode<T> rightLeft = tHuffmanNode.getRight().getLeft().getLeft();
                tHuffmanNode.getRight().getLeft().setLeft(tHuffmanNode.getLeft().getRight().getRight());
                tHuffmanNode.getLeft().getRight().setRight(rightLeft);
            }

        }
        if (tHuffmanNode.getRight() != null) swap(tHuffmanNode.getRight());
        if (tHuffmanNode.getLeft() != null) swap(tHuffmanNode.getLeft());
    }

    public HuffmanNode<T> getParent() {
        return parent;
    }

    public void setParent(HuffmanNode<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public HuffmanNode<T> getLeft() {
        return left;
    }

    void setLeft(HuffmanNode<T> left) {
        if (left != null) {
            left.setParent(this);
        }
        this.left = left;
    }

    public HuffmanNode<T> getRight() {
        return right;
    }

    void setRight(HuffmanNode<T> right) {
        if (right != null) {
            right.setParent(this);
        }
        this.right = right;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void addWeight(long weight) {
        this.weight += weight;
    }

    public void setNode(HuffmanNode<T> node) {
        if (this.getRight() == null) {
            this.right = node;
        } else if (this.getLeft() == null) {
            this.left = node;
        } else {
            throw new RuntimeException("something went wrong while writing node");
        }
        node.setParent(this);
        addWeight(node.getWeight());
        swap(this);
        swap(this);
    }

    @Override
    public String toString() {
        if (this.value == null) {
            return " Weight:" + this.weight;
        }
        return this.value + ", Weight:" + this.weight;
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
