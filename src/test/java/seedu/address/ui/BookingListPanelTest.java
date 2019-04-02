package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalBookings.getTypicalBookings;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBooking;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.BookingCardHandle;
import guitests.guihandles.BookingListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.member.LoyaltyPoints;
import seedu.address.model.person.member.Member;
import seedu.address.testutil.TypicalBookings;

public class BookingListPanelTest extends GuiUnitTest {
    private static final ObservableList<Booking> TYPICAL_BOOKINGS =
            FXCollections.observableList(getTypicalBookings());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();
    private BookingListPanelHandle bookingListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_BOOKINGS);

        for (int i = 0; i < TYPICAL_BOOKINGS.size(); i++) {
            bookingListPanelHandle.navigateToCard(TYPICAL_BOOKINGS.get(i));
            Booking expectedBooking = TYPICAL_BOOKINGS.get(i);
            BookingCardHandle actualCard = bookingListPanelHandle.getBookingCardHandle(i);

            assertCardDisplaysBooking(expectedBooking, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedMemberChanged_selectionChanges() {
        initUi(TYPICAL_BOOKINGS);
        Booking secondBooking = TYPICAL_BOOKINGS.get(INDEX_SECOND.getZeroBased());
        guiRobot.interact(() -> selectedBooking.set(secondBooking));
        guiRobot.pauseForHuman();

        BookingCardHandle expectedBooking = bookingListPanelHandle.getBookingCardHandle(INDEX_SECOND.getZeroBased());
        BookingCardHandle selectedBooking = bookingListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedBooking, selectedBooking);
    }

    /**
     * Verifies that creating and deleting large number of bookings in {@code BookingListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Booking> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of booking cards exceeded time limit");
    }

    /**
     * Returns a list of bookings containing {@code bookingCount} bookings that is used to populate the
     * {@code BookingListPanel}.
     */
    private ObservableList<Booking> createBackingList(int bookingCount) {
        ObservableList<Booking> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < bookingCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            LoyaltyPoints loyaltyPoints = new LoyaltyPoints(1);
            Member member = new Member(name, phone, email, loyaltyPoints);
            Booking booking = new Booking(new BookingWindow(TypicalBookings.START_TIME), member, new BookingSize(5));
            backingList.add(booking);
        }
        return backingList;
    }

    /**
     * Initializes {@code bookingListPanelHandle} with a {@code BookingListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code BookingListPanel}.
     */
    private void initUi(ObservableList<Booking> backingList) {
        BookingListPanel bookingListPanel =
                new BookingListPanel(backingList, selectedBooking, selectedBooking::set);
        uiPartRule.setUiPart(bookingListPanel);

        bookingListPanelHandle = new BookingListPanelHandle(getChildNode(bookingListPanel.getRoot(),
                BookingListPanelHandle.BOOKING_LIST_VIEW_ID));
    }
}
