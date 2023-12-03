package dctImplementation;

public class DCT1 implements DCT {

    private static double c(double number) {
        if (number == 0) {
            return 1.0 / Math.sqrt(2);
        }
        return 1.0;
    }

    @Override
    public double[][] twoDDCT(double[][] original) {
        double[][] result = new double[original.length][original[0].length];
        for (int i = 0; i <= result.length - 1; i++) {
            for (int j = 0; j <= result[i].length - 1; j++) {

                double sum = 0;
                for (int x = 0; x <= original.length - 1; x++) {
                    for (int y = 0; y <= original[x].length - 1; y++) {
                        sum += original[x][y] * Math.cos(((2 * x + 1) * i * Math.PI) / (2 * original.length)) *
                               Math.cos(((2 * y + 1) * j * Math.PI) / (2 * original[x].length));
                    }
                }
                result[i][j] = 2.0 / original.length * c(i) * c(j) * sum;
                //todo Rundungsfehler
            }
        }
        return result;
    }

    @Override
    public double[][] inverseTwoDDCT(double[][] dct) {
        double[][] result = new double[dct.length][dct[0].length];
        for (int x = 0; x <= result.length - 1; x++) {
            for (int y = 0; y <= result[x].length - 1; y++) {

                for (int i = 0; i <= dct.length - 1; i++) {
                    for (int j = 0; j <= dct[i].length - 1; j++) {
                        result[x][y] += (2.0 / dct.length) * c(i) * c(j) * dct[i][j] *
                                        Math.cos(((2 * x + 1) * i * Math.PI) / (2 * dct.length)) *
                                        Math.cos(((2 * y + 1) * j * Math.PI) / (2.0 * dct.length));
                    }
                }

            }
        }
        return result;  //todo Rundungsfehler
    }

}
