package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STAFF;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.staff.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.exceptions.DuplicateItemException;

/**
 * Edits the details of an existing staff in the address book.
 */
public class EditStaffCommand extends Command {

    public static final String COMMAND_WORD = "editstaff";
    public static final String COMMAND_ALIAS = "es";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the staff identified "
            + "by the index number used in the displayed staff list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL]\n"
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "97654321 "
            + PREFIX_APPOINTMENT + "Head Chef";

    public static final String MESSAGE_EDIT_STAFF_SUCCESS = "Edited Staff: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STAFF = "This staff already exists in the address book.";

    private final Index index;
    private final EditStaffDescriptor editStaffDescriptor;

    /**
     * @param index of the staff in the filtered staff list to edit
     * @param editStaffDescriptor details to edit the staff with
     */
    public EditStaffCommand(Index index, EditStaffDescriptor editStaffDescriptor) {
        requireNonNull(index);
        requireNonNull(editStaffDescriptor);

        this.index = index;
        this.editStaffDescriptor = new EditStaffDescriptor(editStaffDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Staff> lastShownList = model.getFilteredStaffList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToEdit = lastShownList.get(index.getZeroBased());
        Staff editedStaff = createEditedStaff(staffToEdit, editStaffDescriptor);

        try {
            model.setStaff(staffToEdit, editedStaff);
        } catch (DuplicateItemException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STAFF);
        }

        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFF);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_EDIT_STAFF_SUCCESS, editedStaff));
    }

    /**
     * Creates and returns a {@code Staff} with the details of {@code staffToEdit}
     * edited with {@code editStaffDescriptor}.
     */
    private static Staff createEditedStaff(Staff staffToEdit, EditStaffDescriptor editStaffDescriptor) {
        assert staffToEdit != null;

        Name updatedName = editStaffDescriptor.getName().orElse(staffToEdit.getName());
        Phone updatedPhone = editStaffDescriptor.getPhone().orElse(staffToEdit.getPhone());
        Email updatedEmail = editStaffDescriptor.getEmail().orElse(staffToEdit.getEmail());
        Appointment updatedAppointment = editStaffDescriptor.getAppointment().orElse(staffToEdit.getAppointment());

        return new Staff(updatedName, updatedPhone, updatedEmail, updatedAppointment);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStaffCommand)) {
            return false;
        }

        // state check
        EditStaffCommand e = (EditStaffCommand) other;
        return index.equals(e.index)
                && editStaffDescriptor.equals(e.editStaffDescriptor);
    }

    /**
     * Stores the details to edit the staff with. Each non-empty field value will replace the
     * corresponding field value of the staff.
     */
    public static class EditStaffDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Appointment appointment;

        public EditStaffDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditStaffDescriptor(EditStaffDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAppointment(toCopy.appointment);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, appointment);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        public Optional<Appointment> getAppointment() {
            return Optional.ofNullable(appointment);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStaffDescriptor)) {
                return false;
            }

            // state check
            EditStaffDescriptor e = (EditStaffDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAppointment().equals(e.getAppointment());
        }
    }
}
