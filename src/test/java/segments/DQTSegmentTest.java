package segments;

import org.testng.Assert;
import org.testng.annotations.Test;


public class DQTSegmentTest {

    double[][] exerciseQuantizationTable =
            {{50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50},
             {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50},
             {50, 50, 50, 50, 50, 50, 50, 50}, {50, 50, 50, 50, 50, 50, 50, 50}};

    double[][] luminanceQuantizationTable =
            {{16, 11, 10, 16, 24, 40, 51, 61}, {12, 12, 14, 19, 26, 58, 60, 55}, {14, 13, 16, 24, 40, 57, 69, 56},
             {14, 17, 22, 29, 51, 87, 80, 62}, {18, 22, 37, 56, 68, 109, 103, 77}, {24, 35, 55, 64, 81, 104, 113, 92},
             {49, 64, 78, 87, 103, 121, 120, 101}, {72, 92, 95, 98, 112, 100, 103, 99}};
    double[][] chrominanceQuantizationTable =
            {{17.0, 18.0, 24.0, 47.0, 99.0, 99.0, 99.0, 99.0}, {18.0, 21.0, 26.0, 66.0, 99.0, 99.0, 99.0, 99.0},
             {24.0, 26.0, 56.0, 99.0, 99.0, 99.0, 99.0, 99.0}, {47.0, 66.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0},
             {99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0}, {99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0},
             {99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0}, {99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0, 99.0}};

    private static boolean deepEqual(double[][] value1, double[][] value2) {
        boolean everything_equal = true;
        for (int i = 0; i < value1.length; i++) {
            for (int j = 0; j < value1[i].length; j++) {
                if (value1[i][j] != value2[i][j]) {
                    System.out.println(
                            "error at: i:" + i + " j:" + j + " expected: " + value1[i][j] + " gotten: " + value2[i][j]);
                    everything_equal = false;
                }
            }
        }
        return everything_equal;
    }


    @Test
    void quantizationTest() {
        double[][] input = {{581, -144, 56, 17, 15, -7, 25, -9}, {-242, 133, -48, 42, -2, -7, 13, -4},
                            {108, -18, -40, 71, -33, 12, 6, -10}, {-56, -93, 48, 19, -8, 7, 6, -2},
                            {-17, 9, 7, -23, -3, -10, 5, 3}, {4, 9, -4, -5, 2, 2, -7, 3}, {-9, 7, 8, -6, 5, 12, 2, -5},
                            {-9, -4, -2, -3, 6, 1, -1, -1}};
        double[][] result = DQTSegment.quantization(input, exerciseQuantizationTable);
        double[][] expected = {{12.0, -3.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0}, {-5.0, 3.0, -1.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                               {2.0, 0.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0}, {-1.0, -2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                               {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                               {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};

        Assert.assertTrue(deepEqual(result, expected));
    }

    @Test
    void quantizationLuminanceTest() {
        double[][] input = {{581, -144, 56, 17, 15, -7, 25, -9}, {-242, 133, -48, 42, -2, -7, 13, -4},
                            {108, -18, -40, 71, -33, 12, 6, -10}, {-56, -93, 48, 19, -8, 7, 6, -2},
                            {-17, 9, 7, -23, -3, -10, 5, 3}, {4, 9, -4, -5, 2, 2, -7, 3}, {-9, 7, 8, -6, 5, 12, 2, -5},
                            {-9, -4, -2, -3, 6, 1, -1, -1}};
        double[][] result = DQTSegment.quantization(input, luminanceQuantizationTable);
        double[][] expected =
                {{36.0, -13.0, 6.0, 1.0, 1.0, 0.0, 0.0, 0.0}, {-20.0, 11.0, -3.0, 2.0, 0.0, 0.0, 0.0, 0.0},
                 {8.0, -1.0, -2.0, 3.0, -1.0, 0.0, 0.0, 0.0}, {-4.0, -5.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                 {-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                 {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};

        Assert.assertTrue(deepEqual(result, expected));
    }

    @Test
    void quantizationChrominanceTest() {
        double[][] input = {{581, -144, 56, 17, 15, -7, 25, -9}, {-242, 133, -48, 42, -2, -7, 13, -4},
                            {108, -18, -40, 71, -33, 12, 6, -10}, {-56, -93, 48, 19, -8, 7, 6, -2},
                            {-17, 9, 7, -23, -3, -10, 5, 3}, {4, 9, -4, -5, 2, 2, -7, 3}, {-9, 7, 8, -6, 5, 12, 2, -5},
                            {-9, -4, -2, -3, 6, 1, -1, -1}};
        double[][] result = DQTSegment.quantization(input, chrominanceQuantizationTable);
        double[][] expected = {{34.0, -8.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {-13.0, 6.0, -2.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                               {5.0, -1.0, -1.0, 1.0, 0.0, 0.0, 0.0, 0.0}, {-1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                               {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                               {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};

        Assert.assertTrue(deepEqual(result, expected));
    }

}
