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
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;
import seedu.address.model.person.exceptions.ItemNotFoundException;

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
        filteredMembers = new FilteredList<>(versionedRestaurantBook.getItemList(Member.class));
        filteredMembers.addListener(this::ensureSelectedMemberIsValid);

        filteredBookings = new FilteredList<>(versionedRestaurantBook.getItemList(Booking.class));

        filteredIngredients = new FilteredList<>(versionedRestaurantBook.getItemList(Ingredient.class));

        filteredStaff = new FilteredList<>(versionedRestaurantBook.getItemList(Staff.class));
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
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedRestaurantBook.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        requireNonNull(target);
        versionedRestaurantBook.removeItem(target);
    }

    @Override
    public void addItem(Item item) {
        requireNonNull(item);
        versionedRestaurantBook.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS, item.getClass());
    }

    @Override
    public <T extends Item> void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        versionedRestaurantBook.setItem(target, editedItem);
    }


    //=========== Filtered Member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Member} backed by the internal list of
     * {@code versionedRestaurantBook}
     */
    @Override
    public <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz) {
        if (clazz.equals(Member.class)) {
            return (ObservableList<T>) filteredMembers;
        } else if (clazz.equals(Booking.class)) {
            return (ObservableList<T>) filteredBookings;
        } else if (clazz.equals(Ingredient.class)) {
            return (ObservableList<T>) filteredIngredients;
        } else if (clazz.equals(Staff.class)) {
            return (ObservableList<T>) filteredStaff;
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
    }

    @Override
    public <T extends Item> void updateFilteredItemList(Predicate<? super T> predicate, Class<T> clazz) {
        requireNonNull(predicate);
        if (clazz == Member.class) {
            filteredMembers.setPredicate((Predicate<Member>) predicate);
        } else if (clazz == Booking.class) {
            filteredBookings.setPredicate((Predicate<Booking>) predicate);
        } else if (clazz == Ingredient.class) {
            filteredIngredients.setPredicate((Predicate<Ingredient>) predicate);
        } else if (clazz == Staff.class) {
            filteredStaff.setPredicate((Predicate<Staff>) predicate);
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
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
    public <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz) {
        if (clazz == Member.class) {
            return (ReadOnlyProperty<T>) selectedMember;
        } else if (clazz == Booking.class) {
            return (ReadOnlyProperty<T>) selectedBooking;
        } else if (clazz == Ingredient.class) {
            return (ReadOnlyProperty<T>) selectedIngredient;
        } else if (clazz == Staff.class) {
            return (ReadOnlyProperty<T>) selectedStaff;
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
    }

    @Override
    public <T extends Item> T getSelectedItem(Class<T> clazz) {
        if (clazz == Member.class) {
            return (T) selectedMember.getValue();
        } else if (clazz == Booking.class) {
            return (T) selectedBooking.getValue();
        } else if (clazz == Ingredient.class) {
            return (T) selectedIngredient.getValue();
        } else if (clazz == Staff.class) {
            return (T) selectedStaff.getValue();
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
    }

    @Override
    public <T extends Item> void setSelectedItem(T item, Class<T> clazz) {
        if (clazz == Member.class) {
            if (item != null && !filteredMembers.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedMember.setValue((Member) item);
        } else if (clazz == Booking.class) {
            if (item != null && !filteredBookings.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedBooking.setValue((Booking) item);
        } else if (clazz == Ingredient.class) {
            if (item != null && !filteredIngredients.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedIngredient.setValue((Ingredient) item);
        } else if (clazz == Staff.class) {
            if (item != null && !filteredStaff.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedStaff.setValue((Staff) item);
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
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
                && Objects.equals(selectedBooking.get(), other.selectedBooking.get());
    }

}
