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
        HuffmanNode<Character> rightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightLeftNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightNode = new HuffmanNode<>();
        leftNode.setLeft(eNode);
        leftNode.setRight(fNode);
        rightNode.setLeft(rightLeftNode);
        rightNode.setRight(rightRightNode);
        rightRightNode.setRight(dNode);
        rightRightNode.setLeft(cNode);
        rightLeftNode.setRight(bNode);
        rightLeftNode.setLeft(aNode);
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);
        this.resultTree = new HuffmanTree<>(rootNode);
        this.resultTreeWithLookupTable = new HuffmanTree<>(rootNode);
    }

    @BeforeMethod(dependsOnMethods = "setupResultTree")
    void setupLookupTable() {
        this.resultTreeWithLookupTable.lookUpTable = new HashMap<>();
        this.resultTreeWithLookupTable.lookUpTable.put('A', new HuffmanLookUpRow<>('A', 4, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('B', new HuffmanLookUpRow<>('B', 5, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('C', new HuffmanLookUpRow<>('C', 6, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('D', new HuffmanLookUpRow<>('D', 7, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('E', new HuffmanLookUpRow<>('E', 0, 2));
        this.resultTreeWithLookupTable.lookUpTable.put('F', new HuffmanLookUpRow<>('F', 1, 2));
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
        Assert.assertEquals(this.resultTree, huffmanTree);
        System.out.println(huffmanTree);
        System.out.println(this.resultTree);
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

        byte[] result = HexFormat.of().parseHex("923CA0");//1001 0010 0011 1100 1010 0000
        System.out.println(Arrays.toString(bitStream.getBytes()));
        Assert.assertEquals(bitStream.getBytes(), Arrays.copyOf(result, 256));
    }
}