package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.exceptions.ItemNotFoundException;
import seedu.address.model.person.member.Member;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.recipe.Recipe;

/**
 * Represents the in-memory model of the restaurant book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedRestaurantBook versionedRestaurantBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Member> filteredMembers;
    private final SimpleObjectProperty<Member> selectedMember = new SimpleObjectProperty<>();

    private final FilteredList<Booking> filteredBookings;
    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();

    private final FilteredList<Ingredient> filteredIngredients;
    private final SimpleObjectProperty<Ingredient> selectedIngredient = new SimpleObjectProperty<>();

    private final FilteredList<Recipe> filteredRecipes;
    private final SimpleObjectProperty<Recipe> selectedRecipe = new SimpleObjectProperty<>();


    private final FilteredList<Staff> filteredStaff;
    private final SimpleObjectProperty<Staff> selectedStaff = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given restaurantBook and userPrefs.
     */
    public ModelManager(ReadOnlyRestaurantBook restaurantBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(restaurantBook, userPrefs);

        logger.fine("Initializing with restaurant book: " + restaurantBook + " and user prefs " + userPrefs);

        versionedRestaurantBook = new VersionedRestaurantBook(restaurantBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMembers = new FilteredList<>(versionedRestaurantBook.getMemberList());
        filteredMembers.addListener(this::ensureSelectedMemberIsValid);

        filteredBookings = new FilteredList<>(versionedRestaurantBook.getBookingList());

        filteredIngredients = new FilteredList<>(versionedRestaurantBook.getIngredientList());

        filteredRecipes = new FilteredList<>(versionedRestaurantBook.getRecipeList());

        filteredStaff = new FilteredList<>(versionedRestaurantBook.getStaffList());
    }

    public ModelManager() {
        this(new RestaurantBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getRestaurantBookFilePath() {
        return userPrefs.getRestaurantBookFilePath();
    }

    @Override
    public void setRestaurantBookFilePath(Path restaurantBookFilePath) {
        requireNonNull(restaurantBookFilePath);
        userPrefs.setRestaurantBookFilePath(restaurantBookFilePath);
    }

    //=========== RestaurantBook ================================================================================

    @Override
    public void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook) {
        versionedRestaurantBook.resetData(restaurantBook);
    }

    @Override
    public ReadOnlyRestaurantBook getRestaurantBook() {
        return versionedRestaurantBook;
    }

    @Override
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return versionedRestaurantBook.hasMember(member);
    }

    @Override
    public boolean hasBooking(Booking booking) {
        requireNonNull(booking);
        return versionedRestaurantBook.hasBooking(booking);
    }

    @Override
    public boolean canAccommodate(Booking booking) {
        requireNonNull(booking);
        return versionedRestaurantBook.canAccommodate(booking);
    }

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return versionedRestaurantBook.hasIngredient(ingredient);
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return versionedRestaurantBook.hasRecipe(recipe);
    }

    @Override
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return versionedRestaurantBook.hasStaff(staff);
    }

    @Override
    public void deleteMember(Member target) {
        requireNonNull(target);
        versionedRestaurantBook.removeMember(target);
    }

    @Override
    public void deleteBooking(Booking target) {
        requireNonNull(target);
        versionedRestaurantBook.removeBooking(target);
    }

    @Override
    public void deleteIngredient(Ingredient target) {
        requireNonNull(target);
        versionedRestaurantBook.removeIngredient(target);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        requireNonNull(target);
        versionedRestaurantBook.removeRecipe(target);
    }

    @Override
    public void deleteStaff(Staff target) {
        requireNonNull(target);
        versionedRestaurantBook.removeStaff(target);
    }

    @Override
    public void addMember(Member member) {
        requireNonNull(member);
        versionedRestaurantBook.addMember(member);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void addBooking(Booking booking) {
        requireNonNull(booking);
        versionedRestaurantBook.addBooking(booking);
        updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        versionedRestaurantBook.addIngredient(ingredient);
        updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        requireNonNull(recipe);
        versionedRestaurantBook.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void addStaff(Staff staff) {
        requireNonNull(staff);
        versionedRestaurantBook.addStaff(staff);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFF);
    }

    @Override
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);
        versionedRestaurantBook.setMember(target, editedMember);
    }

    @Override
    public void setBooking(Booking target, Booking editedBooking) {
        requireAllNonNull(target, editedBooking);
        versionedRestaurantBook.setBooking(target, editedBooking);
    }

    @Override
    public void setStaff(Staff target, Staff editedStaff) {
        requireAllNonNull(target, editedStaff);
        versionedRestaurantBook.setStaff(target, editedStaff);
    }

    @Override
    public boolean canAccommodateEdit(Booking target, Booking editedBooking) {
        return versionedRestaurantBook.canAccommodateEdit(target, editedBooking);
    }

    @Override
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);
        versionedRestaurantBook.setIngredient(target, editedIngredient);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);
        versionedRestaurantBook.setRecipe(target, editedRecipe);
    }

    @Override
    public Capacity getCapacity() {
        return versionedRestaurantBook.getCapacity();
    }

    @Override
    public void setCapacity(Capacity newCapacity) {
        versionedRestaurantBook.setCapacity(newCapacity);
    }

    @Override
    public boolean canUpdateCapacity(Capacity newCapacity) {
        return versionedRestaurantBook.canUpdateCapacity(newCapacity);
    }

    @Override
    public int countBookings(Member member) {
        return versionedRestaurantBook.countBookings(member);
    }

    //=========== Filtered Member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Member} backed by the internal list of
     * {@code versionedRestaurantBook}
     */
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return filteredMembers;
    }

    @Override
    public ObservableList<Booking> getFilteredBookingList() {
        return filteredBookings;
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return filteredRecipes;
    }

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return filteredIngredients;
    }

    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return filteredStaff;
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        filteredMembers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBookingList(Predicate<Booking> predicate) {
        filteredBookings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
        filteredIngredients.setPredicate(predicate);
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        filteredRecipes.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        filteredStaff.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoRestaurantBook() {
        return versionedRestaurantBook.canUndo();
    }

    @Override
    public boolean canRedoRestaurantBook() {
        return versionedRestaurantBook.canRedo();
    }

    @Override
    public void undoRestaurantBook() {
        versionedRestaurantBook.undo();
    }

    @Override
    public void redoRestaurantBook() {
        versionedRestaurantBook.redo();
    }

    @Override
    public void commitRestaurantBook() {
        versionedRestaurantBook.commit();
    }

    //=========== Selected member ===========================================================================

    @Override
    public ReadOnlyProperty<Member> selectedMemberProperty() {
        return selectedMember;
    }

    @Override
    public ReadOnlyProperty<Booking> selectedBookingProperty() {
        return selectedBooking;
    }

    @Override
    public ReadOnlyProperty<Ingredient> selectedIngredientProperty() {
        return selectedIngredient;
    }

    @Override
    public ReadOnlyProperty<Recipe> selectedRecipeProperty() {
        return selectedRecipe;
    }

    @Override
    public ReadOnlyProperty<Staff> selectedStaffProperty() {
        return selectedStaff;
    }

    @Override
    public Member getSelectedMember() {
        return selectedMember.getValue();
    }

    @Override
    public Booking getSelectedBooking() {
        return selectedBooking.getValue();
    }

    @Override
    public Ingredient getSelectedIngredient() {
        return selectedIngredient.getValue();
    }

    @Override
    public Recipe getSelectedRecipe() {
        return selectedRecipe.getValue();
    }

    @Override
    public Staff getSelectedStaff() {
        return selectedStaff.getValue();
    }

    @Override
    public void setSelectedMember(Member member) {
        if (member != null && !filteredMembers.contains(member)) {
            throw new ItemNotFoundException();
        }
        selectedMember.setValue(member);
    }

    @Override
    public void setSelectedBooking(Booking booking) {
        if (booking != null && !filteredBookings.contains(booking)) {
            throw new ItemNotFoundException();
        }
        selectedBooking.setValue(booking);
    }

    @Override
    public void setSelectedIngredient(Ingredient ingredient) {
        if (ingredient != null && !filteredIngredients.contains(ingredient)) {
            throw new ItemNotFoundException();
        }
        selectedIngredient.setValue(ingredient);
    }

    @Override
    public void setSelectedRecipe(Recipe recipe) {
        if (recipe != null && !filteredRecipes.contains(recipe)) {
            throw new ItemNotFoundException();
        }
        selectedRecipe.setValue(recipe);
    }

    @Override
    public void setSelectedStaff(Staff staff) {
        if (staff != null && !filteredStaff.contains(staff)) {
            throw new ItemNotFoundException();
        }
        selectedStaff.setValue(staff);
    }

    /**
     * Ensures {@code selectedMember} is a valid member in {@code filteredMembers}.
     */
    private void ensureSelectedMemberIsValid(ListChangeListener.Change<? extends Member> change) {
        while (change.next()) {
            if (selectedMember.getValue() == null) {
                // null is always a valid selected member, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedMemberReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedMember.getValue());
            if (wasSelectedMemberReplaced) {
                // Update selectedMember to its new value.
                int index = change.getRemoved().indexOf(selectedMember.getValue());
                selectedMember.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedMemberRemoved = change.getRemoved().stream()
                    .anyMatch(removedMember -> selectedMember.getValue().isSameMember(removedMember));
            if (wasSelectedMemberRemoved) {
                // Select the member that came before it in the list,
                // or clear the selection if there is no such member.
                selectedMember.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedRestaurantBook.equals(other.versionedRestaurantBook)
                && userPrefs.equals(other.userPrefs)
                && filteredMembers.equals(other.filteredMembers)
                && Objects.equals(selectedMember.get(), other.selectedMember.get())
                && filteredBookings.equals(other.filteredBookings)
                && Objects.equals(selectedBooking.get(), other.selectedBooking.get())
                && filteredStaff.equals(other.filteredStaff)
                && Objects.equals(selectedStaff.get(), other.selectedStaff.get());
    }

}
