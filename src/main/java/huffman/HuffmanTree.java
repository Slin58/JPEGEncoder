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
            bitStream.setInt(huffmanLookUpRow.getPath(), huffmanLookUpRow.getBitSize());
        }
    }

    public HuffmanNode<T> getRoot() {
        return root;
    }

    public Map<T, HuffmanLookUpRow<T>> getLookUpTable() {
        return lookUpTable;
    }

    @Override
    public String toString() {
        String[] result = getNodeAsString(root, new String[]{"", ""}, "");
        return result[1];
    }

    private String[] getNodeAsString(HuffmanNode<T> node, String[] result, String depth) {
        if (node.getLeft() != null || node.getRight() != null) {
            if (node.getLeft() != null) {
                result[0] += "\n" + depth + "left";
                result = getNodeAsString(node.getLeft(), result, depth + "-");
            }
            if (node.getRight() != null) {
                result[0] += "\n" + depth + "right";
                result = getNodeAsString(node.getRight(), result, depth + "-");
            }

        } else {
            if (node.getValue() != null) {
                result[1] += result[0] + ": " + node;
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

        private final Map<HuffmanNode<T>, Long> probabilities = new HashMap<>();
        private int limit = 16;

        public HuffmanTree<T> build() {
            if (this.probabilities.size() > Math.pow(2, this.limit)) {
                throw new RuntimeException("There ar to many values for a depth of " + this.limit);
            }
            while (this.probabilities.size() > 1) {
                HuffmanNode<T> currentNode = new HuffmanNode<>();
                Long currentProbability = 0L;
                for (int i = 0; i < 2; i++) {
                    // find min probability
                    Map.Entry<HuffmanNode<T>, Long> lowest =
                            this.probabilities.entrySet().stream().min(Map.Entry.comparingByValue())
                                    .orElseThrow(() -> new RuntimeException("No Value Found"));
                    this.probabilities.remove(lowest.getKey());
                    currentNode.setNode(lowest.getKey());
                    currentProbability += lowest.getValue();
                }
                this.probabilities.put(currentNode, currentProbability);
            }
            HuffmanNode<T> root = this.probabilities.keySet().iterator().next();

            root = BRCI(root);

            checkOnes(root);

            return new HuffmanTree<>(root);
        }

        private void checkOnes(HuffmanNode<T> root) {
            HuffmanNode<T> node = root;
            while (node.getRight() != null) {
                node = node.getRight();
            }
            if (node.getParent() != null) {
                HuffmanNode<T> parent = node.getParent();
                while (true) {
                    if (HuffmanNode.getMinDepth(parent.getLeft()) < HuffmanNode.getMinDepth(parent.getRight())) {
                        while (true) {
                            if (parent.getValue() != null) {
                                node.getParent().setRight(null);
                                HuffmanNode<T> newParent = parent.getParent();
                                HuffmanNode<T> tmp = new HuffmanNode<>();
                                tmp.setLeft(parent);
                                tmp.setRight(node);
                                if (newParent.getLeft().equals(parent)) {
                                    newParent.setLeft(tmp);
                                } else {
                                    newParent.setRight(tmp);
                                }
                                break;
                            }
                            if (HuffmanNode.getMinDepth(parent.getRight()) >
                                HuffmanNode.getMinDepth(parent.getLeft())) {
                                parent = parent.getLeft();
                            } else {
                                parent = parent.getRight();
                            }
                        }
                        break;
                    }
                    parent = parent.getParent();
                }
            }

        }

        public Builder<T> setLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder<T> add(Collection<T> values) {
            Map<T, Long> collect = values.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
            collect.forEach((t, count) -> this.probabilities.put(new HuffmanNode<>(t, count), count));
            return this;
        }

        private HuffmanNode<T> BRCI(HuffmanNode<T> root) {
            if (HuffmanNode.getMaxDepth(root) > this.limit) {
                HuffmanNode<T> t2root = removeAtDepth(root, this.limit);
                int maxDepth = HuffmanNode.getMaxDepth(t2root);
                int selectedDepth = this.limit - maxDepth - 1;
                HuffmanNode<T> selectedNode = selectNodeAtDepth(root, selectedDepth);
                HuffmanNode<T> yStar = new HuffmanNode<>();
                if (selectedNode.equals(root)) {
                    yStar.setNode(root);
                    yStar.setNode(t2root);
                    return yStar;
                }
                HuffmanNode<T> parent = selectedNode.getParent();
                if (parent.getRight().equals(selectedNode)) {
                    parent.setRight(yStar);
                } else {
                    parent.setLeft(yStar);
                }
                yStar.setNode(t2root);
                yStar.setNode(selectedNode);

            }
            return root;
        }

        private HuffmanNode<T> selectNodeAtDepth(HuffmanNode<T> node, int selectedDepth) {
            if (selectedDepth == 0) {
                return node;
            } else if (node.getRight() != null) return selectNodeAtDepth(node.getRight(), selectedDepth - 1);
            return null;
        }

        private HuffmanNode<T> removeAtDepth(HuffmanNode<T> root, int limit) {
            List<HuffmanNode<T>> nodesTooDeep = new ArrayList<>();
            addTooDeepNodesToList(root, limit, nodesTooDeep);
            return buildBinaryTree(nodesTooDeep);
        }

        private void addTooDeepNodesToList(HuffmanNode<T> node, int limit, List<HuffmanNode<T>> removedNodes) {
            if (node.getRight() != null) {
                addTooDeepNodesToList(node.getRight(), limit - 1, removedNodes);
            }
            if (node.getLeft() != null) {
                addTooDeepNodesToList(node.getLeft(), limit - 1, removedNodes);
            }
            if (node.getLeft() == null && node.getRight() == null && limit <= 0) {

                HuffmanNode<T> parent = node.getParent();
                node.setParent(null);
                if (parent.getRight() != null && parent.getRight().equals(node)) {
                    removedNodes.add(node);
                    parent.setRight(null);
                } else {
                    if (parent.getLeft().getValue() != null) {
                        parent.setValue(parent.getLeft().getValue());
                    }
                    parent.setLeft(null);
                }
            }
        }

        private HuffmanNode<T> buildBinaryTree(List<HuffmanNode<T>> nodes) {
            while (nodes.size() > 1) {
                HuffmanNode<T> newParent = new HuffmanNode<>();
                newParent.setLeft(nodes.get(0));
                nodes.remove(0);
                newParent.setRight(nodes.get(0));
                nodes.remove(0);
                nodes.add(newParent);
            }
            return nodes.get(0);
        }
    }
}
