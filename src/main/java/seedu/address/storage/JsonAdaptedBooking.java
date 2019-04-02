package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.member.LoyaltyPoints;
import seedu.address.model.person.member.Member;

/**
 * Jackson-friendly version of {@link Booking}.
 */

public class JsonAdaptedBooking {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final String customerName;
    private final String customerPhone;
    private final String customerEmail;
    private final int customerLoyaltyPoints;
    private final String startTime;
    private final int numPersons;

    /**
     * Constructs a {@code JsonAdaptedBooking} with the given booking details.
     */
    @JsonCreator
    public JsonAdaptedBooking (@JsonProperty("customerName") String customerName,
                               @JsonProperty("customerPhone") String customerPhone,
                               @JsonProperty("customerEmail") String customerEmail,
                               @JsonProperty("customerLoyaltyPoints") int customerLoyaltyPoints,
                               @JsonProperty("startTime") String startTime,
                               @JsonProperty("numPersons") int numPersons) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerLoyaltyPoints = customerLoyaltyPoints;
        this.startTime = startTime;
        this.numPersons = numPersons;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedBooking(Booking source) {
        customerName = source.getCustomer().getName().fullName;
        customerPhone = source.getCustomer().getPhone().value;
        customerEmail = source.getCustomer().getEmail().value;

        customerLoyaltyPoints = source.getCustomer().getLoyaltyPoints().value;
        startTime = source.getStartTimeString();
        this.numPersons = source.getNumMembers().getSize();
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code Booking} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted booking.
     */
    public Booking toModelType() throws IllegalValueException {
        final Name modelName = parseName();
        final Phone modelPhone = parsePhone();
        final Email modelEmail = parseEmail();
        final LoyaltyPoints loyaltyPoints = parseLoyaltyPoints();

        final Member modelCustomer = new Member(modelName, modelPhone, modelEmail, loyaltyPoints);

        final BookingWindow modelBookingWindow;
        final BookingSize modelBookingSize;

        try {
            modelBookingWindow = ParserUtil.parseBookingWindow(startTime);
            modelBookingSize = ParserUtil.parseBookingSize(Integer.toString(numPersons));
        } catch (ParseException e) {
            throw new IllegalValueException(e.getMessage());
        }

        return new Booking(modelBookingWindow, modelCustomer, modelBookingSize);
    }

    /**
     * Parses the name and converts into a Name object.
     */
    private Name parseName() throws IllegalValueException {
        if (customerName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(customerName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(customerName);
    }


    /**
     * Parses the phone and converts into a Phone object.
     */
    private Phone parsePhone() throws IllegalValueException {
        if (customerPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(customerPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(customerPhone);
    }

    /**
     * Parses the email and converts into a Email object.
     */
    private Email parseEmail() throws IllegalValueException {
        if (customerEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(customerEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(customerEmail);
    }

    /**
     * Parses the loyalty points and converts into a LoyaltyPoints object.
     */
    private LoyaltyPoints parseLoyaltyPoints() throws IllegalValueException {
        if (!LoyaltyPoints.isValidLoyaltyPoints(customerLoyaltyPoints)) {
            throw new IllegalValueException(LoyaltyPoints.MESSAGE_CONSTRAINTS);
        }
        return new LoyaltyPoints(customerLoyaltyPoints);
    }
}

