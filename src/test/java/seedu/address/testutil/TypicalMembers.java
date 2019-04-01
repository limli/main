package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_LOYALTY_POINTS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_LOYALTY_POINTS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestaurantBook;
import seedu.address.model.person.Member;

/**
 * A utility class containing a list of {@code Member} objects to be used in tests.
 */
public class TypicalMembers {

    public static final Member ALICE = new MemberBuilder().withName("Alice Pauline").withPhone("94351253")
            .withEmail("alice@example.com").build();
    public static final Member BENSON = new MemberBuilder().withName("Benson Meier").withPhone("98765432")
            .withEmail("johnd@example.com").build();
    public static final Member CARL = new MemberBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").build();
    public static final Member DANIEL = new MemberBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").build();
    public static final Member ELLE = new MemberBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").build();
    public static final Member FIONA = new MemberBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").build();
    public static final Member GEORGE = new MemberBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Member HOON = new MemberBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").build();
    public static final Member IDA = new MemberBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").build();

    // Manually added - Member's details found in {@code CommandTestUtil}
    public static final Member AMY =
            new MemberBuilder().withName(PERSON_VALID_NAME_AMY).withPhone(PERSON_VALID_PHONE_AMY)
            .withEmail(PERSON_VALID_EMAIL_AMY).withLoyaltyPoints(MEMBER_VALID_LOYALTY_POINTS_AMY).build();
    public static final Member BOB =
            new MemberBuilder().withName(PERSON_VALID_NAME_BOB).withPhone(PERSON_VALID_PHONE_BOB)
            .withEmail(PERSON_VALID_EMAIL_BOB).withLoyaltyPoints(MEMBER_VALID_LOYALTY_POINTS_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMembers() {} // prevents instantiation

    /**
     * Returns an {@code RestaurantBook} with all the typical members.
     */
    public static RestaurantBook getTypicalAddressBook() {
        RestaurantBook ab = new RestaurantBook();
        for (Member member : getTypicalMembers()) {
            ab.addMember(member);
        }
        return ab;
    }

    public static List<Member> getTypicalMembers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
