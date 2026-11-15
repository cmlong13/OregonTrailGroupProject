package oregontrail.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Party {

    // Keeps track of everyone traveling in the group.
    private final List<Person> members = new ArrayList<>();

    // Adds a new person to the traveling group.
    public void addMember(Person person) {
        members.add(Objects.requireNonNull(
                person, "Person cannot be null"
        ));
    }

    // Returns a list of everyone who is still alive.
    public List<Person> getAlive() {
        List<Person> alive = new ArrayList<>();

        for (Person person : members) {
            if (person.isAlive()) {
                alive.add(person);
            }
        }

        return alive;
    }

    // Checks whether the entire group has died.
    public boolean isWipedOut() {
        return getAlive().isEmpty();
    }

    // Applies the daily health changes from food and travel.
    public void applyDailyHealth(Rations rations, Pace pace) {
        int total = rations.getHealthCost()
                + pace.getHealthCost();

        for (Person person : getAlive()) {
            if (total > 0) {
                person.damage(total);
            } else if (total < 0) {
                person.heal(-total);
            }
        }
    }

    // Returns a copy of the full group.
    public List<Person> getMembers() {
        return List.copyOf(members);
    }
}

