package segments;

import bitstream.BitStream;
import huffman.HuffmanTree;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SegmentTest {

    static void addMultiple(List<Character> valueList, char c, int amount) {
        for (int i = 0; i < amount; i++) {
            valueList.add(c);
        }
    }

    //needs to be verified with JPEGSnoop
    @Test
    public void notAnActualyTestButWhateverTestSegments() {
        List<Character> valueList = new ArrayList<>();

        addMultiple(valueList, 'A', 7);
        addMultiple(valueList, 'B', 2);
        addMultiple(valueList, 'C', 3);
        addMultiple(valueList, 'D', 5);
        addMultiple(valueList, 'E', 17);
        addMultiple(valueList, 'F', 2);
        addMultiple(valueList, 'G', 2);
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
        huffmanTree.createLookUpTable();
        System.out.println(huffmanTree);


        List<int[][]> quantizationTableList = new ArrayList<>();

        int[][] luminanceQuantizationTable =
                {{16, 11, 10, 16, 24, 40, 51, 61}, {12, 12, 14, 19, 26, 58, 60, 55}, {14, 13, 16, 24, 40, 57, 69, 56},
                 {14, 17, 22, 29, 51, 87, 80, 62}, {18, 22, 37, 56, 68, 109, 103, 77},
                 {24, 35, 55, 64, 81, 104, 113, 92}, {49, 64, 78, 87, 103, 121, 120, 101},
                 {72, 92, 95, 98, 112, 100, 103, 99}};
        int[][] chrominanceQuantizationTable =
                {{17, 18, 24, 47, 99, 99, 99, 99}, {18, 21, 26, 66, 99, 99, 99, 99}, {24, 26, 56, 99, 99, 99, 99, 99},
                 {47, 66, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99},
                 {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99}};
        int[][] exerciseQuantizationTable =
                {{50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50},
                 {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50},
                 {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50}};

        quantizationTableList.add(luminanceQuantizationTable);
        quantizationTableList.add(chrominanceQuantizationTable);
        quantizationTableList.add(exerciseQuantizationTable);

        BitStream bitStream = new BitStream();
        bitStream.writeHexString("ffd8");

        SOF0Segment.Component[] components =
                {new SOF0Segment.Component(1, "22", 1), new SOF0Segment.Component(2, "11", 2),
                 new SOF0Segment.Component(3, "11", 3)};
        new APP0JFIFSegment(bitStream).writeSegmentToBitStream(1, 1);
        new DQTSegment(bitStream, quantizationTableList.get(0)).writeSegmentToBitStream(0);
        new DQTSegment(bitStream, quantizationTableList.get(1)).writeSegmentToBitStream(1);
        new SOF0Segment(bitStream, 300, 168, components).writeSegmentToBitStream();
        new DHTSegment<>(bitStream, huffmanTree).writeSegmentToBitStream(1, false);

        bitStream.fillByteWithOnes();

        bitStream.writeHexString("ffd9");
        bitStream.writeBitStreamToFile();

        Assert.assertTrue(true);

    }
}
