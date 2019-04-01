package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_INVALID_LOYALTY_POINTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_LOYALTY_POINTS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_LOYALTY_POINTS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.commands.EditMemberCommand.EditMemberDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.LoyaltyPoints;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.EditMemberDescriptorBuilder;

public class EditMemberCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMemberCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PERSON_VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditMemberCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PERSON_NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PERSON_NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + PERSON_INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + PERSON_INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + PERSON_INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + MEMBER_INVALID_LOYALTY_POINTS_DESC,
                LoyaltyPoints.MESSAGE_CONSTRAINTS); // invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + PERSON_INVALID_PHONE_DESC + PERSON_EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PERSON_PHONE_DESC_BOB + PERSON_INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PERSON_INVALID_NAME_DESC + PERSON_INVALID_EMAIL_DESC
                        + PERSON_VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_AMY + PERSON_NAME_DESC_AMY + MEMBER_LOYALTY_POINTS_DESC_BOB;

        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(PERSON_VALID_NAME_AMY)
                .withPhone(PERSON_VALID_PHONE_BOB).withEmail(PERSON_VALID_EMAIL_AMY)
                .withLoyaltyPoints(MEMBER_VALID_LOYALTY_POINTS_BOB).build();
        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PERSON_PHONE_DESC_BOB + PERSON_EMAIL_DESC_AMY;

        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withPhone(PERSON_VALID_PHONE_BOB)
                .withEmail(PERSON_VALID_EMAIL_AMY).build();
        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + PERSON_NAME_DESC_AMY;
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(PERSON_VALID_NAME_AMY).build();
        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PERSON_PHONE_DESC_AMY;
        descriptor = new EditMemberDescriptorBuilder().withPhone(PERSON_VALID_PHONE_AMY).build();
        expectedCommand = new EditMemberCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + PERSON_EMAIL_DESC_AMY;
        descriptor = new EditMemberDescriptorBuilder().withEmail(PERSON_VALID_EMAIL_AMY).build();
        expectedCommand = new EditMemberCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY
                + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY + PERSON_PHONE_DESC_BOB + PERSON_EMAIL_DESC_BOB;

        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withPhone(PERSON_VALID_PHONE_BOB)
                .withEmail(PERSON_VALID_EMAIL_BOB)
                .build();
        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PERSON_INVALID_PHONE_DESC + PERSON_PHONE_DESC_BOB;
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withPhone(PERSON_VALID_PHONE_BOB).build();
        EditMemberCommand expectedCommand = new EditMemberCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + PERSON_EMAIL_DESC_BOB
                + PERSON_INVALID_PHONE_DESC + PERSON_PHONE_DESC_BOB;
        descriptor = new EditMemberDescriptorBuilder().withPhone(PERSON_VALID_PHONE_BOB)
                .withEmail(PERSON_VALID_EMAIL_BOB).build();
        expectedCommand = new EditMemberCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
