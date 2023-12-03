package dctImplementation;

public class DCT1 implements DCT {


    public static double c(double number) {
        if (number == 0) {
            return 1.0 / Math.sqrt(2);
        }
        return 1.0;
    }

    @Override
    public double[][] twoDDCT(double[][] original) {
        double[][] result = new double[original.length][original[0].length];  //todo initialize
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
                //Math.round(2.0 / original.length * c(i) * c(j) * sum) + Change to int isn't correct
            }
        }
        return result;
    }

}
