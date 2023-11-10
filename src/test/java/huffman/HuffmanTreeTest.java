package huffman;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class HuffmanTreeTest {
    @Test
    void testHuffmanTreeBuilding(){
        List<Character> valueList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            valueList.add('A');
            valueList.add('B');
        }
        for (int i = 0; i < 6; i++) {
            valueList.add('C');
            valueList.add('D');
            valueList.add('E');
        }
        valueList.add('E');
        for (int i = 0; i < 9; i++) {
            valueList.add('F');
        }

        HuffmanTree<Character> huffmanTree = new HuffmanTree.Builder<Character>().add(valueList).build();
        System.out.println(huffmanTree);
    }
}