package imageutil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Program to compare two images");

        System.out.println("Please enter full path (with full file name) for image1: ");
        String image1Path = reader.nextLine().trim();

        System.out.println("Please enter full path (with full file name) for image2: ");
        String image2Path = reader.nextLine().trim();

        System.out.println("Please enter full path (with full file name) for result of comparing: ");
        String imageResultPath = reader.nextLine().trim();

        new ImageComparator(image1Path, image2Path, imageResultPath).drawDifferences();

    }
}
