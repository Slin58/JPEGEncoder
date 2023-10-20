package Bitstream;

public class Bitstream {

    public static void addObjectWithIdentifier(String bitfolge, String object, Node<String> root) {
        Node <String> test = root;
        char [] bitfolgeChar = bitfolge.toCharArray();

        for (char c : bitfolgeChar) {
            test = test.getNode(c, true);
        }
        test.t = object;
    }

    public static String getBitstreamOfData (String bitstrom, Node <String> root) {
        //todo

        return "";
    }

    public static String getDataOfBitstream (String bitstrom, Node <String> root) {
        char [] bitstromChar = bitstrom.toCharArray();
        Node <String> test2 = root;
        String result = "";
        for(int i = 0; i <= bitstromChar.length - 1 ; i++) {
            test2 = test2.getNode(bitstromChar[i], false);
            if (i % 4 == 3) {
                result = result + test2.t;
                test2 = root;
            }
        }
        return result;
    }



    public static void storeBitstreamInFile (String filename, String bitstream) {
        //todo storeBitstream after a defined amount of bits: depending on how efficient it is to store in relation to hold a lot of data
    }
}
