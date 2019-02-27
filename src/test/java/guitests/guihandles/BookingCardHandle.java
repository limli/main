package guitests.guihandles;

import java.text.SimpleDateFormat;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.booking.Booking;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class BookingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String NUM_FIELD_ID = "#numPersons";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String DATE_FIELD_ID = "#date";

    private Label idLabel;
    private Label nameLabel;
    private Label numPersonsLabel;
    private Label phoneLabel;
    private Label dateLabel;

    public BookingCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        numPersonsLabel = getChildNode(NUM_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getNumPersons() {
        return numPersonsLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Booking booking) {
        return getName().equals(booking.getCustomer().getName().fullName)
                && getPhone().equals(booking.getCustomer().getPhone().value)
                && getNumPersons().equals("(" + booking.getNumPersons() + " person(s))")
                && getDate().equals(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(booking.getStartTime()));
    }
}
