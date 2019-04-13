package seedu.address.logic.commands.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOYALTY_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.member.NameAndLoyaltyPointsPredicate;

/**
 * Finds and lists all staff in restaurant book whose name contains any of the argument keywords, and if specified,
 * only staff with appointments that contain any of the argument keywords and only staff who work shifts in a
 * certain day and time range.
 */
public class ListStaffCommand extends Command {

    public static final String COMMAND_WORD = "liststaff";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all staff whose names contain any of "
            + "the list of words (case-insensitive).\n."
            + "If appointment is specified, then only staff whose appointments contain any of "
            + "the list of words (case-insensitive) will be displayed.\n"
            + "If shift is specified, then only staff whose shift occurs within the time range specified "
            + "will be displayed.\n"
            + "If fields are not specified, then all staff will be listed.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME_1 NAME_2...] "
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT]"
            + "[" + PREFIX_START_DAY_OF_WEEK + " START_DAY_OF_WEEK]"
            + "[" + PREFIX_START_TIME + " START_TIME]"
            + "[" + PREFIX_END_DAY_OF_WEEK + " END_DAY_OF_WEEK]"
            + "[" + PREFIX_END_TIME + " END_TIME]\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie a/cook sd/MONDAY ts/12:00 ed/TUESDAY te/18:00";

    private final StaffPredicate predicate;

    public ListStaffCommand(StaffPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredStaffList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STAFF_LISTED_OVERVIEW,
                        model.getFilteredMemberList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListStaffCommand // instanceof handles nulls
                && predicate.equals(((ListStaffCommand) other).predicate)); // state check
    }
}
