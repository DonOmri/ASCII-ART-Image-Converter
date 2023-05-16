package image;

import java.awt.*;

/**
 * A class that represents an image that is created from other existing image
 */
public class PixelImage implements Image {
    private final Color[][] pixelPalette;
    private final int height;
    private final int width;

    /**
     * Constructor
     * @param im the original image object
     * @param upperRow the row from where to start taking pixels
     * @param leftCol the col from where to start taking pixels
     * @param height height of this image
     * @param width width of this image
     */
    public PixelImage(Image im, int upperRow, int leftCol, int height, int width){
        this.height = height;
        this.width = width;
        pixelPalette = createPixelPalette(im, upperRow, leftCol);
    }

    /**
     * creates the actual image data
     * @param im the image from where to get the pixels
     * @param upperRow the row from where to start taking pixels
     * @param leftCol the col from where to start taking pixels
     * @return a 2D Color array, contains the relevant pixels from the given image
     */
    private Color[][] createPixelPalette(Image im, int upperRow, int leftCol){
        Color[][] ret = new Color[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                ret[row][col] = im.getPixel(upperRow+row, leftCol+col);
            }
        }
        return ret;
    }

    @Override
    public Color getPixel(int x, int y) {
        return pixelPalette[x][y];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
