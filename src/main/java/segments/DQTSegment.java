package segments;

import bitstream.BitStream;
import utils.Utils;

public class DQTSegment {

    BitStream bitStream;
    int[][] quantizationTable;

    public DQTSegment(BitStream bitStream, int[][] quantizationTable) {
        this.bitStream = bitStream;
        this.quantizationTable = quantizationTable;
    }

    public void writeSegmentToBitStream(int number) {
        this.bitStream.writeHexString("ffdb");              //marker

        this.bitStream.setInt(64 + 1 + 2, 16);      //segment length;

        //        int j = 0;
        //        int k = 0;
        //        int dirJ = 0;
        //        int dirK = 1;

        this.bitStream.setInt(0, 4); // Genauigkeit/Precision der QT: 0 = 8 bit, sonst 16 bit
        this.bitStream.setInt(number, 4); // Nummer der QT 0-3

        //            while (true) {
        //
        //                this.bitStream.setInt(quantizationTable[j][k], 8);
        //                System.out.println(quantizationTable[j][k]);
        //                if (j >= 7 && k >= 7) {
        //                    j = 0;
        //                    k = 0;
        //                    dirJ = 0;
        //                    dirK = 1;
        //                    break;
        //                }
        //
        //                if (j == 0 && k % 2 == 0 && k != 7) {
        //                    k += 1;
        //                    dirJ = 1;
        //                    dirK = -1;
        //                } else if (k == 0 && j % 2 == 1 && j != 7) {
        //                    j += 1;
        //                    dirJ = -1;
        //                    dirK = 1;
        //                } else if (j == 7 && k % 2 == 0) {
        //                    k += 1;
        //                    dirJ = -1;
        //                    dirK = 1;
        //                } else if (k == 7 && j % 2 == 1) {
        //                    j += 1;
        //                    dirJ = 1;
        //                    dirK = -1;
        //                } else {
        //                    j += dirJ;
        //                    k += dirK;
        //                }
        //            }

        //alternativ fuer zigzag:
        //            List<Integer> result = new ArrayList<>();
        //            int rows =
        //                    quantizationTable.length;    //will not work if array is larger than 64, even if
        //                    the rest is empty
        //            int cols = quantizationTable[0].length;
        //            boolean goingUp = true;
        //
        //            for (int sum = 0; sum < rows + cols - 1; sum++) {
        //                if (goingUp) {
        //                    for (int row = Math.min(sum, rows - 1); row >= 0 && sum - row < cols; row--) {
        //                        result.add(quantizationTable[row][sum - row]);
        //                    }
        //                } else {
        //                    for (int col = Math.min(sum, cols - 1); col >= 0 && sum - col < rows; col--) {
        //                        result.add(quantizationTable[sum - col][col]);
        //                    }
        //                }
        //                goingUp = !goingUp;
        //            }
        for (int num : Utils.getValuesInZigzag(this.quantizationTable)) {
            this.bitStream.setInt(num, 8);
        }
    }
}
