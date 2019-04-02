package seedu.address.model.person.member;

import java.util.Objects;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Represents a Member in the restaurant book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member extends Person {

    private LoyaltyPoints loyaltyPoints;

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Phone phone, Email email, LoyaltyPoints loyaltyPoints) {
        super(name, phone, email);
        this.loyaltyPoints = loyaltyPoints;
    }

    public LoyaltyPoints getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    /**
     * Returns true if both members of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two members.
     */
    public boolean isSameMember(Member otherMember) {
        if (otherMember == this) {
            return true;
        }

        return otherMember != null
                && otherMember.getName().equals(getName())
                && (otherMember.getPhone().equals(getPhone()) || otherMember.getEmail().equals(getEmail()));
    }

    public boolean isSameItem(Object otherItem) {
        return otherItem instanceof Member && isSameMember((Member) otherItem);
    }

    /**
     * Returns true if both members have the same identity and data fields.
     * This defines a stronger notion of equality between two members.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return otherMember.getName().equals(getName())
                && otherMember.getPhone().equals(getPhone())
                && otherMember.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Loyalty Points: ")
                .append(getLoyaltyPoints());
        return builder.toString();
    }

}
