import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.fail;

public class ImageTest {
    private Image getImage() {
        double[][] data1 = {
                {0.0, 0.0, 0.0, 1.0},
                {0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0},
                {1.0, 0.0, 0.0, 0.0}
        };
        double[][] data2 = {
                {0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 0.0}
        };
        double[][] data3 = {
                {0.0, 0.0, 0.0, 1.0},
                {0.0, 7.0 / 15.0, 0.0, 0.0},
                {0.0, 0.0, 7.0 / 15.0, 0.0},
                {1.0, 0.0, 0.0, 0.0}
        };
        return new Image(4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private Image getImage422(){
        double[][] data1 = {{0.0, 0.5}, {0.0, 0.0}, {0.0, 0.0}, {0.5, 0.0}};

        double [][] data2 = {{0.0, 0.0}, {0.5, 0.0}, {0.0, 0.5}, {0.0, 0.0}};

        double [][] data3 = {{0.0, 0.5}, {7.0/30.0, 0.0}, {0.0, 7.0/30.0}, {0.5, 0.0}};

        return new Image(4, 4, ColorSpace.RGB, data1, data2, data3);
    }
    private Image getImage420(){
        double [][] data1 = {{0.0, 0.25}, {0.25, 0.0}};

        double [][] data2 = {{0.25, 0.0}, {0.0, 0.25}};

        double [][] data3 = {{7.0/60.0, 0.25}, {0.25, 7.0/60.0}};

        return new Image(4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private Image getImage411(){
        double [][] data1 = {{0.25}, {0.0}, {0.0}, {0.25}};

        double [][] data2 = {{0.0}, {0.25}, {0.25}, {0.0}};

        double [][] data3 = {{0.25}, {7.0/60.0}, {7.0/60.0}, {0.25}};

        return new Image(4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    private Image getImage410(){
        double [][] data1 = {{0.125}, {0.125}};

        double [][] data2 = {{0.125}, {0.125}};

        double [][] data3 = {{11.0/60.0}, {11.0/60.0}};

        return new Image(4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    @Test
    public void testGetData1TooHigh() {
        Image image = getImage();
        Assert.assertEquals(image.getData1(2, 5), 0.0);
    }

    @Test
    public void testGetData1TooLow() {
        Image image = getImage();
        Assert.assertEquals(image.getData1(-2, -3), 0.0);
    }

    @Test
    public void testGetData2TooHigh() {
        Image image = getImage();
        Assert.assertEquals(image.getData2(2, 5), 0.0);
    }

    @Test
    public void testGetData2TooLow() {
        Image image = getImage();
        Assert.assertEquals(image.getData2(-2, -3), 0.0);
    }

    @Test
    public void testGetData3TooHigh() {
        Image image = getImage();
        Assert.assertEquals(image.getData3(2, 5), 0.0);
    }

    @Test
    public void testGetData3TooLow() {
        Image image = getImage();
        Assert.assertEquals(image.getData3(-2, -3), 0.0);
    }

    @Test
    public void testImagesEqualTrue () {
        Image image1 = getImage();
        Image image2 = getImage();
        Assert.assertEquals(image2, image1);
    }
    @Test
    public void testImagesEqualFalse () {
        Image image1 = getImage();
        Image image2 = getImage();
        image1.colorSpace = ColorSpace.YCbCr;
        Assert.assertNotEquals(image2, image1);
    }

    @Test
    public void testChangeResolutionAWrong () {
        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");
        try {
            image1.changeResolution(5, 2, 2, Arrays.asList(1,2,3));
            fail("should have thrown an exception");
        }
        catch (RuntimeException e) {
            if(!(e.getMessage().equals("No implementation for: 5, 2, 2") || e.getMessage().equals("Not power of two"))) {
                fail("wrong exception was thrown");
            }
        }
    }

    @Test
    public void testChangeResolutionBWrong () {
        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");
        try {
            image1.changeResolution(4, 3, 0, Arrays.asList(1,2,3));
            fail("should have thrown an exception");
        }
        catch (RuntimeException e) {
            if(!(e.getMessage().equals("No implementation for: 4, 3, 0") || e.getMessage().equals("Not power of two"))) {
                fail("wrong exception was thrown");
            }
        }

    }

    @Test
    public void testChangeResolutionCWrong () {
        Image image1 = Utils.readImageFromPPM("ppm\\test.ppm");
        try {
            image1.changeResolution(4, 2, 1, Arrays.asList(1,2,3));
            fail("should have thrown an exception");
        }
        catch (RuntimeException e) {
            if(!(e.getMessage().equals("No implementation for: 4, 2, 1") || e.getMessage().equals("Not power of two"))) {
                fail("wrong exception was thrown");
            }
        }

    }

    @Test
    public void testChangeResolution444 () {
        Image image1 = getImage();
        image1.changeResolution(4, 4, 4, Arrays.asList(1, 2, 3));
        Assert.assertTrue(image1.equals(getImage()));
    }

    @Test
    public void testChangeResolution422 () {
        Image image1 = getImage();
        image1.changeResolution(4, 2, 2, Arrays.asList(1, 2, 3));
        Assert.assertTrue(image1.equals(getImage422()));
    }

    @Test
    public void testChangeResolution420() {
        Image image1 = getImage();
        image1.changeResolution(4, 2, 0, Arrays.asList(1, 2, 3));
        Assert.assertTrue(image1.equals(getImage420()));
    }

    @Test
    public void testChangeResolution411() {
        Image image1 = getImage();
        image1.changeResolution(4, 1, 1, Arrays.asList(1, 2, 3));
        Assert.assertTrue(image1.equals(getImage411()));
    }

    @Test
    public void testChangeResolution410() {
        Image image1 = getImage();
        image1.changeResolution(4, 1, 0, Arrays.asList(1, 2, 3));
        Assert.assertTrue(image1.equals(getImage410()));
    }


    //todo Test with bigger PPM
}
