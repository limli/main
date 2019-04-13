package seedu.address.model;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.model.person.member.Member;

/**
 * The API that stores the booking side of the model.
 */
public interface BookingModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Booking> PREDICATE_SHOW_ALL_BOOKINGS = unused -> true;

    /**
     * Returns true if a booking with the same identity as {@code booking} exists in the restaurant book.
     */
    boolean hasBooking(Booking booking);

    /**
     * Returns true if {@code booking} can be added to the restaurant without exceeding capacity.
     */
    boolean canAccommodate(Booking booking);

    /**
     * Deletes the given booking.
     * The item must exist in the restaurant book.
     */
    void deleteBooking(Booking target);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the restaurant book.
     */
    void addBooking(Booking booking);

    /**
     * Replaces the given booking {@code target} with {@code editedBooking}.
     * {@code target} must exist in the restaurant book.
     * The booking identity of {@code editedBooking}
     * must not be the same as another existing booking in the restaurant book.
     * @throws DuplicateItemException if editing will result in a duplicate booking. In that case,
     * no bookings will be modified. Note that we cannot simply use
     * {@code !target.isSameItem(editedBooking) && hasPerson(editedBooking)} as that assumes transitivity
     * of the isSameItem operator.
     */
    void setBooking(Booking target, Booking editedBooking) throws DuplicateItemException;

    /**
     * Determines if editing the booking will cause the restaurant to be overbooked
     */
    boolean canAccommodateEdit(Booking target, Booking editedBooking);

    /** Returns an unmodifiable view of the filtered booking list */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Updates the filter of the filtered booking list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookingList(Predicate<Booking> predicate);

    /**
     * Selected booking in the filtered booking list.
     * null if no booking is selected.
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Returns the selected booking in the filtered booking list.
     * null if no booking is selected.
     */
    Booking getSelectedBooking();

    /**
     * Sets the selected booking in the filtered booking list.
     */
    void setSelectedBooking(Booking booking);

    /**
     * Gets the capacity of the restaurant.
     */
    Capacity getCapacity();

    /**
     * Sets the capacity of the restaurant.
     */
    void setCapacity(Capacity newCapacity);

    /**
     * Returns true if and only if changing the capacity to {@code newCapacity} will not result in overbooking
     */
    boolean canUpdateCapacity(Capacity newCapacity);

    /**
     * Suggests a possible time to accommodate the booking.
     * @param toAdd The booking that the user wishes to add
     * @return The next available time that the restaurant can accommodate the booking, subjected to the constraint
     * that the returned time must occur after {@code toAdd}. In other words, suggestion always shifts the booking
     * later and never earlier.
     */
    LocalDateTime suggestNextAvailableTime(Booking toAdd);

    /**
     * Counts the number of bookings associated with {@code member}.
     */
    int countBookings(Member member);
}
