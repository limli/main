package seedu.address.model.person.member;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Member}'s {@code Name} matches any of the keywords given
 * and has at least the amount of loyalty points specified.
 */
public class NameAndLoyaltyPointsPredicate implements Predicate<Member> {
    private final List<String> keywords;
    private final int minLoyaltyPoints;

    public NameAndLoyaltyPointsPredicate(List<String> keywords, int minLoyaltyPoints) {
        this.keywords = keywords;
        this.minLoyaltyPoints = minLoyaltyPoints;
    }

    @Override
    public boolean test(Member member) {
        return (keywords.isEmpty() //short circuit if empty
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(member.getName().fullName, keyword)))
                && member.getLoyaltyPoints().value >= minLoyaltyPoints;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameAndLoyaltyPointsPredicate // instanceof handles nulls
                && keywords.equals(((NameAndLoyaltyPointsPredicate) other).keywords)
                && minLoyaltyPoints == ((NameAndLoyaltyPointsPredicate) other).minLoyaltyPoints); // state check
    }

}
