package seedu.address.model.ingredient;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IngredientName(null));
    }

    @Test
    public void constructor_invalidIngredientName_throwsIllegalArgumentException() {
        String emptyName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientName(emptyName));

        String oneChar = "a";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientName(oneChar));
    }

    @Test
    public void testIsValidIngredientName() {
        // null ingredientName
        Assert.assertThrows(NullPointerException.class, () -> IngredientName.isValidIngredientName(null));

        // invalid ingredientName
        assertFalse(IngredientName.isValidIngredientName("")); // empty string
        assertFalse(IngredientName.isValidIngredientName("t")); // one character string
        assertFalse(IngredientName.isValidIngredientName(" ")); // spaces only
        assertFalse(IngredientName.isValidIngredientName("^^")); // only contains non-alphabet symbols
        assertFalse(IngredientName.isValidIngredientName("22")); // only contains non-alphabet integers
        assertFalse(IngredientName.isValidIngredientName("chee*3se")); // contains non-alphabet characters

        // valid name
        assertTrue(IngredientName.isValidIngredientName("to")); //at least 2 character string
        assertTrue(IngredientName.isValidIngredientName("tomato")); //alphabets only no space
        assertTrue(IngredientName.isValidIngredientName("tomato sauce")); // alphabets only with space
    }
}
