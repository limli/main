package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.booking.Booking;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Provides a handle for {@code BookingListPanel} containing the list of {@code BookingCard}.
 */
public class BookingListPanelHandle extends NodeHandle<ListView<Booking>> {
    public static final String BOOKING_LIST_VIEW_ID = "#itemListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Booking> lastRememberedSelectedBookingCard;

    public BookingListPanelHandle(ListView<Booking> bookingListPanelNode) {
        super(bookingListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code BookingCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BookingCardHandle getHandleToSelectedCard() {
        List<Booking> selectedBookingList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedBookingList.size() != 1) {
            throw new AssertionError("Booking list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(BookingCardHandle::new)
                .filter(handle -> handle.equals(selectedBookingList.get(0)))
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
        List<Booking> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code booking}.
     */
    public void navigateToCard(Booking booking) {
        if (!getRootNode().getItems().contains(booking)) {
            throw new IllegalArgumentException("Booking does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(booking);
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
     * Selects the {@code BookingCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the booking card handle of a booking associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BookingCardHandle getBookingCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(BookingCardHandle::new)
                .filter(handle -> handle.equals(getBooking(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Booking getBooking(int index) {
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
     * Remembers the selected {@code BookingCard} in the list.
     */
    public void rememberSelectedBookingCard() {
        List<Booking> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedBookingCard = Optional.empty();
        } else {
            lastRememberedSelectedBookingCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code BookingCard} is different from the value remembered by the most recent
     * {@code rememberSelectedBookingCard()} call.
     */
    public boolean isSelectedBookingCardChanged() {
        List<Booking> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedBookingCard.isPresent();
        } else {
            return !lastRememberedSelectedBookingCard.isPresent()
                    || !lastRememberedSelectedBookingCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
