package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import seedu.address.model.booking.Booking;

/**
 * Manages the statistics of the bookings
 */
public class Statistics {

    public static final String DATE_PATTERN = "dd MMM yyyy";
    public static final String TIME_PATTERN = "HH:mm";
    public static final int MAX_BARS = 20;
    public static final int MAX_BAR_SIZE = 500;
    public static final String MESSAGE_CONSTRAINTS = "Days should be an integer between 1 and "
            + getMaxDays() + " inclusive.";

    private static final int HOURS_IN_A_DAY = 24;

    private final ObservableList<Booking> bookings;
    private final int days;

    public Statistics(ObservableList<Booking> bookings, int days) {
        checkArgument(1 <= days && days <= getMaxDays() , MESSAGE_CONSTRAINTS);
        requireNonNull(bookings);
        this.bookings = bookings;
        this.days = days;
    }

    public static int getMaxDays() {
        return MAX_BARS * MAX_BAR_SIZE;
    }

    private int getDaysDifference(LocalDate start, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Formats the date into a String.
     * @param date the date
     * @return the formatted String
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }

    /**
     * Formats 2 dates into a String.
     * @param start the starting date. Should be before end.
     * @param end the ending date. Should be after start.
     * @return the formatted String
     */
    private String formatDate(LocalDate start, LocalDate end) {
        if (start.isEqual(end)) {
            return formatDate(start);
        }
        return formatDate(start) + " - " + formatDate(end);
    }

    private String formatTime(int hour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return LocalTime.of(hour, 0).format(formatter)
                + " - " + LocalTime.of(hour, 59).format(formatter);
    }

    /**
     * Counts the number of bookings that satisfies the given condition
     * @param condition condition for the bookings to satisfy
     * @return the number of bookings that satisfies the given condition
     */
    private int getNumBookingsWithCondition(Predicate<Booking> condition) {
        return (int) bookings.stream().filter(condition).mapToInt(booking -> booking.getNumMembers().getSize()).sum();
    }

    /**
     * Generates the data for the bar graph containing the number of bookings in the last `days` days.
     * @return
     */
    public List<Data<String, Integer>> generateGraphDataDays() {
        int bucketSize = (days + MAX_BARS - 1) / MAX_BARS; // ceiling of days / MAX_BARS
        int numBuckets = (days + bucketSize - 1) / bucketSize; // ceiling of days / bucketSize
        assert(bucketSize <= MAX_BAR_SIZE);
        assert(numBuckets <= MAX_BARS);
        assert(bucketSize >= 0);
        assert(numBuckets >= 0);
        List<Integer> numBookings = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < numBuckets; i++) {
            int startIndex = i * bucketSize;
            int endIndex = startIndex + (bucketSize - 1);
            numBookings.add(getNumBookingsWithCondition(booking -> {
                LocalDate bookingDate = booking.getStartTime().toLocalDate();
                int difference = getDaysDifference(bookingDate, today);
                return startIndex <= difference && difference <= endIndex;
            }));
        }
        List<Data<String, Integer>> graphData = new ArrayList<>();
        for (int i = numBuckets - 1; i >= 0; i--) { // add the earliest date first
            int startIndex = i * bucketSize;
            int endIndex = startIndex + (bucketSize - 1);
            String name = formatDate(today.minusDays(endIndex), today.minusDays(startIndex));
            graphData.add(new Data<>(name, numBookings.get(i)));
        }
        return graphData;
    }

    /**
     * Generates the data for the bar graph containing the number of bookings per time in the last `days` days.
     * @return
     */
    public List<Data<String, Integer>> generateGraphDataTime() {
        LocalDate today = LocalDate.now();
        List<Data<String, Integer>> graphData = new ArrayList<>();
        for (int hour = 0; hour < HOURS_IN_A_DAY; hour++) {
            final int h = hour;
            int value = getNumBookingsWithCondition(booking ->
                h == booking.getStartTime().getHour()
                    && getDaysDifference(booking.getStartTime().toLocalDate(), today) < days
            );
            graphData.add(new Data<>(formatTime(hour), value));
        }
        return graphData;
    }
}
