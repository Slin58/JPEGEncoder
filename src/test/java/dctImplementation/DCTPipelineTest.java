package dctImplementation;

import image.JPEGEncoderImage;
import org.testng.annotations.Test;
import utils.Utils;

public class DCTPipelineTest {

    @Test
    public void testDCT1Pipeline() {
        JPEGEncoderImage image = Utils.readImageFromPPM("ppm\\testDCT.ppm");
        DCT1 dct1 = new DCT1();
        dct1.calculatePictureDataWithDCT(image);
        dct1.calculatePictureDataWithInverseDCT(image);
        Utils.writePPMFile("ppm\\dctoutputDCT1.jpg", image);
    }

    @Test
    public void testDCT2Pipeline() {
        JPEGEncoderImage image = Utils.readImageFromPPM("ppm\\testDCT.ppm");
        DCT2 dct2 = new DCT2();
        dct2.calculatePictureDataWithDCT(image);
        dct2.calculatePictureDataWithInverseDCT(image);
        Utils.writePPMFile("ppm\\dctoutputDCT2.jpg", image);
    }

    @Test
    public void testAraiDCTPipeline() {
        JPEGEncoderImage image = Utils.readImageFromPPM("ppm\\testDCT.ppm");
        AraiDCT araiDCT = new AraiDCT();
        araiDCT.calculatePictureDataWithDCT(image);
        araiDCT.calculatePictureDataWithInverseDCT(image);
        Utils.writePPMFile("ppm\\dctoutputArayDCT.jpg", image);
    }

}
