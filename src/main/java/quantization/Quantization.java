package quantization;

public class Quantization {

    public static int[][] luminanceQuantizationTable =
            {{3, 2, 2, 3, 4, 6, 8, 10}, {2, 2, 2, 3, 4, 9, 10, 9}, {2, 2, 3, 4, 6, 9, 11, 9},
             {2, 3, 4, 5, 8, 14, 13, 10}, {3, 4, 6, 9, 11, 17, 16, 12}, {4, 6, 9, 10, 13, 17, 18, 15},
             {8, 10, 12, 14, 16, 19, 19, 16}, {12, 15, 15, 16, 18, 16, 16, 16}};


    public static int[][] chrominanceQuantizationTable =
            {{3, 3, 4, 8, 16, 16, 16, 16}, {3, 3, 4, 11, 16, 16, 16, 16}, {4, 4, 9, 16, 16, 16, 16, 16},
             {8, 11, 16, 16, 16, 16, 16, 16}, {16, 16, 16, 16, 16, 16, 16, 16}, {16, 16, 16, 16, 16, 16, 16, 16},
             {16, 16, 16, 16, 16, 16, 16, 16}, {16, 16, 16, 16, 16, 16, 16, 16}};

    public static double[][] quantization(double[][] original, int[][] quantizationTable) {
        // todo be careful if input is normalized to 0-1 everything will be rounded to zero

        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                original[i][j] = Math.round(original[i][j] / quantizationTable[i][j]);
            }
        }
        return original;
    }

    public static double[][] luminanceQuantistation(double[][] original) {
        return quantization(original, luminanceQuantizationTable);
    }

    public static double[][] chrominanceQuantistation(double[][] original) {
        return quantization(original, chrominanceQuantizationTable);
    }
}
