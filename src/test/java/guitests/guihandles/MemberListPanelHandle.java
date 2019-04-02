package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.person.member.Member;

/**
 * Provides a handle for {@code MemberListPanel} containing the list of {@code MemberCard}.
 */
public class MemberListPanelHandle extends NodeHandle<ListView<Member>> {
    public static final String MEMBER_LIST_VIEW_ID = "#itemListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Member> lastRememberedSelectedMemberCard;

    public MemberListPanelHandle(ListView<Member> memberListPanelNode) {
        super(memberListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code MemberCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MemberCardHandle getHandleToSelectedCard() {
        List<Member> selectedMemberList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedMemberList.size() != 1) {
            throw new AssertionError("Member list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(MemberCardHandle::new)
                .filter(handle -> handle.equals(selectedMemberList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Member> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code member}.
     */
    public void navigateToCard(Member member) {
        if (!getRootNode().getItems().contains(member)) {
            throw new IllegalArgumentException("Member does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(member);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code MemberCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the member card handle of a member associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MemberCardHandle getMemberCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(MemberCardHandle::new)
                .filter(handle -> handle.equals(getMember(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Member getMember(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code MemberCard} in the list.
     */
    public void rememberSelectedMemberCard() {
        List<Member> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedMemberCard = Optional.empty();
        } else {
            lastRememberedSelectedMemberCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MemberCard} is different from the value remembered by the most recent
     * {@code rememberSelectedMemberCard()} call.
     */
    public boolean isSelectedMemberCardChanged() {
        List<Member> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedMemberCard.isPresent();
        } else {
            return !lastRememberedSelectedMemberCard.isPresent()
                    || !lastRememberedSelectedMemberCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
