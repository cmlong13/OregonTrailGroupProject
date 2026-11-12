package oregontrail.ui;

import java.util.Scanner;

/**
 * Every line the player sees and every key they type goes through this class,
 * so input checking lives in one place.
 */

public class ConsoleUI {

    /** How many characters wide the screen layout is. */
    public static final int WIDTH = 72;

    /** The longest name a traveler can have. */
    public static final int MAX_NAME_LENGTH = 15;

    private final Scanner in;

    public ConsoleUI() {
        this.in = new Scanner(System.in);
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    /** Prints one line. */
    public void print(String message) {
        System.out.println(message);
    }

    /** Prints an empty line. */
    public void blank() {
        System.out.println();
    }

    /** Prints a line of dashes as wide as the screen. */
    public void divider() {
        // String.repeat(n) (Java 11+) returns the string copied n times in a row.
        print("-".repeat(WIDTH));
    }

    /**
     * Prints text one line at a time, waiting after each line, so ASCII art
     * appears to draw itself.
     *
     * @param text    the text to print; may contain several lines
     * @param delayMs how long to wait after each line, in milliseconds
     */
    public void printSlowly(String text, int delayMs) {
        boolean delaying = true;
        // "\\R" is a regular expression that matches any line break
        // (\n on Mac/Linux or \r\n on Windows), so the text splits into lines.
        for (String line : text.split("\\R")) {
            print(line);
            if (delaying) {
                try {
                    // Thread.sleep pauses the program for the given milliseconds.
                    Thread.sleep(delayMs);
                } catch (InterruptedException e) {
                    // Java makes us handle this in case something asks the program
                    // to stop waiting. We put the "interrupted" flag back so the
                    // rest of the program can still see it, then stop delaying.
                    Thread.currentThread().interrupt();
                    delaying = false;
                }
            }
        }
    }

    /**
     * Prints a screen heading: a blank line, a row of '=', the title centered,
     * and another row of '='.
     *
     * @param title the heading text
     */
    public void header(String title) {
        blank();
        print("=".repeat(WIDTH));
        print(center(title));
        print("=".repeat(WIDTH));
    }

    /**
     * Prints the given lines inside a box as wide as the screen.
     * Lines that are too long are cut off to fit.
     *
     * @param lines the lines to show inside the box
     */
    // "String... lines" is a varargs parameter: the caller can pass any number of
    // Strings, box("a", "b", "c"), and Java hands them to us as a String[] array.
    public void box(String... lines) {
        String edge = "+" + "-".repeat(WIDTH - 2) + "+";
        print(edge);
        for (String line : lines) {
            print("| " + fit(line, WIDTH - 4) + " |");
        }
        print(edge);
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------

    /**
     * Prints a numbered menu and returns the player's choice.
     *
     * @param title   heading above the options
     * @param options the options, shown as 1, 2, 3...
     * @return the chosen option number, from 1 to options.length
     */
    public int showMenu(String title, String[] options) {
        if (options == null || options.length == 0) {
            throw new IllegalArgumentException("A menu needs at least one option");
        }
        print("");
        print(title);
        for (int i = 0; i < options.length; i++) {
            print("  " + (i + 1) + ". " + options[i]);
        }
        return readInt("Your choice", 1, options.length);
    }

    /**
     * Reads a whole number in [min, max] using the prompt "Your choice",
     * asking again until the input is valid. Never throws on bad input.
     *
     * @param min the smallest allowed number
     * @param max the largest allowed number
     * @return a number from min to max
     */
    public int readInt(int min, int max) {
        return readInt("Your choice", min, max);
    }

    /**
     * Reads a whole number in [min, max], asking again until the input is valid.
     * Never throws on bad input.
     *
     * @param prompt what to ask the player; the allowed range is added after it
     * @param min    the smallest allowed number
     * @param max    the largest allowed number
     * @return a number from min to max
     */
    public int readInt(String prompt, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must not be greater than max");
        }
        while (true) {
            String input = readLine(prompt + " (" + min + "-" + max + "): ").trim();
            try {
                // Integer.parseInt turns text like "42" into the int 42. It throws
                // NumberFormatException when the text is not a whole number.
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
                print("Please enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                print("\"" + input + "\" is not a number. Please enter a number from "
                        + min + " to " + max + ".");
            }
        }
    }

    /**
     * Reads a name that is not blank and at most {@link #MAX_NAME_LENGTH}
     * characters, asking again until it is valid.
     *
     * @param prompt what to ask the player
     * @return the name with spaces trimmed off both ends
     */
    public String readName(String prompt) {
        while (true) {
            String name = readLine(prompt + ": ").trim();
            if (name.isEmpty()) {
                print("A name can't be blank.");
            } else if (name.length() > MAX_NAME_LENGTH) {
                print("Please use " + MAX_NAME_LENGTH + " characters or fewer.");
            } else {
                return name;
            }
        }
    }

    /**
     * Asks a yes-or-no question until the player types y, yes, n or no
     * (in any mix of upper and lower case).
     *
     * @param question the question to ask
     * @return true for yes, false for no
     */
    public boolean readYesNo(String question) {
        while (true) {
            String answer = readLine(question + " (y/n): ").trim().toLowerCase();
            switch (answer) {
                case "y":
                case "yes":
                    return true;
                case "n":
                case "no":
                    return false;
                default:
                    print("Please type y or n.");
                    break;
            }
        }
    }

    /**
     * Prints a prompt and reads one line of input. If there is no more input
     * (for example, the input stream was closed), the game ends politely
     * instead of crashing.
     *
     * @param prompt text shown before the cursor
     * @return the line the player typed, without the line break
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        // hasNextLine() is false when input has ended for good (for example,
        // Ctrl+D, or the end of a file fed in as input). nextLine() would throw then.
        if (!in.hasNextLine()) {
            blank();
            print("No more input. Goodbye!");
            // System.exit(0) ends the whole program right away; 0 means "no error".
            System.exit(0);
        }
        return in.nextLine();
    }

    /** Waits for the player to press Enter. */
    public void pause() {
        readLine("(Press Enter to continue) ");
    }

    // ------------------------------------------------------------------
    // Text helpers
    // ------------------------------------------------------------------
    // These are static because they only work on the text they are given and
    // don't need a ConsoleUI object: call them as ConsoleUI.center("Hi").

    /**
     * Pads text with spaces so it sits in the middle of a {@link #WIDTH}-wide line.
     * Text that is already too wide is returned unchanged.
     *
     * @param text the text to center
     * @return the centered text
     */
    public static String center(String text) {
        if (text.length() >= WIDTH) {
            return text;
        }
        int left = (WIDTH - text.length()) / 2;
        return fit(" ".repeat(left) + text, WIDTH);
    }

    /**
     * Makes text exactly the given width, adding spaces on the right
     * or cutting it off.
     *
     * @param text  the text to fit
     * @param width the exact length wanted
     * @return text padded or cut to exactly width characters
     */
    public static String fit(String text, int width) {
        if (text.length() > width) {
            // substring(0, width) keeps the characters from index 0 up to (not including) width.
            return text.substring(0, width);
        }
        return text + " ".repeat(width - text.length());
    }

    /**
     * Turns an enum-style name into normal words, for example
     * "BARE_BONES" becomes "Bare bones".
     *
     * @param name the name in capitals with underscores
     * @return the name with only its first letter capitalized
     */
    public static String titleCase(String name) {
        if (name.isEmpty()) {
            return name;
        }
        String words = name.replace('_', ' ').toLowerCase();
        return Character.toUpperCase(words.charAt(0)) + words.substring(1);
    }

    /**
     * Draws a progress bar like "[####>-----]", or a full "[#####]" when done.
     *
     * @param done  how much is done so far
     * @param total the amount that counts as finished; must be above 0
     * @param width how many characters go between the brackets
     * @return the progress bar
     */
    public static String progressBar(int done, int total, int width) {
        if (total <= 0) {
            throw new IllegalArgumentException("total must be above 0");
        }
        if (done >= total) {
            return "[" + "#".repeat(width) + "]";
        }
        int filled = Math.max(0, done) * width / total;
        return "[" + "#".repeat(filled) + ">" + "-".repeat(width - filled - 1) + "]";
    }
}
