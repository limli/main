package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOYALTY_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListMembersCommand;
import seedu.address.model.person.NameAndLoyaltyPointsPredicate;

public class ListMembersCommandParserTest {

    private ListMembersCommandParser parser = new ListMembersCommandParser();

    @Test
    public void parse_emptyArg_returnsListMembersCommand() {
        ListMembersCommand expectedListMembersCommand =
                new ListMembersCommand(new NameAndLoyaltyPointsPredicate(new ArrayList<>(), 0));
        assertParseSuccess(parser, "     ", expectedListMembersCommand);
        assertParseSuccess(parser, " " + PREFIX_NAME + "     ", expectedListMembersCommand);
    }

    @Test
    public void parse_validArgs_returnsListMembersCommand() {
        ListMembersCommand expectedListMembersCommand =
                new ListMembersCommand(new NameAndLoyaltyPointsPredicate(Arrays.asList("Alice", "Bob"), 4));
        assertParseSuccess(parser, " " + PREFIX_NAME + "  Alice   Bob    "
                + PREFIX_LOYALTY_POINTS + "  4  ", expectedListMembersCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "   trash", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListMembersCommand.MESSAGE_USAGE));
    }

}
