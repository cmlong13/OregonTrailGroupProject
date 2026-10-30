package oregontrail.model;

/**
 * One member of the party. Health is private and can only change through
 * {@link #damage(int)} and {@link #heal(int)} (encapsulation).
 */
public class Person implements Describable {

    public static final int MAX_HEALTH = 100;

    private final String name;
    private int health;

    public Person(String name) {
        this(name, MAX_HEALTH);
    }

    public Person(String name, int health) {
        this.name = name;
        this.health = health;
    }

    /** Lowers health, never below 0. */
    public void damage(int amount) {
        // TODO (model owner): clamp at 0 and reject negative amounts.
        health -= amount;
    }

    /** Raises health, never above {@link #MAX_HEALTH}. */
    public void heal(int amount) {
        // TODO (model owner): clamp at MAX_HEALTH and reject negative amounts.
        health += amount;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String describe() {
        // TODO (model owner): show a word like Good, Fair, Poor or Dead instead of a number.
        return name + " (health " + health + ")";
    }
}
