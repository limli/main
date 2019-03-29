package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.add.AddStaffCommand;
import seedu.address.model.person.staff.Staff;


/**
 * A utility class for Staff.
 */
public class StaffUtil {

    /**
     * Returns an add command string for adding the {@code staff}.
     */
    public static String getAddCommand(Staff staff) {
        return AddStaffCommand.COMMAND_WORD + " " + getStaffDetails(staff);
    }

    /**
     * Returns an add command string for adding the {@code staff}, using the command alias.
     */
    public static String getAddCommandAlias(Staff staff) {
        return AddStaffCommand.COMMAND_ALIAS + " " + getStaffDetails(staff);
    }

    /**
     * Returns the part of command string for the given {@code staff}'s details.
     */
    public static String getStaffDetails(Staff staff) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + staff.getName().fullName + " ");
        sb.append(PREFIX_PHONE + staff.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + staff.getEmail().value + " ");
        sb.append(PREFIX_APPOINTMENT + staff.getAppointment().appointmentName + " ");

        return sb.toString();
    }
}
