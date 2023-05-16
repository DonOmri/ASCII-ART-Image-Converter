package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
class FileImage implements Image {
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private final int width;
    private final int height;
    private final Color[][] pixelArray;

    /**
     * Constructor
     * @param filename the name of the file to pad & create sub-images of
     * @throws IOException in case filename doesn't exist
     */
    public FileImage(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        int origWidth = im.getWidth(), origHeight = im.getHeight();

        width = getClosestPowerOfTwo(origWidth);
        height = getClosestPowerOfTwo(origHeight);
        pixelArray = new Color[height][width];

        padImage(origHeight, origWidth, im);
    }

    @Override
    public int getWidth() { //by pixels
        return width;
    }

    @Override
    public int getHeight() { //by pixels
        return height;
    }

    /**
     * Returns the color of a specified pixel
     * @param x the row of the pixel
     * @param y the column of the pixel
     * @return the color of the pixel
     */
    @Override
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * A function to get a power of 2
     * @param num the number to compare to
     * @return the minimal power of 2 that is bigger or equals to num
     */
    private int getClosestPowerOfTwo(int num){
        int ret = 1;
        while(ret < num){
            ret *= 2;
        }
        return ret;
    }

    /**
     * A function to pad the edges of a given image which the default color.
     * @param origHeight height of the original image
     * @param origWidth width of the original image
     * @param im the original image
     */
    private void padImage(int origHeight, int origWidth, BufferedImage im){
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                //if current pixel (row,col) should be an edge (is one of the pixels required to complete
                //the original resolution size to a power of 2 in either dimension), color it with default
                //color. otherwise, the pixel should be a copy of an original image pixel - color that
                //pixel accordingly.
                if(row < (height - origHeight) / 2 || row >= (height + origHeight) / 2 ||
                        col < (width - origWidth) / 2 || col >= (width + origWidth) / 2){
                    pixelArray[row][col] = DEFAULT_COLOR;
                }
                else{
                    pixelArray[row][col] = new Color(im.getRGB(col - ((width - origWidth) / 2),
                            row - ((height - origHeight) / 2)));
                }
            }
        }
    }

    /**
     * creates sub-images according to user-chosen resolution, from the pixel array.
     * @param size size of each sub-image (the resolution)
     * @return a 2D array of images, each sized size*size
     */
    @Override
    public Image[][] createSubImages(int size){
        int rows = height / size, cols = width / size;
        Image[][] subImages = new PixelImage[rows][cols];

        var iterator = new PixelImage(this,0,0,height,width).subImageIterator(size).iterator();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                subImages[row][col] = iterator.next();
            }
        }
        return subImages;
    }
}
