package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.model.IngredientModel.PREDICATE_SHOW_ALL_INGREDIENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Consumes the quantity of existing ingredient in the restaurant book by stipulated amount.
 */

public class ConsumeIngredientCommand extends Command {

    public static final String COMMAND_WORD = "consumeingredient";
    public static final String COMMAND_ALIAS = "ci";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Consumes the quantity of the ingredient identified "
            + "by the index number used in the displayed ingredient list. "
            + "Input consumption value will be removed from existing ingredient quantity.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INGREDIENT_QUANTITY + "10 ";

    public static final String MESSAGE_SUCCESS = "Consumed Ingredient: %1$s";
    public static final String MESSAGE_EXCEEDS =
            "Quantity to consume exceeds current quantity of ingredient in inventory";


    private final Index index;
    private final IngredientQuantity quantityToConsume;

    /**
     * Creates an ConsumeIngredientCommand to deplete the specified {@code Ingredient}
     *
     * @param index
     */
    public ConsumeIngredientCommand(Index index, IngredientQuantity quantity) {
        requireNonNull(index);
        this.index = index;
        this.quantityToConsume = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToConsume = lastShownList.get(index.getZeroBased());

        if (quantityToConsume.getQuantity() > ingredientToConsume.getIngredientQuantity().getQuantity()) {
            throw new CommandException(MESSAGE_EXCEEDS);
        }

        Ingredient consumedIngredient = createConsumedIngredient(ingredientToConsume, quantityToConsume);
        model.setIngredient(ingredientToConsume, consumedIngredient);


        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, consumedIngredient));
    }

    /**
     * New Ingredient with depleted quantity is returned.
     * @param ingredientToConsume
     * @return Ingredient
     */
    private static Ingredient createConsumedIngredient(Ingredient ingredientToConsume, IngredientQuantity quantity) {
        assert ingredientToConsume != null;

        int currentQuantity = ingredientToConsume.getIngredientQuantity().getQuantity();
        int newQuantity = currentQuantity - quantity.getQuantity();
        IngredientQuantity newIngredientQuantity = new IngredientQuantity(newQuantity);
        return new Ingredient(ingredientToConsume.getIngredientName(), newIngredientQuantity,
                ingredientToConsume.getIngredientUnit(), ingredientToConsume.getIngredientWarningAmount());
    }

    public IngredientQuantity getQuantityToConsume() {
        return quantityToConsume;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConsumeIngredientCommand)) {
            return false;
        }

        // state check
        ConsumeIngredientCommand e = (ConsumeIngredientCommand) other;
        return index.equals(e.index)
                && quantityToConsume.equals(e.getQuantityToConsume());
    }
}
