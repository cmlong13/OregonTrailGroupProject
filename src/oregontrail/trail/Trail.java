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
        // TODO (events owner): add the stops. Suggested (miles are rough):
        //   Landmark Chimney Rock 550, Fort Fort Laramie 640, Landmark Independence Rock 830,
        //   Landmark South Pass 930, Landmark Soda Springs 1150, Landmark Blue Mountains 1700,
        //   Destination Willamette Valley at LENGTH
    }

    private void addStop(Location location) {
        stops.put(location.getMile(), location);
    }

    /** @return the first stop at or after this mile, or null if none is left */
    public Location nextStop(int mile) {
        // TODO (events owner): TreeMap.ceilingEntry(mile) finds this in O(log n).
        return null;
    }

    /** @return the stops after fromMile and up to and including toMile, in order */
    public List<Location> stopsPassed(int fromMile, int toMile) {
        // TODO (events owner): TreeMap.subMap(fromMile, false, toMile, true) gives exactly these stops.
        return new ArrayList<>();
    }
}
