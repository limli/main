package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeIngredientSet;
import seedu.address.model.recipe.RecipeName;

/**
 * Jackson-friendly version of {@link Recipe}.
 */

public class JsonAdaptedRecipe {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String recipeName;
    private final List<JsonAdaptedIngredientAndQuantity> ingredListJson = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given ingredient details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("recipeName") String name,
                             @JsonProperty("ingredListJson") List<JsonAdaptedIngredientAndQuantity> list) {
        this.recipeName = name;
        if (ingredListJson != null) {
            this.ingredListJson.addAll(list);
        }
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        recipeName = source.getRecipeName().getName();
        ingredListJson.addAll(source.getRecipeIngredientSet().getIngredientSet().stream()
                .map(JsonAdaptedIngredientAndQuantity::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code Recipe} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public Recipe toModelType() throws IllegalValueException {

        final List<Pair<Ingredient, IngredientQuantity>> modelIngredList = new ArrayList<>();
        for (JsonAdaptedIngredientAndQuantity ingredientAndQuantity : ingredListJson) {
            modelIngredList.add(ingredientAndQuantity.toModelType());
        }

        if (recipeName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Recipe.class.getSimpleName()));
        }

        if (!RecipeName.isValidRecipeName(recipeName)) {
            throw new IllegalValueException(RecipeName.MESSAGE_CONSTRAINTS);
        }

        RecipeName modelRecipe = new RecipeName(recipeName);

        if (ingredListJson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Recipe.class.getSimpleName()));
        }

        final Set<Pair<Ingredient, IngredientQuantity>> modelIngreds = new HashSet<>(modelIngredList);
        RecipeIngredientSet modelIngredSet = new RecipeIngredientSet(modelIngreds);
        Recipe newRecipe = new Recipe(modelRecipe, modelIngredSet);
        return newRecipe;
    }

}

