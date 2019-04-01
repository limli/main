package seedu.address.logic.commands.booking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CustomerIndexedBooking;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.testutil.CustomerIndexedBookingBuilder;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for AddBookingCommand.
 */
public class AddBookingCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddBookingCommand(null);
    }

    @Test
    public void execute_bookingAcceptedByModel_addSuccessful() throws Exception {
        CustomerIndexedBooking validBooking = new CustomerIndexedBookingBuilder().build();
        CommandResult commandResult = new AddBookingCommand(validBooking).execute(model, commandHistory);
        Booking expectedBooking = validBooking.getBooking(model).get();

        assertEquals(String.format(AddBookingCommand.MESSAGE_SUCCESS, expectedBooking),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedBooking), model.getFilteredBookingList());
    }

    @Test
    public void execute_duplicateBooking_throwsCommandException() throws Exception {
        CustomerIndexedBooking validBooking = new CustomerIndexedBookingBuilder().build();
        AddBookingCommand addBookingCommand = new AddBookingCommand(validBooking);
        addBookingCommand.execute(model, commandHistory);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddBookingCommand.MESSAGE_DUPLICATE);
        addBookingCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_customerIndexOutOfBounds_throwsCommandException() throws Exception {
        Index outOfBoundsIndex = Index.fromZeroBased(model.getFilteredMemberList().size());
        CustomerIndexedBooking invalidBooking = new CustomerIndexedBookingBuilder().withIndex(outOfBoundsIndex).build();
        AddBookingCommand invalidAddBookingCommand = new AddBookingCommand(invalidBooking);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        invalidAddBookingCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        AddBookingCommand defaultBookingCommand =
                new AddBookingCommand(new CustomerIndexedBookingBuilder().build());
        AddBookingCommand duplicateDefaultBookingCommand =
                new AddBookingCommand(new CustomerIndexedBookingBuilder().build());

        AddBookingCommand differentBookingCommand =
                new AddBookingCommand(new CustomerIndexedBookingBuilder().withIndex(INDEX_SECOND).build());
        // same object -> equal
        assertEquals(defaultBookingCommand, defaultBookingCommand);

        // duplicate object -> equal
        assertEquals(defaultBookingCommand, duplicateDefaultBookingCommand);

        // change customer index -> not equal
        assertNotEquals(defaultBookingCommand, differentBookingCommand);

        // different type -> not equal
        assertNotEquals(defaultBookingCommand, 1);
        assertNotEquals(defaultBookingCommand, false);
    }
}
