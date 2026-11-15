
package oregontrail.model;

// Determines how fast the group travels each day.
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

    // Returns how many miles the wagon travels.
    public int getMilesPerDay() {
        return milesPerDay;
    }

    // Returns how much health is lost from traveling.
    public int getHealthCost() {
        return healthCost;
    }
}
