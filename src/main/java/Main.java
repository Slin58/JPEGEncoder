import bitstream.BitStream;
import encoding.ImageEncoding;
import image.JPEGEncoderImage;
import segments.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

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


        quantizationTableList.add(luminanceQuantizationTable);
        quantizationTableList.add(chrominanceQuantizationTable);

        JPEGEncoderImage image = Utils.readImageFromPPM("ppm\\redImage.ppm");
        Utils.rgbToYCbCr(image);
        image.changeResolution(4, 2, 0, List.of(2, 3));

        BitStream bitStream = new BitStream();

        ImageEncoding imageEncoding = new ImageEncoding();
        imageEncoding.encodeImage(image);

        new SOISegment(bitStream).writeSegmentToBitStream();
        new APP0JFIFSegment(bitStream).writeSegmentToBitStream();
        new DQTSegment(bitStream, quantizationTableList).writeSegmentToBitStream();
        SOF0Segment.Component[] components =
                {new SOF0Segment.Component(1, "22", 1), new SOF0Segment.Component(2, "11", 2),
                 new SOF0Segment.Component(3, "11", 3)};
        new SOF0Segment(bitStream, 300, 168, components).writeSegmentToBitStream();
        new DHTSegment<>(bitStream, imageEncoding.getDcYHuffmantree()).writeSegmentToBitStream(1, false);
        new DHTSegment<>(bitStream, imageEncoding.getAcYHuffmantree()).writeSegmentToBitStream(0, true);
        new DHTSegment<>(bitStream, imageEncoding.getDcCbCrHuffmantree()).writeSegmentToBitStream(3, false);
        new DHTSegment<>(bitStream, imageEncoding.getAcCbCrHuffmantree()).writeSegmentToBitStream(2, true);

        new SOSSegment(bitStream, imageEncoding).writeSegmentToBitStream(image);


        bitStream.fillByteWithZeroes();

        new EOISegment(bitStream).writeSegmentToBitStream();
        bitStream.writeBitStreamToFile();

    }
}