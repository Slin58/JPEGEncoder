package dctImplementation;

import image.JPEGEncoderImage;
import org.testng.annotations.Test;
import utils.Utils;

public class DCTPipelineTest {
    @Test
    public void testTwoDDCT() { //todo: unfinished, one test for each dct impl
        JPEGEncoderImage image = Utils.readImageFromPPM("ppm\\testDCT.ppm");

        DCT2 dct2 = new DCT2();
        dct2.calculatePictureDataWithDCT(image);

        dct2.calculatePictureDataWithInverseDCT(image);
        DCT.stop();

        Utils.writePPMFile("ppm\\dctoutput1.jpg", image);

    }


}
