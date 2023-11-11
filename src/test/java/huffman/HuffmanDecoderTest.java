package huffman;

import bitstream.BitStream;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HuffmanDecoderTest {

    HuffmanTree<Character> huffmanTree;
    BitStream bitStream;
    String values =
            "BAFBEAEAADDBBBBBBEACDCFBBBCCFEFCADEBABFCDBAAFEAEDFBEDAADABEFCEEEECDFDBCCAEBDFABDAFFEECBBAEDACCCBFDACFDAFBAECCADEFAADBBCDCCFBFEDAF";
    List<Character> valueList;

    @BeforeTest
    void initiateTest() {
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
        this.huffmanTree = new HuffmanTree.Builder<Character>().add(valueList).build();
        this.huffmanTree.createLookUpTable();
        this.bitStream = new BitStream();
        this.valueList = Arrays.asList(values.chars().mapToObj(c -> (char) c).toArray(Character[]::new));
        this.huffmanTree.encode(this.valueList, this.bitStream);
    }

    @Test
    void testDecode() {
        HuffmanDecoder<Character> characterHuffmanDecoder =
                new HuffmanDecoder<>(this.huffmanTree.getLookUpTable(), this.bitStream);
        List<Character> characters = characterHuffmanDecoder.decodeAll();
        System.out.println(this.valueList);
        System.out.println(characters);
        Assert.assertEquals(characters, this.valueList);
    }

}