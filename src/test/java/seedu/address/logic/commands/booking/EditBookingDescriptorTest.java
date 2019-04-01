package seedu.address.logic.commands.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.logic.commands.EditBookingCommand.EditBookingDescriptor;

import seedu.address.model.booking.BookingWindow;
import seedu.address.testutil.EditBookingDescriptorBuilder;

public class EditBookingDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        // nothing edited
        assertFalse(new EditBookingDescriptor().isAnyFieldEdited());

        // bookingWindow edited
        EditBookingDescriptor windowOnlyEdited =
                new EditBookingDescriptorBuilder().withBookingWindow(LocalDateTime.now()).build();
        windowOnlyEdited.setBookingWindow(new BookingWindow(LocalDateTime.now()));
        assertTrue(windowOnlyEdited.isAnyFieldEdited());

        // bookingSize edited
        EditBookingDescriptor sizeOnlyEdited = new EditBookingDescriptorBuilder().withBookingSize(2).build();
        assertTrue(sizeOnlyEdited.isAnyFieldEdited());

        // both edited
        EditBookingDescriptor bothEdited = new EditBookingDescriptorBuilder().withBookingSize(2)
                .withBookingWindow(LocalDateTime.now()).build();
        assertTrue(bothEdited.isAnyFieldEdited());
    }

    @Test
    public void equals() {

        EditBookingDescriptor ebd = new EditBookingDescriptorBuilder().withBookingSize(5).build();

        // equals to itself
        assertEquals(ebd, ebd);

        // check the copy constructor
        assertEquals(ebd, new EditBookingDescriptor(ebd));

        // different values, returns false
        EditBookingDescriptor ebdModified = new EditBookingDescriptorBuilder(ebd).withBookingSize(6).build();
        assertNotEquals(ebdModified, ebd);

        // test for null
        assertNotEquals(ebd, null);

        // test for other types
        assertNotEquals(ebd, "trash");
        assertNotEquals(ebd, 0);
    }
}
