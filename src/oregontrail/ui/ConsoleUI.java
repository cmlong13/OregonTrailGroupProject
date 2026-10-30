package oregontrail.ui;

import java.util.Scanner;

/**
 * Every line the player sees and every key they type goes through this class,
 * so input checking lives in one place.
 */
public class ConsoleUI {

    private final Scanner in;

    public ConsoleUI() {
        this.in = new Scanner(System.in);
    }

    /** Prints one line. */
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Prints a numbered menu and returns the player's choice.
     *
     * @param title   heading above the options
     * @param options the options, shown as 1, 2, 3...
     * @return the chosen option number, from 1 to options.length
     */
    public int showMenu(String title, String[] options) {
        // TODO (game owner):
        //   print the title
        //   print each option with its number (1, 2, 3...)
        //   return readInt(1, number of options)
        return 1;
    }

    /**
     * Reads a whole number in [min, max], asking again until the input is valid.
     * Never throws on bad input.
     */
    public int readInt(int min, int max) {
        // TODO (game owner):
        //   loop:
        //     read a line and try to turn it into an int
        //     if it is not a number (NumberFormatException), say so and ask again
        //     if it is outside min..max, say the allowed range and ask again
        //     otherwise return it
        return min;
    }

    /** Reads a non-blank name. */
    public String readName() {
        // TODO (game owner): re-ask on a blank line.
        return "Traveler";
    }

    /** Waits for the player to press Enter. */
    public void pause() {
        // TODO (game owner)
    }
}
