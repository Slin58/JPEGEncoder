package huffman;

import bitstream.BitStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HuffmanTree<T> {

    public HuffmanNode<T> root;
    public Map<T, HuffmanLookUpRow<T>> lookUpTable = new HashMap<>();

    public HuffmanTree(HuffmanNode<T> root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return treeToString(root, 0, true);
    }

    private String treeToString(HuffmanNode<T> node, int depth, boolean right) {
        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        if (right)
            sb.append(getIndentation(depth)).append(node).append(" right").append("\n");
        else
            sb.append(getIndentation(depth)).append(node).append(" left").append("\n");

        sb.append(treeToString(node.getLeft(), depth + 1, false));
        sb.append(treeToString(node.getRight(), depth + 1, true));

        return sb.toString();
    }

    public void createLookUpTable(HuffmanNode<T> node) {
        traverseTree(node, 0, 0);
    }

    private void traverseTree(HuffmanNode<T> node, int path, int counter) {
        if (node.getLeft() == null && node.getRight() == null) {
            //System.out.println(node.getValue() + ", path: " + path + ", counter: " + counter);
            //lookUpTable.put(node.getValue(), path + "," + counter); //class mit path, counter
            lookUpTable.put(node.getValue(), new HuffmanLookUpRow<>(node.getValue(), path, counter));
        }
        else {
            counter++;
            traverseTree(node.getLeft(), (path<<1) | 0, counter);
            traverseTree(node.getRight(), (path<<1) | 1, counter);
        }
    }

    public void encode(List<T> input, BitStream bitStream) {
        for (T i : input) {
            HuffmanLookUpRow<T> huffmanLookUpRow = lookUpTable.get(i);
            bitStream.setInt(huffmanLookUpRow.getPath(), huffmanLookUpRow.getCounter());
        }
    }


    private String getIndentation(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("--"); // Two spaces for each level of depth
        }
        return sb.toString();
    }

    public static class Builder<T> {

        private final Map<HuffmanNode<T>, Double> probabilities = new HashMap<>();

        public HuffmanTree<T> build() {
            System.out.println(this.probabilities.entrySet().stream().map(huffmanNodeDoubleEntry -> huffmanNodeDoubleEntry.getKey().toString() + ":" + huffmanNodeDoubleEntry.getValue()).collect(Collectors.joining("\n")));
            while (this.probabilities.size() > 1) {
                HuffmanNode<T> currentNode = new HuffmanNode<>();
                Double currentProbability = 0d;
                for (int i = 0; i < 2; i++) {
                    // find min probability
                    Map.Entry<HuffmanNode<T>, Double> lowest = this.probabilities.entrySet()
                            .stream().min(Map.Entry.comparingByValue()).orElseThrow(() -> new RuntimeException("No Value Found"));
                    this.probabilities.remove(lowest.getKey());
                    currentNode.setNode(lowest.getKey());
                    currentProbability += lowest.getValue();
                }
                this.probabilities.put(currentNode, currentProbability);
            }
            return new HuffmanTree<T>(this.probabilities.keySet().iterator().next());
        }

        public Builder<T> add(Collection<T> values) {
            Map<T, Long> collect = values.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
            double size = values.size();
            collect.forEach((t, count) -> this.probabilities.put(new HuffmanNode<>(t), count / size));
            return this;
        }
    }
}
