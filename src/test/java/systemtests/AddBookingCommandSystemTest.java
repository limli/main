package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.booking.AddBookingCommand.MESSAGE_DUPLICATE;
import static seedu.address.logic.commands.booking.AddBookingCommand.MESSAGE_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BENSON;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.booking.UpdateCapacityCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.booking.AddBookingCommandParser;
import seedu.address.logic.parser.DeleteMemberCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.booking.Capacity;
import seedu.address.model.person.Phone;
import seedu.address.model.person.member.Member;
import seedu.address.testutil.BookingUtil;

public class AddBookingCommandSystemTest extends RestaurantBookSystemTest {

    // TODO: write better test cases
    @Test
    public void add() {
        Model model = getModel();
        CommandHistory commandHistory = new CommandHistory();
        List<Booking> expectedBookingList;

        /* ------------------------ Perform addbooking operations on the shown unfiltered list ---------------------- */

        /* Case: add a member without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        try {
            // initialize the capacity to 13
            new UpdateCapacityCommand(new Capacity(13)).execute(model, commandHistory);

            final String startTimeString1430 = "2019-02-23T14:30";
            final BookingWindow startTime1430 = ParserUtil.parseBookingWindow(startTimeString1430);

            final String startTimeString1400 = "2019-02-23T14:00";
            final BookingWindow startTime1400 = ParserUtil.parseBookingWindow(startTimeString1400);

            String commandString = BookingUtil.getAddBookingCommand(startTimeString1430, Index.fromOneBased(1), 5);
            Booking aliceBooking = new Booking(startTime1430, ALICE, new BookingSize(5));
            expectedBookingList = Arrays.asList(aliceBooking);



            // Add first booking, should pass
            assertCommandSuccess(commandString, model, commandHistory, expectedBookingList);

            // Add duplicate booking, should fail
            assertCommandFailure(commandString, model, commandHistory, MESSAGE_DUPLICATE);

            commandString = BookingUtil.getAddBookingCommand(startTimeString1400, Index.fromOneBased(1), 5);
            Booking alice1400 = new Booking(startTime1400, ALICE, new BookingSize(5));
            expectedBookingList = Arrays.asList(alice1400, aliceBooking);

            // Add booking at different time, booking should be added
            assertCommandSuccess(commandString, model, commandHistory, expectedBookingList);

            Member modifiedAlice = new Member(ALICE.getName(), new Phone("12345678"), ALICE.getEmail(),
                    ALICE.getLoyaltyPoints());
            String editCommandString = " 1 " + PREFIX_PHONE + modifiedAlice.getPhone().toString();
            Command editCommand = new EditCommandParser().parse(editCommandString);
            editCommand.execute(model, commandHistory);
            Booking modifiedAlice1400 = new Booking(startTime1400, modifiedAlice, new BookingSize(5));
            Booking modifiedAliceBooking = new Booking(startTime1430, modifiedAlice, new BookingSize(5));
            expectedBookingList = Arrays.asList(modifiedAlice1400, modifiedAliceBooking);

            // Modify Alice's phone number, booking list should be updated accordingly
            assertEquals(model.getFilteredBookingList(), expectedBookingList);

            // Restaurant is has 10 persons, capacity of 13 and should not accept booking of 4
            commandString = BookingUtil.getAddBookingCommand(startTimeString1400, Index.fromOneBased(2), 4);
            String expectedMessage = String.format(MESSAGE_FULL, "2019-02-23T15:00");
            assertCommandFailure(commandString, model, commandHistory, expectedMessage);

            // But the restaurant should be able to accept a booking of 3
            commandString = BookingUtil.getAddBookingCommand(startTimeString1400, Index.fromOneBased(2), 3);
            Booking benson1400 = new Booking(startTime1400, BENSON, new BookingSize(3));
            expectedBookingList = Arrays.asList(modifiedAlice1400, benson1400, modifiedAliceBooking);
            assertCommandSuccess(commandString, model, commandHistory, expectedBookingList);

            String deleteAliceCommandString = " 1";
            Command deleteAliceCommand = new DeleteMemberCommandParser().parse(deleteAliceCommandString);
            deleteAliceCommand.execute(model, commandHistory);
            expectedBookingList = Arrays.asList(benson1400);

            // All bookings made by Alice should be removed once Alice is deleted, benson's bookings should remain
            assertEquals(model.getFilteredBookingList(), expectedBookingList);

        } catch (ParseException e) {
            throw new AssertionError("Parsing should not fail.");
        } catch (CommandException e) {
            throw new AssertionError("Command should successfully execute.");
        }
    }

    /**
     * Asserts that the command is successfully executed and the model's new list matches {@code expectedBookingList}
     * @param commandString the command to execute as a String
     * @param expectedBookingList the bookinglist to match
     */
    private void assertCommandSuccess(
            String commandString, Model model, CommandHistory commandHistory, List<Booking> expectedBookingList) {
        try {
            Command command = new AddBookingCommandParser().parse(commandString);
            command.execute(model, commandHistory);
            assertEquals(expectedBookingList, model.getFilteredBookingList());
        } catch (CommandException e) {
            throw new AssertionError("Command should not fail to execute.");
        } catch (ParseException e) {
            throw new AssertionError("Parsing should not fail.");
        }
    }

    /**
     * Asserts that the command fails to execute and throws CommandException
     * @param commandString the command to execute as a String
     */
    private void assertCommandFailure(
            String commandString, Model model, CommandHistory commandHistory, String exceptionMessage) {
        try {
            Command command = new AddBookingCommandParser().parse(commandString);
            command.execute(model, commandHistory);
            throw new AssertionError("Execution should throw CommandException.");
        } catch (CommandException e) {
            assertEquals(exceptionMessage, e.getMessage());
        } catch (ParseException e) {
            throw new AssertionError("Parsing should not fail.");
        }
    }

}
