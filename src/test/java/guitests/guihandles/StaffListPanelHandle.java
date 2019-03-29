package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.person.staff.Staff;

/**
 * Provides a handle for {@code StaffListPanel} containing the list of {@code StaffCard}.
 */
public class StaffListPanelHandle extends NodeHandle<ListView<Staff>> {
    public static final String STAFF_LIST_VIEW_ID = "#itemListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Staff> lastRememberedSelectedStaffCard;

    public StaffListPanelHandle(ListView<Staff> staffListPanelNode) {
        super(staffListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code StaffCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public StaffCardHandle getHandleToSelectedCard() {
        List<Staff> selectedStaffList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedStaffList.size() != 1) {
            throw new AssertionError("Staff list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(StaffCardHandle::new)
                .filter(handle -> handle.equals(selectedStaffList.get(0)))
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
        List<Staff> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code staff}.
     */
    public void navigateToCard(Staff staff) {
        if (!getRootNode().getItems().contains(staff)) {
            throw new IllegalArgumentException("Staff does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(staff);
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
     * Selects the {@code StaffCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the staff card handle of a staff associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public StaffCardHandle getStaffCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(StaffCardHandle::new)
                .filter(handle -> handle.equals(getStaff(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Staff getStaff(int index) {
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
     * Remembers the selected {@code StaffCard} in the list.
     */
    public void rememberSelectedStaffCard() {
        List<Staff> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedStaffCard = Optional.empty();
        } else {
            lastRememberedSelectedStaffCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code StaffCard} is different from the value remembered by the most recent
     * {@code rememberSelectedStaffCard()} call.
     */
    public boolean isSelectedStaffCardChanged() {
        List<Staff> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedStaffCard.isPresent();
        } else {
            return !lastRememberedSelectedStaffCard.isPresent()
                    || !lastRememberedSelectedStaffCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
