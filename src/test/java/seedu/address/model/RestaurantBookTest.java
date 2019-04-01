package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.member.Member;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.MemberBuilder;

public class RestaurantBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestaurantBook restaurantBook = new RestaurantBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), restaurantBook.getMemberList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        RestaurantBook newData = getTypicalAddressBook();
        restaurantBook.resetData(newData);
        assertEquals(newData, restaurantBook);
    }

    @Test
    public void resetData_withDuplicateMembers_throwsDuplicateMemberException() {
        // Two members with the same identity fields
        Member editedAlice = new MemberBuilder(ALICE).build();
        List<Member> newMembers = Arrays.asList(ALICE, editedAlice);
        RestaurantBookStub newData = new RestaurantBookStub(newMembers);

        thrown.expect(DuplicateItemException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasMember_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasMember(null);
    }

    @Test
    public void hasMember_memberNotInAddressBook_returnsFalse() {
        assertFalse(restaurantBook.hasMember(ALICE));
    }

    @Test
    public void hasMember_memberInAddressBook_returnsTrue() {
        restaurantBook.addMember(ALICE);
        assertTrue(restaurantBook.hasMember(ALICE));
    }

    @Test
    public void hasMember_memberWithSameIdentityFieldsInAddressBook_returnsTrue() {
        restaurantBook.addMember(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).build();
        assertTrue(restaurantBook.hasMember(editedAlice));
    }

    @Test
    public void getMemberList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getMemberList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        restaurantBook.addListener(listener);
        restaurantBook.addMember(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        restaurantBook.addListener(listener);
        restaurantBook.removeListener(listener);
        restaurantBook.addMember(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyRestaurantBook whose members list can violate interface constraints.
     */
    private static class RestaurantBookStub implements ReadOnlyRestaurantBook {
        private final ObservableList<Member> members = FXCollections.observableArrayList();

        RestaurantBookStub(Collection<Member> members) {
            this.members.setAll(members);
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }

        @Override
        public ObservableList<Booking> getBookingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Ingredient> getIngredientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recipe> getRecipeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Staff> getStaffList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Capacity getCapacity() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
