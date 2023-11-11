package huffman;

import bitstream.BitStream;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

public class HuffmanTreeTest {

    HuffmanTree<Character> resultTree;
    HuffmanTree<Character> resultTreeWithLookupTable;

    @BeforeMethod
    void setupResultTree() {
        HuffmanNode<Character> aNode = new HuffmanNode<>('A');
        HuffmanNode<Character> bNode = new HuffmanNode<>('B');
        HuffmanNode<Character> cNode = new HuffmanNode<>('C');
        HuffmanNode<Character> dNode = new HuffmanNode<>('D');
        HuffmanNode<Character> eNode = new HuffmanNode<>('E');
        HuffmanNode<Character> fNode = new HuffmanNode<>('F');
        HuffmanNode<Character> rootNode = new HuffmanNode<>();
        HuffmanNode<Character> leftNode = new HuffmanNode<>();
        HuffmanNode<Character> leftRightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightNode = new HuffmanNode<>();
        leftNode.setNode(eNode);
        leftNode.setNode(leftRightNode);
        leftRightNode.setNode(aNode);
        leftRightNode.setNode(bNode);
        rightNode.setNode(fNode);
        rightNode.setNode(rightRightNode);
        rightRightNode.setNode(cNode);
        rightRightNode.setNode(dNode);
        rootNode.setNode(leftNode);
        rootNode.setNode(rightNode);
        this.resultTree = new HuffmanTree<>(rootNode);
        this.resultTreeWithLookupTable = new HuffmanTree<>(rootNode);
    }

    @BeforeMethod(dependsOnMethods = "setupResultTree")
    void setupLookupTable() {
        this.resultTreeWithLookupTable.lookUpTable = new HashMap<>();
        this.resultTreeWithLookupTable.lookUpTable.put('A', new HuffmanLookUpRow<>('A', 2, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('B', new HuffmanLookUpRow<>('B', 3, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('C', new HuffmanLookUpRow<>('C', 6, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('D', new HuffmanLookUpRow<>('D', 7, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('E', new HuffmanLookUpRow<>('E', 0, 2));
        this.resultTreeWithLookupTable.lookUpTable.put('F', new HuffmanLookUpRow<>('F', 2, 2));
    }

    @Test
    void testHuffmanTreeBuilding() {
        List<Character> valueList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            valueList.add('A');
            valueList.add('B');
        }
        valueList.add('B');
        for (int i = 0; i < 6; i++) {
            valueList.add('C');
            valueList.add('D');
            valueList.add('E');
        }
        valueList.add('D');
        valueList.add('E');
        valueList.add('E');
        for (int i = 0; i < 9; i++) {
            valueList.add('F');
        }

        HuffmanTree<Character> huffmanTree = new HuffmanTree.Builder<Character>().add(valueList).build();
        Assert.assertEquals(resultTree, huffmanTree);
        System.out.println(huffmanTree);
    }

    @Test
    void testGenerateEncodingList() {
        this.resultTree.createLookUpTable();
        Assert.assertEquals(resultTreeWithLookupTable, resultTree);
    }

    @Test
    void testEncoding() {
        BitStream bitStream = new BitStream();
        String values = "AAAFDEBE";
        Character[] array = values.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        resultTreeWithLookupTable.encode(Arrays.asList(array), bitStream);

        byte[] result = HexFormat.of().parseHex("495C60");//0100 1001 0101 1100 0110 0000
        System.out.println(Arrays.toString(bitStream.getBytes()));
        Assert.assertEquals(bitStream.getBytes(), Arrays.copyOf(result, 256));
    }
}