package seedu.address.model.recipe;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RecipeNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RecipeName(null));
    }

    @Test
    public void constructor_invalidRecipeName_throwsIllegalArgumentException() {
        String emptyName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RecipeName(emptyName));

        String oneChar = "a";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RecipeName(oneChar));
    }

    @Test
    public void testIsValidRecipeName() {
        // null recipeName
        Assert.assertThrows(NullPointerException.class, () -> RecipeName.isValidRecipeName(null));

        // invalid recipeName
        assertFalse(RecipeName.isValidRecipeName("")); // empty string
        assertFalse(RecipeName.isValidRecipeName("t")); // one character string
        assertFalse(RecipeName.isValidRecipeName(" ")); // spaces only
        assertFalse(RecipeName.isValidRecipeName("^^")); // only contains non-alphabet symbols
        assertFalse(RecipeName.isValidRecipeName("22")); // only contains non-alphabet integers
        assertFalse(RecipeName.isValidRecipeName("chee*3se")); // contains non-alphabet characters

        // valid name
        assertTrue(RecipeName.isValidRecipeName("to")); //at least 2 character string
        assertTrue(RecipeName.isValidRecipeName("tomato")); //alphabets only no space
        assertTrue(RecipeName.isValidRecipeName("tomato sauce")); // alphabets only with space
    }
}
