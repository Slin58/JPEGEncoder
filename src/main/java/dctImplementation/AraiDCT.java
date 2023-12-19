package dctImplementation;

public class AraiDCT extends DCT {

    private static final double s0 = calcS(0);
    private static final double s1 = calcS(1);
    private static final double s2 = calcS(2);
    private static final double s3 = calcS(3);
    private static final double s4 = calcS(4);
    private static final double s5 = calcS(5);
    private static final double s6 = calcS(6);
    private static final double s7 = calcS(7);
    private static final double a1 = calcC(4);
    private static final double a2 = calcC(2) - calcC(6);
    private static final double a3 = a1;
    private static final double a4 = calcC(6) + calcC(2);
    private static final double a5 = calcC(6);

    private static double calcC(double k) {
        return Math.cos(k * Math.PI / 16);
    }

    private static double calcS(double k) {
        if (k == 0) {
            return 1 / (2f * Math.sqrt(2));
        }
        return 1 / (4 * calcC(k));
    }

    @Override
    public double[][] twoDDCT(double[][] original) {
        for (int i = 0; i < original.length; i++) {
            oneDDCT(original[i]);
        }
        double[][] transposedResult = transposeMatrix(original);
        for (int i = 0; i < transposedResult.length; i++) {
            oneDDCT(transposedResult[i]);
        }
        return transposeMatrix(transposedResult);
    }

    @Override
    public double[][] inverseTwoDDCT(double[][] dct) {
        //todo
        return new double[0][];
    }

    /**
     * @param original has to be lenth 8
     * @return a dct of the original
     */
    public void oneDDCT(double[] original) {
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
        original[0] = s0 * x0_3;
        original[4] = s4 * x1_3;
        original[2] = s2 * x2_5;
        original[6] = s6 * x3_5;
        original[5] = s5 * x4_6;
        original[1] = s1 * x5_6;
        original[7] = s7 * x6_6;
        original[3] = s3 * x7_6;
    }

}
