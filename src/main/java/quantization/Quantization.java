package quantization;

public class Quantization {
    public static double[][] quantization(double[][] original, int[][] quantizationTable) {
        // todo be careful if input is normalized to 0-1 everything will be rounded to zero

        double[][] result = new double[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                result[i][j] = Math.round(original[i][j] / quantizationTable[i][j]);
            }
        }

        return result;
    }

    public static double[][] luminanceQuantistation(double[][] original) {

        int[][] luminanceQuantizationTable =
                {{16, 11, 10, 16, 24, 40, 51, 61}, {12, 12, 14, 19, 26, 58, 60, 55}, {14, 13, 16, 24, 40, 57, 69, 56},
                 {14, 17, 22, 29, 51, 87, 80, 62}, {18, 22, 37, 56, 68, 109, 103, 77},
                 {24, 35, 55, 64, 81, 104, 113, 92}, {49, 64, 78, 87, 103, 121, 120, 101},
                 {72, 92, 95, 98, 112, 100, 103, 99}};
        return quantization(original, luminanceQuantizationTable);
    }

    public static double[][] chrominanceQuantistation(double[][] original) {
        int[][] chrominanceQuantizationTable =
                {{17, 18, 24, 47, 99, 99, 99, 99}, {18, 21, 26, 66, 99, 99, 99, 99}, {24, 26, 56, 99, 99, 99, 99, 99},
                 {47, 66, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99},
                 {99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99}};
        return quantization(original, chrominanceQuantizationTable);
    }
}
