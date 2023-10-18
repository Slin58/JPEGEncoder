import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class ImageTest {
    private Image getImage(){
        List<List<Double>> data = new ArrayList<>();
        List<Double> list = new ArrayList<>();
        list.add(0.2); list.add(0.0); list.add(1.0);
        data.add(list);
        return new Image(1, 3, ColorSpace.RGB, data, data, data);
    }

    @Test
    public void testGetData1TooHigh() {
        Image image = getImage();
        Assert.assertEquals(image.getData1(2, 3), 1.0);
    }

    @Test
    public void testGetData1TooLow() {
        Image image = getImage();
        Assert.assertEquals(image.getData1(-2, -3), 0.2);
    }

    @Test
    public void testGetData2TooHigh() {
        Image image = getImage();
        Assert.assertEquals(image.getData2(2, 3), 1.0);
    }

    @Test
    public void testGetData2TooLow() {
        Image image = getImage();
        Assert.assertEquals(image.getData2(-2, -3), 0.2);
    }

    @Test
    public void testGetData3TooHigh() {
        Image image = getImage();
        Assert.assertEquals(image.getData3(2, 3), 1.0);
    }

    @Test
    public void testGetData3TooLow() {
        Image image = getImage();
        Assert.assertEquals(image.getData3(-2, -3), 0.2);
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
}
