import org.ejml.simple.SimpleMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Utils {
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
        System.out.println(allLines);
        if (allLines.get(0).equals("P3")) {
            String[] imageSize = allLines.get(1).split("\\s+");
            int maxColor = Integer.parseInt(allLines.get(2));
            List<List<Integer>> data1 = new ArrayList<>();
            List<List<Integer>> data2 = new ArrayList<>();
            List<List<Integer>> data3 = new ArrayList<>();

            for (int i = 3; i <= allLines.size() - 1; i++) {
                String[] row = allLines.get(i).trim().split("\\s+");
                List<Integer> row1 = new ArrayList<>();
                List<Integer> row2 = new ArrayList<>();
                List<Integer> row3 = new ArrayList<>();

                for (int j = 0; j <= row.length - 1; j += 3) {
                    row1.add(Integer.parseInt(row[j])/maxColor);
                    row2.add(Integer.parseInt(row[j + 1])/maxColor);
                    row3.add(Integer.parseInt(row[j + 2])/maxColor);
                }
                data1.add(row1);
                data2.add(row2);
                data3.add(row3);
            }
            result = new Image(Integer.parseInt(imageSize[0]), Integer.parseInt(imageSize[1]), data1, data2, data3);
        }
        //todo refactoring in methoden auslagern
        return result;
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
                image.data1.get(i).set(j, (int) Math.round(rgbVector.get(0, 0)));
                image.data2.get(i).set(j, (int) Math.round(rgbVector.get(1, 0)));
                image.data3.get(i).set(j, (int) Math.round(rgbVector.get(2, 0)));
            }
        }
        image.colorSpace = ColorSpace.YCbCr;


        System.out.println("data1: " + image.data1);
        System.out.println("data2: " + image.data2);
        System.out.println("data3: " + image.data3);

        return image;
    }

    public static void writePPMFile(String outputPath, Image image) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Write PPM header
            writer.write("P3\n");
            writer.write(image.width + " " + image.height + "\n");
            writer.write("255\n"); // Maximum color value

            // Write image data
            for (int i = 0; i < image.data1.size(); i++) {
                for (int j = 0; j < image.data1.get(i).size(); j++) {
                  /*  int r = image.getData1(i, j);
                    int g = image.getData2(i, j);
                    int b = image.getData3(i, j);
                        */

                    int r = image.data1.get(i).get(j);
                    int g = image.data2.get(i).get(j);
                    int b = image.data3.get(i).get(j);



                  //  System.out.println("r,g,b: " + r + "," + g + "," + b );

                    writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

