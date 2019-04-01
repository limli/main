package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;

/**
 * The API of the Model component.
 */
public interface Model extends MemberModel, BookingModel, IngredientModel, RecipeModel, StaffModel {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' restaurant book file path.
     */
    Path getRestaurantBookFilePath();

    /**
     * Sets the user prefs' restaurant book file path.
     */
    void setRestaurantBookFilePath(Path restaurantBookFilePath);

    /**
     * Replaces restaurant book data with the data in {@code restaurantBook}.
     */
    void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook);

    /** Returns the RestaurantBook */
    ReadOnlyRestaurantBook getRestaurantBook();

    /**
     * Returns true if the model has previous restaurant book states to restore.
     */
    boolean canUndoRestaurantBook();

    /**
     * Returns true if the model has undone restaurant book states to restore.
     */
    boolean canRedoRestaurantBook();

    /**
     * Restores the model's restaurant book to its previous state.
     */
    void undoRestaurantBook();

    /**
     * Restores the model's restaurant book to its previously undone state.
     */
    void redoRestaurantBook();

    /**
     * Saves the current restaurant book state for undo/redo.
     */
    void commitRestaurantBook();

}
