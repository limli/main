package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import seedu.address.model.person.staff.Staff;

/**
 * Panel containing the list of staff.
 */
public class StaffListPanel extends ItemListPanel<Staff> {


    @FXML
    private Label title;

    public StaffListPanel(ObservableList<Staff> memberList, ObservableValue<Staff> selectedMember,
                           Consumer<Staff> onSelectedMemberChange) {
        super(memberList, selectedMember, onSelectedMemberChange, listview -> new StaffListViewCell());
        title.setText("Staff");
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Staff} using a {@code StaffCard}.
 */
class StaffListViewCell extends ListCell<Staff> {
    @Override
    protected void updateItem(Staff staff, boolean empty) {
        super.updateItem(staff, empty);

        if (empty || staff == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new StaffCard(staff, getIndex() + 1).getRoot());
        }
    }
}
