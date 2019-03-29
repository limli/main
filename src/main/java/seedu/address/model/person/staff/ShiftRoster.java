package seedu.address.model.person.staff;

import java.util.ArrayList;

/**
 * Represents a collection of shifts for a single staff in the restaurant book.
 */
public class ShiftRoster {

    private ArrayList<Shift> shifts;

    /**
     * Constructs a new {@code ShiftRoster}
     */
    public ShiftRoster() {
        this.shifts = new ArrayList<>();
    }

    /**
     * Checks any of the shifts in the roster conflicts with {@code otherShift}
     *
     * @param otherShift The shift to check for conflicts with.
     * @return {@code true} if the two shifts conflict, {@code false} otherwise.
     */
    public boolean conflictsWith(Shift otherShift) {
        for (Shift shift : shifts) {
            if (shift.conflictsWith(otherShift)) {
                return true;
            }
        }
        return false;
    }

    public void addShift(Shift shift) {
        if (this.conflictsWith(shift)) {
            // TODO: fail to add shift
        }
        this.shifts.add(shift);
    }

    public void deleteShift(Shift shift) {
        // TODO: add checks
        this.shifts.remove(shift);
    }
}
