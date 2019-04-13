package seedu.address.model.person.staff;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.member.Member;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Staff}'s {@code Name} and {@code Appointment} matches any of the keywords given.
 * and {@code Shift} lies in a time range.
 */
public class StaffPredicate implements Predicate<Staff> {
    private final List<String> nameKeywords;
    private final List<String> appointmentKeywords;
    private final Shift shift;

    public StaffPredicate(List<String> nameKeywords, List<String> appointmentKeywords, Shift shift) {
        this.nameKeywords = nameKeywords;
        this.appointmentKeywords = appointmentKeywords;
        this.shift = shift;
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
                && appointmentKeywords.equals(((StaffPredicate) other).appointmentKeywords)
                && shift.equals(((StaffPredicate) other).shift)); // state check
    }

}
