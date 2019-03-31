package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;
import seedu.address.model.recipe.Recipe;

/**
 * An Immutable RestaurantBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableRestaurantBook {

    public static final String MESSAGE_DUPLICATE_MEMBER = "Members list contains duplicate member(s).";
    public static final String MESSAGE_DUPLICATE_BOOKING = "Bookings list contains duplicate booking(s).";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Ingredient list contains duplicate ingredient(s).";
    public static final String MESSAGE_DUPLICATE_RECIPE = "Recipe list contains duplicate recipe(s).";
    public static final String MESSAGE_DUPLICATE_STAFF = "Staff list contains duplicate staff(s).";


    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();
    private final List<JsonAdaptedRecipe> recipes = new ArrayList<>();
    private final List<JsonAdaptedStaff> staff = new ArrayList<>();
    private final List<JsonAdaptedBooking> bookings = new ArrayList<>();
    private final int intCapacity;

    /**
     * Constructs a {@code JsonSerializableRestaurantBook} with the given members.
     */
    @JsonCreator
    public JsonSerializableRestaurantBook(@JsonProperty("members") List<JsonAdaptedMember> members,
                                          @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
                                          @JsonProperty("recipes") List<JsonAdaptedRecipe> recipes,
                                          @JsonProperty("staff") List<JsonAdaptedStaff> staff,
                                          @JsonProperty("bookings") List<JsonAdaptedBooking> bookings,
                                          @JsonProperty("capacity") int intCapacity) {

        this.members.addAll(members);
        this.ingredients.addAll(ingredients);
        this.recipes.addAll(recipes);
        this.staff.addAll(staff);
        this.bookings.addAll(bookings);
        this.intCapacity = intCapacity;
    }

    /**
     * Converts a given {@code ReadOnlyRestaurantBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestaurantBook}.
     */
    public JsonSerializableRestaurantBook(ReadOnlyRestaurantBook source) {

        members.addAll(source.getMemberList().stream()
                .map(JsonAdaptedMember::new).collect(Collectors.toList()));
        ingredients.addAll(source.getIngredientList().stream()
                .map(JsonAdaptedIngredient::new).collect(Collectors.toList()));
        recipes.addAll(source.getRecipeList().stream()
                .map(JsonAdaptedRecipe::new).collect(Collectors.toList()));
        staff.addAll(source.getStaffList().stream()
                .map(JsonAdaptedStaff::new).collect(Collectors.toList()));
        bookings.addAll(source.getBookingList().stream()
                .map(JsonAdaptedBooking::new).collect(Collectors.toList()));
        intCapacity = source.getCapacity().getValue();

    }

    /**
     * Converts this address book into the model's {@code RestaurantBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RestaurantBook toModelType() throws IllegalValueException {
        RestaurantBook restaurantBook = new RestaurantBook();
        for (JsonAdaptedMember jsonAdaptedMember : members) {
            Member member = jsonAdaptedMember.toModelType();
            if (restaurantBook.hasMember(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBER);
            }
            restaurantBook.addMember(member);
        }


        for (JsonAdaptedIngredient jsonAdaptedIngredient : ingredients) {
            Ingredient ingredient = jsonAdaptedIngredient.toModelType();
            if (restaurantBook.hasIngredient(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            restaurantBook.addIngredient(ingredient);
        }

        for (JsonAdaptedRecipe jsonAdaptedRecipe : recipes) {
            Recipe recipe = jsonAdaptedRecipe.toModelType();
            if (restaurantBook.hasRecipe(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECIPE);
            }
            restaurantBook.addRecipe(recipe);
        }

        for (JsonAdaptedStaff jsonAdaptedStaff : staff) {
            Staff staff = jsonAdaptedStaff.toModelType();
            if (restaurantBook.hasStaff(staff)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            restaurantBook.addStaff(staff);
        }

        for (JsonAdaptedBooking jsonAdaptedBooking : bookings) {
            Booking booking = jsonAdaptedBooking.toModelType();
            if (restaurantBook.hasBooking(booking)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOKING);
            }
            restaurantBook.addBooking(booking);
        }

        try {
            Capacity capacity = new Capacity(intCapacity);
            restaurantBook.setCapacity(capacity);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(Capacity.MESSAGE_CONSTRAINTS);
        }

        return restaurantBook;
    }

}
