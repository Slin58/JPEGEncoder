import bitstream.BitStream;
import huffman.HuffmanTree;
import segments.APP0JFIFSegment;
import segments.DHTSegment;
import segments.SOF0Segment;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*
        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");
        System.out.println(image1);
        image1.changeResolution(4, 0, 0, List.of(2, 3));
        System.out.println(image1);
         */
       /* long startTime = System.currentTimeMillis(); // Get the current time in milliseconds

        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");

        long endTime = System.currentTimeMillis(); // Get the time after the function call
        long elapsedTime = endTime - startTime; // Calculate the elapsed time

        System.out.println("Function call took " + elapsedTime + " milliseconds.");
        */

        /*Image image1 = Utils.readImageFromPPM("ppm\\test2.ppm");
        System.out.println(image1.data1[0][0]);
        System.out.println(image1.data2[0][0]);
        System.out.println(image1.data3[0][0]);
        Utils.writePPMFile("ppm\\test3.ppm", image1);
         */

        //image1.changeResolution(5, 2, 2, Arrays.asList(1,2,3));
        //System.out.println(Arrays.toString(image1.data1[0]));
        //System.out.println(Arrays.toString(image1.data1[1]));

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
        BitStream bitStream = new BitStream();
        bitStream.writeHexString("ffd8");

        SOF0Segment.Component[] components =
                {new SOF0Segment.Component(1, "22", 1), new SOF0Segment.Component(2, "11", 2),
                 new SOF0Segment.Component(3, "11", 3)};
        new APP0JFIFSegment(bitStream).writeSegmentToBitStream();
        new SOF0Segment(bitStream, 300, 168, components).writeSegmentToBitStream();
        new DHTSegment<>(bitStream, huffmanTree).writeSegmentToBitStream(1, false);
        bitStream.writeHexString("ffd9");
        bitStream.writeBitStreamToFile();
        //System.out.println(bitStream.getHexString());


        //BitStream bitStream = new BitStream();
        //byte[] values = HexFormat.of().parseHex("ffff"); //5bab71d5800ae6
        //System.out.println(Arrays.toString(values));
        //bitStream.setBytes(values);
        // int result = bitStream.getBits(11);
        // System.out.println(result);

    }

    static void addMultiple(List<Character> valueList, char c, int amount) {
        for (int i = 0; i < amount; i++) {
            valueList.add(c);
        }
    }
}