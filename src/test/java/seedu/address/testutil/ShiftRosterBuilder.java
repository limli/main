package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.ShiftRoster;


/**
 * A utility class to help with building ShiftRoster objects.
 */
public class ShiftRosterBuilder {

    public static final String DEFAULT_START_DAY_OF_WEEK = "MONDAY";
    public static final String DEFAULT_START_TIME = "12:00";
    public static final String DEFAULT_END_DAY_OF_WEEK = "MONDAY";
    public static final String DEFAILT_END_TIME = "14:00";

    private List<Shift> shifts;

    public ShiftRosterBuilder() {
        shifts = new ArrayList<>();
        shifts.add(new Shift(DEFAULT_START_DAY_OF_WEEK, DEFAULT_START_TIME, DEFAULT_END_DAY_OF_WEEK, DEFAILT_END_TIME));
    }

    /**
     * Initializes the ShiftRosterBuilder with the data of {@code shiftRosterToCopy}.
     */
    public ShiftRosterBuilder(ShiftRoster shiftRosterToCopy) {
        shifts = new ArrayList<>();
        shifts.addAll(shiftRosterToCopy.getShifts());
    }

    /**
     * Adds a {@code Shift} to the {@code ShiftRoster} that we are building.
     */
    public ShiftRosterBuilder withShift(Shift shift) {
        this.shifts.add(shift);
        return this;
    }

    public ShiftRoster build() {
        return new ShiftRoster(shifts);
    }

}
