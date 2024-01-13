import huffman.HuffmanLookUpRow;
import image.ColorSpace;
import image.JPEGEncoderImage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class UtilsTest {
    private JPEGEncoderImage getImageTestPPM() {
        double[][] data1 = {{0.0, 0.0, 0.0, 1.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {1.0, 0.0, 0.0, 0.0}};
        double[][] data2 = {{0.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 0.0}};
        double[][] data3 =
                {{0.0, 0.0, 0.0, 1.0}, {0.0, 7.0 / 15.0, 0.0, 0.0}, {0.0, 0.0, 7.0 / 15.0, 0.0}, {1.0, 0.0, 0.0, 0.0}};
        return new JPEGEncoderImage(4, 4, 4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    @Test
    public void testReadImageFromPPM() {
        JPEGEncoderImage JPEGEncoderImage1 = getImageTestPPM();
        JPEGEncoderImage JPEGEncoderImage2 = Utils.readImageFromPPM("ppm\\test.ppm");
        Assert.assertTrue(JPEGEncoderImage2.equals(JPEGEncoderImage1));
    }

    @Test
    public void testReadImageFromInvalidPPM() {
        JPEGEncoderImage JPEGEncoderImage = Utils.readImageFromPPM("ppm\\test_invalid.ppm");  //wrong ppm
        Assert.assertNull(JPEGEncoderImage);
    }

    @Test
    public void testReadImageFrom() {
        JPEGEncoderImage JPEGEncoderImage1 = Utils.readImageFromPPM("ppm\\test2.ppm");
        Assert.assertEquals(JPEGEncoderImage1.getData1()[0][0], 0.11764705882352941);
        Assert.assertEquals(JPEGEncoderImage1.getData2()[0][0], 0.2627450980392157);
        Assert.assertEquals(JPEGEncoderImage1.getData3()[0][0], 0.5294117647058824);
        Assert.assertEquals(JPEGEncoderImage1.getData1()[299][167], 0.48627450980392156);
        Assert.assertEquals(JPEGEncoderImage1.getData2()[299][167], 0.3137254901960784);
        Assert.assertEquals(JPEGEncoderImage1.getData3()[299][167], 0.21568627450980393);

    }

    @Test
    public void testReadBigImage() {
        JPEGEncoderImage JPEGEncoderImage = Utils.readImageFromPPM("ppm\\fat.ppm");
        Assert.assertNotNull(JPEGEncoderImage);
    }

    @Test
    public void testTcast() {
        HuffmanLookUpRow<Character> row = new HuffmanLookUpRow<>('A', 0, 0);
        System.out.println(row.encodedValue());
    }


}
