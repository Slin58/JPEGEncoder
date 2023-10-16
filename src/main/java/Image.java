import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Image {
    int height;
    int width;
    List<List<Double>> data1;   //Farbwerte zwischen 0 und 1
    List<List<Double>> data2;
    List<List<Double>> data3;
    ColorSpace colorSpace;

    public Image(int height, int width, List<List<Double>> data1, List<List<Double>> data2, List<List<Double>> data3, ColorSpace colorSpace) {
        this.height = height;
        this.width = width;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.colorSpace = colorSpace;
    }


    public void changeResolution(int a, int b, int c) {
        //todo
    }


    public double getData1(int x, int y) {
        x = min(data1.size()-1, x);
        x = max(0, x);

        y = min(data1.get(x).size()-1, y);
        y = max(0, y);

        return this.data1.get(x).get(y);
    }


    public double getData2(int x, int y) {
        x = min(data2.size()-1, x);
        x = max(0, x);

        y = min(data2.get(x).size()-1, y);
        y = max(0, y);

        return this.data2.get(x).get(y);
    }


    public double getData3(int x, int y) {
        x = min(data3.size()-1, x);
        x = max(0, x);

        y = min(data3.get(x).size()-1, y);
        y = max(0, y);

        return this.data3.get(x).get(y);
    }

}
