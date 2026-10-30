package oregontrail.model;

/**
 * How much each person eats per day. Less food means more health lost.
 */
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

    public int getPoundsPerPerson() {
        return poundsPerPerson;
    }

    /** @return health lost per day; negative means health is gained */
    public int getHealthCost() {
        return healthCost;
    }
}
