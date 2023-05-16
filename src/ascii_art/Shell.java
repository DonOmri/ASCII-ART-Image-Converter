package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Shell {
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int MIN_ASCII_VAL_ALLOWED = 32;
    private static final int MAX_ASCII_VAL_ALLOWED = 126;
    private static final int MAX_WORDS = 2;
    private static final int MIN_WORDS = 0;
    private static  final int RESIZE_FACTOR = 2;
    private static final int CORRECT_SHORT_INPUT_LENGTH = 1;
    private static final int CORRECT_LONG_INPUT_LENGTH = 2;
    private static final int FIRST_CHAR_IN_RANGE = 0;
    private static final int MIDDLE_CHAR_IN_RANGE = 1;
    private static final int LAST_CHAR_IN_RANGE = 2;
    private static final char ASCII_RANGE_SEPARATOR = '-';
    private static final String FONT = "Courier New";
    private static final String HTML_FILE = "out.html";
    private static final Character[] INIT_CHARSET_VALUES = {'0','1','2','3','4','5','6','7','8','9'};
    private static final String INCORRECT_FORMAT_ERR_ADD = "Did not add due to incorrect format";
    private static final String INCORRECT_FORMAT_ERR_REMOVE = "Did not remove due to incorrect format";
    private static final String INCORRECT_COMMAND_ERR = "Did not executed due to incorrect command";
    private static final String RESOLUTION_LIMIT_ERR = "Did not change due to exceeding boundaries";
    private static final String RESOLUTION_CHANGED_MSG = "Width set to ";
    private static final String CHARS_REMOVED = "All characters were removed!";
    private static final String AGAIN_CODE = "again";
    private static final String EXIT_CODE = "exit";
    private static final String NEW_LINE_PROMPT = ">>>";
    private final HashSet<Character> charSet = new HashSet<>();
    private int charsInRow;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private final Image image;
    private boolean isOutputConsole = false;

    /**
     * Constructor
     * @param image an image file to create the shell around
     */
    public Shell(Image image){
        charSet.addAll(Arrays.asList(INIT_CHARSET_VALUES));
        minCharsInRow = Math.max(1, image.getWidth()/image.getHeight());
        maxCharsInRow = image.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        this.image = image;
    }

    /**
     * runs the process of getting user input, validating it and operating corresponding commands
     */
    public void run(){
        Scanner userInput = new Scanner(System.in);

        while(true){
            System.out.print(NEW_LINE_PROMPT);
            if(commandFactory(userInput.nextLine().split(" ")).equals(EXIT_CODE)) return;
        }
    }

    /**
     * A factory to determine which command to run, based on user input.
     * @param inputParts split user input to test
     */
    private String commandFactory(String[] inputParts){
        if(inputParts.length > MAX_WORDS || inputParts.length == MIN_WORDS) inputParts[0] = AGAIN_CODE;

        switch(inputParts[0]){
            case "exit":
                return EXIT_CODE;
            case "chars":
                printAllChars(inputParts);
                break;
            case "add":
                changeChars(inputParts, HashSet::add, INCORRECT_FORMAT_ERR_ADD);
                break;
            case "remove":
                changeChars(inputParts, HashSet::remove, INCORRECT_FORMAT_ERR_REMOVE);
                break;
            case "res":
                changeResolution(inputParts);
                break;
            case "console":
                isOutputConsole = true;
                break;
            case "render":
                renderImage();
                break;
            default:
                System.out.println(INCORRECT_COMMAND_ERR);
                break;
        }
        return AGAIN_CODE;
    }

    /**
     * Prints all added chars
     * @param inputParts user input (is verified by this function)
     */
    private void printAllChars(String[] inputParts){
        if(inputParts.length != CORRECT_SHORT_INPUT_LENGTH) System.out.println(INCORRECT_COMMAND_ERR);
        else if(charSet.size() == 0) System.out.println(CHARS_REMOVED);
        else {
            for(char c : charSet){
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private void changeChars(String[] inputParts, BiConsumer<HashSet<Character>, Character> changeFunc,
                             String errorMessage){
        if(inputParts.length != CORRECT_LONG_INPUT_LENGTH) {
            System.out.println(errorMessage);
            return;
        }

        String input = inputParts[1];

        if(input.length() == 1){ //change one char
            changeFunc.accept(charSet,input.charAt(FIRST_CHAR_IN_RANGE));
        }
        else if(input.equals("all")){ //change all chars
            changeCharsInRange(MIN_ASCII_VAL_ALLOWED, MAX_ASCII_VAL_ALLOWED,changeFunc);
        }
        else if(input.equals("space")){ //change the ' ' (space) char
            changeFunc.accept(charSet, ' ');
        }
        else if(input.length() == 3 && input.charAt(MIDDLE_CHAR_IN_RANGE) == ASCII_RANGE_SEPARATOR){
            changeCharsInRange(Math.min(input.charAt(FIRST_CHAR_IN_RANGE),input.charAt(LAST_CHAR_IN_RANGE)),
                    Math.max(input.charAt(FIRST_CHAR_IN_RANGE),input.charAt(LAST_CHAR_IN_RANGE)),changeFunc);
        }
        else{
            System.out.println(errorMessage);
        }
    }

    /**
     * Adds all chars within a range to charSet
     * @param min first char to add (inclusive)
     * @param max last char to add (inclusive)
     */
    private void changeCharsInRange(int min, int max, BiConsumer<HashSet<Character>, Character> changeFunc){
        for(int i = min; i <= max; ++i){
            changeFunc.accept(charSet,(char) i);
        }
    }

    /**
     * Changes the resolution of the image, based on user input
     * @param inputParts user input to base on
     */
    private void changeResolution(String[] inputParts){
        if(inputParts.length != CORRECT_LONG_INPUT_LENGTH) System.out.println(INCORRECT_COMMAND_ERR);
        else if(inputParts[1].equals("up")){
            if(charsInRow == maxCharsInRow) System.out.println(RESOLUTION_LIMIT_ERR);
            else{
                charsInRow *= RESIZE_FACTOR;
                System.out.println(RESOLUTION_CHANGED_MSG + charsInRow);
            }
        }
        else if (inputParts[1].equals("down")){
            if(charsInRow == minCharsInRow) System.out.println(RESOLUTION_LIMIT_ERR);
            else{
                charsInRow /= RESIZE_FACTOR;
                System.out.println(RESOLUTION_CHANGED_MSG + charsInRow);
            }
        }
        else System.out.println(INCORRECT_COMMAND_ERR);
    }

    /**
     * Renders the image on either html file (default) or console
     */
    private void renderImage(){
        char[][] imageAsAscii = new BrightnessImgCharMatcher(image,FONT).chooseChars
                (charsInRow,charSet.toArray(new Character[0]));
        if(isOutputConsole){
            new ConsoleAsciiOutput().output(imageAsAscii);
            isOutputConsole = false;
        }
        else{
            new HtmlAsciiOutput(HTML_FILE,FONT).output(imageAsAscii);
        }
    }
}

