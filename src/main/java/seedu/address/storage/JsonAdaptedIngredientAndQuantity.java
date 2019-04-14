package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;


/**
 * Jackson-friendly version.
 */
class JsonAdaptedIngredientAndQuantity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    private final String ingredName;
    private final String ingredUnit;
    private final int ingredQuantity;
    private final int ingredWarningAmount;
    private final int ingredQuantityInRecipe;

    /**
     * Constructs a {@code JsonAdaptedIngredientAndQuantity} with the given ingredient details.
     */

    @JsonCreator
    public JsonAdaptedIngredientAndQuantity (@JsonProperty("ingredName") String name,
                                  @JsonProperty("ingredQuantityInRecipe") int quantityInRecipe,
                                  @JsonProperty("ingredUnit") String unit,
                                  @JsonProperty("ingredQuantity") int quantity,
                                  @JsonProperty("ingredWarningAmount") int warningAmount) {
        this.ingredName = name;
        this.ingredQuantityInRecipe = quantityInRecipe;
        this.ingredUnit = unit;
        this.ingredQuantity = quantity;
        this.ingredWarningAmount = warningAmount;
    }

    /**
     * Converts a given {@code Pair<Ingredient,IngredientQuantity>} into this class for Jackson use.
     */
    public JsonAdaptedIngredientAndQuantity(Pair<Ingredient, IngredientQuantity> source) {
        ingredName = source.getKey().getIngredientName().getName();
        ingredQuantityInRecipe = source.getValue().getQuantity();
        ingredUnit = source.getKey().getIngredientUnit().getUnit();
        ingredQuantity = source.getKey().getIngredientQuantity().getQuantity();
        ingredWarningAmount = source.getKey().getIngredientWarningAmount().getWarningAmount();
    }


    /**
     * Converts this Jackson-friendly adapted ingredientAndQuantity object
     * into the model's {@code Pair<Ingredient,Quantity>} object.
     * TODO: check if ingredient fields are of proper type
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Pair<Ingredient, IngredientQuantity> toModelType() throws IllegalValueException {

        if (ingredName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Ingredient.class.getSimpleName()));
        }
        if (!IngredientName.isValidIngredientName(ingredName)) {
            throw new IllegalValueException(IngredientName.MESSAGE_CONSTRAINTS);
        }

        if (ingredUnit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientUnit.class.getSimpleName()));
        }
        if (!IngredientUnit.isValidIngredientUnit(ingredUnit)) {
            throw new IllegalValueException(IngredientUnit.MESSAGE_CONSTRAINTS);
        }

        if (!IngredientQuantity.isValidIngredientQuantity(Integer.toString(ingredQuantityInRecipe))) {
            throw new IllegalValueException(IngredientQuantity.MESSAGE_CONSTRAINTS);
        }

        if (!IngredientQuantity.isValidIngredientQuantity(Integer.toString(ingredQuantity))) {
            throw new IllegalValueException(IngredientQuantity.MESSAGE_CONSTRAINTS);
        }

        if (!IngredientWarningAmount.isValidIngredientWarningAmount(Integer.toString(ingredWarningAmount))) {
            throw new IllegalValueException(IngredientWarningAmount.MESSAGE_CONSTRAINTS);
        }


        IngredientName ingredientName = new IngredientName(ingredName);
        IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredQuantity);
        IngredientUnit ingredientUnit = new IngredientUnit(ingredUnit);
        IngredientWarningAmount ingredientWarningAmount = new IngredientWarningAmount(ingredWarningAmount);
        IngredientQuantity ingredientQuantityInRecipe = new IngredientQuantity(ingredQuantityInRecipe);

        Ingredient ingred = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit, ingredientWarningAmount);
        Pair<Ingredient, IngredientQuantity> ingredientAndQuantityPair = new Pair(ingred, ingredientQuantityInRecipe);
        return ingredientAndQuantityPair;
    }

}
