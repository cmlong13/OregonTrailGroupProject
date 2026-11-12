package oregontrail.trail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The stops on the trail, kept in a TreeMap keyed by mile so the next stop
 * and the stops passed in a day can be found in O(log n).
 * Mile markers are rough placeholders to tune later.
 */
public class Trail {

    public static final int LENGTH = 2000;

    private final TreeMap<Integer, Location> stops = new TreeMap<>();

    /** Builds the trail with every river, landmark, fort and the destination. */
    public Trail() {
        addStop(new RiverCrossing("Kansas River", 100, 4, 5));
        addStop(new RiverCrossing("Big Blue River", 190, 3, 5));
        addStop(new Landmark("Chimney Rock", 550,
                "A tall spire of rock that travelers can see for days before they reach it."));
        addStop(new Fort("Fort Laramie", 640));
        addStop(new Landmark("Independence Rock", 830,
                "A huge granite dome covered in the names of the travelers who passed before you."));
        addStop(new Landmark("South Pass", 930,
                "A wide, gentle pass where the trail crosses the Continental Divide."));
        addStop(new RiverCrossing("Green River", 1000, 6, 10));
        addStop(new Landmark("Soda Springs", 1150,
                "Bubbling springs whose water fizzes like soda."));
        addStop(new Fort("Fort Hall", 1250));
        addStop(new RiverCrossing("Snake River", 1350, 5, 10));
        addStop(new Landmark("Blue Mountains", 1700,
                "A steep, forested range, one of the last hard climbs before Oregon."));
        addStop(new Destination("Willamette Valley", LENGTH));
    }

    private void addStop(Location location) {
        stops.put(location.getMile(), location);
    }

    /**
     * @param mile the mile to search from
     * @return the first stop at or after this mile, or null if none is left
     */
    public Location nextStop(int mile) {
        // ceilingEntry(mile) returns the entry with the smallest key that is
        // greater than or equal to mile, or null if there isn't one.
        Map.Entry<Integer, Location> entry = stops.ceilingEntry(mile);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    /**
     * @param fromMile where the wagon was at the start of the day (not included)
     * @param toMile   where the wagon is now (included)
     * @return the stops after fromMile and up to and including toMile, in order
     */
    public List<Location> stopsPassed(int fromMile, int toMile) {
        // subMap(from, false, to, true) is a view of only the entries with keys
        // above fromMile and up to toMile; the booleans say whether each end
        // is included. We copy its values into a new list.
        return new ArrayList<>(stops.subMap(fromMile, false, toMile, true).values());
    }

    /** @return every stop in mile order, as a read-only collection */
    public Collection<Location> getStops() {
        // unmodifiableCollection wraps the values so callers can read them
        // but get an error if they try to add or remove a stop.
        return Collections.unmodifiableCollection(stops.values());
    }
}
