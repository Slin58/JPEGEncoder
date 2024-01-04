import bitstream.BitStream;
import dctImplementation.AraiDCT;
import dctImplementation.DCT1;
import dctImplementation.DCT2;
import huffman.HuffmanTree;
import image.ColorSpace;
import image.JPEGEncoderImage;
import segments.APP0JFIFSegment;
import segments.DHTSegment;
import segments.DQTSegment;
import segments.SOF0Segment;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.readImageFromPPM;
import static utils.Utils.writePPMFile;

public class Main {

    public static void main(String[] args) {

        /*
        image.Image image1 = image.Utils.readImageFromPPM("ppm\\test.ppm");
        System.out.println(image1);
        image1.changeResolution(4, 0, 0, List.of(2, 3));
        System.out.println(image1);
         */
       /* long startTime = System.currentTimeMillis(); // Get the current time in milliseconds

        image.Image image1 = image.Utils.readImageFromPPM("ppm\\test.ppm");

        long endTime = System.currentTimeMillis(); // Get the time after the function call
        long elapsedTime = endTime - startTime; // Calculate the elapsed time

        System.out.println("Function call took " + elapsedTime + " milliseconds.");
        */

        /*image.Image image1 = image.Utils.readImageFromPPM("ppm\\test2.ppm");
        System.out.println(image1.data1[0][0]);
        System.out.println(image1.data2[0][0]);
        System.out.println(image1.data3[0][0]);
        image.Utils.writePPMFile("ppm\\test3.ppm", image1);
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
        new APP0JFIFSegment(bitStream).writeSegmentToBitStream();
        new DQTSegment(bitStream, quantizationTableList).writeSegmentToBitStream();
        new SOF0Segment(bitStream, 300, 168, components).writeSegmentToBitStream();
        new DHTSegment<>(bitStream, huffmanTree).writeSegmentToBitStream(1, false);
        bitStream.writeHexString("ffd9");
        bitStream.writeBitStreamToFile();


        if (true) return;


        //System.out.println(bitStream.getHexString());

        //BitStream bitStream = new BitStream();
        //byte[] values = HexFormat.of().parseHex("ffff"); //5bab71d5800ae6
        //System.out.println(Arrays.toString(values));
        //bitStream.setBytes(values);
        // int result = bitStream.getBits(11);
        // System.out.println(result);


        System.out.println("Processors:" + Runtime.getRuntime().availableProcessors());
        runDCT1test();
        runAraiTest();
        runDCT2test();

        //DCT.close();

        if (true) return;

        JPEGEncoderImage image = readImageFromPPM("ppm\\testDCT.ppm");

        DCT2 dct2 = new DCT2();
        dct2.calculatePictureDataWithDCT(image);

        dct2.calculatePictureDataWithInverseDCT(image);

        //        AraiDCT araiDCT = new AraiDCT();
        //        araiDCT.calculatePictureDataWithDCT(image);
        //
        //        araiDCT.inverseTwoDDCT(image.getData1());
        //        araiDCT.inverseTwoDDCT(image.getData2());
        //        araiDCT.inverseTwoDDCT(image.getData3());


        writePPMFile("ppm\\dctoutput1.jpg", image);
        //writePPMFile("ppm\\writeppmTest.jpg", image);


    }

    static void addMultiple(List<Character> valueList, char c, int amount) {
        for (int i = 0; i < amount; i++) {
            valueList.add(c);
        }
    }

    static void runAraiTest() {
        JPEGEncoderImage jpegEncoderImage =
                new JPEGEncoderImage(3840, 2160, ColorSpace.YCbCr, new double[3840][2160], new double[][]{},
                                     new double[][]{});
        double[][] data1 = jpegEncoderImage.getData1();
        for (int i = 0; i < 3840; i++) {
            for (int j = 0; j < 2160; j++) {
                data1[i][j] = (i + j * 8) % 256;
            }
        }
        List<Long> results = new ArrayList<>();
        AraiDCT araiDCT = new AraiDCT();
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 10000) {
            JPEGEncoderImage currentImage = new JPEGEncoderImage(jpegEncoderImage);
            long t = System.currentTimeMillis();
            araiDCT.calculateOnePictureDataWithDCT(currentImage);
            results.add((System.currentTimeMillis() - t));
            System.gc();
        }
        System.out.println("Time in ms with Arai DCT: ");
        System.out.println("Runs: " + results.size());
        System.out.println("Max: " + results.stream().max(Long::compareTo).get());
        System.out.println("Min: " + results.stream().min(Long::compareTo).get());
        System.out.println("Average: " + results.stream().mapToDouble(value -> value).average().getAsDouble());
        //        System.out.println(results.stream().sorted().map(String::valueOf).collect(Collectors.joining("\n")));
    }

    static void runDCT2test() {
        JPEGEncoderImage jpegEncoderImage =
                new JPEGEncoderImage(3840, 2160, ColorSpace.YCbCr, new double[3840][2160], new double[][]{},
                                     new double[][]{});
        double[][] data1 = jpegEncoderImage.getData1();
        for (int i = 0; i < 3840; i++) {
            for (int j = 0; j < 2160; j++) {
                data1[i][j] = (i + j * 8) % 256;
            }
        }
        List<Long> results = new ArrayList<>();
        DCT2 araiDCT = new DCT2();
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 10000) {
            JPEGEncoderImage currentImage = new JPEGEncoderImage(jpegEncoderImage);
            long t = System.currentTimeMillis();
            araiDCT.calculateOnePictureDataWithDCT(currentImage);
            results.add((System.currentTimeMillis() - t));
            System.gc();
        }
        System.out.println("Time in ms with DCT2: ");
        System.out.println("Runs: " + results.size());
        System.out.println("Max: " + results.stream().max(Long::compareTo).get());
        System.out.println("Min: " + results.stream().min(Long::compareTo).get());
        System.out.println("Average: " + results.stream().mapToDouble(value -> value).average().getAsDouble());
    }

    static void runDCT1test() {
        JPEGEncoderImage jpegEncoderImage =
                new JPEGEncoderImage(3840, 2160, ColorSpace.YCbCr, new double[3840][2160], new double[][]{},
                                     new double[][]{});
        double[][] data1 = jpegEncoderImage.getData1();
        for (int i = 0; i < 3840; i++) {
            for (int j = 0; j < 2160; j++) {
                data1[i][j] = (i + j * 8) % 256;
            }
        }
        List<Long> results = new ArrayList<>();
        DCT1 araiDCT = new DCT1();
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 10000) {
            JPEGEncoderImage currentImage = new JPEGEncoderImage(jpegEncoderImage);
            long t = System.currentTimeMillis();
            araiDCT.calculateOnePictureDataWithDCT(currentImage);
            results.add((System.currentTimeMillis() - t));
            System.gc();
        }
        System.out.println("Time in ms with DCT1: ");
        System.out.println("Runs: " + results.size());
        System.out.println("Max: " + results.stream().max(Long::compareTo).get());
        System.out.println("Min: " + results.stream().min(Long::compareTo).get());
        System.out.println("Avarage: " + results.stream().mapToDouble(value -> value).average().getAsDouble());
    }
}