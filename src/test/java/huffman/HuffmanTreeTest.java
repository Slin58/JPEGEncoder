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
        HuffmanNode<Character> aNode = new HuffmanNode<>('A', 4);
        HuffmanNode<Character> bNode = new HuffmanNode<>('B', 5);
        HuffmanNode<Character> cNode = new HuffmanNode<>('C', 6);
        HuffmanNode<Character> dNode = new HuffmanNode<>('D', 7);
        HuffmanNode<Character> eNode = new HuffmanNode<>('E', 8);
        HuffmanNode<Character> fNode = new HuffmanNode<>('F', 9);
        HuffmanNode<Character> rootNode = new HuffmanNode<>();
        HuffmanNode<Character> leftNode = new HuffmanNode<>();
        HuffmanNode<Character> rightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightLeftNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightRightNode = new HuffmanNode<>();
        leftNode.setLeft(fNode);
        leftNode.setRight(eNode);
        rightNode.setLeft(rightLeftNode);
        rightNode.setRight(rightRightNode);
        rightRightNode.setRight(rightRightRightNode);
        rightRightRightNode.setLeft(aNode);
        rightRightNode.setLeft(bNode);
        rightLeftNode.setRight(cNode);
        rightLeftNode.setLeft(dNode);
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);
        this.resultTree = new HuffmanTree<>(rootNode);
        this.resultTreeWithLookupTable = new HuffmanTree<>(rootNode);
    }

    @BeforeMethod(dependsOnMethods = "setupResultTree")
    void setupLookupTable() {
        this.resultTreeWithLookupTable.lookUpTable = new HashMap<>();
        this.resultTreeWithLookupTable.lookUpTable.put('A', new HuffmanLookUpRow<>('A', 14, 4));
        this.resultTreeWithLookupTable.lookUpTable.put('B', new HuffmanLookUpRow<>('B', 6, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('C', new HuffmanLookUpRow<>('C', 5, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('D', new HuffmanLookUpRow<>('D', 4, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('E', new HuffmanLookUpRow<>('E', 1, 2));
        this.resultTreeWithLookupTable.lookUpTable.put('F', new HuffmanLookUpRow<>('F', 0, 2));
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
        System.out.println(huffmanTree);
        System.out.println(this.resultTree);
        Assert.assertEquals(this.resultTree, huffmanTree);
    }

    @Test
    void testGenerateEncodingList() {
        this.resultTree.createLookUpTable();
        Assert.assertEquals(resultTreeWithLookupTable, resultTree);
    }

    @Test
    void testEncoding() {
        BitStream bitStream = new BitStream();
        String values = "AAAFDEBEF"; //1110 1110 1110 0010 0011 1001 00
        Character[] array = values.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        resultTreeWithLookupTable.encode(Arrays.asList(array), bitStream);

        byte[] result = HexFormat.of().parseHex("EEE23900"); //1110 1110 1110 0010 0011 1001 0000 0000
        System.out.println(Arrays.toString(bitStream.getBytes()));
        Assert.assertEquals(bitStream.getBytes(), Arrays.copyOf(result, 256));
    }
}