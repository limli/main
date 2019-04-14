package seedu.address.logic.parser.booking;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.booking.UpdateCapacityCommand;
import seedu.address.model.booking.Capacity;

/**
 * Checks if capacity is properly parsed by the {@code UpdateCapacityCommandParser}
 */
public class UpdateCapacityCommandParserTest {
    private UpdateCapacityCommandParser parser = new UpdateCapacityCommandParser();

    @Test
    public void parse_validArgs_returnsUpdateCapacityCommand() {

        Capacity capacity = new Capacity(50);
        assertParseSuccess(parser, "50", new UpdateCapacityCommand(capacity));

        capacity = new Capacity(10000);
        assertParseSuccess(parser, "10000", new UpdateCapacityCommand(capacity));

        capacity = new Capacity(1);
        assertParseSuccess(parser, "1", new UpdateCapacityCommand(capacity));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", Capacity.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "10001", Capacity.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "0", Capacity.MESSAGE_CONSTRAINTS);
    }
}
