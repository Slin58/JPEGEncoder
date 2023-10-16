import org.ejml.simple.SimpleMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class Utils {

    public static double checkForValidRange(double number) {
        return min(max(number, 0.0), 1.0);
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
            String[] imageSize = allLines.get(1).split("\\s+");
            int maxColor = Integer.parseInt(allLines.get(2));
            List<List<Double>> data1 = new ArrayList<>();
            List<List<Double>> data2 = new ArrayList<>();
            List<List<Double>> data3 = new ArrayList<>();

            for (int i = 3; i <= allLines.size() - 1; i++) {
                String[] row = allLines.get(i).trim().split("\\s+");
                List<Double> row1 = new ArrayList<>();
                List<Double> row2 = new ArrayList<>();
                List<Double> row3 = new ArrayList<>();

                for (int j = 0; j <= row.length - 1; j += 3) {
                    row1.add(checkForValidRange(Double.parseDouble(row[j]) / maxColor));
                    row2.add(checkForValidRange(Double.parseDouble(row[j + 1]) / maxColor));
                    row3.add(checkForValidRange(Double.parseDouble(row[j + 2]) / maxColor));
                }
                data1.add(row1);
                data2.add(row2);
                data3.add(row3);
            }
            result = new Image(Integer.parseInt(imageSize[0]), Integer.parseInt(imageSize[1]), data1, data2, data3, ColorSpace.RGB);
        }
        return result;
    }


    public static void writePPMFile(String outputPath, Image image) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Write PPM header
            writer.write("P3\n");
            writer.write(image.height + " " + image.width + "\n");
            writer.write("255\n"); // Maximum color value

            // Write image data
            for (int i = 0; i < image.data1.size(); i++) {
                for (int j = 0; j < image.data1.get(i).size(); j++) {
                  /*  int r = image.getData1(i, j);
                    int g = image.getData2(i, j);
                    int b = image.getData3(i, j);
                        */

                    int r = (int) Math.round(image.data1.get(i).get(j) * 255);
                    int g = (int) Math.round(image.data2.get(i).get(j) * 255);
                    int b = (int) Math.round(image.data3.get(i).get(j) * 255);



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
        SimpleMatrix vector = new SimpleMatrix(new double[][] { //todo: better name
                {0.0},
                {0.5},
                {0.5}
        });

        for(int i = 0; i < image.data1.size(); i++) {
            for(int j = 0; j < image.data1.get(i).size(); j++) {
               // System.out.println("i;j: " + i + ";" + j);
               // System.out.println("data1: " + image.data1);
                SimpleMatrix rgbVector = new SimpleMatrix(new double[][] {
                       /* {image.data1.get(i).get(j)},
                        {image.data2.get(i).get(j)},
                        {image.data2.get(i).get(j)},    */
                        {image.getData1(i,j)},
                        {image.getData2(i,j)},
                        {image.getData3(i,j)},
                });

                System.out.println("rgbVectOld: " + rgbVector);

                rgbVector = vector.plus(transformMatrix.mult(rgbVector));

                System.out.println("transformMatrix: " + transformMatrix);
                System.out.println("vector: " + vector);


                System.out.println("rgbVectNew: " + rgbVector);
                image.data1.get(i).set(j, rgbVector.get(0, 0));
                image.data2.get(i).set(j, rgbVector.get(1, 0));
                image.data3.get(i).set(j, rgbVector.get(2, 0));
            }
        }
        image.colorSpace = ColorSpace.YCbCr;


        System.out.println("data1: " + image.data1);
        System.out.println("data2: " + image.data2);
        System.out.println("data3: " + image.data3);

        return image;
    }

}
