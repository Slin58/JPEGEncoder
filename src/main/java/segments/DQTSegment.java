package segments;

import bitstream.BitStream;

import java.util.List;

public class DQTSegment {

    BitStream bitStream;
    List<int[][]> quantizationTableList;

    public DQTSegment(BitStream bitStream, List<int[][]> quantizationTableList) {
        this.bitStream = bitStream;
        this.quantizationTableList = quantizationTableList;
    }

    public void writeSegmentToBitStream() {
        this.bitStream.writeHexString("ffdb");                                      //marker
        this.bitStream.setInt(quantizationTableList.size() * 65 + 2, 16);     //segment length

        if (quantizationTableList.size() > 4) {
            throw new RuntimeException("Amount of Quantizationtables shouldn't be greater than four");
        }

        int number = 0;
        int j = 0;
        int k = 0;
        int dirJ = 0;
        int dirK = 1;
        for (int[][] quantizationTable : quantizationTableList) {

            this.bitStream.setInt(0, 4); // Genauigkeit/Precision der QT: 0 = 8 bit, sonst 16 bit
            this.bitStream.setInt(number, 4); // Nummer der QT 0-3
            number++;

            while (true) {

                this.bitStream.setInt(quantizationTable[j][k], 8);

                if (j >= 7 && k >= 7) {
                    break;
                }

                if (j == 0 && k % 2 == 0 && k != 7) {
                    k += 1;
                    dirJ = 1;
                    dirK = -1;
                } else if (k == 0 && j % 2 == 1 && j != 7) {
                    j += 1;
                    dirJ = -1;
                    dirK = 1;
                } else if (j == 7 && k % 2 == 0) {
                    k += 1;
                    dirJ = -1;
                    dirK = 1;
                } else if (k == 7 && j % 2 == 1) {
                    j += 1;
                    dirJ = 1;
                    dirK = -1;
                } else {
                    j += dirJ;
                    k += dirK;
                }
            }
        }
    }
}
