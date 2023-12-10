package utils;

import image.ColorSpace;
import image.JPEGEncoderImage;
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

    public static boolean isNumeric(char c) {
        try {
            Double.parseDouble(String.valueOf(c));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double checkForValidRange(double number) {
        double newNumber = min(max(number, 0.0), 1.0);
        if (number != newNumber) {
            System.out.println("One of the values wasn't in range between 0 and 1 and has therefore been adjusted.");
        }
        return newNumber;
    }

    public static JPEGEncoderImage readImageFromPPM(String path) {
        List<String> allLines = null;

        try {
            allLines = Files.readAllLines(Paths.get(path));


        } catch (IOException e) {
            e.printStackTrace();
        }

        JPEGEncoderImage result = null;
        double maxColor = -1.0;
        int height = -1;
        int width = -1;
        if (allLines.get(0).equals("P3")) {
            int commentCounter = 0;
            for (int i = 1; i <= allLines.size() - 1; i++) {
                if (isNumeric(allLines.get(i).charAt(0))) {
                    if (height == -1) {
                        height = Integer.parseInt(allLines.get(i).split("\\s+")[0]);
                        width = Integer.parseInt(allLines.get(i).split("\\s+")[1]);
                    } else {
                        maxColor = Double.parseDouble(allLines.get(i));
                    }
                } else {
                    commentCounter++;
                }
                if (maxColor != -1.0) {
                    break;
                }

            }

            double[][] data1 = new double[height][width];
            double[][] data2 = new double[height][width];
            double[][] data3 = new double[height][width];
            int i = 0;
            int j = 0;

            for (int rowInFile = 3 + commentCounter; rowInFile <= allLines.size() - 1; rowInFile++) {
                String[] rowFile = allLines.get(rowInFile).trim().split("\\s+");
                if (rowFile[0] == "#") {
                    continue;
                }
                for (int valueInRow = 0; valueInRow <= rowFile.length - 1; valueInRow += 3) {
                    if (j >= width) {
                        i++;
                        j = 0;
                    }
                    data1[i][j] = Double.parseDouble(rowFile[valueInRow]) /
                                  maxColor;       //checkForValidRange(Double.parseDouble(rowFile[valueInRow]) /
                    // maxColor);
                    data2[i][j] = Double.parseDouble(rowFile[valueInRow + 1]) /
                                  maxColor;   //checkForValidRange(Double.parseDouble(rowFile[valueInRow + 1]) /
                    // maxColor);
                    data3[i][j] = Double.parseDouble(rowFile[valueInRow + 2]) /
                                  maxColor;   //checkForValidRange(Double.parseDouble(rowFile[valueInRow + 2]) /
                    // maxColor);
                    j++;
                }

            }
            result = new JPEGEncoderImage(height, width, ColorSpace.RGB, data1, data2, data3);
        }
        return result;
    }

    public static void writePPMFile(String outputPath, JPEGEncoderImage JPEGEncoderImage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Write PPM header
            writer.write("P3\n");
            writer.write(JPEGEncoderImage.getHeight() + " " + JPEGEncoderImage.getWidth() + "\n");
            writer.write("255\n"); // Maximum color value

            for (int i = 0; i < JPEGEncoderImage.getData1().length; i++) {
                for (int j = 0; j < JPEGEncoderImage.getData1()[i].length; j++) {
                    //for RGB here
                    int r = (int) Math.round(JPEGEncoderImage.getData1()[i][j] * 255);
                    int g = (int) Math.round(JPEGEncoderImage.getData2()[i][j] * 255);
                    int b = (int) Math.round(JPEGEncoderImage.getData3()[i][j] * 255);
                    //  System.out.println("r,g,b: " + r + "," + g + "," + b );

                    writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JPEGEncoderImage rgbToYCbCr(JPEGEncoderImage JPEGEncoderImage) {
        //todo: Test
        SimpleMatrix transformMatrix = new SimpleMatrix(
                new double[][]{{0.299, 0.587, 0.114}, {-0.1687, -0.3312, 0.5}, {0.5, -0.4186, -0.0813}});
        SimpleMatrix prefixVector = new SimpleMatrix(new double[][]{{0.0}, {0.5}, {0.5}});

        for (int i = 0; i < JPEGEncoderImage.getData1().length; i++) {
            for (int j = 0; j < JPEGEncoderImage.getData1()[i].length; j++) {
                SimpleMatrix rgbVector = new SimpleMatrix(
                        new double[][]{{JPEGEncoderImage.getData1(i, j)}, {JPEGEncoderImage.getData2(i, j)},
                                       {JPEGEncoderImage.getData3(i, j)},});
                rgbVector = prefixVector.plus(transformMatrix.mult(rgbVector));
                JPEGEncoderImage.getData1()[i][j] = rgbVector.get(0, 0);
                JPEGEncoderImage.getData2()[i][j] = rgbVector.get(1, 0);
                JPEGEncoderImage.getData3()[i][j] = rgbVector.get(2, 0);
            }
        }
        JPEGEncoderImage.setColorSpace(ColorSpace.YCbCr);
        return JPEGEncoderImage;
    }
}