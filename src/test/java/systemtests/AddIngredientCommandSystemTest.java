package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_UNIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_WARNINGAMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_TOMATO_KETCHUP;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_QUANTITY_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_TOMATO_KETCHUP;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_NAME_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_QUANTITY_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_UNIT_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_WARNINGAMT_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_WARNINGAMT_DESC_CHEESE;
import static seedu.address.testutil.TypicalIngredients.CHEESE;
import static seedu.address.testutil.TypicalIngredients.CHICKEN;
import static seedu.address.testutil.TypicalIngredients.COFFEE;
import static seedu.address.testutil.TypicalIngredients.TOMATO_KETCHUP;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.add.AddIngredientCommand;
import seedu.address.logic.commands.add.AddMemberCommand;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;
import seedu.address.testutil.IngredientBuilder;
import seedu.address.testutil.IngredientUtil;

public class AddIngredientCommandSystemTest extends RestaurantBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an ingredient to a non-empty restaurant book, command with leading spaces and trailing spaces
         * -> added
         */
        Ingredient toAdd = CHEESE;
        String command = "   " + AddIngredientCommand.COMMAND_WORD + "  " + INGREDIENT_NAME_DESC_CHEESE + "  "
                + INGREDIENT_QUANTITY_DESC_CHEESE + " "
                + INGREDIENT_UNIT_DESC_CHEESE + " " + INGREDIENT_WARNINGAMT_DESC_CHEESE;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding cheese to the list -> cheese deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding cheese to the list -> cheese added again */
        command = RedoCommand.COMMAND_WORD;
        model.addIngredient(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add an ingredient with all fields same except ingredient name -> added */
        toAdd = new IngredientBuilder(CHEESE).withIngredientName(INGREDIENT_VALID_NAME_TOMATO).build();
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_NAME_DESC_TOMATO
                + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                + INGREDIENT_WARNINGAMT_DESC_CHEESE;
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty restaurant book -> added */
        deleteAllIngredients();
        assertCommandSuccess(COFFEE);

        /* Case: add an ingredient, command with all pararameters in random order -> added */
        toAdd = CHEESE;
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_WARNINGAMT_DESC_CHEESE
                + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                + INGREDIENT_NAME_DESC_CHEESE;
        assertCommandSuccess(command, toAdd);

        /* Case: add an ingredient, command with compulsory parameters in random order -> added */
        toAdd = TOMATO_KETCHUP;
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_UNIT_DESC_TOMATO_KETCHUP
                + INGREDIENT_NAME_DESC_TOMATO_KETCHUP;
        assertCommandSuccess(command, toAdd);

        /* Case: add an ingredient -> added */
        assertCommandSuccess(CHICKEN);

        /* TODO: add command executed after filtering list*/

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate ingredient -> rejected */
        command = IngredientUtil.getAddCommand(CHEESE);
        assertCommandFailure(command, AddIngredientCommand.MESSAGE_DUPLICATE);

        /* Case: add a duplicate ingredient except with different unit-> rejected */
        toAdd = new IngredientBuilder(CHEESE).withIngredientUnit(INGREDIENT_VALID_UNIT_TOMATO).build();
        command = IngredientUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddIngredientCommand.MESSAGE_DUPLICATE);

        /* Case: add a duplicate ingredient except with different quantity -> rejected */
        toAdd = new IngredientBuilder(CHEESE)
                .withIngredientQuantity(Integer.parseInt(INGREDIENT_VALID_QUANTITY_TOMATO)).build();
        command = IngredientUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddIngredientCommand.MESSAGE_DUPLICATE);

        /* Case: add a duplicate ingredient except with different warning amount -> rejected */
        toAdd = new IngredientBuilder(CHEESE)
                .withIngredientWarningAmount(Integer.parseInt(INGREDIENT_VALID_WARNINGAMT_TOMATO)).build();
        command = IngredientUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddIngredientCommand.MESSAGE_DUPLICATE);

        /* Case: missing compulsory field, missing ingredient name -> rejected */
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_UNIT_DESC_CHEESE
                + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_WARNINGAMT_DESC_CHEESE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddIngredientCommand.MESSAGE_USAGE));

        /* Case: missing compulsory field, missing ingredient unit -> rejected */
        command = AddMemberCommand.COMMAND_WORD + INGREDIENT_NAME_DESC_CHEESE
                + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_WARNINGAMT_DESC_CHEESE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));


        /* Case: invalid keyword -> rejected */
        command = "adds " + IngredientUtil.getIngredientDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid ingredient name -> rejected */
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_INVALID_NAME_DESC + INGREDIENT_UNIT_DESC_CHEESE;
        assertCommandFailure(command, IngredientName.MESSAGE_CONSTRAINTS);

        /* Case: invalid ingredient unit -> rejected */
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_INVALID_UNIT_DESC;
        assertCommandFailure(command, IngredientUnit.MESSAGE_CONSTRAINTS);

        /* Case: invalid ingredient quantity -> rejected */
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                + INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC;
        assertCommandFailure(command, IngredientQuantity.MESSAGE_CONSTRAINTS);

        /* Case: invalid ingredient warning amount-> rejected */
        command = AddIngredientCommand.COMMAND_WORD + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                + INGREDIENT_INVALID_WARNINGAMOUNT_DESC;
        assertCommandFailure(command, IngredientWarningAmount.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddIngredientommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddIngredientCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code IngredientListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     */
    private void assertCommandSuccess(Ingredient toAdd) {
        assertCommandSuccess(IngredientUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Ingredient)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, Ingredient toAdd) {
        Model expectedModel = getModel();
        expectedModel.addIngredient(toAdd);
        String expectedResultMessage = String.format(AddIngredientCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Ingredient)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code IngredientListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code MemberListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
