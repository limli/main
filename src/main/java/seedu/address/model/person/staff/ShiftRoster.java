package seedu.address.model.person.staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a collection of shifts for a single staff in the restaurant book.
 *
 * Guarantees: immutable;
 */
public class ShiftRoster {

    public static final String MESSAGE_CONSTRAINTS =
            "The shift roster for a certain staff member should not contain any shifts that clash with each other.";

    private final List<Shift> shifts;

    /**
     * Constructs a new {@code ShiftRoster}
     */
    public ShiftRoster(Shift... shifts) {
        this.shifts = List.of(shifts);
    }

    public ShiftRoster(List<Shift> shifts) {
        this.shifts = Collections.unmodifiableList(shifts);
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

    /**
     * Adds a new shift to the roster.
     *
     * @param shift The shift to add to the roster.
     */
    public ShiftRoster addShift(Shift shift) {
        if (conflictsWith(shift)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        } else {
            List<Shift> newShiftRoster = new ArrayList<>(shifts);
            newShiftRoster.add(shift);
            Collections.sort(newShiftRoster);
            return new ShiftRoster(newShiftRoster);
        }
    }

    /**
     * Removes a shift to the roster if it exists.
     *
     * @param shift The shift to remove from the roster.
     */
    public ShiftRoster deleteShift(Shift shift) {
        List<Shift> newShiftRoster = new ArrayList<>(shifts);
        newShiftRoster.remove(shift);
        return new ShiftRoster(newShiftRoster);
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ShiftRoster) {
            if (shifts.size() != ((ShiftRoster) other).shifts.size()) {
                return false;
            }
            for (int i = 0; i < shifts.size(); i++) {
                if (!shifts.get(i).equals(((ShiftRoster) other).shifts.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (Shift shift : shifts) {
            output += shift + "; ";
        }
        return output;
    }
}
