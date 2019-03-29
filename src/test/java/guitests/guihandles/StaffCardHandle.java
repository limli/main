package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.person.staff.Staff;

/**
 * Provides a handle to a staff card in the staff list panel.
 */
public class StaffCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String APPOINTMENT_FIELD_ID = "#appointment";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label appointmentLabel;

    public StaffCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        appointmentLabel = getChildNode(APPOINTMENT_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAppointment() {
        return appointmentLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code staff}.
     */
    public boolean equals(Staff staff) {
        return getName().equals(staff.getName().fullName)
                && getPhone().equals(staff.getPhone().value)
                && getEmail().equals(staff.getEmail().value)
                && getAppointment().equals(staff.getAppointment().appointmentName);
    }
}
