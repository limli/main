package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConsumeIngredientCommand;
import seedu.address.logic.commands.DeleteBookingCommand;
import seedu.address.logic.commands.DeleteIngredientCommand;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.DeleteRecipeCommand;
import seedu.address.logic.commands.DeleteShiftCommand;
import seedu.address.logic.commands.DeleteStaffCommand;
import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.commands.EditStaffCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListIngredientsCommand;
import seedu.address.logic.commands.ListMembersCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RestockIngredientCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCapacityCommand;
import seedu.address.logic.commands.ViewStatsDaysCommand;
import seedu.address.logic.commands.ViewStatsTimeCommand;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.commands.add.AddIngredientCommand;
import seedu.address.logic.commands.add.AddMemberCommand;
import seedu.address.logic.commands.add.AddRecipeCommand;
import seedu.address.logic.commands.add.AddShiftCommand;
import seedu.address.logic.commands.add.AddStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RestaurantBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddMemberCommand.COMMAND_WORD:
        case AddMemberCommand.COMMAND_ALIAS:
            return new AddMemberCommandParser().parse(arguments);

        case AddBookingCommand.COMMAND_WORD:
        case AddBookingCommand.COMMAND_ALIAS:
            return new AddBookingCommandParser().parse(arguments);

        case AddIngredientCommand.COMMAND_WORD:
        case AddIngredientCommand.COMMAND_ALIAS:
            return new AddIngredientCommandParser().parse(arguments);

        case AddRecipeCommand.COMMAND_WORD:
        case AddRecipeCommand.COMMAND_ALIAS:
            return new AddRecipeCommandParser().parse(arguments);

        case AddStaffCommand.COMMAND_WORD:
        case AddStaffCommand.COMMAND_ALIAS:
            return new AddStaffCommandParser().parse(arguments);

        case AddShiftCommand.COMMAND_WORD:
        case AddShiftCommand.COMMAND_ALIAS:
            return new AddShiftCommandParser().parse(arguments);

        case UpdateCapacityCommand.COMMAND_WORD:
            return new UpdateCapacityCommandParser().parse(arguments);

        case EditMemberCommand.COMMAND_WORD:
        case EditMemberCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case EditBookingCommand.COMMAND_WORD:
        case EditBookingCommand.COMMAND_ALIAS:
            return new EditBookingCommandParser().parse(arguments);

        case EditStaffCommand.COMMAND_WORD:
        case EditStaffCommand.COMMAND_ALIAS:
            return new EditStaffCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD:
        case DeleteMemberCommand.COMMAND_ALIAS:
            return new DeleteMemberCommandParser().parse(arguments);

        case DeleteBookingCommand.COMMAND_WORD:
        case DeleteBookingCommand.COMMAND_ALIAS:
            return new DeleteBookingCommandParser().parse(arguments);

        case DeleteIngredientCommand.COMMAND_WORD:
        case DeleteIngredientCommand.COMMAND_ALIAS:
            return new DeleteIngredientCommandParser().parse(arguments);

        case DeleteRecipeCommand.COMMAND_WORD:
        case DeleteRecipeCommand.COMMAND_ALIAS:
            return new DeleteRecipeCommandParser().parse(arguments);

        case DeleteStaffCommand.COMMAND_WORD:
        case DeleteStaffCommand.COMMAND_ALIAS:
            return new DeleteStaffCommandParser().parse(arguments);

        case DeleteShiftCommand.COMMAND_WORD:
        case DeleteShiftCommand.COMMAND_ALIAS:
            return new DeleteShiftCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case ListMembersCommand.COMMAND_WORD:
            return new ListMembersCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case RestockIngredientCommand.COMMAND_WORD:
        case RestockIngredientCommand.COMMAND_ALIAS:
            return new RestockIngredientCommandParser().parse(arguments);

        case ConsumeIngredientCommand.COMMAND_WORD:
        case ConsumeIngredientCommand.COMMAND_ALIAS:
            return new ConsumeIngredientCommandParser().parse(arguments);

        case ListIngredientsCommand.COMMAND_WORD:
            return new ListIngredientsCommandParser().parse(arguments);

        case ViewStatsDaysCommand.COMMAND_WORD:
            return new ViewStatsDaysCommandParser().parse(arguments);

        case ViewStatsTimeCommand.COMMAND_WORD:
            return new ViewStatsTimeCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
