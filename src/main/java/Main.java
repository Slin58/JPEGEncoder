import bitstream.BitStream;
import encoding.ImageEncoding;
import image.JPEGEncoderImage;
import quantization.Quantization;
import segments.*;
import utils.Utils;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //List<int[][]> quantizationTableList = new ArrayList<>();

        //int[][] luminanceQuantizationTable = Quantization.luminanceQuantizationTable;
        //int[][] chrominanceQuantizationTable = Quantization.chrominanceQuantizationTable;


        //quantizationTableList.add(luminanceQuantizationTable);
        //quantizationTableList.add(chrominanceQuantizationTable);

        JPEGEncoderImage image = Utils.readImageFromPPM("ppm\\SquareImage.ppm");
        //        System.out.println(image);
        Utils.rgbToYCbCr(image);
        image.changeResolution(4, 2, 0, List.of(2, 3));

        BitStream bitStream = new BitStream();

        ImageEncoding imageEncoding = new ImageEncoding();
        imageEncoding.encodeImage(image);

        System.out.println("WriteSOI");
        new SOISegment(bitStream).writeSegmentToBitStream();
        new APP0JFIFSegment(bitStream).writeSegmentToBitStream(image.getWidth(), image.getHeight());

        System.out.println("WriteDQT");
        new DQTSegment(bitStream, Quantization.luminanceQuantizationTable).writeSegmentToBitStream(0);
        new DQTSegment(bitStream, Quantization.chrominanceQuantizationTable).writeSegmentToBitStream(1);
        SOF0Segment.Component[] components =
                {new SOF0Segment.Component(1, "22", 0), new SOF0Segment.Component(2, "11", 1),
                 new SOF0Segment.Component(3, "11", 1)};
        new SOF0Segment(bitStream, image.getOriginalWith(), image.getOriginalHeight(),
                        components).writeSegmentToBitStream();
        System.out.println("WriteDHT");
        new DHTSegment<>(bitStream, imageEncoding.getDcYHuffmantree()).writeSegmentToBitStream(0, false);
        new DHTSegment<>(bitStream, imageEncoding.getAcYHuffmantree()).writeSegmentToBitStream(0, true);
        new DHTSegment<>(bitStream, imageEncoding.getDcCbCrHuffmantree()).writeSegmentToBitStream(1, false);
        new DHTSegment<>(bitStream, imageEncoding.getAcCbCrHuffmantree()).writeSegmentToBitStream(1, true);

        System.out.println("WriteSOS");
        new SOSSegment(bitStream, imageEncoding).writeSegmentToBitStream(image);


        bitStream.fillByteWithOnes();

        new EOISegment(bitStream).writeSegmentToBitStream();

        //        byte[] bytes = bitStream.getBytes();
        //        System.out.println("Anzahl Hex: " + bytes.length);
        //
        //        String ff = "FF";
        //        for (byte b : bytes) {
        //            String hex = String.format("%02X", b);
        //            if (ff.equals(hex)) {
        //                System.out.println();
        //            }
        //            System.out.print(hex);
        //        }

        bitStream.writeBitStreamToFile();

    }
}