package dctImplementation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class DCT1Test {
    @Test
    public void testTwoDDCT() {
        double[][] checkerboard = new double[][]{{0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0},
                                                 {0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0},
                                                 {0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0},
                                                 {0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0}};
        double[][] checkerboardResult =
                new double[][]{{1020, 0, 0, 0, 0, 0, 0, 0}, {0, -33, 0, -39, 0, -58, 0, -166}, {0, 0, 0, 0, 0, 0, 0, 0},
                               {0, -39, 0, -46, 0, -69, 0, -196}, {0, 0, 0, 0, 0, 0, 0, 0},
                               {0, -58, 0, -69, 0, -103, 0, -294}, {0, 0, 0, 0, 0, 0, 0, 0},
                               {0, -166, 0, -196, 0, -294, 0, -837}};

        DCT1 dct1 = new DCT1();
        double[][] checkerboardDCT = dct1.twoDDCT(checkerboard);

        for (double[] x : checkerboardDCT) {
            for (double y : x) {
                System.out.printf(y + ", ");
            }
            System.out.println();
        }

        Assert.assertTrue(Arrays.deepEquals(checkerboardResult, checkerboardDCT));
    }

    @Test
    public void testTwoDDCT2() {
        double[][] randomValues = {{6, 18, 4, 200, 123, 65, 11, 241}, {54, 154, 190, 2, 251, 201, 55, 143},
                                   {133, 102, 99, 201, 55, 21, 66, 65}, {77, 1, 211, 87, 95, 103, 66, 137},
                                   {63, 98, 220, 102, 100, 9, 76, 32}, {21, 144, 29, 1, 102, 198, 201, 11},
                                   {87, 21, 85, 230, 241, 21, 54, 94}, {103, 191, 9, 32, 105, 43, 91, 102}};
        double[][] randomValuesResult =
                {{769, -21, -101, -55, 54, -71, -11, 44}, {36, -50, -24, -80, 52, -33, 116, -36},
                 {7, -77, 24, 33, 133, -116, -73, -78}, {-22, -106, -60, 24, -26, -13, -19, -12},
                 {-63, -6, 59, -103, 42, 12, 37, -8}, {-25, -56, -60, -67, 191, 116, 22, -55},
                 {-79, -11, 103, 50, -58, 62, -102, -123}, {-29, 114, -50, -111, 144, 96, -64, -24}};

        DCT1 dct1 = new DCT1();
        double[][] checkerboardDCT = dct1.twoDDCT(randomValues);

        for (double[] x : checkerboardDCT) {
            for (double y : x) {
                System.out.printf(y + ", ");
            }
            System.out.println();
        }

        Assert.assertTrue(Arrays.deepEquals(randomValuesResult, checkerboardDCT));
    }

    @Test
    public void testInverseTwoDDCT() {
        double[][] checkerboard = new double[][]{{0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0},
                                                 {0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0},
                                                 {0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0},
                                                 {0, 255, 0, 255, 0, 255, 0, 255}, {255, 0, 255, 0, 255, 0, 255, 0}};
        double[][] checkerboardResult =
                new double[][]{{1020, 0, 0, 0, 0, 0, 0, 0}, {0, -33, 0, -39, 0, -58, 0, -166}, {0, 0, 0, 0, 0, 0, 0, 0},
                               {0, -39, 0, -46, 0, -69, 0, -196}, {0, 0, 0, 0, 0, 0, 0, 0},
                               {0, -58, 0, -69, 0, -103, 0, -294}, {0, 0, 0, 0, 0, 0, 0, 0},
                               {0, -166, 0, -196, 0, -294, 0, -837}};

        DCT1 dct1 = new DCT1();
        double[][] checkerboardDCT = dct1.inverseTwoDDCT(checkerboardResult);

        for (double[] x : checkerboardDCT) {
            for (double y : x) {
                System.out.printf(y + ", ");
            }
            System.out.println();
        }

        Assert.assertTrue(Arrays.deepEquals(checkerboard, checkerboardDCT));
    }

    @Test
    public void testInverseTwoDDCT2() {
        double[][] randomValues = {{6, 18, 4, 200, 123, 65, 11, 241}, {54, 154, 190, 2, 251, 201, 55, 143},
                                   {133, 102, 99, 201, 55, 21, 66, 65}, {77, 1, 211, 87, 95, 103, 66, 137},
                                   {63, 98, 220, 102, 100, 9, 76, 32}, {21, 144, 29, 1, 102, 198, 201, 11},
                                   {87, 21, 85, 230, 241, 21, 54, 94}, {103, 191, 9, 32, 105, 43, 91, 102}};
        double[][] randomValuesResult =
                {{769, -21, -101, -55, 54, -71, -11, 44}, {36, -50, -24, -80, 52, -33, 116, -36},
                 {7, -77, 24, 33, 133, -116, -73, -78}, {-22, -106, -60, 24, -26, -13, -19, -12},
                 {-63, -6, 59, -103, 42, 12, 37, -8}, {-25, -56, -60, -67, 191, 116, 22, -55},
                 {-79, -11, 103, 50, -58, 62, -102, -123}, {-29, 114, -50, -111, 144, 96, -64, -24}};

        DCT1 dct1 = new DCT1();
        double[][] checkerboardDCT = dct1.inverseTwoDDCT(randomValuesResult);

        for (double[] x : checkerboardDCT) {
            for (double y : x) {
                System.out.printf(y + ", ");
            }
            System.out.println();
        }

        Assert.assertTrue(Arrays.deepEquals(randomValues, checkerboardDCT));
    }
}
