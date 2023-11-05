import bitstream.BitStream;
import segments.JFIFSegment;
import segments.SOF0Segment;

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


        BitStream bitStream = new BitStream();


        SOF0Segment.Component[] components = {new SOF0Segment.Component(1, "22", 1), new SOF0Segment.Component(2, "11", 2), new SOF0Segment.Component(3, "11", 3)};
        new JFIFSegment(bitStream).writeSegmentToBitStream();
        new SOF0Segment(bitStream, 300, 168, components).writeSegmentToBitStream();
        bitStream.writeBitStreamToFile();




    }

}