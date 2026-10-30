package oregontrail.model;

/**
 * How hard the party travels. Faster means more miles but more health lost.
 */
public enum Pace {
    STEADY(12, 0),
    STRENUOUS(16, 3),
    GRUELING(20, 6);

    private final int milesPerDay;
    private final int healthCost;

    Pace(int milesPerDay, int healthCost) {
        this.milesPerDay = milesPerDay;
        this.healthCost = healthCost;
    }

    public int getMilesPerDay() {
        return milesPerDay;
    }

    public int getHealthCost() {
        return healthCost;
    }
}
