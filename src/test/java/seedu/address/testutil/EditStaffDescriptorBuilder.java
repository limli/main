package seedu.address.testutil;

import seedu.address.logic.commands.EditStaffCommand.EditStaffDescriptor;
import seedu.address.model.person.staff.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Staff;

/**
 * A utility class to help with building EditStaffDescriptor objects.
 */
public class EditStaffDescriptorBuilder {

    private EditStaffDescriptor descriptor;

    public EditStaffDescriptorBuilder() {
        descriptor = new EditStaffDescriptor();
    }

    public EditStaffDescriptorBuilder(EditStaffDescriptor descriptor) {
        this.descriptor = new EditStaffDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStaffDescriptor} with fields containing {@code staff}'s details
     */
    public EditStaffDescriptorBuilder(Staff staff) {
        descriptor = new EditStaffDescriptor();
        descriptor.setName(staff.getName());
        descriptor.setPhone(staff.getPhone());
        descriptor.setEmail(staff.getEmail());
        descriptor.setAppointment(staff.getAppointment());
    }

    /**
     * Sets the {@code Name} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withAppointment(String appointment) {
        descriptor.setAppointment(new Appointment(appointment));
        return this;
    }

    public EditStaffDescriptor build() {
        return descriptor;
    }
}
