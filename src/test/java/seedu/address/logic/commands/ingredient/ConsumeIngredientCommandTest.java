package seedu.address.logic.commands.ingredient;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INGREDIENT;
import static seedu.address.testutil.TypicalIngredients.CHEESE;
import static seedu.address.testutil.TypicalIngredients.TYPICAL_CONSUME_AMOUNT;
import static seedu.address.testutil.TypicalIngredients.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.testutil.IngredientBuilder;

public class ConsumeIngredientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_indexAndQuantityPresent_success() {
        Ingredient consumedIngredient = new IngredientBuilder()
                .withIngredientName("cheese").withIngredientQuantity(3)
                .withIngredientUnit("pounds").withIngredientWarningAmount(3).build();

        ConsumeIngredientCommand consumeIngredientCommand =
                new ConsumeIngredientCommand(INDEX_FIRST_INGREDIENT, new IngredientQuantity(TYPICAL_CONSUME_AMOUNT));

        String expectedMessage = String.format(ConsumeIngredientCommand.MESSAGE_SUCCESS, consumedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setIngredient(model.getFilteredIngredientList().get(0), consumedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(consumeIngredientCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        ConsumeIngredientCommand consumeCommand =
                new ConsumeIngredientCommand(outOfBoundIndex, new IngredientQuantity(TYPICAL_CONSUME_AMOUNT));
        assertCommandFailure(consumeCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidConsumeQuantity_failure() {
        int invalidConsumeQuantity = CHEESE.getIngredientQuantity().getQuantity() + 1;
        ConsumeIngredientCommand consumeCommand =
                new ConsumeIngredientCommand(INDEX_FIRST_INGREDIENT, new IngredientQuantity(invalidConsumeQuantity));
        assertCommandFailure(consumeCommand, model, commandHistory,
                ConsumeIngredientCommand.MESSAGE_EXCEEDS);
    }
}
