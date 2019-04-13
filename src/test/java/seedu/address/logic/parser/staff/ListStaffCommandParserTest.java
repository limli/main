package seedu.address.logic.parser.staff;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.model.person.staff.StaffPredicate;

public class ListStaffCommandParserTest {

    private ListStaffCommandParser parser = new ListStaffCommandParser();

    @Test
    public void parse_emptyArg_returnsListStaffCommand() {
        ListStaffCommand expectedListStaffCommand =
                new ListStaffCommand(new StaffPredicate(new ArrayList<>(), new ArrayList<>()));
        assertParseSuccess(parser, "     ", expectedListStaffCommand);
        assertParseSuccess(parser, " " + PREFIX_NAME + "     ", expectedListStaffCommand);
    }

    @Test
    public void parse_validArgs_returnsListStaffCommand() {
        ListStaffCommand expectedListStaffCommand =
                new ListStaffCommand(new StaffPredicate(Arrays.asList("Alice", "Bob"),
                        Arrays.asList("Cook", "Server")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "  Alice   Bob    "
                + PREFIX_APPOINTMENT + "  Cook   Server  ", expectedListStaffCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "   badargs ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListStaffCommand.MESSAGE_USAGE));
    }

}
