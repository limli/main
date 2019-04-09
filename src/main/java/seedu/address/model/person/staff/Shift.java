package seedu.address.model.person.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Represents a single shift of a staff member in the restaurant book.
 */
public class Shift implements Comparable<Shift> {

    public static final String MESSAGE_CONSTRAINTS =
            "Day of week should be specified as MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY or SUNDAY.\n"
                    + "Start time and end time should be in the HH:MM format.\n"
                    + "A shift must last for a positive duration of time.\n"
                    + "If a shift lasts more than 24 hours, the starting day of week of the shift and ending day "
                    + "of week of the shift must be different.\n";

    private final DayOfWeek startDayOfWeek;
    private final LocalTime startTime;
    private final DayOfWeek endDayOfWeek;
    private final LocalTime endTime;

    /**
     * Constructs a {@code Shift}.
     *
     * @param startDayOfWeek The start day of the week of the shift.
     * @param startTime The time the shift begins.
     * @param endDayOfWeek The start day of the week of the shift.
     * @param endTime The time the shift ends.
     */
    public Shift(DayOfWeek startDayOfWeek, LocalTime startTime, DayOfWeek endDayOfWeek, LocalTime endTime) {
        requireNonNull(startDayOfWeek);
        requireNonNull(startTime);
        requireNonNull(endDayOfWeek);
        requireNonNull(endTime);
        checkArgument(isValidShift(startDayOfWeek, startTime, endDayOfWeek, endTime), MESSAGE_CONSTRAINTS);
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
    }

    @JsonCreator
    public Shift(@JsonProperty("startDayOfWeek") String startDayOfWeekString,
                 @JsonProperty("startTime") String startTimeString,
                 @JsonProperty("endDayOfWeek") String endDayOfWeekString,
                 @JsonProperty("endTime") String endTimeString) {
        try {
            startDayOfWeek = DayOfWeek.valueOf(startDayOfWeekString);
            startTime = LocalTime.parse(startTimeString);
            endDayOfWeek = DayOfWeek.valueOf(endDayOfWeekString);
            endTime = LocalTime.parse(endTimeString);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        if (!isValidShift(startDayOfWeek, startTime, endDayOfWeek, endTime)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks if this shift conflicts with {@code otherShift}
     *
     * @param otherShift The shift to check for conflicts with.
     * @return true if the two shifts conflict, false otherwise.
     */
    public boolean conflictsWith(Shift otherShift) {
        int thisStartDayOfWeek = this.startDayOfWeek.getValue();
        int thisEndDayOfWeek = this.endDayOfWeek.getValue();
        int otherStartDayOfWeek = otherShift.startDayOfWeek.getValue();
        int otherEndDayOfWeek = otherShift.endDayOfWeek.getValue();

        if (thisStartDayOfWeek <= thisEndDayOfWeek && otherStartDayOfWeek <= otherEndDayOfWeek) {
            return !(thisEndDayOfWeek < otherStartDayOfWeek || otherEndDayOfWeek < thisStartDayOfWeek
                    || (thisEndDayOfWeek == otherStartDayOfWeek && !this.endTime.isAfter(otherShift.startTime))
                    || (otherEndDayOfWeek == thisStartDayOfWeek && !otherShift.endTime.isAfter(this.startTime)));
        } else if (thisStartDayOfWeek <= thisEndDayOfWeek && otherStartDayOfWeek > otherEndDayOfWeek) {
            return !((otherEndDayOfWeek < thisStartDayOfWeek && thisEndDayOfWeek < otherStartDayOfWeek)
                    || (otherEndDayOfWeek < thisStartDayOfWeek && thisEndDayOfWeek == otherStartDayOfWeek
                    && (!this.endTime.isAfter(otherShift.startTime)))
                    || (otherEndDayOfWeek == thisStartDayOfWeek && thisEndDayOfWeek < otherStartDayOfWeek
                    && (!otherShift.endTime.isAfter(this.startTime))));
        } else if (thisStartDayOfWeek > thisEndDayOfWeek && otherStartDayOfWeek <= otherEndDayOfWeek) {
            return !((thisEndDayOfWeek < otherStartDayOfWeek && otherEndDayOfWeek < thisStartDayOfWeek)
                    || (thisEndDayOfWeek < otherStartDayOfWeek && otherEndDayOfWeek == thisStartDayOfWeek
                    && (!otherShift.endTime.isAfter(this.startTime)))
                    || (thisEndDayOfWeek == otherStartDayOfWeek && otherEndDayOfWeek < thisStartDayOfWeek
                    && (!this.endTime.isAfter(otherShift.startTime))));
        }
        return true;
    }

    /**
     * Returns true if a shift is valid.
     */
    public static boolean isValidShift(DayOfWeek startDayOfWeek, LocalTime startTime,
                                       DayOfWeek endDayOfWeek, LocalTime endTime) {
        return (!startDayOfWeek.equals(endDayOfWeek) || endTime.isAfter(startTime));
    }

    public DayOfWeek getStartDayOfWeek() {
        return this.startDayOfWeek;
    }

    public DayOfWeek getEndDayOfWeek() {
        return this.endDayOfWeek;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    @Override
    public int compareTo(Shift otherShift) {
        if (startDayOfWeek == otherShift.startDayOfWeek) {
            return startTime.compareTo(otherShift.startTime);
        } else {
            return startDayOfWeek.compareTo(otherShift.startDayOfWeek);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Shift) {
            return startDayOfWeek.equals(((Shift) other).startDayOfWeek)
                    && endDayOfWeek.equals(((Shift) other).endDayOfWeek)
                    && startTime.equals(((Shift) other).startTime)
                    && endTime.equals(((Shift) other).endTime);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s, %s to %s, %s", startDayOfWeek, startTime, endDayOfWeek, endTime);
    }

}
