package image;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class JPEGEncoderImage {
    int originalHeight;
    int originalWith;
    int height;
    int width;
    ColorSpace colorSpace;
    //Farbwerte zwischen 0 und 1
    double[][] data1;
    double[][] data2;
    double[][] data3;

    public JPEGEncoderImage(int height, int width, int originalHeight, int originalWidth, ColorSpace colorSpace,
                            double[][] data1, double[][] data2, double[][] data3) {
        if (data2.length != data3.length) {
            throw new RuntimeException("Unequal channel size. Something may be wrong with the image");
        }
        for (int i = 0; i <= data2.length - 1; i++) {
            if (data2[i].length != data3[i].length) {
                System.out.println(data2[i].length);
                System.out.println(data3[i].length);
                throw new RuntimeException("Unequal channel size. Something may be wrong with the image");
            }
        }
        this.height = height;
        this.width = width;
        this.colorSpace = colorSpace;
        this.originalHeight = originalHeight;
        this.originalWith = originalWidth;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    //TODO: Schrittweiten nachdefinieren; Abfrage nicht per Pixel (s. getData)
    // max color immer auf 255 (? raff ich nicht tbh)
    // tests fuer riesige bilder
    // ppm reader Zeilenaufteilung mit Width,

    public JPEGEncoderImage(JPEGEncoderImage image) {
        this.height = image.height;
        this.width = image.width;
        this.colorSpace = image.colorSpace;
        this.data1 = image.data1;
        this.data2 = image.data2;
        this.data3 = image.data3;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public int getOriginalWith() {
        return originalWith;
    }

    public void setOriginalWith(int originalWith) {
        this.originalWith = originalWith;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ColorSpace getColorSpace() {
        return colorSpace;
    }

    public void setColorSpace(ColorSpace colorSpace) {
        this.colorSpace = colorSpace;
    }

    public double[][] getData1() {
        return data1;
    }

    public void setData1(double[][] data1) {
        this.data1 = data1;
    }

    public double[][] getData2() {
        return data2;
    }

    public void setData2(double[][] data2) {
        this.data2 = data2;
    }

    public double[][] getData3() {
        return data3;
    }

    public void setData3(double[][] data3) {
        this.data3 = data3;
    }

    public boolean isPowerOfTwo(int number) {
        if ((number & (number - 1)) != 0) {
            return false;
        }
        return true;
    }

    //todo: test fuer GROSSES bild
    public double[][] changeResolutionHorizontal(double[][] data, int amount) {
        double[][] newImageData = new double[this.height][this.width / amount];
        for (int i = 0; i <= data.length - 1; i++) {
            double[] row = new double[data[i].length / amount];

            for (int j = 0; j <= data[i].length - 1; j += amount) {
                double d2 = 0;
                for (int k = 0; k < amount; k++) {
                    d2 += data[i][j + k];
                }
                row[j / amount] = d2 / amount;
            }
            newImageData[i] = row;
        }
        return newImageData;
    }

    public double[][] halveResolutionVertical(double[][] data) {
        double[][] newImageData = new double[this.height / 2][this.width];

        for (int i = 0; i <= data.length - 1; i += 2) {
            double[] row = new double[data[i].length];

            for (int j = 0; j <= data[i].length - 1; j++) {
                double d2 = (data[i][j] + data[i + 1][j]) / 2;
                row[j] = d2;
            }
            newImageData[i / 2] = row;
        }
        return newImageData;
    }

    public void changeResolution(int a, int b, int c, List<Integer> channel) {
        if (!isPowerOfTwo(a) || !isPowerOfTwo(b) || !isPowerOfTwo(c)) {
            throw new RuntimeException("Not power of two");
        }

        if (a == 4) {
            if (b == 4) {
            } else if (b == 2) {
                data1 = (channel.contains(1)) ? changeResolutionHorizontal(data1, 2) : data1;
                data2 = (channel.contains(2)) ? changeResolutionHorizontal(data2, 2) : data2;
                data3 = (channel.contains(3)) ? changeResolutionHorizontal(data3, 2) : data3;
            } else if (b == 1) {
                data1 = (channel.contains(1)) ? changeResolutionHorizontal(data1, 4) : data1;
                data2 = (channel.contains(2)) ? changeResolutionHorizontal(data2, 4) : data2;
                data3 = (channel.contains(3)) ? changeResolutionHorizontal(data3, 4) : data3;
            } else {
                throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
            }
            if (c == b) {
            } else if (c == 0) {
                data1 = (channel.contains(1)) ? halveResolutionVertical(data1) : data1;
                data2 = (channel.contains(2)) ? halveResolutionVertical(data2) : data2;
                data3 = (channel.contains(3)) ? halveResolutionVertical(data3) : data3;
            } else {
                throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
            }
        } else {
            throw new RuntimeException("No implementation for: " + a + ", " + b + ", " + c);
        }

    }

    public double getData1(int x, int y) {
        x = min(data1.length - 1, x);
        x = max(0, x);
        y = min(data1[x].length - 1, y);
        y = max(0, y);

        return this.data1[x][y];
    }

    public double getData2(int x, int y) {
        x = min(data2.length - 1, x);
        x = max(0, x);
        y = min(data2[x].length - 1, y);
        y = max(0, y);

        return this.data2[x][y];
    }

    public double getData3(int x, int y) {
        x = min(data3.length - 1, x);
        x = max(0, x);
        y = min(data3[x].length - 1, y);
        y = max(0, y);

        return this.data3[x][y];
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }
        JPEGEncoderImage JPEGEncoderImage = (JPEGEncoderImage) o;

        if (this.height != JPEGEncoderImage.height) {
            System.out.println("Images height is not the same");
            return false;
        }
        if (this.width != JPEGEncoderImage.width) {
            System.out.println("Images width is not the same");
            return false;
        }
        if (this.colorSpace != JPEGEncoderImage.colorSpace) {
            System.out.println("Images colorSpace is not the same");
            return false;
        }
        if (this.data1.length != JPEGEncoderImage.data1.length) {
            System.out.println("Images data1.size() is not the same");
            return false;
        }
        if (this.data2.length != JPEGEncoderImage.data2.length) {
            System.out.println("Images data2.size() is not the same");
            return false;
        }
        if (this.data3.length != JPEGEncoderImage.data3.length) {
            System.out.println("Images data3.size() is not the same");
            return false;
        }
        for (int i = 0; i < data1.length; i++) {
            for (int j = 0; j < data1[i].length; j++) {
                if (Math.round(this.data1[i][j] * 10000) / 10000.0 !=
                    Math.round(JPEGEncoderImage.data1[i][j] * 10000) / 10000.0) {
                    System.out.println("this.data1: i:" + i + " j:" + j + ", " + this.data1[i][j]);
                    System.out.println("image.data1: i:" + i + " j:" + j + ", " + JPEGEncoderImage.data1[i][j]);
                    return false;
                }
            }
        }
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2[i].length; j++) {
                if (Math.round(this.data2[i][j] * 10000) / 10000.0 !=
                    Math.round(JPEGEncoderImage.data2[i][j] * 10000) / 10000.0) {
                    System.out.println("this.data2: i:" + i + " j:" + j + ", " + this.data2[i][j]);
                    System.out.println("image.data2: i:" + i + " j:" + j + ", " + JPEGEncoderImage.data2[i][j]);
                    return false;
                }
            }
        }
        for (int i = 0; i < data3.length; i++) {
            for (int j = 0; j < data3[i].length; j++) {
                if (Math.round(this.data3[i][j] * 10000) / 10000.0 !=
                    Math.round(JPEGEncoderImage.data3[i][j] * 10000) / 10000.0) {
                    System.out.println("this.data3: i:" + i + " j:" + j + ", " + this.data3[i][j]);
                    System.out.println("image.data3: i:" + i + " j:" + j + ", " + JPEGEncoderImage.data3[i][j]);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String d1 = "";
        String d2 = "";
        String d3 = "";

        for (double[] a : data1) {
            for (double b : a) {
                d1 += b + ", ";
            }
            d1 += "\n";
        }
        for (double[] a : data2) {
            for (double b : a) {
                d2 += b + ", ";
            }
            d2 += "\n";
        }
        for (double[] a : data3) {
            for (double b : a) {
                d3 += b + ", ";
            }
            d3 += "\n";
        }

        return "height = " + height + ", width = " + width + ", colorSpace = " + colorSpace + "\ndata1 = " + d1 +
               "\ndata2 = " + d2 + "\ndata3 = " + d3;
    }

}
