package huffman;

public class HuffmanLookUpRow<T> {
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
}
