package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.*;

public class BrightnessImgCharMatcher {
    private static final int CHAR_RESOLUTION = 16;
    private static final int MAX_RGB = 255;
    private static final float BALANCE_RED = (float) 0.2126;
    private static final float BALANCE_GREEN = (float) 0.7152;
    private static final float BALANCE_BLUE = (float) 0.0722;
    private float minBrightness = 1;
    private float maxBrightness = 0;
    private final Image image;
    private final String fontName;
    private final HashMap<Character,Float> actualCharsBrightnessMap = new HashMap<>();
    //the map above represents the current chars that will be used to render the image
    private final HashMap<Character,Float> computedCharsBrightnessMap = new HashMap<>();
    //the map above represents any char that was ever used to render the image (hence its brightness was
    // calculated)

    /**
     * Constructor
     * @param image the given image to transform into ascii
     * @param font the font in which to present the ascii
     */
    public BrightnessImgCharMatcher(Image image, String font){
        this.image  = image;
        this.fontName = font;
    }

    /**
     * A function that replaces amount of pixels with ascii chars
     * @param numCharsInRow number of ascii chars in a row, helps determine resolution and pixels per char
     * @param charSet all the characters available to transform into
     * @return a 2D matrix with the ascii chars
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet){
        char[][] charsImage = new char[image.getHeight() / (image.getWidth()/numCharsInRow)][numCharsInRow];

        updateCorrectChars(charSet);
        Image[][] subImages = image.createSubImages(image.getWidth()/numCharsInRow);

        for (int row = 0; row < subImages.length; row++) {
            for (int col = 0; col < subImages[row].length; col++) {
                float subImageBrightness = computeSubImageBrightness(subImages[row][col]);
                charsImage[row][col] = replaceSubImageWithChar(subImageBrightness);
            }
        }
        return charsImage;
    }

    /**
     * Checks chars given by user input and:
     * 1. if char was never used to render image, compute its brightness and insert it to the map of chars
     * currently used to render the image
     * 2. if char was used to render image, but not the last one, its brightness computed at some time. get
     * its brightness (saved in a hash map) and insert it to the map of chars currently used to render the
     * image
     * 3. if the char was used in the last render, do nothing (its already in the relevant hash map)
     * @param charArray array of chars to render the image from.
     */
    private void updateCorrectChars(Character[] charArray){
        HashSet<Character> charSet = new HashSet<>(Arrays.asList(charArray)); //faster to find items in a set
        //if user didn't send a specific char, but that char is found in the map of the chars to render,
        // remove that char from the map
        for(Character c : actualCharsBrightnessMap.keySet()){
            if(!charSet.contains(c)) actualCharsBrightnessMap.remove(c);
        }
        Character[] newChars = new Character[charArray.length]; //the new chars to check their brightness
        for(int i = 0; i< charArray.length; ++i){
            if(!computedCharsBrightnessMap.containsKey(charArray[i])) newChars[i] = charArray[i];
            else if (!actualCharsBrightnessMap.containsKey(charArray[i])) {
                actualCharsBrightnessMap.put(charArray[i], computedCharsBrightnessMap.get(charArray[i]));
            }
        }

        //compute the brightness of all new chars, and add them to the actual chars map
        getCharsBrightness(newChars);
        for(Character c : newChars) {
            actualCharsBrightnessMap.put(c, computedCharsBrightnessMap.get(c));
        }
    }

    /**
     * Computes (and normalizes according to all results) brightness of each char in a given array of chars
     * @param chars an array of chars to compute brightness to each
     */
    private void getCharsBrightness(Character[] chars){
        for (Character c : chars) {
            float charBrightness = computeCharBrightness(c);

            maxBrightness = Math.max(maxBrightness, charBrightness);
            minBrightness = Math.min(minBrightness, charBrightness);

            computedCharsBrightnessMap.put(c, charBrightness);
        }

        //normalize according to min and max brightness
        for (Character c : chars) {
            float finalCharBrightness = (computedCharsBrightnessMap.get(c) - minBrightness) /
                    (maxBrightness - minBrightness);
            computedCharsBrightnessMap.put(c,finalCharBrightness);
        }
    }

    /**
     * A helper function to compute the basic normalized brightness value of a char (between 0-1)
     * @param c the char to compute
     * @return a value between 0-1, describes the total brightness of the character
     */
    private float computeCharBrightness(char c){
        boolean[][] charBrightnessMatrix = CharRenderer.getImg(c, CHAR_RESOLUTION, fontName);
        float whiteCounter = 0;

        for (int row = 0; row < charBrightnessMatrix.length; row++) {
            for (int col = 0; col < charBrightnessMatrix[row].length; col++) {
                if (charBrightnessMatrix[row][col]) ++whiteCounter;
            }
        }

        return whiteCounter / (CHAR_RESOLUTION * CHAR_RESOLUTION);
    }

    /**
     * Computes the average brightness of a given sub-image
     * @param image the image itself, represented by a 2D Color matrix
     * @return the average brightness of a pixel of the sub image
     */
    private float computeSubImageBrightness(Image image){
        float greySum = 0;

        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                Color pixel = image.getPixel(row, col);
                greySum += (pixel.getRed() * BALANCE_RED +
                            pixel.getGreen() * BALANCE_GREEN +
                            pixel.getBlue() * BALANCE_BLUE);
            }
        }
        return (greySum / (image.getHeight() * image.getWidth())) / MAX_RGB; //normalized brightness
    }

    /**
     * Replaces a sub image with a char, that is chosen by the closest brightness
     * @param subImageBrightness the brightness of the sub-image to replace
     * @return the replaced char
     */
    private char replaceSubImageWithChar(float subImageBrightness){
        float minDifference = 1; //placeholder
        char minChar = ' '; //placeholder

        for (HashMap.Entry<Character,Float> tuple : actualCharsBrightnessMap.entrySet()) {
            float diffUnderCheck = Math.abs(subImageBrightness - tuple.getValue());
            if(diffUnderCheck < minDifference){
                minDifference = diffUnderCheck;
                minChar = tuple.getKey();
            }
        }
        return minChar;
    }
}
