package huffman;

import java.util.Objects;

public class HuffmanNode<T> {
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

    public static <T> int getMinDepth(HuffmanNode<T> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 0;
        if (node.getLeft() == null) return getMinDepth(node.getLeft());
        if (node.getRight() == null) return getMinDepth(node.getRight());
        return Math.min(getMinDepth(node.getLeft()), getMinDepth(node.getRight())) + 1;
    }

    public static <T> int getMaxDepth(HuffmanNode<T> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 0;
        if (node.getLeft() == null) return getMaxDepth(node.getLeft());
        if (node.getRight() == null) return getMaxDepth(node.getRight());
        return Math.max(getMaxDepth(node.getLeft()), getMaxDepth(node.getRight())) + 1;
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
        if (this.getLeft() != null && this.getRight() != null) {
            if (getMinDepth(this.getRight()) == 1 && getMaxDepth(this.getLeft()) > 1) {
                HuffmanNode<T> tmp = this.getLeft();
                this.setLeft(this.getRight());
                this.setRight(tmp);
            }
            if (getMaxDepth(this.getLeft()) > getMaxDepth(this.getRight())) {
                final HuffmanNode<T> tmp = this.getLeft();
                this.setLeft(this.getRight());
                this.setRight(tmp);
            }

            if (getMaxDepth(this.getLeft()) > getMinDepth(this.getRight())) {
                final HuffmanNode<T> rightLeft = this.getRight().getLeft();
                this.getRight().setLeft(this.getLeft().getRight());
                this.getLeft().setRight(rightLeft);
            }
        }
    }

    @Override
    public String toString() {
        if (this.value == null) {
            return "+, Weight:" + this.weight;
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
