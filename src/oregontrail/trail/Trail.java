package oregontrail.trail;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * The stops on the trail, kept in a TreeMap keyed by mile so the next stop
 * and the stops passed in a day can be found in O(log n).
 * Mile markers are rough placeholders to tune later.
 */
public class Trail {

    public static final int LENGTH = 2000;

    private final TreeMap<Integer, Location> stops = new TreeMap<>();

    public Trail() {
        addStop(new Landmark("Chimney Rock", 550, "A tall spire of rock rises from the plains."));
        addStop(new Fort("Fort Laramie", 640));
        addStop(new Landmark("Independence Rock", 830, "Travelers carve their names into the rock."));
        addStop(new Landmark("South Pass", 930, "You cross the Continental Divide."));
        addStop(new Landmark("Soda Springs", 1150, "Bubbling springs line the trail."));
        addStop(new Landmark("Blue Mountains", 1700, "The last big climb before Oregon."));
        addStop(new Destination("the Willamette Valley", LENGTH));
    }

    private void addStop(Location location) {
        stops.put(location.getMile(), location);
    }

    /** @return the first stop at or after this mile, or null if none is left */
    public Location nextStop(int mile) {
        var entry = stops.ceilingEntry(mile);
        return entry == null ? null : entry.getValue();
    }

    /** @return the stops after fromMile and up to and including toMile, in order */
    public List<Location> stopsPassed(int fromMile, int toMile) {
        return new ArrayList<>(stops.subMap(fromMile, false, toMile, true).values());
    }
}
