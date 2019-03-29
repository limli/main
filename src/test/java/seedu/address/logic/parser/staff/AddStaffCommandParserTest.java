package seedu.address.logic.parser.staff;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.PERSON_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_VALID_APPOINTMENT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStaff.AMY;
import static seedu.address.testutil.TypicalStaff.BOB;

import org.junit.Test;

import seedu.address.logic.commands.add.AddStaffCommand;
import seedu.address.logic.parser.AddStaffCommandParser;
import seedu.address.model.person.staff.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Staff;
import seedu.address.testutil.StaffBuilder;


public class AddStaffCommandParserTest {
    private AddStaffCommandParser parser = new AddStaffCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Staff expectedStaff = new StaffBuilder(AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PERSON_NAME_DESC_AMY
                + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple names - last name accepted
        assertParseSuccess(parser, PERSON_NAME_DESC_BOB + PERSON_NAME_DESC_AMY
                + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, PERSON_NAME_DESC_AMY + PERSON_PHONE_DESC_BOB
                + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple emails - last email accepted
        assertParseSuccess(parser, PERSON_NAME_DESC_AMY + PERSON_PHONE_DESC_AMY
                + PERSON_EMAIL_DESC_BOB + PERSON_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple appointments - last appointment accepted
        assertParseSuccess(parser, PERSON_NAME_DESC_AMY + PERSON_PHONE_DESC_AMY
                + PERSON_EMAIL_DESC_BOB + PERSON_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_BOB + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Staff expectedStaff = new StaffBuilder(BOB).build();
        assertParseSuccess(parser, PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                new AddStaffCommand(expectedStaff));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PERSON_VALID_NAME_BOB + PERSON_PHONE_DESC_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + PERSON_VALID_PHONE_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                        + PERSON_VALID_EMAIL_BOB + STAFF_APPOINTMENT_DESC_BOB,
                expectedMessage);

        // missing appointment prefix
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_VALID_APPOINTMENT_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, PERSON_VALID_NAME_BOB + PERSON_VALID_PHONE_BOB
                        + PERSON_VALID_EMAIL_BOB + STAFF_VALID_APPOINTMENT_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, PERSON_INVALID_NAME_DESC + PERSON_PHONE_DESC_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + PERSON_INVALID_PHONE_DESC
                        + PERSON_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                        + PERSON_INVALID_EMAIL_DESC + STAFF_APPOINTMENT_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid appointment
        assertParseFailure(parser, PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_INVALID_APPOINTMENT_DESC,
                Appointment.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, PERSON_INVALID_NAME_DESC + PERSON_PHONE_DESC_BOB
                        + PERSON_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PERSON_NAME_DESC_BOB
                + PERSON_PHONE_DESC_BOB + PERSON_EMAIL_DESC_BOB
                + STAFF_APPOINTMENT_DESC_BOB, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddStaffCommand.MESSAGE_USAGE));
    }
}
