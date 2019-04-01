package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.BookingModel.PREDICATE_SHOW_ALL_BOOKINGS;
import static seedu.address.model.IngredientModel.PREDICATE_SHOW_ALL_INGREDIENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.address.model.StaffModel.PREDICATE_SHOW_ALL_STAFF;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoRestaurantBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoRestaurantBook();
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFF);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
