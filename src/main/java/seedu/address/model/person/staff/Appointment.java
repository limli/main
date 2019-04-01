package seedu.address.model.person.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Staff's appointment in the restaurant book.
 *
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentName(String)}
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS =
            "Appointment names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the appointment name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String appointmentName;

    /**
     * Constructs a {@code Appointment}.
     *
     * @param appointmentName A valid appointment name.
     */
    public Appointment(String appointmentName) {
        requireNonNull(appointmentName);
        checkArgument(isValidAppointmentName(appointmentName), MESSAGE_CONSTRAINTS);
        this.appointmentName = appointmentName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidAppointmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return appointmentName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && appointmentName.equals(((Appointment) other).appointmentName)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentName.hashCode();
    }

}
