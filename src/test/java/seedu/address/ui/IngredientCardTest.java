package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysIngredient;

import org.junit.Test;

import guitests.guihandles.IngredientCardHandle;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

public class IngredientCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Ingredient ingredient = new IngredientBuilder()
                .withIngredientName("corn").withIngredientQuantity(12)
                .withIngredientUnit("kg").withIngredientWarningAmount(3).build();
        IngredientCard ingredientCard = new IngredientCard(ingredient, 1);
        uiPartRule.setUiPart(ingredientCard);
        assertCardDisplay(ingredientCard, ingredient, 1);
    }

    @Test
    public void equals() {
        Ingredient ingredient = new IngredientBuilder()
                .withIngredientName("corn").withIngredientQuantity(12)
                .withIngredientUnit("kg").withIngredientWarningAmount(3).build();

        IngredientCard ingredientCard = new IngredientCard(ingredient, 0);

        // same ingredient, same index -> returns true
        IngredientCard copy = new IngredientCard(ingredient, 0);
        assertTrue(ingredientCard.equals(copy));

        // same object -> returns true
        assertTrue(ingredientCard.equals(ingredientCard));

        // null -> returns false
        assertFalse(ingredientCard.equals(null));

        // different types -> returns false
        assertFalse(ingredientCard.equals(0));

        // different ingredient, same index -> returns false
        Ingredient differentIngredient = new IngredientBuilder()
                .withIngredientName("differentName")
                .withIngredientQuantity(3).withIngredientUnit("differentUnit")
                .withIngredientWarningAmount(1).build();
        assertFalse(ingredientCard.equals(new IngredientCard(differentIngredient, 0)));

        // same ingredient, different index -> returns false
        assertFalse(ingredientCard.equals(new IngredientCard(ingredient, 1)));
    }

    /**
     * Asserts that {@code ingredientCard} displays the details of {@code expectedIngredient} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(IngredientCard ingredientCard, Ingredient expectedIngredient, int expectedId) {
        guiRobot.pauseForHuman();

        IngredientCardHandle ingredientCardHandle = new IngredientCardHandle(ingredientCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", ingredientCardHandle.getId());

        // verify ingredient details are displayed correctly
        assertCardDisplaysIngredient(expectedIngredient, ingredientCardHandle);
    }
}
