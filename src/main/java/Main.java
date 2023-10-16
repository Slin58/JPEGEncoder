import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[][] a = {{3, 1, 2}, {4}};
        List<List<Integer>> inputData = new ArrayList<>();
        for (int[] row : a) {
            List<Integer> rowList = new ArrayList<>();
            for (int value : row) {
                rowList.add(value);
            }
            inputData.add(rowList);
        }

        Image i = new Image(2, 3, inputData, null, null);

        System.out.println(i.getData1(0, 20));


        Image i2 = Utils.readImageFromPPM("test2.ppm");
        i2 = Utils.rgbToYCbCr(i2);

        String outputPath = "YCbCrImage.ppm";

        // Write the image data to the PPM file
        Utils.writePPMFile(outputPath, i2);
    }
}
