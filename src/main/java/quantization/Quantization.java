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
}
