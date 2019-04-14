package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;

/**
 * Panel containing the list of members.
 */
public class BookingListPanel extends ItemListPanel<Booking> {

    @FXML
    private Label title;

    public BookingListPanel(ObservableList<Booking> bookingList, ObservableValue<Booking> selectedBooking,
                            Consumer<Booking> onSelectedBookingChange) {
        super(bookingList, selectedBooking, onSelectedBookingChange, listview -> new BookingListViewCell());
        title.setText("Booking");
    }

    public void setCapacity(Capacity capacity) {
        title.setText("Booking\nCapacity: " + capacity.toString());
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Member} using a {@code MemberCard}.
 */
class BookingListViewCell extends ListCell<Booking> {
    @Override
    protected void updateItem(Booking booking, boolean empty) {
        super.updateItem(booking, empty);

        if (empty || booking == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new BookingCard(booking, getIndex() + 1).getRoot());
        }
    }
}
