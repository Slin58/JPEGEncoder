import Bitstream.Bitstream;
import Bitstream.Node;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");
        System.out.println(image1);
        image1.changeResolution(4, 0, 0, List.of(2, 3));
        System.out.println(image1);

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
    }
}
