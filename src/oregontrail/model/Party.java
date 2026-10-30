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
        // TODO (model owner): return only the members where isAlive() is true.
        return new ArrayList<>();
    }

    public boolean isWipedOut() {
        // TODO (model owner): true when nobody is alive.
        return false;
    }

    /** Applies one day of health change from rations and pace to every living member. */
    public void applyDailyHealth(Rations rations, Pace pace) {
        // TODO (model owner):
        //   for each living member: damage by rations health cost + pace health cost
        //   (a negative total means heal instead)
    }

    public List<Person> getMembers() {
        return List.copyOf(members);
    }
}
