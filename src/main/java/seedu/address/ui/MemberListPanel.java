package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import seedu.address.model.person.member.Member;

/**
 * Panel containing the list of members.
 */
public class MemberListPanel extends ItemListPanel<Member> {

    @FXML
    private Label title;

    public MemberListPanel(ObservableList<Member> memberList, ObservableValue<Member> selectedMember,
                           Consumer<Member> onSelectedMemberChange) {
        super(memberList, selectedMember, onSelectedMemberChange, listview -> new MemberListViewCell());
        title.setText("Member");
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Member} using a {@code MemberCard}.
 */
class MemberListViewCell extends ListCell<Member> {
    @Override
    protected void updateItem(Member member, boolean empty) {
        super.updateItem(member, empty);

        if (empty || member == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new MemberCard(member, getIndex() + 1).getRoot());
        }
    }
}
