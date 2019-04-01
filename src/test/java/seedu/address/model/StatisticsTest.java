package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalMembers;

public class StatisticsTest {

    private final ObservableList<Booking> emptyBookings = FXCollections.observableArrayList();
    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();

    @Before
    public void setUp() {
        LocalDateTime now10 = LocalDateTime.now().withHour(10).withMinute(0).withSecond(0);
        LocalDateTime now12 = LocalDateTime.now().withHour(12).withMinute(0).withSecond(0);
        bookings.add(new Booking(new BookingWindow(now10), TypicalMembers.ALICE, new BookingSize(5)));
        bookings.add(new Booking(new BookingWindow(now12.minusDays(1)), TypicalMembers.ALICE,
                new BookingSize(6)));
    }

    @After
    public void tearDown() {
        bookings.clear();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Statistics(null, 30));
    }

    @Test
    public void constructor_invalidDays_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Statistics(emptyBookings, 0));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Statistics(emptyBookings,
                Statistics.getMaxDays() + 1));
    }

    @Test
    public void generateGraphDataDays_valid_returnsList() {
        List<XYChart.Data<String, Integer>> datas = new Statistics(emptyBookings, 10).generateGraphDataDays();
        assertTrue(datas.size() == 10);
        for (XYChart.Data<String, Integer> data : datas) {
            assertEquals(data.getYValue(), Integer.valueOf(0));
        }

        datas = new Statistics(bookings, 2).generateGraphDataDays();
        assertTrue(datas.size() == 2);
        assertEquals(datas.get(0).getYValue(), Integer.valueOf(6));
        assertEquals(datas.get(1).getYValue(), Integer.valueOf(5));

        datas = new Statistics(bookings, Statistics.MAX_BARS * 2).generateGraphDataDays();
        assertTrue(datas.size() == Statistics.MAX_BARS);
        for (int i = 0; i < datas.size(); i++) {
            if (i < datas.size() - 1) {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(0));
            } else {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(11));
            }
        }
    }

    @Test
    public void generateGraphDataTime_valid_returnsList() {
        List<XYChart.Data<String, Integer>> datas = new Statistics(emptyBookings, 10).generateGraphDataTime();
        assertTrue(datas.size() == 24);
        for (XYChart.Data<String, Integer> data : datas) {
            assertEquals(data.getYValue(), Integer.valueOf(0));
        }

        datas = new Statistics(bookings, 2).generateGraphDataTime();
        assertTrue(datas.size() == 24);
        for (int i = 0; i < 24; i++) {
            if (i == 10) {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(5));
            } else if (i == 12) {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(6));
            } else {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(0));
            }
        }

        datas = new Statistics(bookings, 1).generateGraphDataTime();
        assertTrue(datas.size() == 24);
        for (int i = 0; i < 24; i++) {
            if (i == 10) {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(5));
            } else {
                assertEquals(datas.get(i).getYValue(), Integer.valueOf(0));
            }
        }
    }
}
