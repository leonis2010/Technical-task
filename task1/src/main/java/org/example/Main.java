package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.valueOf(sc.nextLine());
        int m = Integer.valueOf(sc.nextLine());
        sc.close();

        int actual = 1;
        do {
            System.out.print(actual);
            actual = (actual + m - 2) % n + 1;
        } while (actual != 1);
    }
}