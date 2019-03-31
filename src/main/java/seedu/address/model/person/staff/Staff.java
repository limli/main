package seedu.address.model.person.staff;

import java.util.Objects;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Represents a Staff in the restaurant book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Staff extends Person {

    private Appointment appointment;
    private ShiftRoster shiftRoster;

    /**
     * Every field must be present and not null.
     */
    public Staff(Name name, Phone phone, Email email, Appointment appointment, ShiftRoster shiftRoster) {
        super(name, phone, email);
        this.appointment = appointment;
        this.shiftRoster = shiftRoster;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public ShiftRoster getShiftRoster() {
        return shiftRoster;
    }

    /**
     * Returns true if both staff of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two staff.
     */
    public boolean isSameStaff(Staff otherStaff) {
        if (otherStaff == this) {
            return true;
        }

        return otherStaff != null
                && otherStaff.getName().equals(getName())
                && (otherStaff.getPhone().equals(getPhone()) || otherStaff.getEmail().equals(getEmail()));
    }

    public boolean isSameItem(Object otherItem) {
        return otherItem instanceof Staff && isSameStaff((Staff) otherItem);
    }

    /**
     * Returns true if both staff have the same identity and data fields.
     * This defines a stronger notion of equality between two staff.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Staff)) {
            return false;
        }

        Staff otherStaff = (Staff) other;
        return otherStaff.getName().equals(getName())
                && otherStaff.getPhone().equals(getPhone())
                && otherStaff.getEmail().equals(getEmail())
                && otherStaff.getAppointment().equals(getAppointment())
                && otherStaff.getShiftRoster().equals(getShiftRoster());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, appointment);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Appointment: ")
                .append(getAppointment())
                .append("\nShifts:\n")
                .append(getShiftRoster());
        return builder.toString();
    }

}
