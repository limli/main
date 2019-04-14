package seedu.address.logic.commands.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.StaffModel.PREDICATE_SHOW_ALL_STAFF;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.ShiftRoster;
import seedu.address.model.person.staff.Staff;


/**
 * A command that adds a shift for a staff to the restaurant book.
 */
public class AddShiftCommand extends Command {
    public static final String COMMAND_WORD = "addshift";
    public static final String COMMAND_ALIAS = "ash";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a shift for a staff member identified "
            + "by the index number used in the displayed staff list.\n"
            + "The shift to be added should not clash with any existing shifts.\n"
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

    public static final String MESSAGE_SUCCESS = "Shift added: %1$s";
    public static final String MESSAGE_CLASH = "The new shift clashes with an existing shift!";

    private Shift toAdd;
    private Index index;


    public AddShiftCommand(Index index, Shift shift) {
        requireNonNull(shift);
        this.index = index;
        this.toAdd = shift;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Staff> lastShownList = model.getFilteredStaffList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToAddShift = lastShownList.get(index.getZeroBased());
        ShiftRoster shiftRoster = staffToAddShift.getShiftRoster();
        if (shiftRoster.conflictsWith(toAdd)) {
            throw new CommandException(MESSAGE_CLASH);
        }
        Staff staffWithShiftAdded = createStaffWithShiftAdded(staffToAddShift, toAdd);

        try {
            model.setStaff(staffToAddShift, staffWithShiftAdded);
        } catch (DuplicateItemException e) {
            throw new CommandException(MESSAGE_CLASH);
        }

        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFF);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, staffWithShiftAdded));
    }

    /**
     * Creates and returns a {@code Staff} with {@code shift} added to {@code staffToAddShift}.
     */
    private static Staff createStaffWithShiftAdded(Staff staffToAddShift, Shift shift) {
        assert staffToAddShift != null;

        ShiftRoster newShiftRoster = staffToAddShift.getShiftRoster().addShift(shift);
        return new Staff(staffToAddShift.getName(),
                staffToAddShift.getPhone(),
                staffToAddShift.getEmail(),
                staffToAddShift.getAppointment(),
                newShiftRoster);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddShiftCommand // instanceof handles nulls
                && toAdd.equals(((AddShiftCommand) other).toAdd)
                && index.equals(((AddShiftCommand) other).index));
    }
}
