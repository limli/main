package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.booking.AddBookingCommand;
import seedu.address.logic.commands.booking.DeleteBookingCommand;
import seedu.address.logic.commands.booking.EditBookingCommand;
import seedu.address.logic.commands.booking.UpdateCapacityCommand;
import seedu.address.logic.commands.ingredient.AddIngredientCommand;
import seedu.address.logic.commands.ingredient.ConsumeIngredientCommand;
import seedu.address.logic.commands.ingredient.DeleteIngredientCommand;
import seedu.address.logic.commands.ingredient.ListIngredientsCommand;
import seedu.address.logic.commands.ingredient.RestockIngredientCommand;
import seedu.address.logic.commands.member.AddMemberCommand;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.commands.member.EditMemberCommand;
import seedu.address.logic.commands.member.ListMembersCommand;
import seedu.address.logic.commands.recipe.AddRecipeCommand;
import seedu.address.logic.commands.recipe.DeleteRecipeCommand;
import seedu.address.logic.commands.staff.AddShiftCommand;
import seedu.address.logic.commands.staff.AddStaffCommand;
import seedu.address.logic.commands.staff.DeleteShiftCommand;
import seedu.address.logic.commands.staff.DeleteStaffCommand;
import seedu.address.logic.commands.staff.EditStaffCommand;
import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.logic.commands.stats.ViewStatsDaysCommand;
import seedu.address.logic.commands.stats.ViewStatsTimeCommand;
import seedu.address.logic.parser.booking.AddBookingCommandParser;
import seedu.address.logic.parser.booking.DeleteBookingCommandParser;
import seedu.address.logic.parser.booking.EditBookingCommandParser;
import seedu.address.logic.parser.booking.UpdateCapacityCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.ingredient.AddIngredientCommandParser;
import seedu.address.logic.parser.ingredient.ConsumeIngredientCommandParser;
import seedu.address.logic.parser.ingredient.DeleteIngredientCommandParser;
import seedu.address.logic.parser.ingredient.ListIngredientsCommandParser;
import seedu.address.logic.parser.ingredient.RestockIngredientCommandParser;
import seedu.address.logic.parser.member.AddMemberCommandParser;
import seedu.address.logic.parser.member.DeleteMemberCommandParser;
import seedu.address.logic.parser.member.EditMemberCommandParser;
import seedu.address.logic.parser.member.ListMembersCommandParser;
import seedu.address.logic.parser.recipe.AddRecipeCommandParser;
import seedu.address.logic.parser.recipe.DeleteRecipeCommandParser;
import seedu.address.logic.parser.staff.AddShiftCommandParser;
import seedu.address.logic.parser.staff.AddStaffCommandParser;
import seedu.address.logic.parser.staff.DeleteShiftCommandParser;
import seedu.address.logic.parser.staff.DeleteStaffCommandParser;
import seedu.address.logic.parser.staff.EditStaffCommandParser;
import seedu.address.logic.parser.staff.ListStaffCommandParser;
import seedu.address.logic.parser.stats.ViewStatsDaysCommandParser;
import seedu.address.logic.parser.stats.ViewStatsTimeCommandParser;

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
            return new EditMemberCommandParser().parse(arguments);

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

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case ListMembersCommand.COMMAND_WORD:
            return new ListMembersCommandParser().parse(arguments);

        case ListStaffCommand.COMMAND_WORD:
        case ListStaffCommand.COMMAND_ALIAS:
            return new ListStaffCommandParser().parse(arguments);

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
