package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import javafx.util.Pair;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;
import seedu.address.testutil.Assert;
import seedu.address.testutil.IngredientBuilder;


public class JsonAdaptedIngredientAndQuantityTest {
    private static final String INVALID_INGREDIENT_NAME = "ch!l!";
    private static final String INVALID_INGREDIENT_UNIT = "k!l0gr@m";
    private static final int INVALID_INGREDIENT_QUANTITY = -1;
    private static final int INVALID_INGREDIENT_WARNING_AMOUNT = -1;
    private static final int INVALID_INGREDIENT_QUANTITY_IN_RECIPE = -1;

    private static final String VALID_INGREDIENT_NAME = "corn";
    private static final String VALID_INGREDIENT_UNIT = "kg";
    private static final int VALID_INGREDIENT_QUANTITY = 5;
    private static final int VALID_INGREDIENT_WARNING_AMOUNT = 3;
    private static final int VALID_INGREDIENT_QUANTITY_IN_RECIPE = 2;

    private static final Ingredient VALID_INGREDIENT = new IngredientBuilder().withIngredientName(VALID_INGREDIENT_NAME)
            .withIngredientQuantity(VALID_INGREDIENT_QUANTITY).withIngredientUnit(VALID_INGREDIENT_UNIT)
            .withIngredientWarningAmount(VALID_INGREDIENT_WARNING_AMOUNT).build();

    @Test
    public void toModelType_validIngredientDetails_returnsIngredientQuantityPair() throws Exception {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(VALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        VALID_INGREDIENT_UNIT, VALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);

        assertEquals(new Pair<>(VALID_INGREDIENT, new IngredientQuantity(VALID_INGREDIENT_QUANTITY_IN_RECIPE)),
                ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidIngredientName_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(INVALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        VALID_INGREDIENT_UNIT, VALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = IngredientName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullIngredientName_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(null, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        VALID_INGREDIENT_UNIT, VALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ingredient.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidIngredientUnit_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(VALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        INVALID_INGREDIENT_UNIT, VALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = IngredientUnit.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullIngredientUnit_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(VALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        null, VALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientUnit.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidIngredientQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(VALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        VALID_INGREDIENT_UNIT, INVALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = IngredientQuantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidIngredientWarningAmount_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(VALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        VALID_INGREDIENT_UNIT, VALID_INGREDIENT_QUANTITY, INVALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = IngredientWarningAmount.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidIngredientQuantityInRecipe_throwsIllegalValueException() {
        JsonAdaptedIngredientAndQuantity ingredient =
                new JsonAdaptedIngredientAndQuantity(VALID_INGREDIENT_NAME, INVALID_INGREDIENT_QUANTITY_IN_RECIPE,
                        VALID_INGREDIENT_UNIT, VALID_INGREDIENT_QUANTITY, VALID_INGREDIENT_WARNING_AMOUNT);
        String expectedMessage = IngredientQuantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }



}
