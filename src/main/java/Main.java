import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Image image = Utils.readImageFromPPM("ppm\\test.ppm");
        Image iycbcr = Utils.rgbToYCbCr(image);
        iycbcr.changeResolution(4, 1, 0, Arrays.asList(1,2,3));
        System.out.println(iycbcr.data1);
        System.out.println(iycbcr.data2);
        System.out.println(iycbcr.data3);



        /*
        Image i2 = Utils.readImageFromPPM("ppm\\test2.ppm");
        i2 = Utils.rgbToYCbCr(i2);
        i2.changeResolution(5, 3, 2);

        String outputPath = "ppm\\YCbCrImage.ppm";

        // Write the image data to the PPM file
        Utils.writePPMFile(outputPath, i2);

         */
    }
}
