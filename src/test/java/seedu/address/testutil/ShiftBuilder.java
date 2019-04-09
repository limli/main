package seedu.address.testutil;

import seedu.address.model.person.staff.Shift;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * A utility class to help with building Shift objects.
 */
public class ShiftBuilder {

    public static final String DEFAULT_START_DAY_OF_WEEK = "MONDAY";
    public static final String DEFAULT_START_TIME = "00:00";
    public static final String DEFAULT_END_DAY_OF_WEEK = "SUNDAY";
    public static final String DEFAULT_END_TIME = "23:59";

    private DayOfWeek startDayOfWeek;
    private LocalTime startTime;
    private DayOfWeek endDayOfWeek;
    private LocalTime endTime;

    public ShiftBuilder() {
        startDayOfWeek = DayOfWeek.valueOf(DEFAULT_START_DAY_OF_WEEK);
        startTime = LocalTime.parse(DEFAULT_START_TIME);
        endDayOfWeek = DayOfWeek.valueOf(DEFAULT_END_DAY_OF_WEEK);
        endTime = LocalTime.parse(DEFAULT_END_TIME);
    }

    /**
     * Initializes the ShiftBuilder with the data of {@code shiftToCopy}.
     */
    public ShiftBuilder(Shift shiftToCopy) {
        startDayOfWeek = shiftToCopy.getStartDayOfWeek();
        startTime = shiftToCopy.getStartTime();
        endDayOfWeek = shiftToCopy.getEndDayOfWeek();
        endTime = shiftToCopy.getEndTime();
    }

    /**
     * Sets the {@code startDayOfWeek} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withStartDayOfWeek(String startDayOfWeek) {
        this.startDayOfWeek = DayOfWeek.valueOf(startDayOfWeek);
        return this;
    }

    /**
     * Sets the {@code startDayOfWeek} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withStartDayOfWeek(DayOfWeek startDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
        return this;
    }

    /**
     * Sets the {@code endDayOfWeek} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withEndDayOfWeek(String endDayOfWeek) {
        this.endDayOfWeek = DayOfWeek.valueOf(endDayOfWeek);
        return this;
    }

    /**
     * Sets the {@code endDayOfWeek} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withEndDayOfWeek(DayOfWeek endDayOfWeek) {
        this.startDayOfWeek = endDayOfWeek;
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Shift} that we are building.
     */
    public ShiftBuilder withEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Shift build() {
        return new Shift(startDayOfWeek, startTime, endDayOfWeek, endTime);
    }

}
