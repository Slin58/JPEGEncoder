package segments;

public class DQTSegment {
    public static double[][] quantization(double[][] original, double[][] quantizationTable) {

        double[][] result = new double[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                result[i][j] = Math.round(original[i][j] / quantizationTable[i][j]);
            }
        }

        return result;
    }


}
