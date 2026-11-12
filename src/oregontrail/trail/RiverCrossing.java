package oregontrail.trail;

import oregontrail.Game;
import oregontrail.exceptions.InsufficientFundsException;
import oregontrail.model.Inventory;
import oregontrail.model.ItemType;
import oregontrail.model.Person;
import oregontrail.ui.AsciiArt;
import oregontrail.ui.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A river the party has to cross. The player chooses how to cross, and a bad
 * crossing loses supplies and hurts a traveler. The depth can change while
 * the party waits for the water to drop.
 */
public class RiverCrossing extends Location {

    /** Chance of trouble for each foot of water above 2 feet when fording. */
    private static final double FORD_RISK_PER_FOOT = 0.20;

    /** Chance of trouble when floating the wagon, at any depth. */
    private static final double FLOAT_RISK = 0.20;

    /** Damage to the traveler caught in the current. */
    private static final int DROWNING_DAMAGE = 25;

    private final int ferryCost;
    private int depth;
    private final Random rng;

    /**
     * Creates a river crossing with an unseeded Random.
     *
     * @param name      the river's name
     * @param mile      where on the trail it is
     * @param depth     how deep the water starts, in feet
     * @param ferryCost what the ferry charges, in dollars
     */
    public RiverCrossing(String name, int mile, int depth, int ferryCost) {
        this(name, mile, depth, ferryCost, new Random());
    }

    /**
     * Pass a seeded Random to get the same results every run (useful for tests).
     *
     * @param name      the river's name
     * @param mile      where on the trail it is
     * @param depth     how deep the water starts, in feet
     * @param ferryCost what the ferry charges, in dollars
     * @param rng       the random number generator to use
     */
    public RiverCrossing(String name, int mile, int depth, int ferryCost, Random rng) {
        super(name, mile);
        this.depth = depth;
        this.ferryCost = ferryCost;
        this.rng = rng;
    }

    /** Asks how to cross, and keeps asking while the party waits or can't pay the ferry. */
    @Override
    public void onArrive(Game game) {
        ConsoleUI ui = game.getUi();
        // stripTrailing() removes the line break at the end of the picture,
        // so there isn't an extra blank line under it.
        ui.print(AsciiArt.RIVER.stripTrailing());
        ui.header("YOU HAVE REACHED THE " + getName().toUpperCase());

        while (true) {
            ui.blank();
            ui.print("The river is " + depth + " feet deep here. You have $" + game.getMoney() + ".");
            String[] options = {
                    "Ford it: drive the wagon straight across",
                    "Float it: seal the wagon and float it over",
                    "Take the ferry for $" + ferryCost,
                    "Wait a day to see if the water drops"
            };
            int choice = ui.showMenu("HOW WILL YOU CROSS?", options);

            switch (choice) {
                case 1:
                    cross(game, Math.max(0, depth - 2) * FORD_RISK_PER_FOOT,
                            "You drive the wagon into the water and reach the far bank safely.",
                            "The water is too deep! The wagon tips over in the current.");
                    return;
                case 2:
                    cross(game, FLOAT_RISK,
                            "The sealed wagon floats over and reaches the far bank safely.",
                            "The wagon takes on water halfway across and nearly sinks!");
                    return;
                case 3:
                    try {
                        game.spendMoney(ferryCost);
                        ui.print("The ferry carries your wagon safely across.");
                        ui.pause();
                        return;
                    } catch (InsufficientFundsException e) {
                        ui.print(e.getMessage());
                    }
                    break;
                case 4:
                    game.skipDays(1);
                    // nextInt(3) gives 0, 1 or 2, so subtracting 1 gives -1, 0 or +1.
                    depth = Math.max(1, depth + rng.nextInt(3) - 1);
                    ui.print("You camp by the river for a day.");
                    break;
            }
        }
    }

    /**
     * Tries to cross. With chance {@code risk} something goes wrong.
     *
     * @param risk           chance of trouble, from 0.0 (never) to 1.0 or more (always)
     * @param successMessage shown when the crossing goes well
     * @param troubleMessage shown when it doesn't
     */
    private void cross(Game game, double risk, String successMessage, String troubleMessage) {
        ConsoleUI ui = game.getUi();
        // nextDouble() gives a random number from 0.0 up to (not including) 1.0,
        // so it is below 0.4 about 40% of the time.
        if (rng.nextDouble() < risk) {
            ui.print(troubleMessage);
            loseSupplies(game);
            hurtSomeone(game);
        } else {
            ui.print(successMessage);
        }
        ui.pause();
    }

    /** Washes away a quarter of the food and ammunition and 1 or 2 sets of clothing. */
    private void loseSupplies(Game game) {
        Inventory inventory = game.getWagon().getInventory();
        int food = inventory.removeUpTo(ItemType.FOOD, inventory.get(ItemType.FOOD) / 4);
        int ammo = inventory.removeUpTo(ItemType.AMMUNITION, inventory.get(ItemType.AMMUNITION) / 4);
        int clothing = inventory.removeUpTo(ItemType.CLOTHING, 1 + rng.nextInt(2));

        List<String> lost = new ArrayList<>();
        if (food > 0) {
            lost.add(food + " " + ItemType.FOOD.unitFor(food) + " of food");
        }
        if (ammo > 0) {
            lost.add(ammo + " " + ItemType.AMMUNITION.unitFor(ammo) + " of ammunition");
        }
        if (clothing > 0) {
            lost.add(clothing + " " + ItemType.CLOTHING.unitFor(clothing) + " of clothing");
        }

        if (lost.isEmpty()) {
            game.getUi().print("Luckily, nothing was washed away.");
        } else {
            // String.join puts ", " between the items: "a, b, c".
            game.getUi().print("Washed away: " + String.join(", ", lost) + ".");
        }
    }

    /** One random living traveler is caught in the current. */
    private void hurtSomeone(Game game) {
        List<Person> alive = game.getParty().getAlive();
        if (alive.isEmpty()) {
            return;
        }
        Person unlucky = alive.get(rng.nextInt(alive.size()));
        unlucky.damage(DROWNING_DAMAGE);
        if (unlucky.isAlive()) {
            game.getUi().print(unlucky.getName() + " was swept downstream but made it back to the wagon.");
        } else {
            game.getUi().print(unlucky.getName() + " was swept away and drowned.");
        }
    }

    /** @return the river's name, mile and current depth */
    @Override
    public String describe() {
        return getName() + " (river crossing at mile " + getMile() + ", " + depth + " feet deep)";
    }
}
