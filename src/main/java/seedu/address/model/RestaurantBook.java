package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.exceptions.RestaurantOverbookedException;
import seedu.address.model.person.member.Member;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.recipe.Recipe;

/**
 * Wraps all data at the restaurant-book level
 * Duplicates are not allowed (by .isSameItem() comparison)
 */
public class RestaurantBook implements ReadOnlyRestaurantBook {

    private final UniqueItemList<Member> members;
    private final UniqueItemList<Booking> bookings;
    private final UniqueItemList<Ingredient> ingredients;
    private final UniqueItemList<Recipe> recipes;
    private final UniqueItemList<Staff> staff;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    private Capacity capacity = Capacity.getDefaultCapacity();
    private Consumer<Capacity> callback;

     /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        members = new UniqueItemList<>();
        bookings = new UniqueItemList<>();
        ingredients = new UniqueItemList<>();
        recipes = new UniqueItemList<>();
        staff = new UniqueItemList<>();
    }

    public RestaurantBook() {
    }

    /**
     * Creates an RestaurantBook using the Members in the {@code toBeCopied}
     */
    public RestaurantBook(ReadOnlyRestaurantBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the member list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setMembers(List<Member> members) {
        this.members.setItems(members);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code bookings}.
     * {@code bookings} must not contain duplicate bookings.
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings.setItems(bookings);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code ingredients}.
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setItems(ingredients);
        indicateModified();
    }


    /**
     * Replaces the contents of the recipe list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes.setItems(recipes);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code staff}.
     * {@code staff} must not contain duplicate staff.
     */
    // Temporary rename to not make it look like overloaded method with setStaff(Staff target, Staff editedStaff)
    // TODO: find a better name
    public void setStaffList(List<Staff> staff) {
        this.staff.setItems(staff);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code RestaurantBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRestaurantBook newData) {
        requireNonNull(newData);
        setMembers(newData.getMemberList());
        setBookings(newData.getBookingList());
        setIngredients(newData.getIngredientList());
        setRecipes(newData.getRecipeList());
        setStaffList(newData.getStaffList());
        capacity = newData.getCapacity();
    }

    //// item-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the restaurant book.
     */
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Returns true if a booking with the same identity as {@code booking} exists in the restaurant book.
     */
    public boolean hasBooking(Booking booking) {
        requireNonNull(booking);
        return bookings.contains(booking);
    }

    /**
     * Returns true if {@code booking} can be added to the restaurant without exceeding capacity.
     */
    public boolean canAccommodate(Booking booking) {
        List<Booking> newList = new ArrayList<>();
        for (Booking b : bookings) {
            newList.add(b);
        } // copy all the bookings over as booking list is immutable
        newList.add(booking);
        return getCapacity().canAccommodate(newList);
    }

    /**
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return ingredients.contains(ingredient);
    }

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the restaurant book.
     */
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipes.contains(recipe);
    }

    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the restaurant book.
     */
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return this.staff.contains(staff);
    }

    /**
     * Adds a member to the restaurant book.
     * The member must not already exist in the restaurant book.
     */
    public void addMember(Member member) {
        members.add(member);
        indicateModified();
    }

    /**
     * Adds a booking to the restaurant book.
     * The booking must not already exist in the restaurant book.
     * The addition of this booking must not allow restaurant to exceed capacity.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
        if (!getCapacity().canAccommodate(bookings.asUnmodifiableObservableList())) {
            throw new RestaurantOverbookedException();
        }
        bookings.sort(Comparator.naturalOrder());
        indicateModified();
    }

    /**
     * Adds a ingredient to the restaurant book.
     * The ingredient must not already exist in the restaurant book.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        indicateModified();
    }

    /**
     * Adds a recipe to the restaurant book.
     * The recipe must not already exist in the restaurant book.
     */
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        indicateModified();
    }

    /**
     * Adds a staff to the restaurant book.
     * The staff must not already exist in the restaurant book.
     */
    public void addStaff(Staff s) { // 1 letter name used to avoid variable name conflict
        staff.add(s);
        indicateModified();
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedMember} must not be the
     * same as another existing member in the restaurant book.
     */
    public void setMember(Member target, Member editedMember) {
        members.setItem(target, editedMember);
        ObservableList<Booking> bookingObservableList = bookings.asUnmodifiableObservableList();
        Function<Booking, Booking>
                updateBooking = b -> (b.getCustomer().equals(target) ? b.editContacts(editedMember) : b);
        setBookings(bookingObservableList.stream().map(updateBooking).collect(Collectors.toList()));
        indicateModified();
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedBooking}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedBooking} must not be the
     * same as another existing booking in the restaurant book.
     */
    public void setBooking(Booking target, Booking editedBooking) {
        bookings.setItem(target, editedBooking);
        if (!capacity.canAccommodate(bookings.asUnmodifiableObservableList())) {
            throw new RestaurantOverbookedException();
        }
        bookings.sort(Comparator.naturalOrder());
        indicateModified();
    }

    /**
     * Determines if editing the booking will cause the restaurant to be overbooked
     */
    public boolean canAccommodateEdit(Booking target, Booking editedBooking) {
        // newList simulates what happens when the target is replaced
        List<Booking> newList = new ArrayList<>();
        for (Booking b : bookings) {
            if (!b.equals(target)) {
                newList.add(b);
            } else {
                newList.add(editedBooking);
            }
        }
        return getCapacity().canAccommodate(newList);
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the restaurant book.
     * Recipes that include the ingredient must also be updated with the change in the ingredient.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        ingredients.setItem(target, editedIngredient);
        ObservableList<Recipe> recipeObservableList = recipes.asUnmodifiableObservableList();
        Function<Recipe, Recipe>
                updateRecipe = r -> (r.containsIngredient(target) ? r.editIngredientSet(target, editedIngredient) : r);
        setRecipes(recipeObservableList.stream().map(updateRecipe).collect(Collectors.toList()));
        indicateModified();
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the restaurant book.
     * The recipe identity of {@code editedRecipe} must not be the
     * same as another existing recipe in the restaurant book.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        recipes.setItem(target, editedRecipe);
        indicateModified();
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedStaff}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedStaff} must not be the
     * same as another existing member in the restaurant book.
     */
    public void setStaff(Staff target, Staff editedStaff) {
        staff.setItem(target, editedStaff);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * All bookings made by this member will also be removed.
     * {@code key} must exist in the restaurant book.
     */
    public void removeMember(Member key) {
        members.remove(key);
        // When a member is deleted, all associated bookings are also deleted.
        Predicate<Booking> isValidBooking = b -> !b.getCustomer().equals(key);
        ObservableList<Booking> bookingObservableList = bookings.asUnmodifiableObservableList();
        setBookings(bookingObservableList.stream().filter(isValidBooking).collect(Collectors.toList()));
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * {@code key} must exist in the restaurant book.
     */
    public void removeBooking(Booking key) {
        bookings.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * {@code key} must exist in the restaurant book.
     * Recipes that include this ingredient must also be removed.
     */
    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);

        // When an ingred is deleted, all associated recipes are also deleted.
        Predicate<Recipe> isValidRecipe =
            r -> r.getRecipeIngredientSet().getIngredientMap()
                        .keySet().stream().noneMatch(ingred -> ingred.equals(key));
        ObservableList<Recipe> recipeObservableList = recipes.asUnmodifiableObservableList();
        setRecipes(recipeObservableList.stream().filter(isValidRecipe).collect(Collectors.toList()));
        indicateModified();
    }

    /**
     * Gets the set of recipe names associated to the ingredient
     */
    public Set<String> getRecipesAssociated(Ingredient ingredient) {
        Predicate<Recipe> hasGivenIngredient = (recipe -> recipe.getRecipeIngredientSet()
                .getIngredientMap().containsKey(ingredient));
        Set<String> recipesAssociatedSet =
                recipes.asUnmodifiableObservableList().stream().filter(hasGivenIngredient)
                        .map(recipe -> recipe.getRecipeName().getName()).collect(Collectors.toSet());
        return recipesAssociatedSet;
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * {@code key} must exist in the restaurant book.
     */
    public void removeRecipe(Recipe key) {
        recipes.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * {@code key} must exist in the restaurant book.
     */
    public void removeStaff(Staff key) {
        staff.remove(key);
        indicateModified();
    }

    @Override
    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity newCapacity) {
        capacity = newCapacity;
        if(callback != null)
            callback.accept(capacity);
        if (!newCapacity.canAccommodate(bookings.asUnmodifiableObservableList())) {
            throw new RestaurantOverbookedException();
        }
    }

    /**
     * Counts the number of bookings associated with {@code member}
     */
    public int countBookings(Member member) {
        Predicate<Booking> hasGivenMember = (bookingToCheck -> bookingToCheck.getCustomer().equals(member));
        return (int) bookings.asUnmodifiableObservableList().stream().filter(hasGivenMember).count();
    }

    /**
     * Suggests a possible time to accommodate the booking.
     * @param toAdd The booking that the user wishes to add
     * @return The next available time that the restaurant can accommodate the booking, subjected to the constraint
     * that the returned time must occur after {@code toAdd}. In other words, suggestion always shifts the booking
     * later and never earlier.
     */
    public LocalDateTime suggestNextAvailableTime(Booking toAdd) {
        return capacity.suggestNextAvailableTime(toAdd, bookings.asUnmodifiableObservableList());
    }

    public boolean canUpdateCapacity(Capacity newCapacity) {
        return newCapacity.canAccommodate(bookings.asUnmodifiableObservableList());
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the restaurant book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return members.asUnmodifiableObservableList().size() + " members ";
        // TODO: refine later
    }

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Booking> getBookingList() {
        return bookings.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Ingredient> getIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }


    @Override
    public ObservableList<Recipe> getRecipeList() {
        return recipes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return staff.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestaurantBook // instanceof handles nulls
                && members.equals(((RestaurantBook) other).members)
                && bookings.equals(((RestaurantBook) other).bookings)
                && ingredients.equals(((RestaurantBook) other).ingredients)
                && recipes.equals(((RestaurantBook) other).recipes)
                && staff.equals(((RestaurantBook) other).staff));
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, bookings, ingredients, recipes, staff);
    }

    public void setUpdateCapacityCallback(Consumer<Capacity> callback) {
        this.callback = callback;
        callback.accept(capacity);
    }
}
