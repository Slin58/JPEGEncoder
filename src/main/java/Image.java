import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Image {
    int height; //brauchen wir?
    int width;  //brauchen wir?
    ColorSpace colorSpace;
    //evtl. max. und min. Value für data1-3
    List<List<Double>> data1;   //Farbwerte zwischen 0 und 1
    List<List<Double>> data2;
    List<List<Double>> data3;


    public Image(int height, int width, ColorSpace colorSpace, List<List<Double>> data1, List<List<Double>> data2, List<List<Double>> data3) {
        this.height = height;
        this.width = width;
        this.colorSpace = colorSpace;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
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

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;

        if (this.height != image.height) {
            System.out.println("Images height is not the same");
            return false;
        }
        if (this.width != image.width) {
            System.out.println("Images width is not the same");
            return false;
        }
        if (this.colorSpace != image.colorSpace) {
            System.out.println("Images colorSpace is not the same");
            return false;
        }
        if (this.data1.size() != image.data1.size()) {
            System.out.println("Images data1.size() is not the same");
            return false;
        }
        if (this.data2.size() != image.data2.size()) {
            System.out.println("Images data2.size() is not the same");
            return false;
        }
        if (this.data3.size() != image.data3.size()) {
            System.out.println("Images data3.size() is not the same");
            return false;
        }
        for (int i = 0; i < data1.size(); i++) {
            for (int j = 0; j < data1.get(i).size(); j++) {
                if (!this.data1.get(i).get(j).equals(image.data1.get(i).get(j))) {
                    System.out.println("this.data1: i:" + i + " j:" + j + ", " + this.data1.get(i).get(j));
                    System.out.println("image.data1: i:" + i + " j:" + j + ", " + image.data1.get(i).get(j));
                    return false;
                }
            }
        }
        for (int i = 0; i < data2.size(); i++) {
            for (int j = 0; j < data2.get(i).size(); j++) {
                if (!this.data2.get(i).get(j).equals(image.data2.get(i).get(j))) {
                    System.out.println("this.data2: i:" + i + " j:" + j + ", " + this.data2.get(i).get(j));
                    System.out.println("image.data2: i:" + i + " j:" + j + ", " + image.data2.get(i).get(j));
                    return false;
                }
            }
        }
        for (int i = 0; i < data3.size(); i++) {
            for (int j = 0; j < data3.get(i).size(); j++) {
                if (!this.data3.get(i).get(j).equals(image.data3.get(i).get(j))) {
                    System.out.println("this.data3: i:" + i + " j:" + j + ", " + this.data3.get(i).get(j));
                    System.out.println("image.data3: i:" + i + " j:" + j + ", " + image.data3.get(i).get(j));
                    return false;
                }
            }
        }
        return true;
    }
}
