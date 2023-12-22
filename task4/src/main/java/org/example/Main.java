package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
// S:\My_projects\task4\src\main\resources\File1.txt - для теста
        String stringForArray = "";
        try {
            Scanner scanner = new Scanner(System.in);
            String pathForFile1 = scanner.nextLine();
            scanner.close();
            stringForArray = new String(Files.readAllBytes(Paths.get(pathForFile1)), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        int[] intArray = Arrays.stream(stringForArray.split("\r\n"))
                .mapToInt((val) -> Integer.valueOf(val.trim())).toArray();

        double average = Math.round(IntStream.of(intArray).average().getAsDouble());
        int count = 0;
        int i = 0;
        do {
            if (intArray[i] > average) {
                intArray[i]--;
                count++;
            } else if (intArray[i] < average) {
                intArray[i]++;
                count++;
            } else {
                i++;
            }
        } while (i < intArray.length);

        System.out.println(count);
    }
}