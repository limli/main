package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestaurantBook;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class containing a list of {@code Ingredient} objects to be used in tests.
 */

public class TypicalIngredients {

    public static final Ingredient CHEESE =
            new IngredientBuilder().withIngredientName("cheese").withIngredientQuantity(4)
                    .withIngredientUnit("pounds").withIngredientWarningAmount(3).build();
    public static final Ingredient CHEESE_BASIC =
            new IngredientBuilder().withIngredientName("cheese").withIngredientQuantity(0)
                    .withIngredientUnit("pounds").withIngredientWarningAmount(0).build();
    public static final Ingredient TOMATO =
            new IngredientBuilder().withIngredientName("tomato")
            .withIngredientQuantity(5).withIngredientUnit("pieces").withIngredientWarningAmount(2).build();
    public static final Ingredient COFFEE =
            new IngredientBuilder().withIngredientName("coffee")
            .withIngredientQuantity(10).withIngredientUnit("sacks").withIngredientWarningAmount(5).build();
    public static final Ingredient CHICKEN =
            new IngredientBuilder().withIngredientName("chicken")
            .withIngredientQuantity(100).withIngredientUnit("full chickens")
            .withIngredientWarningAmount(20).build();
    public static final Ingredient RICE =
            new IngredientBuilder().withIngredientName("rice")
            .withIngredientQuantity(20).withIngredientUnit("sacks")
            .withIngredientWarningAmount(6).build();
    public static final Ingredient JUICE =
            new IngredientBuilder().withIngredientName("juice")
            .withIngredientQuantity(5).withIngredientUnit("bottles")
            .withIngredientWarningAmount(1).build();
    public static final Ingredient TOMATO_KETCHUP =
            new IngredientBuilder().withIngredientName("tomato ketchup")
                    .withIngredientQuantity(0).withIngredientUnit("bottles")
                    .withIngredientWarningAmount(0).build();

    public static final int TYPICAL_RESTOCK_AMOUNT = 1;
    public static final int TYPICAL_CONSUME_AMOUNT = 1;
    public static final String KEYWORD_MATCHING_TOMATO = "tomato"; // A keyword that matches MEIER



    private TypicalIngredients() {} // prevents instantiation

    /**
     * Returns an {@code RestaurantBook} with all the typical ingredients.
     */
    public static RestaurantBook getTypicalAddressBook() {
        RestaurantBook ab = new RestaurantBook();
        for (Ingredient ingred : getTypicalIngredients()) {
            ab.addIngredient(ingred);
        }
        return ab;
    }

    public static List<Ingredient> getTypicalIngredients() {
        return new ArrayList<>(Arrays.asList(CHEESE, TOMATO, COFFEE, CHICKEN, RICE, JUICE));
    }
}
