package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STAFF;
import static seedu.address.testutil.TypicalStaff.getTypicalStaff;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysStaff;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.StaffCardHandle;
import guitests.guihandles.StaffListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Appointment;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.ShiftRoster;
import seedu.address.model.person.staff.Staff;

public class StaffListPanelTest extends GuiUnitTest {
    private static final ObservableList<Staff> TYPICAL_STAFF =
            FXCollections.observableList(getTypicalStaff());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Staff> selectedStaff = new SimpleObjectProperty<>();
    private StaffListPanelHandle staffListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_STAFF);

        for (int i = 0; i < TYPICAL_STAFF.size(); i++) {
            staffListPanelHandle.navigateToCard(TYPICAL_STAFF.get(i));
            Staff expectedStaff = TYPICAL_STAFF.get(i);
            StaffCardHandle actualCard = staffListPanelHandle.getStaffCardHandle(i);

            assertCardDisplaysStaff(expectedStaff, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedStaffChanged_selectionChanges() {
        initUi(TYPICAL_STAFF);
        Staff secondStaff = TYPICAL_STAFF.get(INDEX_SECOND_STAFF.getZeroBased());
        guiRobot.interact(() -> selectedStaff.set(secondStaff));
        guiRobot.pauseForHuman();

        StaffCardHandle expectedStaff = staffListPanelHandle.getStaffCardHandle(INDEX_SECOND_STAFF.getZeroBased());
        StaffCardHandle selectedStaff = staffListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedStaff, selectedStaff);
    }

    /**
     * Verifies that creating and deleting large number of staff in {@code StaffListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Staff> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of staff cards exceeded time limit");
    }

    /**
     * Returns a list of staff containing {@code staffCount} staff that is used to populate the
     * {@code StaffListPanel}.
     */
    private ObservableList<Staff> createBackingList(int staffCount) {
        ObservableList<Staff> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < staffCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            Appointment appointment = new Appointment("aaaa");
            ShiftRoster shiftRoster = new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"));
            Staff staff = new Staff(name, phone, email, appointment, shiftRoster);
            backingList.add(staff);
        }
        return backingList;
    }

    /**
     * Initializes {@code staffListPanelHandle} with a {@code StaffListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code StaffListPanel}.
     */
    private void initUi(ObservableList<Staff> backingList) {
        StaffListPanel staffListPanel =
                new StaffListPanel(backingList, selectedStaff, selectedStaff::set);
        uiPartRule.setUiPart(staffListPanel);

        staffListPanelHandle = new StaffListPanelHandle(getChildNode(staffListPanel.getRoot(),
                StaffListPanelHandle.STAFF_LIST_VIEW_ID));
    }
}
