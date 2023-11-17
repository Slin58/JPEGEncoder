package huffman;

import bitstream.BitStream;

import java.util.*;
import java.util.stream.Collectors;

public class HuffmanTree<T> {

    public HuffmanNode<T> root;
    public Map<T, HuffmanLookUpRow<T>> lookUpTable = new HashMap<>();

    public HuffmanTree(HuffmanNode<T> root) {
        this.root = root;
    }

    public HuffmanNode<T> getRoot() {
        return root;
    }

    public Map<T, HuffmanLookUpRow<T>> getLookUpTable() {
        return lookUpTable;
    }

    public void createLookUpTable() {
        traverseTree(root, 0, 0);
    }

    private void traverseTree(HuffmanNode<T> node, int path, int counter) {
        if (node.getLeft() == null && node.getRight() == null) {
            lookUpTable.put(node.getValue(), new HuffmanLookUpRow<>(node.getValue(), path, counter));
        } else {
            counter++;
            if (node.getLeft() != null) traverseTree(node.getLeft(), (path << 1) | 0, counter);
            if (node.getRight() != null) traverseTree(node.getRight(), (path << 1) | 1, counter);
        }
    }

    public void encode(List<T> input, BitStream bitStream) {
        for (T i : input) {
            HuffmanLookUpRow<T> huffmanLookUpRow = lookUpTable.get(i);
            bitStream.setInt(huffmanLookUpRow.getPath(), huffmanLookUpRow.getCounter());
        }
    }

    @Override
    public String toString() {
        String[] result = getTreeAsString(root, new String[]{"", ""}, "");
        return result[1];
    }

    private String[] getTreeAsString(HuffmanNode<T> node, String[] result, String depth) {
        if (node.getLeft() != null || node.getRight() != null) {
            if (node.getLeft() != null) {
                result[0] += "\n" + depth + "left";
                result = getTreeAsString(node.getLeft(), result, depth + "-");
            }
            if (node.getRight() != null) {
                result[0] += "\n" + depth + "right";
                result = getTreeAsString(node.getRight(), result, depth + "-");
            }

        } else {
            if (node.getValue() != null) {
                result[1] += result[0] + ": " + node.getValue();
            }
            result[0] = "";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HuffmanTree<?> that)) return false;
        return Objects.equals(root, that.root) && Objects.equals(lookUpTable, that.lookUpTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, lookUpTable);
    }

    public static class Builder<T> {

        private final Map<HuffmanNode<T>, Double> probabilities = new HashMap<>();

        public HuffmanTree<T> build() {
            while (this.probabilities.size() > 1) {
                HuffmanNode<T> currentNode = new HuffmanNode<>();
                Double currentProbability = 0d;
                for (int i = 0; i < 2; i++) {
                    // find min probability
                    Map.Entry<HuffmanNode<T>, Double> lowest =
                            this.probabilities.entrySet().stream().min(Map.Entry.comparingByValue())
                                    .orElseThrow(() -> new RuntimeException("No Value Found"));
                    this.probabilities.remove(lowest.getKey());
                    currentNode.setNode(lowest.getKey());
                    currentProbability += lowest.getValue();
                }
                this.probabilities.put(currentNode, currentProbability);
            }
            HuffmanNode<T> root = this.probabilities.keySet().iterator().next();
            HuffmanNode<T> temp = root;
            while (temp.getRight() != null) {
                temp = temp.getRight();
            }
            temp.setLeft(new HuffmanNode<>(temp.getValue()));
            temp.setValue(null);
            return new HuffmanTree<T>(root);
        }

        public Builder<T> add(Collection<T> values) {
            Map<T, Long> collect = values.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
            double size = values.size();
            collect.forEach((t, count) -> this.probabilities.put(new HuffmanNode<>(t), count / size));
            return this;
        }
    }
}
