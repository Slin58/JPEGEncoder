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
import java.util.function.Function;

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
        int height = -1;
        int width = -1;
        if (allLines.get(0).equals("P3")) {
            int commentCounter = 0;
            for (int i = 1; i <= allLines.size() - 1; i++) {
                if (isNumeric(allLines.get(i).charAt(0))) {
                    if (height == -1) {
                        height = Integer.parseInt(allLines.get(i).split("\\s+")[0]);
                        width = Integer.parseInt(allLines.get(i).split("\\s+")[1]);
                    }
                } else {
                    commentCounter++;
                }

            }
            int originalwidth = width;
            int originalheight = height;
            if (width % 16 != 0) {width = 16 - (width % 16) + width;}
            if (height % 16 != 0) {height = 16 - (height % 16) + height;}
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
                        if (i > originalheight) {
                            while (i < height) {
                                data1[i] = data1[originalheight - 1];
                                data2[i] = data2[originalheight - 1];
                                data3[i] = data3[originalheight - 1];
                                i++;
                            }
                            continue;
                        }
                    }
                    if (j < originalwidth) {
                        data1[i][j] = Double.parseDouble(rowFile[valueInRow]);

                        data2[i][j] = Double.parseDouble(rowFile[valueInRow + 1]);

                        data3[i][j] = Double.parseDouble(rowFile[valueInRow + 2]);

                    } else {
                        data1[i][j] = data1[i][originalwidth - 1];

                        data2[i][j] = data2[i][originalwidth - 1];

                        data3[i][j] = data3[i][originalwidth - 1];
                    }
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
                    int r = (int) Math.round(JPEGEncoderImage.getData1()[i][j]);
                    int g = (int) Math.round(JPEGEncoderImage.getData2()[i][j]);
                    int b = (int) Math.round(JPEGEncoderImage.getData3()[i][j]);
                    //  System.out.println("r,g,b: " + r + "," + g + "," + b );

                    writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JPEGEncoderImage rgbToYCbCr(JPEGEncoderImage jPEGEncoderImage) {
        //todo: Test
        SimpleMatrix transformMatrix = new SimpleMatrix(
                new double[][]{{0.299, 0.587, 0.114}, {-0.1687, -0.3312, 0.5}, {0.5, -0.4186, -0.0813}});
        SimpleMatrix prefixVector = new SimpleMatrix(new double[][]{{0.0}, {128}, {128}});
        SimpleMatrix subVector = new SimpleMatrix(new double[][]{{128}, {128}, {128}});

        for (int i = 0; i < jPEGEncoderImage.getData1().length; i++) {
            for (int j = 0; j < jPEGEncoderImage.getData1()[i].length; j++) {
                SimpleMatrix rgbVector = new SimpleMatrix(
                        new double[][]{{jPEGEncoderImage.getData1(i, j)}, {jPEGEncoderImage.getData2(i, j)},
                                       {jPEGEncoderImage.getData3(i, j)},});
                rgbVector = prefixVector.plus(transformMatrix.mult(rgbVector)).minus(subVector);
                jPEGEncoderImage.getData1()[i][j] = rgbVector.get(0, 0);
                jPEGEncoderImage.getData2()[i][j] = rgbVector.get(1, 0);
                double cr = rgbVector.get(2, 0);
                jPEGEncoderImage.getData3()[i][j] = cr;
            }
        }
        jPEGEncoderImage.setColorSpace(ColorSpace.YCbCr);
        return jPEGEncoderImage;
    }

    public static double[][] calculateMethodOnArray(double[][] data, int i1, int j1,
                                                    Function<double[][], double[][]> method) {
        return method.apply(get8x8Array(data, i1, j1));
    }

    public static double[] calculateOnArray(double[][] data, int i1, int j1, Function<double[][], double[]> method) {
        return method.apply(get8x8Array(data, i1, j1));
    }

    public static double[][] get8x8Array(double[][] data, int i1, int j1) {
        return new double[][]{
                {data[i1][j1], data[i1][j1 + 1], data[i1][j1 + 2], data[i1][j1 + 3], data[i1][j1 + 4], data[i1][j1 + 5],
                 data[i1][j1 + 6], data[i1][j1 + 7]},
                {data[i1 + 1][j1], data[i1 + 1][j1 + 1], data[i1 + 1][j1 + 2], data[i1 + 1][j1 + 3],
                 data[i1 + 1][j1 + 4], data[i1 + 1][j1 + 5], data[i1 + 1][j1 + 6], data[i1 + 1][j1 + 7]},
                {data[i1 + 2][j1], data[i1 + 2][j1 + 1], data[i1 + 2][j1 + 2], data[i1 + 2][j1 + 3],
                 data[i1 + 2][j1 + 4], data[i1 + 2][j1 + 5], data[i1 + 2][j1 + 6], data[i1 + 2][j1 + 7]},
                {data[i1 + 3][j1], data[i1 + 3][j1 + 1], data[i1 + 3][j1 + 2], data[i1 + 3][j1 + 3],
                 data[i1 + 3][j1 + 4], data[i1 + 3][j1 + 5], data[i1 + 3][j1 + 6], data[i1 + 3][j1 + 7]},
                {data[i1 + 4][j1], data[i1 + 4][j1 + 1], data[i1 + 4][j1 + 2], data[i1 + 4][j1 + 3],
                 data[i1 + 4][j1 + 4], data[i1 + 4][j1 + 5], data[i1 + 4][j1 + 6], data[i1 + 4][j1 + 7]},
                {data[i1 + 5][j1], data[i1 + 5][j1 + 1], data[i1 + 5][j1 + 2], data[i1 + 5][j1 + 3],
                 data[i1 + 5][j1 + 4], data[i1 + 5][j1 + 5], data[i1 + 5][j1 + 6], data[i1 + 5][j1 + 7]},
                {data[i1 + 6][j1], data[i1 + 6][j1 + 1], data[i1 + 6][j1 + 2], data[i1 + 6][j1 + 3],
                 data[i1 + 6][j1 + 4], data[i1 + 6][j1 + 5], data[i1 + 6][j1 + 6], data[i1 + 6][j1 + 7]},
                {data[i1 + 7][j1], data[i1 + 7][j1 + 1], data[i1 + 7][j1 + 2], data[i1 + 7][j1 + 3],
                 data[i1 + 7][j1 + 4], data[i1 + 7][j1 + 5], data[i1 + 7][j1 + 6], data[i1 + 7][j1 + 7]}};
    }

    public static void calculateOnArraysWithoutModification(double[][] data, Function<double[][], double[][]> method) {
        for (int i = 0; i < data.length; i += 8) {
            for (int j = 0; j < data[i].length; j += 8) {
                calculateMethodOnArray(data, i, j, method);
            }
        }
    }

    public static double[] getValuesInZigzag(double[][] values) {
        return new double[]{values[0][0], values[0][1], values[1][0], values[2][0], values[1][1], values[0][2],
                            values[0][3], values[1][2], values[2][1], values[3][0], values[4][0], values[3][1],
                            values[2][2], values[1][3], values[0][4], values[0][5], values[1][4], values[2][3],
                            values[3][2], values[4][1], values[5][0], values[6][0], values[5][1], values[4][2],
                            values[3][3], values[2][4], values[1][5], values[0][6], values[0][7], values[1][6],
                            values[2][5], values[3][4], values[4][3], values[5][2], values[6][1], values[7][0],
                            values[7][1], values[6][2], values[5][3], values[4][4], values[3][5], values[2][6],
                            values[1][7], values[2][7], values[3][6], values[4][5], values[5][4], values[6][3],
                            values[7][2], values[7][3], values[6][4], values[5][5], values[4][6], values[3][7],
                            values[4][7], values[5][6], values[6][5], values[7][4], values[7][5], values[6][6],
                            values[5][7], values[6][7], values[7][6], values[7][7]};
    }

    public static int[] getValuesInZigzag(int[][] values) {
        return new int[]{values[0][0], values[0][1], values[1][0], values[2][0], values[1][1], values[0][2],
                         values[0][3], values[1][2], values[2][1], values[3][0], values[4][0], values[3][1],
                         values[2][2], values[1][3], values[0][4], values[0][5], values[1][4], values[2][3],
                         values[3][2], values[4][1], values[5][0], values[6][0], values[5][1], values[4][2],
                         values[3][3], values[2][4], values[1][5], values[0][6], values[0][7], values[1][6],
                         values[2][5], values[3][4], values[4][3], values[5][2], values[6][1], values[7][0],
                         values[7][1], values[6][2], values[5][3], values[4][4], values[3][5], values[2][6],
                         values[1][7], values[2][7], values[3][6], values[4][5], values[5][4], values[6][3],
                         values[7][2], values[7][3], values[6][4], values[5][5], values[4][6], values[3][7],
                         values[4][7], values[5][6], values[6][5], values[7][4], values[7][5], values[6][6],
                         values[5][7], values[6][7], values[7][6], values[7][7]};
    }
}
