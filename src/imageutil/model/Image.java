package imageutil.model;

import java.util.Arrays;

public class Image {

    private double [][] rgbPixels;

    private int width;

    private int height;


    public Image(double[][] rgbPixels, int width, int height) {
        this.rgbPixels = rgbPixels;
        this.width = width;
        this.height = height;
    }

    public void setRgbPixels(double[][] rgbPixels) {
        this.rgbPixels = rgbPixels;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double[][] getRgbPixels() {
        return rgbPixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Arrays.deepHashCode(this.rgbPixels);
        hash = 83 * hash + this.width;
        hash = 83 * hash + this.height;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        boolean result =false;
        if (obj == null) {
            result = false;
        }
        if (getClass() != obj.getClass()) {
            result =  false;
        }
        final Image other = (Image) obj;

        if ((other.height == this.height) && (other.width == this.width) ) {
            int count = 0;
            for (int i = 0; i < other.width; i++) {
                for (int j = 0; j < other.height; j++) {
                    if (other.rgbPixels[i][j] != other.rgbPixels[i][j]) {
                        count ++;
                    }
                }
            }

            if(count == 0) {
                result =  true;
            }

            else {
                result =  false;
            }
        }

        return result;

    }

}
