package seedu.address.testutil;

import seedu.address.model.person.Email;
import seedu.address.model.person.member.LoyaltyPoints;
import seedu.address.model.person.member.Member;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building Member objects.
 */
public class MemberBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final int DEFAULT_LOYALTY_POINTS = 12;

    private Name name;
    private Phone phone;
    private Email email;
    private LoyaltyPoints loyaltyPoints;

    public MemberBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        loyaltyPoints = new LoyaltyPoints(DEFAULT_LOYALTY_POINTS);
    }

    /**
     * Initializes the MemberBuilder with the data of {@code memberToCopy}.
     */
    public MemberBuilder(Member memberToCopy) {
        name = memberToCopy.getName();
        phone = memberToCopy.getPhone();
        email = memberToCopy.getEmail();
        loyaltyPoints = memberToCopy.getLoyaltyPoints();
    }

    /**
     * Sets the {@code Name} of the {@code Member} that we are building.
     */
    public MemberBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Member} that we are building.
     */
    public MemberBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Member} that we are building.
     */
    public MemberBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Member} that we are building.
     */
    public MemberBuilder withLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = new LoyaltyPoints(loyaltyPoints);
        return this;
    }

    public Member build() {
        return new Member(name, phone, email, loyaltyPoints);
    }

}
