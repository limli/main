package seedu.address.logic.parser.ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_QUANTITY_NONINTEGER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_UNIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_WARNINGAMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_QUANTITY_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_QUANTITY_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_NAME_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_UNIT_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_WARNINGAMT_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_WARNINGAMT_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIngredients.CHEESE;
import static seedu.address.testutil.TypicalIngredients.CHEESE_BASIC;

import org.junit.Test;

import seedu.address.logic.commands.ingredient.AddIngredientCommand;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.testutil.IngredientBuilder;

public class AddIngredientCommandParserTest {
    private AddIngredientCommandParser parser = new AddIngredientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Ingredient expectedIngredient = new IngredientBuilder(CHEESE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                        + INGREDIENT_WARNINGAMT_DESC_CHEESE,
                new AddIngredientCommand(expectedIngredient));


        // multiple ingredient names last ingredient name recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_TOMATO
                        + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_QUANTITY_DESC_CHEESE
                        + INGREDIENT_UNIT_DESC_CHEESE + INGREDIENT_WARNINGAMT_DESC_CHEESE,
                new AddIngredientCommand(expectedIngredient));

        // multiple ingredient quantities last ingredient quantity recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_TOMATO + INGREDIENT_QUANTITY_DESC_CHEESE
                        + INGREDIENT_UNIT_DESC_CHEESE + INGREDIENT_WARNINGAMT_DESC_CHEESE,
                new AddIngredientCommand(expectedIngredient));

        // multiple ingredient units last ingredient unit recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_TOMATO
                        + INGREDIENT_UNIT_DESC_CHEESE + INGREDIENT_WARNINGAMT_DESC_CHEESE,
                new AddIngredientCommand(expectedIngredient));

        // multiple ingredient warning amts last ingredient unit recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                + INGREDIENT_WARNINGAMT_DESC_TOMATO + INGREDIENT_WARNINGAMT_DESC_CHEESE,
            new AddIngredientCommand(expectedIngredient));


    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        //ingredient quantity and ingredient warning amount missing
        Ingredient expectedIngredient = new IngredientBuilder(CHEESE_BASIC).build();
        assertParseSuccess(parser, INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                new AddIngredientCommand(expectedIngredient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE);

        // missing ingredientName
        assertParseFailure(parser, INGREDIENT_QUANTITY_DESC_CHEESE
                + INGREDIENT_UNIT_DESC_CHEESE, expectedMessage);

        // missing ingredientName prefix
        assertParseFailure(parser, INGREDIENT_VALID_NAME_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                expectedMessage);

        // missing ingredientUnit
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_QUANTITY_DESC_CHEESE, expectedMessage);

        // missing ingredientUnit prefix
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_VALID_UNIT_CHEESE,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid ingredientName
        assertParseFailure(parser, INGREDIENT_INVALID_NAME_DESC
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE
                        + INGREDIENT_WARNINGAMT_DESC_CHEESE, IngredientName.MESSAGE_CONSTRAINTS);


        // invalid ingredientQuantity, quantity is non-numerical
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_INVALID_QUANTITY_NONINTEGER_DESC + INGREDIENT_UNIT_DESC_CHEESE
                        + INGREDIENT_WARNINGAMT_DESC_CHEESE, IngredientQuantity.MESSAGE_CONSTRAINTS);

        // invalid ingredientQuantity, quantity is non-positive
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC
                        + INGREDIENT_UNIT_DESC_CHEESE + INGREDIENT_WARNINGAMT_DESC_CHEESE,
                IngredientQuantity.MESSAGE_CONSTRAINTS);

        // invalid ingredientUnit
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_INVALID_UNIT_DESC
                        + INGREDIENT_WARNINGAMT_DESC_CHEESE, IngredientUnit.MESSAGE_CONSTRAINTS);

        //invalid ingredientWarningAmount
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE
                        + INGREDIENT_INVALID_UNIT_DESC + INGREDIENT_INVALID_WARNINGAMOUNT_DESC,
                IngredientUnit.MESSAGE_CONSTRAINTS);

    }
}
