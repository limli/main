package seedu.address.logic.parser.staff;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.DayOfWeek;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.staff.AddShiftCommand;
import seedu.address.model.person.staff.Shift;
import seedu.address.testutil.ShiftBuilder;

public class AddShiftCommandParserTest {
    private AddShiftCommandParser parser = new AddShiftCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "MONDAY " + PREFIX_END_TIME + "14:00";

        // valid input
        assertParseSuccess(parser, userInput, new AddShiftCommand(targetIndex,
                new ShiftBuilder().withStartDayOfWeek(DayOfWeek.MONDAY).withStartTime("12:00")
                        .withEndDayOfWeek(DayOfWeek.MONDAY).withEndTime("14:00").build()));

        // last start day of week is taken
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_DAY_OF_WEEK + "TUESDAY " + PREFIX_START_TIME + "12:00 "
                + PREFIX_END_DAY_OF_WEEK + "SUNDAY " + PREFIX_END_TIME + "14:00";

        assertParseSuccess(parser, userInput, new AddShiftCommand(targetIndex,
                new ShiftBuilder().withStartDayOfWeek(DayOfWeek.TUESDAY).withStartTime("12:00")
                        .withEndDayOfWeek(DayOfWeek.SUNDAY).withEndTime("14:00").build()));

        // last start time is taken
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_START_TIME + "14:00 "
                + PREFIX_END_DAY_OF_WEEK + "SUNDAY " + PREFIX_END_TIME + "14:00";

        assertParseSuccess(parser, userInput, new AddShiftCommand(targetIndex,
                new ShiftBuilder().withStartDayOfWeek(DayOfWeek.MONDAY).withStartTime("14:00")
                        .withEndDayOfWeek(DayOfWeek.SUNDAY).withEndTime("14:00").build()));

        // last end day of week is taken
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "SUNDAY "
                + PREFIX_END_DAY_OF_WEEK + "SATURDAY " + PREFIX_END_TIME + "14:00";

        assertParseSuccess(parser, userInput, new AddShiftCommand(targetIndex,
                new ShiftBuilder().withStartDayOfWeek(DayOfWeek.MONDAY).withStartTime("12:00")
                        .withEndDayOfWeek(DayOfWeek.SATURDAY).withEndTime("14:00").build()));

        // last end time is taken
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "SUNDAY "
                + PREFIX_END_TIME + "14:00 " + PREFIX_END_TIME + "12:00";

        assertParseSuccess(parser, userInput, new AddShiftCommand(targetIndex,
                new ShiftBuilder().withStartDayOfWeek(DayOfWeek.MONDAY).withStartTime("12:00")
                        .withEndDayOfWeek(DayOfWeek.SUNDAY).withEndTime("12:00").build()));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddShiftCommand.MESSAGE_USAGE);

        // missing index
        String userInput = PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "SUNDAY "
                + PREFIX_END_TIME + "14:00 ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing start day of week prefix
        userInput = INDEX_FIRST.getOneBased() + " " + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "SUNDAY "
                + PREFIX_END_TIME + "14:00 ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing start time prefix
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + "12:00 " + PREFIX_END_DAY_OF_WEEK + "SUNDAY "
                + PREFIX_END_TIME + "14:00 ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing end day of week prefix
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + "SUNDAY "
                + PREFIX_END_TIME + "14:00 ";
        assertParseFailure(parser, userInput, expectedMessage);

        // all end time prefix
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "SUNDAY "
                + "14:00 ";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid start day of week
        String userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "JANUARY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "MONDAY " + PREFIX_END_TIME + "14:00";
        assertParseFailure(parser, userInput, Shift.MESSAGE_CONSTRAINTS);

        // invalid start time
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "25:00 " + PREFIX_END_DAY_OF_WEEK + "MONDAY " + PREFIX_END_TIME + "14:00";
        assertParseFailure(parser, userInput, Shift.MESSAGE_CONSTRAINTS);

        // invalid end day of week
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "JANUARY " + PREFIX_END_TIME + "14:00";
        assertParseFailure(parser, userInput, Shift.MESSAGE_CONSTRAINTS);

        // invalid end time
        userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "MONDAY " + PREFIX_END_TIME + "25:00";
        assertParseFailure(parser, userInput, Shift.MESSAGE_CONSTRAINTS);

        // invalid preamble
        userInput = "BAD " + INDEX_FIRST.getOneBased() + " " + PREFIX_START_DAY_OF_WEEK + "MONDAY "
                + PREFIX_START_TIME + "12:00 " + PREFIX_END_DAY_OF_WEEK + "MONDAY " + PREFIX_END_TIME + "25:00";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddShiftCommand.MESSAGE_USAGE));
    }
}
