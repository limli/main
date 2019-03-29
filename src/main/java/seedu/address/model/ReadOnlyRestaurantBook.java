package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;
import seedu.address.model.recipe.Recipe;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyRestaurantBook extends Observable {

    /**
     * Returns an unmodifiable view of the {@code Member} list.
     * This list will not contain any duplicate members.
     */
    ObservableList<Member> getMemberList();

    /**
     * Returns an unmodifiable view of {@code Booking} list.
     * This list will not contain any duplicate bookings.
     */
    ObservableList<Booking> getBookingList();

    /**
     * Returns an unmodifiable view of {@code Ingredient} list.
     * This list will not contain any duplicate ingredients.
     */
    ObservableList<Ingredient> getIngredientList();

    /**
     * Returns an unmodifiable view of {@code Recipe} list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getRecipeList();

    /**
     * Returns an unmodifiable view of {@code Staff} list.
     * This list will not contain any duplicate staff.
     */
    ObservableList<Staff> getStaffList();

    /**
     * Returns the capacity of the restaurant.
     */
    Capacity getCapacity();
}
