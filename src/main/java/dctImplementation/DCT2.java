package dctImplementation;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class DCT2 implements DCT {

    private static double c0(double k) {
        if (k == 0) {
            return 1.0 / Math.sqrt(2);
        }
        return 1.0;
    }

    @Override
    public double[][] twoDDCT(double[][] original) {
        SimpleMatrix aMatrix = new SimpleMatrix(buildAMatrix(original.length));
        SimpleMatrix originalMatrix = new SimpleMatrix(original);

        return Arrays.stream(aMatrix.mult(originalMatrix.mult(aMatrix.transpose())).toArray2())
                .map(row -> Arrays.stream(row).map(Math::round).toArray())
                .toArray(double[][]::new);    //todo: rundungsfehler oder rechenfehler?

    }

    private double[][] buildAMatrix(int originalLength) {
        double[][] aMatrix = new double[originalLength][originalLength];

        for (int k = 0; k < originalLength; k++) {
            for (int n = 0; n < originalLength; n++) {
                aMatrix[k][n] = c0(k) * Math.sqrt(2.0 / originalLength) *
                                Math.cos((2.0 * n + 1.0) * ((k * Math.PI) / (2.0 * originalLength)));
            }
        }
        return aMatrix;
    }

}
