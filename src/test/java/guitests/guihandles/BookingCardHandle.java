package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.booking.Booking;

/**
 * Provides a handle to a booking card in the booking list panel.
 */
public class BookingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String NUM_MEMBERS_FIELD_ID = "#numMembers";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String DATE_FIELD_ID = "#date";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label numMembersLabel;
    private final Label phoneLabel;
    private final Label dateLabel;

    public BookingCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        numMembersLabel = getChildNode(NUM_MEMBERS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getNumMembers() {
        return numMembersLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code booking}.
     */
    public boolean equals(Booking booking) {
        System.out.println(booking.getCustomer().getName().fullName + " | " + getName());
        System.out.println("(" + booking.getNumMembers() + " person(s))" + " | " + getNumMembers());
        System.out.println(booking.getCustomer().getPhone() + " | " + getPhone());
        System.out.println(booking.getStartTimeString() + " | " + getDate());
        return getName().equals(booking.getCustomer().getName().fullName)
                && getNumMembers().equals("(" + booking.getNumMembers() + " person(s))")
                && getPhone().equals(booking.getCustomer().getPhone().value)
                && getDate().equals(booking.getStartTimeString());
    }
}
