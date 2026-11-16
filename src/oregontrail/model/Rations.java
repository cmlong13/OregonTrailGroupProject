package oregontrail.model;

// Determines how much food each traveler receives.
public enum Rations {

    FILLING(3, -2),
    MEAGER(2, 1),
    BARE_BONES(1, 4);

    private final int poundsPerPerson;
    private final int healthCost;

    Rations(int poundsPerPerson, int healthCost) {
        this.poundsPerPerson = poundsPerPerson;
        this.healthCost = healthCost;
    }

    // Returns how much food each person eats daily.
    public int getPoundsPerPerson() {
        return poundsPerPerson;
    }

    // Returns the health change from the current food amount.
    // A negative number means health is restored.
    public int getHealthCost() {
        return healthCost;
    }
}
