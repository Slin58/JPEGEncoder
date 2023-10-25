import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsTest {
    private Image getImageTestPPM() {
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

    @Test
    public void testReadImageFromPPM () {
        Image image1 = getImageTestPPM();
        Image image2 = Utils.readImageFromPPM("ppm\\test.ppm");
        Assert.assertTrue(image2.equals(image1));
    }

    @Test
    public void testReadImageFromInvalidPPM () {
        Image image = Utils.readImageFromPPM("ppm\\test_invalid.ppm");  //wrong ppm
        Assert.assertNull(image);
    }

    @Test
    public void testReadImageFrom (){
        Image image1 = Utils.readImageFromPPM("ppm\\test2.ppm");
        Assert.assertEquals(image1.data1[0][0], 0.11764705882352941);
        Assert.assertEquals(image1.data2[0][0], 0.2627450980392157);
        Assert.assertEquals(image1.data3[0][0], 0.5294117647058824);
        Assert.assertEquals(image1.data1[299][167], 0.48627450980392156);
        Assert.assertEquals(image1.data2[299][167], 0.3137254901960784);
        Assert.assertEquals(image1.data3[299][167], 0.21568627450980393);

    }

    @Test
    public void testReadBigImage () {
        Image image = Utils.readImageFromPPM("ppm\\fat.ppm");
        Assert.assertNotNull(image);
    }


}
