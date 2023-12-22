package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String stringForCircle = "";
        String stringForPointers = "";
        try {
            Scanner scanner = new Scanner(System.in);
            String pathForFile1 = scanner.nextLine();
            String pathForFile2 = scanner.nextLine();
            scanner.close();
            stringForCircle = new String(Files.readAllBytes(Paths.get(pathForFile1)), StandardCharsets.UTF_8);
            stringForPointers = new String(Files.readAllBytes(Paths.get(pathForFile2)), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        double[][] ArrayForCircle = createArrayFromCoordinates(stringForCircle);
        double x_circle = ArrayForCircle[0][0];
        double y_circle = ArrayForCircle[0][1];
        double radius_circle = ArrayForCircle[1][0];
        double[][] ArrayForPointers = createArrayFromCoordinates(stringForPointers);
        //    S:\My_projects\ТЗ\task2\File1.txt
        //    S:\My_projects\ТЗ\task2\File2.txt    - для тестирования

        for (int i = 0; i < ArrayForPointers.length; i++) {
            for (int j = 0; j < ArrayForPointers[i].length; j++) {
                System.out.println(i + " - " + calculation_point_as_part_of_circle(x_circle, y_circle, radius_circle, ArrayForPointers[i][j], ArrayForPointers[i][j + 1]));
                j++;
            }
        }
    }

    private static String calculation_point_as_part_of_circle(double x_circle, double y_circle, double radius_circle, double pointCoordinates_X, double pointCoordinates_Y) {
        if (Math.pow((pointCoordinates_X - x_circle), 2) + Math.pow((pointCoordinates_Y - y_circle), 2) < Math.pow(radius_circle, 2)) {
            return "точка внутри";
        } else if (Math.pow((pointCoordinates_X - x_circle), 2) + Math.pow((pointCoordinates_Y - y_circle), 2) == Math.pow(radius_circle, 2)) {
            return "точка лежит на окружности";
        } else return "точка снаружи";
    }

    private static double[][] createArrayFromCoordinates(String stringForPointersOrCircle) {
        double[][] intArrayForPointersOrCircle = Arrays.stream(stringForPointersOrCircle.split("\r\n"))
                .map((str) -> str.replace(" ", ","))
                .map((str) -> str.split(","))
                .map((str) -> Arrays.stream(str).mapToDouble((val) -> Double.valueOf(val.trim())).toArray()) //преобразовываем значения каждого полученного массива в double[]
                .toArray(double[][]::new);
        return intArrayForPointersOrCircle;
    }
}