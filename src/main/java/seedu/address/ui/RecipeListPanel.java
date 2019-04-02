package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import seedu.address.model.recipe.Recipe;

/**
 * Panel containing the list of recipes.
 */
public class RecipeListPanel extends ItemListPanel<Recipe> {

    @FXML
    private Label title;

    public RecipeListPanel(ObservableList<Recipe> recipeList, ObservableValue<Recipe> selectedRecipe,
                           Consumer<Recipe> onSelectedRecipeChange) {
        super(recipeList, selectedRecipe, onSelectedRecipeChange, listview -> new RecipeListViewCell());
        title.setText("Recipe");
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
 */
class RecipeListViewCell extends ListCell<Recipe> {
    @Override
    protected void updateItem(Recipe recipe, boolean empty) {
        super.updateItem(recipe, empty);

        if (empty || recipe == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new RecipeCard(recipe, getIndex() + 1).getRoot());
        }
    }
}
