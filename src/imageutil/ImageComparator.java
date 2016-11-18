package imageutil;

import imageutil.model.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageComparator {

    private String image1Path;
    private String image2Path;
    private String imageResultPath;

    public ImageComparator(String image1Path, String image2Path, String imageResultPath) {
        this.image1Path = image1Path;
        this.image2Path = image2Path;
        this.imageResultPath = imageResultPath;
    }

    private int widthImage1;
    private int heightImage1;

    private int widthImage2;
    private int heightImage2;

    private BufferedImage bufferedImageResult;

    private boolean[][] calculateImageComparisonMatrix(Image image1, Image image2) { // calculate comparison matrix
        boolean[][] comparisonResult = new boolean[widthImage1][heightImage1];
        System.out.println("width of Image1 = " + widthImage1);
        System.out.println("height of Image1 = " + heightImage1);

        System.out.println("width of Image2 = " + widthImage2);
        System.out.println("height of Image2 = " + heightImage2);

        if ((widthImage1 != widthImage2) || (heightImage1 != heightImage2)) {
            for (int i = 0; i < widthImage1; i++) {
                for (int j = 0; j < heightImage1; j++) {
                    comparisonResult[i][j] = false;
                }
            }

        } else {
            double[][] pixelsImage1 = image1.getRgbPixels();
            double[][] pixelsImage2 = image2.getRgbPixels();

            int countOfMismatchPixels = 0;

            for (int i = 0; i < widthImage1; i++) {
                for (int j = 0; j < heightImage1; j++) {

                    // We should only mark pixel as "different" if difference between them is more than 10%
                    if(100.0*(Math.abs(pixelsImage1[i][j] - pixelsImage2[i][j])/255.0) > 10) {
                        comparisonResult[i][j] = false;
                        countOfMismatchPixels++;
                    } else {
                        comparisonResult[i][j] = true;
                    }
                }
            }
            System.out.println("count of mismatch pixels = " + countOfMismatchPixels);
            if (countOfMismatchPixels == 0) {
                System.out.println("Result: two images are equals.");
            } else {

                System.out.println("Result: two images are not equals.");
            }

        }

        return comparisonResult;
    }

    private Image readImage(String imagePath) {
        BufferedImage bufferedImage = null;
        Image image = null;
        try {

            bufferedImage = ImageIO.read(new File(imagePath));
            if(imagePath.equals(image2Path)) {
                bufferedImageResult = bufferedImage;
            }

            int widthImage = bufferedImage.getWidth();
            int  heightImage = bufferedImage.getHeight();

            double[][] pixelsImage = new double[widthImage][heightImage];

            for (int i = 0; i < widthImage; i++) {
                for (int j = 0; j < heightImage; j++) {
                    pixelsImage[i][j] = bufferedImage.getRGB(i, j);
                }
            }

            image = new Image(pixelsImage, widthImage, heightImage);
        } catch (IOException e) {
            System.out.println("Issue with opening " + imagePath);
        }
        return image;
    }

    private void writeImage(String imageResultFileName, BufferedImage bufferedImageResult) {
        try {
            File outputFile = new File(imageResultFileName); // create output file with result of comparison
            ImageIO.write(bufferedImageResult, "png", outputFile);
            System.out.println("Please, see differences in red rectangles in file " + imageResultFileName);
        } catch (IOException e) {
            System.out.println("Issue with creating " + imageResultFileName);

        }
    }

    public boolean drawDifferences () { // draw differences in red rectangles on the image

        Image image1 = readImage(image1Path);
        Image image2 = readImage(image2Path);

        boolean isImagesEqual = true;

        if ((image1 != null) && (image2 != null)) {
            widthImage1 = image1.getWidth();
            heightImage1 = image1.getHeight();

            widthImage2 = image2.getWidth();
            heightImage2 = image2.getHeight();

            boolean[][] comparisonResult = calculateImageComparisonMatrix(image1, image2);

            List<Integer> iArrayMismatch = new ArrayList<>();
            List<Integer> jArrayMismatch = new ArrayList<>();

            for (int i = 0; i < widthImage1; i++) {
                for (int j = 0; j < heightImage1; j++) {

                    if (comparisonResult[i][j] == false) {

                        iArrayMismatch.add(i);
                        jArrayMismatch.add(j);

                        isImagesEqual = false;

                    }
                }

            }
            List<Integer> widthRectanglesList = new ArrayList<>();
            List<Integer> heightRectanglesList = new ArrayList<>();

            List<Integer> xCoordinateList = new ArrayList<>();
            List<Integer> yCoordinateList = new ArrayList<>();

            int xMin = widthImage1 + 1;
            int yMin = heightImage1 + 1;

            int widthRectangleMax = 0;
            int widthRectangleMin = widthImage1 + 1;

            int heightRectangleMax = 0;
            int heightRectangleMin = heightImage1 + 1;

            int heightRectangle = 0;
            int widthRectangle = 0;


            for (int i = 0; i < iArrayMismatch.size() - 1; i++) {
                // We should only mark pixel as "different" if difference between them is more than 10%
                if ((100.0 * Math.abs(iArrayMismatch.get(i) - iArrayMismatch.get(i + 1))) / 255.0 <= 10.0) {

                    if (iArrayMismatch.get(i) < xMin) {
                        xMin = iArrayMismatch.get(i);
                    }

                    if (jArrayMismatch.get(i) < yMin) {
                        yMin = jArrayMismatch.get(i);
                    }


                    if (iArrayMismatch.get(i) > widthRectangleMax) {
                        widthRectangleMax = iArrayMismatch.get(i);
                    }

                    if (iArrayMismatch.get(i) < widthRectangleMin) {
                        widthRectangleMin = iArrayMismatch.get(i);
                    }

                    if (jArrayMismatch.get(i) > heightRectangleMax) {
                        heightRectangleMax = jArrayMismatch.get(i);
                    }

                    if (jArrayMismatch.get(i) < heightRectangleMin) {
                        heightRectangleMin = jArrayMismatch.get(i);
                    }
               } else {
                    if ((xMin != widthImage1 + 1) && (yMin != heightImage1 + 1)) { // if we have point of rectangle
                        xCoordinateList.add(xMin);
                        yCoordinateList.add(yMin);
                    }
                    xMin = widthImage1 + 1;
                    yMin = heightImage1 + 1;

                    if ((widthRectangleMin != widthImage1 + 1 && (widthRectangleMax != 0))) { // if we can calculate width of rectangle
                        widthRectangle = Math.abs(widthRectangleMax - widthRectangleMin) + 1;
                        widthRectanglesList.add(widthRectangle);
                    }
                    widthRectangle = 0;
                    widthRectangleMin = widthImage1 + 1;
                    widthRectangleMax = 0;

                    if ((heightRectangleMin != heightImage1 + 1) && (heightRectangleMax != 0)) { // if we can calculate height of rectangle
                        heightRectangle = Math.abs(heightRectangleMax - heightRectangleMin) + 1;
                        heightRectanglesList.add(heightRectangle);
                    }
                    heightRectangle = 0;
                    heightRectangleMin = heightImage1 + 1;
                    heightRectangleMax = 0;
               }

            }

            if ((xMin != widthImage1 + 1) && (yMin != heightImage1 + 1)) { // if we have point of rectangle
                xCoordinateList.add(xMin);
                yCoordinateList.add(yMin);
            }

            if ((widthRectangleMin != widthImage1 + 1 && (widthRectangleMax != 0))) { // if we can calculate width of rectangle
                widthRectangle = Math.abs(widthRectangleMax - widthRectangleMin) + 1;
                widthRectanglesList.add(widthRectangle);
            }

            if ((heightRectangleMin != heightImage1 + 1) && (heightRectangleMax != 0)) { // if we can calculate height of rectangle
                heightRectangle = Math.abs(heightRectangleMax - heightRectangleMin) + 1;
                heightRectanglesList.add(heightRectangle);
            }

            Graphics2D graphics = bufferedImageResult.createGraphics();
            graphics.setColor(Color.RED);

            for (int index = 0; index < xCoordinateList.size(); index++) {
                // draw red rectangles of mismatch images
                graphics.drawRect(xCoordinateList.get(index)-1, yCoordinateList.get(index)-1,
                        widthRectanglesList.get(index)+1, heightRectanglesList.get(index)+1);
            }

            writeImage(imageResultPath, bufferedImageResult);



        }

        return isImagesEqual;

    }


}
