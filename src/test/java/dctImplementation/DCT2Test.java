package dctImplementation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class DCT2Test {
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

        DCT2 dct2 = new DCT2();
        double[][] checkerboardDCT = dct2.twoDDCT(checkerboard);

        for (double[] x : checkerboardDCT) {
            for (double y : x) {
                System.out.printf(y + ", ");
            }
            System.out.println();
        }

        Assert.assertTrue(Arrays.deepEquals(checkerboardResult, checkerboardDCT));
    }
}

