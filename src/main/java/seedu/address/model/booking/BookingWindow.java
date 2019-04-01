package seedu.address.model.booking;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A booking window that represents the start and end times of a booking.
 */
public class BookingWindow implements Comparable<BookingWindow> {


    public static final String MESSAGE_CONSTRAINTS =
            "Please follow the time format of yyyy-MM-ddTHH:mm (e.g. 2019-03-12T12:00) or HH:mm (e.g. 12:00)";

    private static final Duration DEFAULT_DURATION = Duration.ofHours(1);

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Constructs a {@code BookingWindow}.
     *
     * @param startTimeString A valid date in the yyyy-MM-ddTHH:mm format or HH:mm format. Input follows HH:mm format,
     *                        the current date will be used.
     */
    public BookingWindow(String startTimeString) {
        if (isValidDateTime(startTimeString)) {
            startTime = LocalDateTime.parse(startTimeString);
        } else if (isValidTime(startTimeString)) {
            startTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(startTimeString));
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        endTime = startTime.plus(DEFAULT_DURATION);
    }

    public BookingWindow(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plus(DEFAULT_DURATION);
    }

    /**
     * Checks if {@code stringToCheck} is a valid LocalDateTime.
     */
    private boolean isValidDateTime(String stringToCheck) {
        try {
            LocalDateTime.parse(stringToCheck);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Checks if {@code stringToCheck} is a valid LocalTime.
     */
    private boolean isValidTime(String stringToCheck) {
        try {
            LocalTime.parse(stringToCheck);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return startTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingWindow// instanceof handles nulls
                && startTime.equals(((BookingWindow) other).startTime)); // state check
    }

    @Override
    public int compareTo(BookingWindow other) {
        return startTime.compareTo(other.startTime);
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

}
