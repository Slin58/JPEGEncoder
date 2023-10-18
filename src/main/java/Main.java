import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Image i2 = Utils.readImageFromPPM("ppm\\test2.ppm");
        i2 = Utils.rgbToYCbCr(i2);
        i2.changeResolution(5, 3, 2);

        String outputPath = "ppm\\YCbCrImage.ppm";

        // Write the image data to the PPM file
        Utils.writePPMFile(outputPath, i2);
    }
}
