package seedu.address.testutil;

import seedu.address.model.RestaurantBook;
import seedu.address.model.person.member.Member;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code RestaurantBook ab = new AddressBookBuilder().withMember("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private RestaurantBook restaurantBook;

    public AddressBookBuilder() {
        restaurantBook = new RestaurantBook();
    }

    public AddressBookBuilder(RestaurantBook restaurantBook) {
        this.restaurantBook = restaurantBook;
    }

    /**
     * Adds a new {@code Member} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withMember(Member member) {
        restaurantBook.addMember(member);
        return this;
    }

    public RestaurantBook build() {
        return restaurantBook;
    }
}
