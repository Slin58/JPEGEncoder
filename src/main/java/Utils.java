import org.ejml.simple.SimpleMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Utils {

    public static double checkForValidRange(double number) {
        double newNumber = min(max(number, 0.0), 1.0);
        if (number != newNumber) {
            System.out.println("One of the values wasn't in range between 0 and 1 and has therefore been adjusted.");
        }
        return newNumber;
    }

    public static Image readImageFromPPM(String path) {
        List<String> allLines = null;

        try {
            allLines = Files.readAllLines(Paths.get(path));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= allLines.size() - 1; i++) {
            if (allLines.get(i).charAt(0) == '#') {
                allLines.remove(i);
                i--;
            }
        }

        Image result = null;
        if (allLines.get(0).equals("P3")) {
            int[] imageSize = {Integer.parseInt(allLines.get(1).split("\\s+")[0]), Integer.parseInt(allLines.get(1).split("\\s+")[1])}; //height, width

            double maxColor = Double.parseDouble(allLines.get(2));
            allLines.remove(0);
            allLines.remove(0);
            allLines.remove(0);

            double[][] data1 = new double[imageSize[0]][imageSize[1]];
            double[][] data2 = new double[imageSize[0]][imageSize[1]];
            double[][] data3 = new double[imageSize[0]][imageSize[1]];
            int i = 0;
            int j = 0;

            for (int rowInFile = 0; rowInFile <= allLines.size() - 1; rowInFile++) {
                String[] rowFile = allLines.get(rowInFile).trim().split("\\s+");

                for (int valueInRow = 0; valueInRow <= rowFile.length - 1; valueInRow+=3) {
                    if (j >= imageSize[1]) {
                        i++;
                        j = 0;
                    }
                    data1[i][j] = checkForValidRange(Double.parseDouble(rowFile[valueInRow]) / maxColor);
                    data2[i][j] = checkForValidRange(Double.parseDouble(rowFile[valueInRow + 1]) / maxColor);
                    data3[i][j] = checkForValidRange(Double.parseDouble(rowFile[valueInRow + 2]) / maxColor);
                    j++;
                }

            }
            result = new Image(imageSize[0], imageSize[1], ColorSpace.RGB, data1, data2, data3);
        }
        return result;
    }

    public static void writePPMFile(String outputPath, Image image) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Write PPM header
            writer.write("P3\n");
            writer.write(image.height + " " + image.width + "\n");
            writer.write("255\n"); // Maximum color value

            for (int i = 0; i < image.data1.length; i++) {
                for (int j = 0; j < image.data1[i].length; j++) {
                    //for RGB here
                    int r = (int) Math.round(image.data1[i][j] * 255);
                    int g = (int) Math.round(image.data2[i][j] * 255);
                    int b = (int) Math.round(image.data3[i][j] * 255);
                    //  System.out.println("r,g,b: " + r + "," + g + "," + b );

                    writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image rgbToYCbCr (Image image) {
        //todo: Test
        SimpleMatrix transformMatrix = new SimpleMatrix(new double[][] {
                {0.299, 0.587, 0.114},
                {-0.1687, -0.3312, 0.5},
                {0.5, -0.4186, -0.0813}
        });
        SimpleMatrix prefixVector = new SimpleMatrix(new double[][] {
                {0.0},
                {0.5},
                {0.5}
        });

        for(int i = 0; i < image.data1.length; i++) {
            for(int j = 0; j < image.data1[i].length; j++) {
                SimpleMatrix rgbVector = new SimpleMatrix(new double[][] {
                        {image.getData1(i,j)},
                        {image.getData2(i,j)},
                        {image.getData3(i,j)},
                });
                rgbVector = prefixVector.plus(transformMatrix.mult(rgbVector));
                image.data1[i][j] = rgbVector.get(0,0);
                image.data2[i][j] = rgbVector.get(1,0);
                image.data3[i][j] = rgbVector.get(2,0);
            }
        }
        image.colorSpace = ColorSpace.YCbCr;
        return image;
    }
}
