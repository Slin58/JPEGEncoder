import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Image {
    int height;
    int width;
    List<List<Integer>> data1;
    List<List<Integer>> data2;
    List<List<Integer>> data3;
    //todo Farbraum?? z.B. RGB, ...

    public Image(int height, int width, List<List<Integer>> data1, List<List<Integer>> data2, List<List<Integer>> data3) {
        this.height = height;
        this.width = width;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }


    public void changeResolution(int a, int b, int c) {
        //todo
    }


    public int getData1(int x, int y) {
        x = min(data1.size()-1, x);
        x = max(0, x);

        y = min(data1.get(x).size()-1, y);
        y = max(0, y);

        return this.data1.get(x).get(y);
    }


    public int getData2(int x, int y) {
        x = min(data2.size()-1, x);
        x = max(0, x);

        y = min(data2.get(x).size()-1, y);
        y = max(0, y);

        return this.data2.get(x).get(y);
    }


    public int getData3(int x, int y) {
        x = min(data3.size()-1, x);
        x = max(0, x);

        y = min(data3.get(x).size()-1, y);
        y = max(0, y);

        return this.data3.get(x).get(y);
    }

}
