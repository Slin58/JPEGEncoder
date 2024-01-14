package image;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import java.util.Arrays;

import static org.testng.Assert.fail;

public class JPEGEncoderImageTest {
    private JPEGEncoderImage getImage() {
        double[][] data1 = {{0.0, 0.0, 0.0, 1.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {1.0, 0.0, 0.0, 0.0}};
        double[][] data2 = {{0.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 0.0}};
        double[][] data3 =
                {{0.0, 0.0, 0.0, 1.0}, {0.0, 7.0 / 15.0, 0.0, 0.0}, {0.0, 0.0, 7.0 / 15.0, 0.0}, {1.0, 0.0, 0.0, 0.0}};
        return new JPEGEncoderImage(4, 4, 4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private JPEGEncoderImage getImage422() {
        double[][] data1 = {{0.0, 0.5}, {0.0, 0.0}, {0.0, 0.0}, {0.5, 0.0}};

        double[][] data2 = {{0.0, 0.0}, {0.5, 0.0}, {0.0, 0.5}, {0.0, 0.0}};

        double[][] data3 = {{0.0, 0.5}, {7.0 / 30.0, 0.0}, {0.0, 7.0 / 30.0}, {0.5, 0.0}};

        return new JPEGEncoderImage(4, 4, 4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private JPEGEncoderImage getImage420() {
        double[][] data1 = {{0.0, 0.25}, {0.25, 0.0}};

        double[][] data2 = {{0.25, 0.0}, {0.0, 0.25}};

        double[][] data3 = {{7.0 / 60.0, 0.25}, {0.25, 7.0 / 60.0}};

        return new JPEGEncoderImage(4, 4, 4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private JPEGEncoderImage getImage411() {
        double[][] data1 = {{0.25}, {0.0}, {0.0}, {0.25}};

        double[][] data2 = {{0.0}, {0.25}, {0.25}, {0.0}};

        double[][] data3 = {{0.25}, {7.0 / 60.0}, {7.0 / 60.0}, {0.25}};

        return new JPEGEncoderImage(4, 4, 4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private JPEGEncoderImage getImage410() {
        double[][] data1 = {{0.125}, {0.125}};

        double[][] data2 = {{0.125}, {0.125}};

        double[][] data3 = {{11.0 / 60.0}, {11.0 / 60.0}};

        return new JPEGEncoderImage(4, 4, 4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    @Test
    public void testGetData1TooHigh() {
        JPEGEncoderImage JPEGEncoderImage = getImage();
        Assert.assertEquals(JPEGEncoderImage.getData1(2, 5), 0.0);
    }

    @Test
    public void testGetData1TooLow() {
        JPEGEncoderImage JPEGEncoderImage = getImage();
        Assert.assertEquals(JPEGEncoderImage.getData1(-2, -3), 0.0);
    }

    @Test
    public void testGetData2TooHigh() {
        JPEGEncoderImage JPEGEncoderImage = getImage();
        Assert.assertEquals(JPEGEncoderImage.getData2(2, 5), 0.0);
    }

    @Test
    public void testGetData2TooLow() {
        JPEGEncoderImage JPEGEncoderImage = getImage();
        Assert.assertEquals(JPEGEncoderImage.getData2(-2, -3), 0.0);
    }

    @Test
    public void testGetData3TooHigh() {
        JPEGEncoderImage JPEGEncoderImage = getImage();
        Assert.assertEquals(JPEGEncoderImage.getData3(2, 5), 0.0);
    }

    @Test
    public void testGetData3TooLow() {
        JPEGEncoderImage JPEGEncoderImage = getImage();
        Assert.assertEquals(JPEGEncoderImage.getData3(-2, -3), 0.0);
    }

    @Test
    public void testImagesEqualTrue() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage JPEGEncoderImage2 = getImage();
        Assert.assertEquals(JPEGEncoderImage2, JPEGEncoderImage1);
    }

    @Test
    public void testImagesEqualFalse() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage JPEGEncoderImage2 = getImage();
        JPEGEncoderImage1.colorSpace = ColorSpace.YCbCr;
        Assert.assertNotEquals(JPEGEncoderImage2, JPEGEncoderImage1);
    }

    @Test
    public void testChangeResolutionAWrong() {
        JPEGEncoderImage JPEGEncoderImage1 = Utils.readImageFromPPM("ppm\\test.ppm");
        try {
            JPEGEncoderImage1.changeResolution(5, 2, 2, Arrays.asList(1, 2, 3));
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
            if (!(e.getMessage().equals("No implementation for: 5, 2, 2") ||
                  e.getMessage().equals("Not power of two"))) {
                fail("wrong exception was thrown");
            }
        }
    }

    @Test
    public void testChangeResolutionBWrong() {
        JPEGEncoderImage JPEGEncoderImage1 = Utils.readImageFromPPM("ppm\\test.ppm");
        try {
            JPEGEncoderImage1.changeResolution(4, 3, 0, Arrays.asList(1, 2, 3));
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
            if (!(e.getMessage().equals("No implementation for: 4, 3, 0") ||
                  e.getMessage().equals("Not power of two"))) {
                fail("wrong exception was thrown");
            }
        }

    }

    @Test
    public void testChangeResolutionCWrong() {
        JPEGEncoderImage JPEGEncoderImage1 = Utils.readImageFromPPM("ppm\\test.ppm");
        try {
            JPEGEncoderImage1.changeResolution(4, 2, 1, Arrays.asList(1, 2, 3));
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
            if (!(e.getMessage().equals("No implementation for: 4, 2, 1") ||
                  e.getMessage().equals("Not power of two"))) {
                fail("wrong exception was thrown");
            }
        }

    }

    @Test
    public void testChangeResolution444() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage1.changeResolution(4, 4, 4, Arrays.asList(1, 2, 3));
        Assert.assertTrue(JPEGEncoderImage1.equals(getImage()));
    }

    @Test
    public void testChangeResolution422() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage1.changeResolution(4, 2, 2, Arrays.asList(1, 2, 3));
        Assert.assertTrue(JPEGEncoderImage1.equals(getImage422()));
    }

    @Test
    public void testChangeResolution420() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage1.changeResolution(4, 2, 0, Arrays.asList(1, 2, 3));
        Assert.assertTrue(JPEGEncoderImage1.equals(getImage420()));
    }

    @Test
    public void testChangeResolution411() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage1.changeResolution(4, 1, 1, Arrays.asList(1, 2, 3));
        Assert.assertTrue(JPEGEncoderImage1.equals(getImage411()));
    }

    @Test
    public void testChangeResolution410() {
        JPEGEncoderImage JPEGEncoderImage1 = getImage();
        JPEGEncoderImage1.changeResolution(4, 1, 0, Arrays.asList(1, 2, 3));
        Assert.assertTrue(JPEGEncoderImage1.equals(getImage410()));
    }


    //todo Test with bigger PPM
}
