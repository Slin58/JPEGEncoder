import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
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
        if (data2.size() != data3.size()) {
            throw new RuntimeException("Image data is broken please destroy the image!!!");
        }
        for (int i = 0; i <= data2.size() - 1; i++) {
            if (data2.get(i).size() != data3.get(i).size()) {
                System.out.println(data2.get(i).size());
                System.out.println(data3.get(i).size());
                throw new RuntimeException("Image data is broken please destroy the image!!!");
            }
        }

        this.height = height;
        this.width = width;
        this.colorSpace = colorSpace;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public void changeResolution(int a, int b, int c) {
        //todo
        if (!colorSpace.equals(ColorSpace.YCbCr)) {
            throw new RuntimeException("ColorSpace " + colorSpace + " not valid for change Resolution");
        }

        if (a == 4)  {
            if (b == 4) {
                if (c == 4) {
                    return;
                }
            }
            else if (b == 2) {
                List<List<Double>> data2New = new ArrayList<>();
                List<List<Double>> data3New = new ArrayList<>();

                for (int i = 0; i <= data2.size() - 1; i++) {
                    List<Double> row2 = new ArrayList<>();
                    List<Double> row3 = new ArrayList<>();

                    for (int j = 0; j <= data2.get(i).size() - 1; j+=2) {
                        if (j + 1 <= data2.get(i).size() - 1) {
                            double d2 = (data2.get(i).get(j) + data2.get(i).get(j + 1)) / 2;
                            row2.add(d2);

                            double d3 = (data3.get(i).get(j) + data3.get(i).get(j + 1)) / 2;
                            row3.add(d3);
                        }
                        else {
                            row2.add(data2.get(i).get(j));
                            row3.add(data3.get(i).get(j));
                        }
                    }
                    data2New.add(row2);
                    data3New.add(row3);
                }
                data2 = data2New;
                data3 = data3New;

                if (c == 2) {
                    return;
                }
                else if (c == 0) {

                    List<List<Double>> data2New2 = new ArrayList<>();
                    List<List<Double>> data3New2 = new ArrayList<>();

                    for (int i = 0; i <= data2.size() - 1; i+=2) {
                        List<Double> row2 = new ArrayList<>();
                        List<Double> row3 = new ArrayList<>();

                        for (int j = 0; j <= data2.get(i).size() - 1; j++) {
                            if (i + 1 <= data2.size() - 1) {
                                double d2 = (data2.get(i).get(j) + data2.get(i + 1).get(j)) / 2;
                                row2.add(d2);

                                double d3 = (data3.get(i).get(j) + data3.get(i + 1).get(j)) / 2;
                                row3.add(d3);
                            }
                            else {
                                row2.add(data2.get(i).get(j));
                                row3.add(data3.get(i).get(j));
                            }
                        }
                        data2New2.add(row2);
                        data3New2.add(row3);
                    }
                    data2 = data2New2;
                    data3 = data3New2;
                    return;
                }
            }
            else if (b == 1) {
                if (c == 1) {
                    //todo
                }
            }
        }

        throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
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
