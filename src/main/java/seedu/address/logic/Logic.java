package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.member.Member;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.recipe.Recipe;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the RestaurantBook.
     *
     * @see seedu.address.model.Model#getRestaurantBook()
     */
    ReadOnlyRestaurantBook getRestaurantBook();

    /** Returns an unmodifiable view of the filtered list of members */
    ObservableList<Member> getFilteredMemberList();

    /** Returns an unmodifiable view of the filtered list of bookings */
    ObservableList<Booking> getFilteredBookingList();

    /** Returns an unmodifiable view of the filtered list of ingredients */
    ObservableList<Ingredient> getFilteredIngredientList();

    /** Returns an unmodifiable view of the filtered list of ingredients */
    ObservableList<Recipe> getFilteredRecipeList();

    /** Returns an unmodifiable view of the filtered list of staff */
    ObservableList<Staff> getFilteredStaffList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getRestaurantBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected member in the filtered member list.
     * null if no member is selected.
     *
     * @see seedu.address.model.Model#selectedMemberProperty()
     */
    ReadOnlyProperty<Member> selectedMemberProperty();

    /**
     * Selected booking in the filtered booking list.
     * null if no booking is selected.
     *
     * @see seedu.address.model.Model#selectedBookingProperty()
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Selected ingredient in the filtered ingredient list.
     * null if no ingredient is selected.
     *
     * @see seedu.address.model.Model#selectedIngredientProperty()
     */
    ReadOnlyProperty<Ingredient> selectedIngredientProperty();

    /**
     * Selected recipe in the filtered recipe list.
     * null if no recipe is selected.
     *
     * @see seedu.address.model.Model#selectedRecipeProperty()
     */
    ReadOnlyProperty<Recipe> selectedRecipeProperty();

    /**
     * Selected staff in the filtered staff list.
     * null if no staff is selected.
     *
     * @see seedu.address.model.Model#selectedStaffProperty()
     */
    ReadOnlyProperty<Staff> selectedStaffProperty();

    /**
     * Sets the selected member in the filtered member list.
     *
     * @see seedu.address.model.Model#setSelectedMember(Member)
     */
    void setSelectedMember(Member member);

    /**
     * Sets the selected booking in the filtered booking list.
     *
     * @see seedu.address.model.Model#setSelectedBooking(Booking)
     */
    void setSelectedBooking(Booking booking);

    /**
     * Sets the selected ingredient in the filtered ingredient list.
     *
     * @see seedu.address.model.Model#setSelectedIngredient(Ingredient)
     */
    void setSelectedIngredient(Ingredient ingredient);

    /**
     * Sets the selected recipe in the filtered recipe list.
     *
     * @see seedu.address.model.Model#setSelectedRecipe(Recipe)
     */
    void setSelectedRecipe(Recipe recipe);

    /**
     * Sets the selected staff in the filtered staff list.
     *
     * @see seedu.address.model.Model#setSelectedStaff(Staff)
     */
    void setSelectedStaff(Staff staff);
}
