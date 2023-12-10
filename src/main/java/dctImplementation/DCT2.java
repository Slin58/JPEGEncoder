package dctImplementation;

import org.ejml.simple.SimpleMatrix;

public class DCT2 extends DCT {

    private static final double sqrt = 1.0 / Math.sqrt(2);

    private static double c0(double number) {
        if (number == 0) {
            return sqrt;
        }
        return 1.0;
    }

    @Override
    public double[][] twoDDCT(double[][] original) {    //todo: buggy
        double[][] aMatrixArray = buildAMatrix(original.length);
        SimpleMatrix aMatrix = new SimpleMatrix(aMatrixArray);
        SimpleMatrix transposedAMatrix = new SimpleMatrix(transposeMatrix(aMatrixArray));
        SimpleMatrix originalMatrix = new SimpleMatrix(original);

        //        return Arrays.stream(aMatrix.mult(originalMatrix.mult(transposedAMatrix)).toArray2())
        //                .map(row -> Arrays.stream(row).map(Math::round).toArray())
        //                .toArray(double[][]::new);    //todo: rundungsfehler oder rechenfehler?
        return aMatrix.mult(originalMatrix.mult(transposedAMatrix)).toArray2();

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

    @Override
    public double[][] inverseTwoDDCT(double[][] dct) {      //todo: buggy
        SimpleMatrix aMatrix = new SimpleMatrix(buildAMatrix(dct.length));
        SimpleMatrix originalMatrix = new SimpleMatrix(dct);
        SimpleMatrix aMatrixInverse = aMatrix.invert();

        //        return Arrays.stream(aMatrixInverse.mult(originalMatrix.mult(aMatrixInverse.transpose())).toArray2())
        //                .map(row -> Arrays.stream(row).map(Math::round).toArray())
        //                .toArray(double[][]::new);    //todo: rundungsfehler oder rechenfehler?
        return aMatrixInverse.mult(originalMatrix.mult(aMatrixInverse.transpose())).toArray2();
    }


}
