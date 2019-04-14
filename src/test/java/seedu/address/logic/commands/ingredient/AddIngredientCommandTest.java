package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalIngredients.CHEESE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

/**
 * Add Ingredient Command test to check if ingredients are properly added into the model.
 */

public class AddIngredientCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_ingredientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIngredientAdded modelStub = new ModelStubAcceptingIngredientAdded();
        Ingredient validIngredient = new IngredientBuilder().build();

        CommandResult commandResult = new AddIngredientCommand(validIngredient).execute(modelStub, commandHistory);

        assertEquals(String.format(AddIngredientCommand.MESSAGE_SUCCESS, validIngredient),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIngredient), modelStub.ingredientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() throws Exception {
        Ingredient validIngredient = new IngredientBuilder(CHEESE).build();
        AddIngredientCommand addCommand = new AddIngredientCommand(validIngredient);
        ModelStub modelStub = new ModelStubWithIngredient(validIngredient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddIngredientCommand.MESSAGE_DUPLICATE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Ingredient cheese =
                new IngredientBuilder().withIngredientName("cheese")
                .withIngredientQuantity(4).withIngredientUnit("pounds")
                .withIngredientWarningAmount(2).build();

        Ingredient tomato =
                new IngredientBuilder().withIngredientName("tomato")
                .withIngredientQuantity(5).withIngredientUnit("pieces")
                .withIngredientWarningAmount(5).build();

        Ingredient modifiedCheeseQuantity =
                new IngredientBuilder().withIngredientName("cheese")
                .withIngredientQuantity(6).withIngredientUnit("pounds")
                .withIngredientWarningAmount(2).build();

        Ingredient modifiedCheeseUnit =
                new IngredientBuilder().withIngredientName("cheese")
                .withIngredientQuantity(4).withIngredientUnit("kg")
                .withIngredientWarningAmount(2).build();

        Ingredient modifiedCheeseWarningAmt =
                new IngredientBuilder().withIngredientName("cheese")
                .withIngredientQuantity(4).withIngredientUnit("pounds")
                .withIngredientWarningAmount(3).build();

        AddIngredientCommand addCheeseCommand = new AddIngredientCommand(cheese);
        AddIngredientCommand addTomatoCommand = new AddIngredientCommand(tomato);

        // same object -> returns true
        assertEquals(addCheeseCommand, addCheeseCommand);

        // same ingredient name, quantity, unit, warningamt -> returns true
        AddIngredientCommand addCheeseCommandCopy = new AddIngredientCommand(cheese);
        assertEquals(addCheeseCommandCopy, addCheeseCommand);

        //all same fields except unit -> returns false
        AddIngredientCommand addCheeseCommandModifiedUnit = new AddIngredientCommand(modifiedCheeseUnit);
        assertNotEquals(addCheeseCommand, addCheeseCommandModifiedUnit);

        //all same fields except quantity -> returns false
        AddIngredientCommand addCheeseCommandModifiedQty = new AddIngredientCommand(modifiedCheeseQuantity);
        assertNotEquals(addCheeseCommand, addCheeseCommandModifiedQty);

        //all same fields except warningamt -> returns false
        AddIngredientCommand addCheeseCommandModifiedWarningAmt = new AddIngredientCommand(modifiedCheeseWarningAmt);
        assertNotEquals(addCheeseCommand, addCheeseCommandModifiedWarningAmt);

        // different types -> returns false
        assertNotEquals(addCheeseCommand, 1);

        // null -> returns false
        assertNotEquals(addCheeseCommand, null);

        // different ingredient -> returns false
        assertNotEquals(addCheeseCommand, addTomatoCommand);
    }

    /**
     * A Model stub that contains an ingredient.
     */
    private class ModelStubWithIngredient extends ModelStub {
        private final Ingredient ingredient;

        ModelStubWithIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            this.ingredient = ingredient;
        }

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return this.ingredient.isSameItem(ingredient);
        }
    }

    /**
     * A Model stub that always accept the ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {
        final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return ingredientsAdded.stream().anyMatch(ingredient::isSameItem);
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            ingredientsAdded.add(ingredient);
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code AddIngredientCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }
    }

}
