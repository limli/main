package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.logic.commands.booking.EditBookingCommand.EditBookingDescriptor;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;

/**
 * A utility class to help with building EditBookingDescriptor objects.
 */
public class EditBookingDescriptorBuilder {

    private EditBookingDescriptor descriptor;

    public EditBookingDescriptorBuilder() {
        descriptor = new EditBookingDescriptor();
    }

    public EditBookingDescriptorBuilder(EditBookingDescriptor descriptor) {
        this.descriptor = new EditBookingDescriptor(descriptor);
    }

    public EditBookingDescriptorBuilder(Booking booking) {
        this(booking.getNumMembers(), booking.getBookingWindow());
    }

    public EditBookingDescriptorBuilder(BookingSize bookingSize, BookingWindow bookingWindow) {
        descriptor = new EditBookingDescriptor();
        descriptor.setBookingSize(bookingSize);
        descriptor.setBookingWindow(bookingWindow);
    }

    /**
     * Sets the {@code BookingSize} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withBookingSize(int bookingSize) {
        descriptor.setBookingSize(new BookingSize(bookingSize));
        return this;
    }

    /**
     * Sets the {@code} BookingWindow} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withBookingWindow(LocalDateTime startTime) {
        descriptor.setBookingWindow(new BookingWindow(startTime));
        return this;
    }

    public EditBookingDescriptor build() {
        return descriptor;
    }
}
