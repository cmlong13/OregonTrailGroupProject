package oregontrail.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The travelers in the wagon.
 */
public class Party {

    private final List<Person> members = new ArrayList<>();

    public void addMember(Person person) {
        members.add(person);
    }

    /** @return the members who are still alive */
    public List<Person> getAlive() {
        List<Person> alive = new ArrayList<>();
        for (Person p : members) {
            if (p.isAlive()) {
                alive.add(p);
            }
        }
        return alive;
    }

    /** @return true when nobody is alive */
    public boolean isWipedOut() {
        return getAlive().isEmpty();
    }

    /**
     * Applies one day of health change from rations and pace to every living member.
     * A positive total damages them; a negative total heals them.
     */
    public void applyDailyHealth(Rations rations, Pace pace) {
        int total = rations.getHealthCost() + pace.getHealthCost();
        for (Person p : getAlive()) {
            if (total > 0) {
                p.damage(total);
            } else if (total < 0) {
                p.heal(-total);
            }
        }
    }

    public List<Person> getMembers() {
        return List.copyOf(members);
    }
}
