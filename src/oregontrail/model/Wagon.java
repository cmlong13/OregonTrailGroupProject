package oregontrail.model;

import oregontrail.exceptions.InsufficientSuppliesException;

public class Wagon implements Describable {

    private static final int STARVATION_DAMAGE = 10;

    private final Inventory inventory;
    private int milesTraveled;
    private Pace pace;
    private Rations rations;

    // Creates a new wagon with no supplies and a steady pace.
    public Wagon() {
        inventory = new Inventory();
        milesTraveled = 0;
        pace = Pace.STEADY;
        rations = Rations.FILLING;
    }

    // Moves the wagon forward if the player has oxen.
    public void travelOneDay() {
        if (inventory.get(ItemType.OXEN) <= 0) {
            return;
        }

        milesTraveled += pace.getMilesPerDay();
    }

    // Gives food to the party and handles shortages.
    public boolean feed(Party party) {
        if (party == null) {
            throw new IllegalArgumentException(
                    "Party cannot be null"
            );
        }

        int livingMembers = party.getAlive().size();

        if (livingMembers == 0) {
            return true;
        }

        int needed = Math.multiplyExact(
                rations.getPoundsPerPerson(),
                livingMembers
        );

        try {
            inventory.remove(ItemType.FOOD, needed);
            return true;

        } catch (InsufficientSuppliesException e) {

            // Uses whatever food is left and lowers everyone's health.
            inventory.removeUpTo(ItemType.FOOD, needed);

            for (Person person : party.getAlive()) {
                person.damage(STARVATION_DAMAGE);
            }

            return false;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getMilesTraveled() {
        return milesTraveled;
    }

    public Pace getPace() {
        return pace;
    }

    // Changes the wagon's travel speed.
    public void setPace(Pace pace) {
        if (pace == null) {
            throw new IllegalArgumentException(
                    "Pace cannot be null"
            );
        }

        this.pace = pace;
    }

    public Rations getRations() {
        return rations;
    }

    // Changes how much food each traveler receives.
    public void setRations(Rations rations) {
        if (rations == null) {
            throw new IllegalArgumentException(
                    "Rations cannot be null"
            );
        }

        this.rations = rations;
    }

    // Displays basic information about the wagon.
    @Override
    public String describe() {
        return "Miles traveled: " + milesTraveled
                + " | Pace: " + pace
                + " | Rations: " + rations;
    }
}
