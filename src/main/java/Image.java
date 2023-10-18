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

    public List<List<Double>> changeResolutionHorizontal(List<List<Double>> data, int amount) {
        List<List<Double>> dataNew = new ArrayList<>();

        for (int i = 0; i <= data.size() - 1; i++) {
            List<Double> row = new ArrayList<>();

            for (int j = 0; j <= data.get(i).size() - 1; j+=amount) {
                int remainingInRange = data.get(i).size() - j;
                if (remainingInRange > amount) {
                    remainingInRange = amount;
                }

                double d2 = 0;
                for (int k = 0; k < remainingInRange; k++) {
                    d2 += data.get(i).get(j+k);
                }
                row.add(d2 / remainingInRange);
            }
            dataNew.add(row);
        }
        return dataNew;
    }

    public List<List<Double>> halveResolutionVertical(List<List<Double>> data) {
        List<List<Double>> dataNew = new ArrayList<>();

        for (int i = 0; i <= data.size() - 1; i+=2) {
            List<Double> row = new ArrayList<>();

            for (int j = 0; j <= data.get(i).size() - 1; j++) {
                if (i + 1 <= data.size() - 1) {
                    double d2 = (data.get(i).get(j) + data.get(i + 1).get(j)) / 2;
                    row.add(d2);
                }
                else {
                    row.add(data.get(i).get(j));
                }
            }
            dataNew.add(row);
        }
        return dataNew;
    }

    public void changeResolution(int a, int b, int c, List<Integer> channel) {

        if (a == 4)  {
            if (b == 4) {
            }
            else if (b == 2) {
                data1 = (channel.contains(1)) ? changeResolutionHorizontal(data1, 2) : data1;
                data2 = (channel.contains(2)) ? changeResolutionHorizontal(data2, 2) : data2;
                data3 = (channel.contains(3)) ? changeResolutionHorizontal(data3, 2) : data3;
            }
            else if (b == 1) {
                data1 = (channel.contains(1)) ? changeResolutionHorizontal(data1, 4) : data1;
                data2 = (channel.contains(2)) ? changeResolutionHorizontal(data2, 4) : data2;
                data3 = (channel.contains(3)) ? changeResolutionHorizontal(data3, 4) : data3;
            }
            else {
                throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
            }

            if (c == b) {
            }
            else if(c == 0) {
                data1 = (channel.contains(1)) ? halveResolutionVertical(data1) : data1;
                data2 = (channel.contains(2)) ? halveResolutionVertical(data2) : data2;
                data3 = (channel.contains(3)) ? halveResolutionVertical(data3) : data3;
            }
            else {
                throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
            }
        }
        else {
            throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
        }

    }

    /*
    public void changeResolutionOld(int a, int b, int c) {

        if (a == 4)  {
            if (b == 4) {
                if (c == 4) {
                    return;
                }
                else if (c == 0) {
                    data2 = halveResolutionVertical(data2);
                    data3 = halveResolutionVertical(data3);
                    return;
                }
            }
            else if (b == 2) {
                data2 = changeResolutionHorizontal(data2, 2);
                data3 = changeResolutionHorizontal(data3, 2);

                if (c == 2) {
                    return;
                }
                else if (c == 1) {

                }
                else if (c == 0) {
                    data2 = halveResolutionVertical(data2);
                    data3 = halveResolutionVertical(data3);
                    return;
                }
            }
            else if (b == 1) {
                data2 = changeResolutionHorizontal(data2, 4);
                data3 = changeResolutionHorizontal(data3, 4);
                if (c == 1) {
                    return;
                }
                else if(c == 0) {
                    data2 = halveResolutionVertical(data2);
                    data3 = halveResolutionVertical(data3);
                    return;
                }
            }
        }

        throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
    }
     */


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
