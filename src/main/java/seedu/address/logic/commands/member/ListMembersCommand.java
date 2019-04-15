package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOYALTY_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.member.NameAndLoyaltyPointsPredicate;

/**
 * Finds and lists all members in restaurant book whose name contains any of the argument keywords
 * and has at least as many loyalty points as specified.
 * Keyword matching is case insensitive.
 */
public class ListMembersCommand extends Command {

    public static final String COMMAND_WORD = "listmembers";
    public static final String COMMAND_ALIAS = "lm";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all members whose names contain any of "
            + "the list of words (case-insensitive) and who has at least the specified amount of loyalty points.\n."
            + "If fields are not specified, then all members will be listed.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME_1 NAME_2...] [" + PREFIX_LOYALTY_POINTS + "MIN_LOYALTY_POINTS]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie l/10";

    private final NameAndLoyaltyPointsPredicate predicate;

    public ListMembersCommand(NameAndLoyaltyPointsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMemberList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW,
                        model.getFilteredMemberList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListMembersCommand // instanceof handles nulls
                && predicate.equals(((ListMembersCommand) other).predicate)); // state check
    }
}
