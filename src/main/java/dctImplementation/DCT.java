package dctImplementation;


import image.JPEGEncoderImage;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

public abstract class DCT {

    private final static ThreadPoolExecutor EXECUTOR_SERVICE =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static int[][] toInt(double[][] array) {
        int[][] result = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[i][j] = (int) Math.round(array[i][j] * 10000) / 10000;
            }
        }
        return result;
    }

    public static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {for (int j = 0; j < m[0].length; j++) {temp[j][i] = m[i][j];}}
        return temp;
    }

    public void calculateDateOnArrays(double[][] data, Function<double[][], double[][]> method) {
        for (int i = 0; i < data.length; i += 8) {
            for (int j = 0; j < data[i].length; j += 8) {
                final int i1 = i;
                final int j1 = j;
                EXECUTOR_SERVICE.execute(
                        () -> saveNewValues(i1, j1, data, calculateTwoDDctFromDataArray(data, i1, j1, method)));
            }
        }
    }

    private double[][] calculateTwoDDctFromDataArray(double[][] data, int i1, int j1,
                                                     Function<double[][], double[][]> method) {
        return method.apply(new double[][]{
                {data[i1][j1], data[i1][j1 + 1], data[i1][j1 + 2], data[i1][j1 + 3], data[i1][j1 + 4], data[i1][j1 + 5],
                 data[i1][j1 + 6], data[i1][j1 + 7]},
                {data[i1 + 1][j1], data[i1 + 1][j1 + 1], data[i1 + 1][j1 + 2], data[i1 + 1][j1 + 3],
                 data[i1 + 1][j1 + 4], data[i1 + 1][j1 + 5], data[i1 + 1][j1 + 6], data[i1 + 1][j1 + 7]},
                {data[i1 + 2][j1], data[i1 + 2][j1 + 1], data[i1 + 2][j1 + 2], data[i1 + 2][j1 + 3],
                 data[i1 + 2][j1 + 4], data[i1 + 2][j1 + 5], data[i1 + 2][j1 + 6], data[i1 + 2][j1 + 7]},
                {data[i1 + 3][j1], data[i1 + 3][j1 + 1], data[i1 + 3][j1 + 2], data[i1 + 3][j1 + 3],
                 data[i1 + 3][j1 + 4], data[i1 + 3][j1 + 5], data[i1 + 3][j1 + 6], data[i1 + 3][j1 + 7]},
                {data[i1 + 4][j1], data[i1 + 4][j1 + 1], data[i1 + 4][j1 + 2], data[i1 + 4][j1 + 3],
                 data[i1 + 4][j1 + 4], data[i1 + 4][j1 + 5], data[i1 + 4][j1 + 6], data[i1 + 4][j1 + 7]},
                {data[i1 + 5][j1], data[i1 + 5][j1 + 1], data[i1 + 5][j1 + 2], data[i1 + 5][j1 + 3],
                 data[i1 + 5][j1 + 4], data[i1 + 5][j1 + 5], data[i1 + 5][j1 + 6], data[i1 + 5][j1 + 7]},
                {data[i1 + 6][j1], data[i1 + 6][j1 + 1], data[i1 + 6][j1 + 2], data[i1 + 6][j1 + 3],
                 data[i1 + 6][j1 + 4], data[i1 + 6][j1 + 5], data[i1 + 6][j1 + 6], data[i1 + 6][j1 + 7]},
                {data[i1 + 7][j1], data[i1 + 7][j1 + 1], data[i1 + 7][j1 + 2], data[i1 + 7][j1 + 3],
                 data[i1 + 7][j1 + 4], data[i1 + 7][j1 + 5], data[i1 + 7][j1 + 6], data[i1 + 7][j1 + 7]}});
    }

    private void saveNewValues(int x, int y, double[][] data, double[][] newData) {
        for (int i = 0; i < 8; i++) {
            System.arraycopy(newData[i], 0, data[x + i], y, 8);
        }
    }

    /**
     * @param original a 8x8 matrix
     * @return a 8x8 matrix of a DCT
     */
    abstract public double[][] twoDDCT(double[][] original);

    abstract public double[][] inverseTwoDDCT(double[][] dct);

    public void calculatePictureDataWithDCT(JPEGEncoderImage data) {
        calculateDateOnArrays(data.getData1(), this::twoDDCT);
        calculateDateOnArrays(data.getData2(), this::twoDDCT);
        calculateDateOnArrays(data.getData3(), this::twoDDCT);
    }

    // Used In the performance test
    public void calculateOnePictureDataWithDCT(JPEGEncoderImage data) {
        calculateDateOnArrays(data.getData1(), this::twoDDCT);
        while (EXECUTOR_SERVICE.getActiveCount() != 0) {
            try {
                Thread.sleep(0, 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
