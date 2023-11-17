package huffman;

import java.util.Objects;

public class HuffmanLookUpRow<T> implements Comparable<HuffmanLookUpRow<T>> {
    private T value;
    private int path;
    private int bitSize;

    public HuffmanLookUpRow(T value, int path, int bitSize) {
        this.value = value;
        this.path = path;
        this.bitSize = bitSize;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public int getBitSize() {
        return bitSize;
    }

    public void setBitSize(int bitSize) {
        this.bitSize = bitSize;
    }

    @Override
    public int compareTo(HuffmanLookUpRow<T> o) {
        return this.getPath() > o.getPath() ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HuffmanLookUpRow<?> that)) return false;
        return getPath() == that.getPath() && getBitSize() == that.getBitSize() &&
               Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getPath(), getBitSize());
    }
}
