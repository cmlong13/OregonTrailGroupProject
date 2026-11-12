package oregontrail;

import oregontrail.model.Inventory;
import oregontrail.model.ItemType;
import oregontrail.ui.ConsoleUI;

import java.util.Random;

/**
 * A hunting trip: the player types a short code quickly to hit an animal
 * and bring back food. Each shot uses one box of ammunition, and a hunt
 * takes the whole day.
 */
public class Hunt {

    private static final int MAX_SHOTS = 3;
    private static final int CODE_LENGTH = 4;

    /** Letters used for codes. I, O and Q are left out because they look like 1, 0 and O. */
    private static final String CODE_LETTERS = "ABCDEFGHJKLMNPRSTUVWXYZ";

    /** Nanoseconds in one second. The underscores are only there to make the number easier to read. */
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    /**
     * The animals the party can find. An enum can be private when only this
     * class uses it.
     */
    private enum Prey {
        RABBIT("rabbit", 10, 3.0),
        DEER("deer", 50, 4.0),
        BISON("bison", 100, 5.0);

        private final String label;
        private final int pounds;
        private final double secondsAllowed;

        Prey(String label, int pounds, double secondsAllowed) {
            this.label = label;
            this.pounds = pounds;
            this.secondsAllowed = secondsAllowed;
        }
    }

    private final Random rng;

    /** Creates a hunt with an unseeded Random. */
    public Hunt() {
        this(new Random());
    }

    /**
     * Pass a seeded Random to get the same animals and codes every run (useful for tests).
     *
     * @param rng the random number generator to use
     */
    public Hunt(Random rng) {
        this.rng = rng;
    }

    /**
     * Goes hunting. Without ammunition the player is turned away and no day passes.
     * Otherwise the player gets up to {@link #MAX_SHOTS} shots while ammunition lasts,
     * the hunt ends at the first hit, and the day is used up either way.
     *
     * @param game the game, for the screen, the wagon and the day counter
     */
    public void go(Game game) {
        ConsoleUI ui = game.getUi();
        Inventory inventory = game.getWagon().getInventory();

        ui.header("HUNTING");
        if (inventory.get(ItemType.AMMUNITION) == 0) {
            ui.print("You have no ammunition, so you can't go hunting.");
            ui.pause();
            return;
        }

        Prey prey = Prey.values()[rng.nextInt(Prey.values().length)];
        ui.print("You spot a " + prey.label + " in the distance!");
        ui.print("To shoot, type the code you're shown and press Enter within "
                + prey.secondsAllowed + " seconds.");
        ui.print("You get up to " + MAX_SHOTS + " shots, and each one uses 1 box of ammunition.");

        boolean hit = false;
        for (int shot = 1; shot <= MAX_SHOTS && !hit && inventory.get(ItemType.AMMUNITION) > 0; shot++) {
            String code = randomCode();
            ui.blank();
            ui.print("Shot " + shot + ": type " + code);

            // System.nanoTime() reads a very precise clock, in nanoseconds
            // (billionths of a second). It isn't the time of day; it only
            // means something when you subtract one reading from another,
            // which tells you how long the player took to type.
            long start = System.nanoTime();
            String typed = ui.readLine("> ");
            double seconds = (System.nanoTime() - start) / NANOS_PER_SECOND;

            inventory.removeUpTo(ItemType.AMMUNITION, 1);

            if (!typed.trim().equalsIgnoreCase(code)) {
                ui.print("Your aim slips. Missed!");
            } else if (seconds > prey.secondsAllowed) {
                // String.format fills in the %.1f with the number rounded to 1 decimal place.
                ui.print(String.format("Too slow (%.1f seconds). Missed!", seconds));
            } else {
                hit = true;
                inventory.add(ItemType.FOOD, prey.pounds);
                ui.print("A clean shot! You bring back " + prey.pounds + " pounds of "
                        + prey.label + " meat.");
            }
        }

        if (!hit) {
            if (inventory.get(ItemType.AMMUNITION) == 0) {
                ui.print("You're out of ammunition.");
            }
            ui.print("The " + prey.label + " got away.");
        }

        ui.blank();
        ui.print("Hunting took the whole day.");
        game.skipDays(1);
        ui.pause();
    }

    /** @return CODE_LENGTH random letters from CODE_LETTERS */
    private String randomCode() {
        // A StringBuilder builds up a String piece by piece without creating
        // a new String every time a letter is added.
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CODE_LETTERS.charAt(rng.nextInt(CODE_LETTERS.length())));
        }
        return code.toString();
    }
}
