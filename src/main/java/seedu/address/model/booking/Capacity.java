package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to ensure that all bookings stays within the capacity of the restaurant.
 * The restaurant will not be able to hold more bookings if the capacity is exceeded.
 */
public class Capacity {
    public static final int MAX_CAPACITY = 10000;
    public static final int DEFAULT_CAPACITY = 200;
    public static final String MESSAGE_CONSTRAINTS = "Capacity should be an integer between 1 and "
            + MAX_CAPACITY + " inclusive.";
    private final int value;

    public Capacity(int intCapacity) {
        checkArgument(isValidCapacity(intCapacity), MESSAGE_CONSTRAINTS);
        value = intCapacity;
    }

    public Capacity(String strCapacity) {
        checkArgument(isValidCapacity(strCapacity), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(strCapacity);
    }

    /**
     * Represents the default capacity if no particular value has been chosen.
     */
    public static Capacity getDefaultCapacity() {
        return new Capacity(DEFAULT_CAPACITY);
    }

    public int getValue() {
        return value;
    }

    /**
     * Checks if the capacity is valid.
     */
    public static boolean isValidCapacity(int intCapacity) {
        return intCapacity > 0 && intCapacity <= MAX_CAPACITY;
    }

    /**
     * Checks if strCapacity is valid after converting to an integer.
     */
    public static boolean isValidCapacity(String strCapacity) {
        requireNonNull(strCapacity);
        try {
            return isValidCapacity(Integer.parseInt(strCapacity));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the capacity is sufficient to hold the bookings.
     * Guarantees that the list {@code bookings} does not change.
     */
    public boolean canAccommodate(List<Booking> bookings) {
        List<Event> events = new ArrayList<>();
        for (Booking booking: bookings) {
            Event arrival = new Event(booking.getNumMembers().getSize(), booking.getStartTime());
            Event departure = new Event(-booking.getNumMembers().getSize(), booking.getEndTime());
            events.add(arrival);
            events.add(departure);
        }

        events.sort(Comparator.naturalOrder());
        int currentOccupancy = 0;
        for (Event event: events) {
            currentOccupancy += event.changeInPersons;
            if (currentOccupancy > value) { // if this happens, the restaurant is full at this time
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if adding the booking {@code} toAdd will result in exceeding the capacity.
     * Guarantees that the list {@code existingBookings} does not change. In particular, the booking will not be added
     * regardless of whether the method returns true or false.
     */
    public boolean canAddBooking(Booking toAdd, List<Booking> existingBookings) {
        // make a copy so that the input list is not modified
        List<Booking> copyExistingBookings = new ArrayList<>(existingBookings);
        copyExistingBookings.add(toAdd);
        return canAccommodate(copyExistingBookings);
    }

    /**
     * Suggests a possible time to accommodate the booking. {@code toAdd} cannot contain more members than the capacity.
     * Pre-condition 1: toAdd.getNumMembers().getSize() > value. In other words, the number of persons cannot exceed
     * the capacity value.
     * Pre-condition 2: {@code existingBookings} must fit within the capacity (as described by {@code canAccommodate})
     * @param toAdd The booking that the user wishes to add
     * @param existingBookings The current list of bookings.
     * @return The next available time that the restaurant can accommodate the booking, subjected to the constraint
     * that the returned time must occur after {@code toAdd}. In other words, suggestion always shifts the booking
     * later and never earlier.
     */
    public LocalDateTime suggestNextAvailableTime(Booking toAdd, List<Booking> existingBookings) {
        if (!canAccommodate(existingBookings)) {
            throw new IllegalArgumentException("The current booking list does not fit in the capacity.");
        }

        if (toAdd.getNumMembers().getSize() > value) {
            throw new IllegalArgumentException("This booking cannot be accepted.");
        }

        assert(canAccommodate(existingBookings));
        if (canAddBooking(toAdd, existingBookings)) {
            return toAdd.getStartTime();
        }

        // We only need to check the timings which correspond to end time of another booking
        // The best time for a customer to arrive is when another customer leaves
        List<LocalDateTime> possibleTimings =
                existingBookings.stream().map(Booking::getEndTime).sorted().collect(Collectors.toList());
        for (LocalDateTime endTime : possibleTimings) {
            if (endTime.isAfter(toAdd.getStartTime())) {
                BookingWindow suggestedBookingWindow = new BookingWindow(endTime);
                Booking suggestedBooking =
                        new Booking(suggestedBookingWindow, toAdd.getCustomer(), toAdd.getNumMembers());
                if (canAddBooking(suggestedBooking, existingBookings)) {
                    return endTime;
                }
            }
        }
        // as the pre-conditions are checked, it is impossible to loop through all the possible timings without
        // returning an answer
        assert(false);
        throw new IllegalArgumentException("This booking cannot be accepted.");
    }

    /**
     * Represents the departure or arrival of customers at a specific time.
     */
    private class Event implements Comparable<Event> {
        final int changeInPersons;
        final LocalDateTime eventTime;
        // For changeInPersons, positive values represent arrivals while negative values represent departures.
        Event(int changeInPersons, LocalDateTime eventTime) {
            this.changeInPersons = changeInPersons;
            this.eventTime = eventTime;
        }

        @Override
        public int compareTo(Event other) {
            if (eventTime.compareTo(other.eventTime) != 0) {
                return eventTime.compareTo(other.eventTime);
            } else {
                return changeInPersons - other.changeInPersons; // important: ensure that departures occur first
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Capacity // instanceof handles nulls
                && value == ((Capacity) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
