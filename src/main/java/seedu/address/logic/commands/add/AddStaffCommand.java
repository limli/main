package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.staff.Staff;

/**
 * A command that adds a staff to the restaurant book.
 */
public class AddStaffCommand extends Command {
    public static final String COMMAND_WORD = "addstaff";
    public static final String COMMAND_ALIAS = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a staff to the restaurant.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_APPOINTMENT + "APPOINTMENT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jane Smith "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "jsmith@example.com "
            + PREFIX_APPOINTMENT + "Server";

    public static final String MESSAGE_SUCCESS = "New staff added: %1$s";
    public static final String MESSAGE_DUPLICATE = "This staff already exists in the restaurant!";

    private Staff toAdd;
    public AddStaffCommand(Staff staff) {
        requireNonNull(staff);
        toAdd = staff;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasStaff(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.addStaff(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddStaffCommand // instanceof handles nulls
                && toAdd.equals(((AddStaffCommand) other).toAdd));
    }
}
