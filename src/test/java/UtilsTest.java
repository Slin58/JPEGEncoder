import org.testng.Assert;
import org.testng.annotations.Test;

public class UtilsTest {

    @Test
    public void testReadImageFromPPM () {
        Image image = Utils.readImageFromPPM("ppm\\test.ppm");
        Assert.assertEquals(4, image.height);
        //todo
    }

    @Test
    public void testReadImageFromPPMException () {
        Image image = Utils.readImageFromPPM("ppm\\test.ppm");  //wrong ppm
        Assert.assertEquals(0.0, 0.0);
        //todo
    }

}
