package seedu.address.model.person.member;

/**
 * Represents a Member's points in the restaurant book.
 *
 */
public class LoyaltyPoints {

    public static final String MESSAGE_CONSTRAINTS =
            "Loyalty points should be a non-negative integer";

    /*
     * The first character of the appointment name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[0-9]*$";

    public final int value;

    /**
     * Constructs a {@code Appointment}.
     *
     * @param points The amount of loyalty points.
     */
    public LoyaltyPoints(int points) {
        this.value = points;
    }

    /**
     * Returns true if a given int is a valid loyalty points.
     */
    public static boolean isValidLoyaltyPoints(int points) {
        return 0 <= points; //TODO might want to set upper bound
    }

    /**
     * Returns true if a given string is a valid loyalty points.
     */
    public static boolean isValidLoyaltyPoints(String points) {
        int integerPoints;
        try {
            integerPoints = Integer.parseInt(points);
        } catch (NumberFormatException e) {
            return false;
        }
        return integerPoints >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoyaltyPoints // instanceof handles nulls
                && value == ((LoyaltyPoints) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }

}
