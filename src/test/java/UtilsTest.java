import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsTest {
    private Image getImageTestPPM(){
        List<List<Double>> data1 = new ArrayList<>();
        data1.add(Arrays.asList(0.0, 0.0, 0.0, 1.0));
        data1.add(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        data1.add(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        data1.add(Arrays.asList(1.0, 0.0, 0.0, 0.0));

        List<List<Double>> data2 = new ArrayList<>();
        data2.add(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        data2.add(Arrays.asList(0.0, 1.0, 0.0, 0.0));
        data2.add(Arrays.asList(0.0, 0.0, 1.0, 0.0));
        data2.add(Arrays.asList(0.0, 0.0, 0.0, 0.0));

        List<List<Double>> data3 = new ArrayList<>();
        data3.add(Arrays.asList(0.0, 0.0, 0.0, 1.0));
        data3.add(Arrays.asList(0.0, 7.0/15.0, 0.0, 0.0));
        data3.add(Arrays.asList(0.0, 0.0, 7.0/15.0, 0.0));
        data3.add(Arrays.asList(1.0, 0.0, 0.0, 0.0));

        return new Image(4, 4, ColorSpace.RGB, data1, data2, data3);
    }

    @Test
    public void testReadImageFromPPM () {
        Image image1 = getImageTestPPM();
        Image image2 = Utils.readImageFromPPM("ppm\\test.ppm");
        Assert.assertEquals(image2, image1);
    }

    @Test
    public void testReadImageFromInvalidPPM () {
        Image image = Utils.readImageFromPPM("ppm\\test_invalid.ppm");  //wrong ppm
        Assert.assertNull(image);
    }

}
