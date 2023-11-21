package huffman;

import bitstream.BitStream;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

public class HuffmanTreeTest {

    HuffmanTree<Character> resultTree;
    HuffmanTree<Character> resultTreeWithLookupTable;

    HuffmanTree<Character> lenghtLimitedTree;

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
        HuffmanNode<Character> leftRight = new HuffmanNode<>();
        leftNode.setLeft(eNode);
        leftNode.setRight(leftRight);
        leftRight.setLeft(fNode);
        leftRight.setRight(cNode);
        rightNode.setLeft(rightLeftNode);
        rightNode.setRight(rightRightNode);
        rightRightNode.setLeft(dNode);
        rightLeftNode.setRight(aNode);
        rightLeftNode.setLeft(bNode);
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);
        this.resultTree = new HuffmanTree<>(rootNode);
        this.resultTreeWithLookupTable = new HuffmanTree<>(rootNode);
    }

    @BeforeMethod
    void setupLengthLimitedTree() {
        HuffmanNode<Character> aNode = new HuffmanNode<>('A', 4);
        HuffmanNode<Character> bNode = new HuffmanNode<>('B', 5);
        HuffmanNode<Character> cNode = new HuffmanNode<>('C', 6);
        HuffmanNode<Character> dNode = new HuffmanNode<>('D', 7);
        HuffmanNode<Character> eNode = new HuffmanNode<>('E', 8);
        HuffmanNode<Character> fNode = new HuffmanNode<>('F', 9);
        HuffmanNode<Character> gNode = new HuffmanNode<>('G', 10);
        HuffmanNode<Character> hNode = new HuffmanNode<>('H', 11);
        HuffmanNode<Character> iNode = new HuffmanNode<>('I', 12);

        HuffmanNode<Character> rootNode = new HuffmanNode<>();
        HuffmanNode<Character> leftNode = new HuffmanNode<>();
        HuffmanNode<Character> rightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightLeftNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightLeftLeftNode = new HuffmanNode<>();
        HuffmanNode<Character> rightLeftRightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightRightNode = new HuffmanNode<>();
        HuffmanNode<Character> rightRightLeftNode = new HuffmanNode<>();


        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        leftNode.setLeft(fNode);
        leftNode.setRight(gNode);

        rightNode.setLeft(rightLeftNode);
        rightNode.setRight(rightRightNode);

        rightLeftNode.setRight(rightLeftRightNode);
        rightLeftNode.setLeft(rightLeftLeftNode);

        rightRightNode.setLeft(rightRightLeftNode);
        rightRightNode.setRight(rightRightRightNode);

        rightLeftLeftNode.setLeft(aNode);
        rightLeftLeftNode.setRight(bNode);

        rightLeftRightNode.setLeft(hNode);
        rightLeftRightNode.setRight(iNode);

        rightRightLeftNode.setLeft(dNode);
        rightRightLeftNode.setRight(cNode);

        rightRightRightNode.setLeft(eNode);

        this.lenghtLimitedTree = new HuffmanTree<>(rootNode);

    }

    @BeforeMethod(dependsOnMethods = "setupResultTree")
    void setupLookupTable() {
        this.resultTreeWithLookupTable.lookUpTable = new HashMap<>();
        this.resultTreeWithLookupTable.lookUpTable.put('A', new HuffmanLookUpRow<>('A', 5, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('B', new HuffmanLookUpRow<>('B', 4, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('C', new HuffmanLookUpRow<>('C', 3, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('D', new HuffmanLookUpRow<>('D', 6, 3));
        this.resultTreeWithLookupTable.lookUpTable.put('E', new HuffmanLookUpRow<>('E', 0, 2));
        this.resultTreeWithLookupTable.lookUpTable.put('F', new HuffmanLookUpRow<>('F', 2, 3));
    }

    void addMultiple(List<Character> valueList, char c, int amount) {
        for (int i = 0; i < amount; i++) {
            valueList.add(c);
        }
    }

    @Test
    void testHuffmanTreeBuilding() {
        List<Character> valueList = new ArrayList<>();

        addMultiple(valueList, 'A', 4);
        addMultiple(valueList, 'B', 5);
        addMultiple(valueList, 'C', 6);
        addMultiple(valueList, 'D', 7);
        addMultiple(valueList, 'E', 8);
        addMultiple(valueList, 'F', 9);

        HuffmanTree<Character> huffmanTree = new HuffmanTree.Builder<Character>().add(valueList).build();
        System.out.println(huffmanTree);
        System.out.println(this.resultTree);
        Assert.assertEquals(this.resultTree, huffmanTree);
    }

    @Test
    void testHuffmanTreeBuildingBig() {
        List<Character> valueList = new ArrayList<>();

        addMultiple(valueList, 'A', 7);
        addMultiple(valueList, 'B', 2);
        addMultiple(valueList, 'C', 3);
        addMultiple(valueList, 'D', 5);
        addMultiple(valueList, 'E', 17);
        addMultiple(valueList, 'F', 2);
        addMultiple(valueList, 'H', 3);
        addMultiple(valueList, 'I', 5);
        addMultiple(valueList, 'J', 8);
        addMultiple(valueList, 'K', 1);
        addMultiple(valueList, 'L', 1);
        addMultiple(valueList, 'M', 3);
        addMultiple(valueList, 'N', 10);
        addMultiple(valueList, 'O', 3);
        addMultiple(valueList, 'P', 1);
        addMultiple(valueList, 'Q', 1);
        addMultiple(valueList, 'R', 7);
        addMultiple(valueList, 'S', 7);
        addMultiple(valueList, 'T', 6);
        addMultiple(valueList, 'U', 4);
        addMultiple(valueList, 'V', 1);
        addMultiple(valueList, 'W', 2);
        addMultiple(valueList, 'X', 1);
        addMultiple(valueList, 'Y', 1);
        addMultiple(valueList, 'Z', 1);

        HuffmanTree<Character> huffmanTree = new HuffmanTree.Builder<Character>().setLimit(5).add(valueList).build();
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
        String values = "AAAFDEBEF"; //1011 0110 1010 1100 0100 0001 0
        Character[] array = values.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        resultTreeWithLookupTable.encode(Arrays.asList(array), bitStream);

        byte[] result = HexFormat.of().parseHex("B6AC4100"); //1011 0110 1010 1100 0100 0001 0000 0000
        System.out.println(Arrays.toString(bitStream.getBytes()));
        Assert.assertEquals(bitStream.getBytes(), Arrays.copyOf(result, 256));
    }

    @Test
    void testBRCI() {
        List<Character> valueList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            valueList.add('A');
            valueList.add('B');
        }
        valueList.add('B');
        for (int i = 0; i < 8; i++) {
            valueList.add('C');
            valueList.add('D');
            valueList.add('E');
        }
        valueList.add('D');
        valueList.add('E');
        valueList.add('E');
        for (int i = 0; i < 16; i++) {
            valueList.add('F');
        }
        for (int i = 0; i < 32; i++) {
            valueList.add('G');
        }
        valueList.add('H');
        valueList.add('I');

        HuffmanTree<Character> huffmanTree = new HuffmanTree.Builder<Character>().setLimit(4).add(valueList).build();
        System.out.println(huffmanTree);
        System.out.println(this.lenghtLimitedTree);
        Assert.assertEquals(this.lenghtLimitedTree, huffmanTree);
    }
}