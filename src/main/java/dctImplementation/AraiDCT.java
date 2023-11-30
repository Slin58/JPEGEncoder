package dctImplementation;

import org.ejml.simple.SimpleMatrix;

public class AraiDCT implements DCT {

    private static double s0 = calcS(0);
    private static double s1 = calcS(1);
    private static double s2 = calcS(2);
    private static double s3 = calcS(3);
    private static double s4 = calcS(4);
    private static double s5 = calcS(5);
    private static double s6 = calcS(6);
    private static double s7 = calcS(7);
    private static double a1 = calcC(4);
    private static double a2 = calcC(2) - calcC(6);
    private static double a3 = a1;
    private static double a4 = calcC(6) + calcC(2);
    private static double a5 = calcC(6);

    private static double calcC(double k) {
        return Math.cos(k * Math.PI / 16);
    }

    private static double calcS(double k) {
        if (k == 0) {
            return 1 / (2f * Math.sqrt(2));
        }
        return 1 / (4 * calcC(k));
    }

    public static int[][] toInt(double[][] array) {
        int[][] result = new int[8][8];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[i][j] = (int) Math.round(array[i][j] * 10000) / 10000;
            }
        }
        return result;
    }

    @Override
    public double[][] twoDDCT(double[][] original) {
        for (int i = 0; i < original.length; i++) {
            original[i] = oneDDCT(original[i]);
        }
        double[][] transposedResult = new SimpleMatrix(original).transpose().toArray2();
        for (int i = 0; i < transposedResult.length; i++) {
            transposedResult[i] = oneDDCT(transposedResult[i]);
        }
        return new SimpleMatrix(transposedResult).transpose().toArray2();
    }

    /**
     * @param original has to be lenth 8
     * @return a dct of the original
     */
    public double[] oneDDCT(double[] original) {
        if (original.length != 8) {
            throw new RuntimeException("1-D DCT only works with 8 values when using Arai");
        }
        double x0 = original[0];
        double x1 = original[1];
        double x2 = original[2];
        double x3 = original[3];
        double x4 = original[4];
        double x5 = original[5];
        double x6 = original[6];
        double x7 = original[7];

        // First Step
        double x0_1 = x0 + x7;
        double x1_1 = x1 + x6;
        double x2_1 = x2 + x5;
        double x3_1 = x3 + x4;
        double x4_1 = x3 - x4;
        double x5_1 = x2 - x5;
        double x6_1 = x1 - x6;
        double x7_1 = x0 - x7;

        // Second Step
        double x0_2 = x0_1 + x3_1;
        double x1_2 = x1_1 + x2_1;
        double x2_2 = x1_1 - x2_1;
        double x3_2 = x0_1 - x3_1;
        double x4_2 = -x4_1 - x5_1;
        double x5_2 = x5_1 + x6_1;
        double x6_2 = x6_1 + x7_1;

        // Third Step
        double x0_3 = x0_2 + x1_2;
        double x1_3 = x0_2 - x1_2;
        double x2_3 = x2_2 + x3_2;

        // Fourth Step
        double a5_4 = a5 * (x4_2 + x6_2);
        double x2_4 = a1 * x2_3;
        double x4_4 = -(a2 * x4_2) - a5_4;
        double x5_4 = a3 * x5_2;
        double x6_4 = (a4 * x6_2) - a5_4;

        // Fifth Step
        double x2_5 = x2_4 + x3_2;
        double x3_5 = x3_2 - x2_4;
        double x5_5 = x5_4 + x7_1;
        double x7_5 = x7_1 - x5_4;

        // Sixth Step
        double x4_6 = x4_4 + x7_5;
        double x5_6 = x5_5 + x6_4;
        double x6_6 = x5_5 - x6_4;
        double x7_6 = x7_5 - x4_4;

        // Final Step
        double y0 = s0 * x0_3;
        double y4 = s4 * x1_3;
        double y2 = s2 * x2_5;
        double y6 = s6 * x3_5;
        double y5 = s5 * x4_6;
        double y1 = s1 * x5_6;
        double y7 = s7 * x6_6;
        double y3 = s3 * x7_6;

        return new double[]{y0, y1, y2, y3, y4, y5, y6, y7};
    }

}
