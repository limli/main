package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOYALTY_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.member.AddMemberCommand;
import seedu.address.logic.commands.member.EditMemberCommand.EditMemberDescriptor;
import seedu.address.model.person.member.Member;

/**
 * A utility class for Member.
 */
public class MemberUtil {

    /**
     * Returns an add command string for adding the {@code member}.
     */
    public static String getAddCommand(Member member) {
        return AddMemberCommand.COMMAND_WORD + " " + getMemberDetails(member);
    }

    /**
     * Returns an add command string for adding the {@code member}, using the command alias.
     */
    public static String getAddCommandAlias(Member member) {
        return AddMemberCommand.COMMAND_ALIAS + " " + getMemberDetails(member);
    }

    /**
     * Returns the part of command string for the given {@code member}'s details.
     */
    public static String getMemberDetails(Member member) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + member.getName().fullName + " ");
        sb.append(PREFIX_PHONE + member.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + member.getEmail().value + " ");
        sb.append(PREFIX_LOYALTY_POINTS + Integer.toString(member.getLoyaltyPoints().value) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMemberDescriptor}'s details.
     */
    public static String getEditMemberDescriptorDetails(EditMemberDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getLoyaltyPoints().ifPresent(
            loyaltyPoints -> sb.append(PREFIX_LOYALTY_POINTS).append(loyaltyPoints.value).append(" "));
        return sb.toString();
    }
}
