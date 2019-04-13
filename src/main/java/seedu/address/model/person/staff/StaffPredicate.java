package seedu.address.model.person.staff;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Staff}'s {@code Name} and {@code Appointment} matches any of the keywords given.
 * and {@code Shift} lies in a time range.
 */
public class StaffPredicate implements Predicate<Staff> {
    private final List<String> nameKeywords;
    private final List<String> appointmentKeywords;

    public StaffPredicate(List<String> nameKeywords, List<String> appointmentKeywords) {
        this.nameKeywords = nameKeywords;
        this.appointmentKeywords = appointmentKeywords;
    }

    @Override
    public boolean test(Staff staff) {
        return (nameKeywords.isEmpty() //short circuit if empty
                || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(staff.getName().fullName, keyword)))
                && (appointmentKeywords.isEmpty() //short circuit if empty
                || appointmentKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        staff.getAppointment().appointmentName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StaffPredicate // instanceof handles nulls
                && nameKeywords.equals(((StaffPredicate) other).nameKeywords)
                && appointmentKeywords.equals(((StaffPredicate) other).appointmentKeywords));
    }

}
