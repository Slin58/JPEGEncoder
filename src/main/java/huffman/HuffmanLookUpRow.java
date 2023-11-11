package huffman;

import java.util.Objects;

public class HuffmanLookUpRow<T> implements Comparable<HuffmanLookUpRow<T>> {
    private T value;
    private int path;
    private int counter;

    public HuffmanLookUpRow(T value, int path, int counter) {
        this.value = value;
        this.path = path;
        this.counter = counter;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public int compareTo(HuffmanLookUpRow<T> o) {
        return this.getPath() > o.getPath() ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HuffmanLookUpRow<?> that)) return false;
        return getPath() == that.getPath() && getCounter() == that.getCounter() &&
               Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getPath(), getCounter());
    }
}
