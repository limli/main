package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.member.Member;

/**
 * Deletes a member identified using it's displayed index from the address book.
 */
public class DeleteMemberCommand extends Command {

    public static final String COMMAND_WORD = "deletemember";
    public static final String COMMAND_ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member identified by the index number used in the displayed member list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEMBER_SUCCESS = "Deleted Member: %1$s";
    public static final String MESSAGE_BOOKINGS_DELETED = "%1$s booking(s) made by the member was deleted.";

    private final Index targetIndex;

    public DeleteMemberCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        Member memberToDelete = lastShownList.get(targetIndex.getZeroBased());
        int bookingsDeleted = model.countBookings(memberToDelete);
        model.deleteMember(memberToDelete);
        model.commitRestaurantBook();
        if (bookingsDeleted == 0) {
            return new CommandResult(String.format(MESSAGE_DELETE_MEMBER_SUCCESS, memberToDelete));
        } else {
            String displayMessage = String.format(MESSAGE_DELETE_MEMBER_SUCCESS, memberToDelete) + "\n"
                    + String.format(MESSAGE_BOOKINGS_DELETED, bookingsDeleted);
            return new CommandResult(displayMessage);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMemberCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteMemberCommand) other).targetIndex)); // state check
    }
}
