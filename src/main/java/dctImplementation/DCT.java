package dctImplementation;

public interface DCT {

    /**
     * @param original a 8x8 matrix
     * @return a 8x8 matrix of a DCT
     */
    double[][] twoDDCT(double[][] original);

    double[][] inverseTwoDDCT(double[][] dct);
}
