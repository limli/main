package seedu.address.model.util;

import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.member.LoyaltyPoints;
import seedu.address.model.person.member.Member;

/**
 * Contains utility methods for populating {@code RestaurantBook} with sample data.
 */
public class SampleDataUtil {
    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new LoyaltyPoints(1)),
            new Member(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new LoyaltyPoints(1)),
            new Member(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new LoyaltyPoints(1)),
            new Member(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new LoyaltyPoints(1)),
            new Member(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new LoyaltyPoints(1)),
            new Member(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new LoyaltyPoints(1))
        };
    }

    public static ReadOnlyRestaurantBook getSampleRestaurantBook() {
        RestaurantBook sampleAb = new RestaurantBook();
        for (Member sampleMember : getSampleMembers()) {
            sampleAb.addMember(sampleMember);
        }
        return sampleAb;
    }

}
