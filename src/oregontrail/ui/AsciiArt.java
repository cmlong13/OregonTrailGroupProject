package oregontrail.ui;

/**
 * The ASCII art pictures shown on the title, river, game over and win screens.
 * Every picture fits inside {@link ConsoleUI#WIDTH} characters.
 */
public final class AsciiArt {

    // A private constructor means no one can write "new AsciiArt()":
    // this class only holds constants, so an object of it would be useless.
    private AsciiArt() {
    }

    // Each picture below is a text block (Java 15+): everything between the
    // opening """ and the closing """ is one String, line breaks included.
    // Java removes the shared indentation on the left, so the pictures keep
    // their shape. Inside a text block a backslash must be written as \\
    // to print one \ (a single \ starts an escape such as \n).

    /** The game's name in big letters. */
    public static final String TITLE = """
                                        T  H  E

                          ###  ####  #####  ####  ###  #   #
                         #   # #   # #     #     #   # ##  #
                         #   # ####  ####  #  ## #   # # # #
                         #   # #  #  #     #   # #   # #  ##
                          ###  #   # #####  ####  ###  #   #

                             ##### ####   ###  ##### #
                               #   #   # #   #   #   #
                               #   ####  #####   #   #
                               #   #  #  #   #   #   #
                               #   #   # #   # ##### #####
            """;

    /** A covered wagon pulled by an ox. */
    public static final String WAGON = """
                            ___________________________
                          /                             \\
                         /                               \\            ____
                        |_________________________________|__________/ oo |
                        |____|_______________________|____|          \\____/
                           (@)                     (@)                 ||  ||
                 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            """;

    /** Mountains above a wide river. */
    public static final String RIVER = """
                  /\\              /\\       /\\
                 /  \\      /\\    /  \\     /  \\        /\\
                /    \\    /  \\  /    \\   /    \\      /  \\
              ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                 ~~~~    ~~~~~    ~~~~    ~~~~~    ~~~~    ~~~~~
              ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            """;

    /** A gravestone, for the game over screen. */
    public static final String TOMBSTONE = """
                                      ___________
                                    /             \\
                                   /     R.I.P.    \\
                                  |                 |
                                  |                 |
                                  |                 |
                                  |                 |
                              ,,,,|_________________|,,,,
                            ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
            """;

    /** The sun rising over the green valley, for the win screen. */
    public static final String VALLEY = """
                    \\   |   /
                      .---.            /\\
                 --- (     ) ---      /  \\      /\\
                      '---'          /    \\    /  \\    /\\
                    /   |   \\       /      \\  /    \\  /  \\
              _____________________/        \\/      \\/    \\______________
                ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,
                   ,    ,    ,    ,    ,    ,    ,    ,    ,    ,
            """;
}
