package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Appointment;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.ShiftRoster;
import seedu.address.model.person.staff.Staff;

/**
 * Deletes a staff identified using it's displayed index from the restaurant book.
 */
public class DeleteShiftCommand extends Command {

    public static final String COMMAND_WORD = "deleteshift";
    public static final String COMMAND_ALIAS = "dsh";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified shift from the staff identified by the index number used in the "
            + "displayed staff list.\n"
            + "The shift to be deleted must exist in the specified staff's shift roster.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_START_DAY_OF_WEEK + "START_DAY_OF_WEEK "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_DAY_OF_WEEK + "END_DAY_OF_WEEK "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_START_DAY_OF_WEEK + "MONDAY "
            + PREFIX_START_TIME + "12:00 "
            + PREFIX_END_DAY_OF_WEEK + "MONDAY "
            + PREFIX_END_TIME + "14:00 ";

    public static final String MESSAGE_DELETE_SHIFT_SUCCESS = "Deleted Shift: %1$s";
    public static final String MESSAGE_SHIFT_DOES_NOT_EXIST = "The specified shift does not exist!";

    private final Index targetIndex;
    private Shift toDelete;

    public DeleteShiftCommand(Index targetIndex, Shift toDelete) {
        this.targetIndex = targetIndex;
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Staff> lastShownList = model.getFilteredStaffList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToDeleteShift = lastShownList.get(targetIndex.getZeroBased());
        Staff staffWithShiftDeleted = deleteShiftFromStaff(staffToDeleteShift);
        model.setStaff(staffToDeleteShift, staffWithShiftDeleted);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_DELETE_SHIFT_SUCCESS, staffWithShiftDeleted));
    }

    /**
     * Creates and returns a {@code Staff} with the shift {@code toDelete} deleted.
     */
    private Staff deleteShiftFromStaff(Staff staffToDeleteShift) throws CommandException {
        assert staffToDeleteShift != null;

        Name name = staffToDeleteShift.getName();
        Phone phone = staffToDeleteShift.getPhone();
        Email email = staffToDeleteShift.getEmail();
        Appointment appointment = staffToDeleteShift.getAppointment();
        ShiftRoster oldShiftRoster = staffToDeleteShift.getShiftRoster();

        if (!oldShiftRoster.containsShift(toDelete)) {
            throw new CommandException(MESSAGE_SHIFT_DOES_NOT_EXIST);
        }

        ShiftRoster shiftRosterWithShiftDeleted = oldShiftRoster.deleteShift(toDelete);

        return new Staff(name, phone, email, appointment, shiftRosterWithShiftDeleted);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteShiftCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteShiftCommand) other).targetIndex))
                && toDelete.equals(((DeleteShiftCommand) other).toDelete); // state check
    }
}
