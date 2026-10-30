package oregontrail.model;

/**
 * The wagon: its supplies, how far it has gone, and the player's pace and rations.
 */
public class Wagon implements Describable {

    private final Inventory inventory;
    private int milesTraveled;
    private Pace pace;
    private Rations rations;

    public Wagon() {
        this.inventory = new Inventory();
        this.milesTraveled = 0;
        this.pace = Pace.STEADY;
        this.rations = Rations.FILLING;
    }

    /** Moves the wagon forward by the current pace. */
    public void travelOneDay() {
        // TODO (model owner): no oxen means no travel.
        milesTraveled += pace.getMilesPerDay();
    }

    /** Uses one day of food for every living member of the party. */
    public void feed(Party party) {
        // TODO (model owner): remove food; if there is not enough, eat what is left and hurt everyone.
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

    public void setPace(Pace pace) {
        this.pace = pace;
    }

    public Rations getRations() {
        return rations;
    }

    public void setRations(Rations rations) {
        this.rations = rations;
    }

    @Override
    public String describe() {
        return "Miles traveled: " + milesTraveled + " | Pace: " + pace + " | Rations: " + rations;
    }
}
