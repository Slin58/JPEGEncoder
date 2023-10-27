public class Main {
    public static void main(String[] args) {
        /*
        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");
        System.out.println(image1);
        image1.changeResolution(4, 0, 0, List.of(2, 3));
        System.out.println(image1);
         */
        long startTime = System.currentTimeMillis(); // Get the current time in milliseconds

        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");

        long endTime = System.currentTimeMillis(); // Get the time after the function call
        long elapsedTime = endTime - startTime; // Calculate the elapsed time

        System.out.println("Function call took " + elapsedTime + " milliseconds.");

        /*Image image1 = Utils.readImageFromPPM("ppm\\test2.ppm");
        System.out.println(image1.data1[0][0]);
        System.out.println(image1.data2[0][0]);
        System.out.println(image1.data3[0][0]);
        Utils.writePPMFile("ppm\\test3.ppm", image1);
         */

        //image1.changeResolution(5, 2, 2, Arrays.asList(1,2,3));
        //System.out.println(Arrays.toString(image1.data1[0]));
        //System.out.println(Arrays.toString(image1.data1[1]));

    }
}

        /*
        Node <String> test = new Node<String>();

        test.addToTree("0101", "abc");
        test.addToTree("1111", "abcd");

        System.out.println(test.left.right.left.right.t);

        Node <String> root = new Node<String>();

        Bitstream.addObjectWithIdentifier("0000", "a", root);
        Bitstream.addObjectWithIdentifier("0001", "b", root);
        Bitstream.addObjectWithIdentifier("0010", "c", root);
        Bitstream.addObjectWithIdentifier("0011", "d", root);
        Bitstream.addObjectWithIdentifier("0100", "e", root);
        Bitstream.addObjectWithIdentifier("0110", "f", root);
        Bitstream.addObjectWithIdentifier("0111", "g", root);

        System.out.println(Bitstream.getDataOfBitstream("001100000001", root));
         */