package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.booking.BookingSize.MAX_BOOKING_SIZE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;
import seedu.address.model.person.Email;
import seedu.address.model.person.LoyaltyPoints;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_LOYALTY_POINTS = "-123";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_LOYALTY_POINTS = "1234";
    private static final int VALID_LOYALTY_POINTS_INT = 1234;

    private static final String INVALID_INGREDIENT_NAME_INTEGER = "10";
    private static final String INVALID_INGREDIENT_NAME_SYMBOLS = "cheese@4";
    private static final String INVALID_INGREDIENT_UNIT_INTEGER = "1";
    private static final String INVALID_INGREDIENT_UNIT_SYMBOLS = "sac3`k";
    private static final String INVALID_INGREDIENT_QUANTITY_NEGATIVE = "-1";
    private static final String INVALID_INGREDIENT_QUANTITY_PLUS_SIGN = "+1";
    private static final String INVALID_INGREDIENT_WARNINGAMT_NEGATIVE = "-1";
    private static final String INVALID_INGREDIENT_WARNINGAMT_PLUS_SIGN = "+1";

    private static final String VALID_INGREDIENT_NAME = "cheese";
    private static final String VALID_INGREDIENT_QUANTITY_POSITIVEINT = "1";
    private static final String VALID_INGREDIENT_QUANTITY_ZERO = "0";
    private static final String VALID_INGREDIENT_UNIT = "sacks";
    private static final String VALID_INGREDIENT_WARNINGAMT_POSITIVEINT = "1";
    private static final String VALID_INGREDIENT_WARNINGAMT_ZERO = "0";

    private static final String INVALID_FEB_29 = "2019-02-29T12:00";
    private static final String WRONG_DATE_FORMAT = "2019-02-28T1200";
    private static final String VALID_FEB_29 = "2020-02-29T12:00";
    private static final String TIME_ONLY_NO_DATE = "12:00";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString((long) Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseLoyaltyPoints_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseLoyaltyPoints(INVALID_LOYALTY_POINTS));
    }

    @Test
    public void parseLoyaltyPoints_validValueWithoutWhitespace_returnsLoyaltyPoints() throws Exception {
        LoyaltyPoints expectedLoyaltyPoints = new LoyaltyPoints(VALID_LOYALTY_POINTS_INT);
        assertEquals(expectedLoyaltyPoints, ParserUtil.parseLoyaltyPoints(VALID_LOYALTY_POINTS));
    }

    @Test
    public void parseLoyaltyPoints_validValueWithWhitespace_returnsLoyaltyPoints() throws Exception {
        String loyaltyPointsWithWhitespace = WHITESPACE + VALID_LOYALTY_POINTS + WHITESPACE;
        LoyaltyPoints expectedLoyaltyPoints = new LoyaltyPoints(VALID_LOYALTY_POINTS_INT);
        assertEquals(expectedLoyaltyPoints, ParserUtil.parseLoyaltyPoints(loyaltyPointsWithWhitespace));
    }

    @Test
    public void parseIngredientName_invalidNonAlphabetValue_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientName(INVALID_INGREDIENT_NAME_INTEGER);
    }

    @Test
    public void parseIngredientName_invalidSymbolsValue_throwsExcetpion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientName(INVALID_INGREDIENT_NAME_SYMBOLS);
    }

    @Test
    public void parseIngredientName_null_throwsException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredientName((String) null));
    }


    @Test
    public void parseIngredientName_validValue_returnsIngredientName() throws Exception {
        String ingredientNameWithWhiteSpace = WHITESPACE + VALID_INGREDIENT_NAME + WHITESPACE;
        IngredientName expectedIngredientName = new IngredientName(VALID_INGREDIENT_NAME);

        //with white space
        assertEquals(expectedIngredientName, ParserUtil.parseIngredientName((ingredientNameWithWhiteSpace)));

        //without white space
        assertEquals(expectedIngredientName, ParserUtil.parseIngredientName((VALID_INGREDIENT_NAME)));
    }

    @Test
    public void parseIngredientQuantity_invalidNegativeValue_throwsExcepion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientQuantity(INVALID_INGREDIENT_QUANTITY_NEGATIVE);
    }

    @Test
    public void parseIngredientQuantity_invalidPlusValue_throwsExcepion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientQuantity(INVALID_INGREDIENT_QUANTITY_PLUS_SIGN);
    }


    @Test
    public void parseIngredientQuantity_invalidOutOfRange_throwsExcepion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientQuantity(Long.toString((long) Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIngredientQuantity_null_throwsException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredientQuantity((String) null));
    }
    @Test
    public void parseIngredientQuantity_validValue_returnsIngredientQuantity() throws Exception {
        String posIngredientQuantityWithWhiteSpace = WHITESPACE + VALID_INGREDIENT_QUANTITY_POSITIVEINT + WHITESPACE;
        IngredientQuantity posExpectedIngredientQuantity =
                new IngredientQuantity(Integer.parseInt(VALID_INGREDIENT_QUANTITY_POSITIVEINT));

        //positive integer with white space
        assertEquals(posExpectedIngredientQuantity,
                ParserUtil.parseIngredientQuantity((posIngredientQuantityWithWhiteSpace)));

        //positive integer without white space
        assertEquals(posExpectedIngredientQuantity,
                ParserUtil.parseIngredientQuantity(VALID_INGREDIENT_QUANTITY_POSITIVEINT));

        String zeroIngredientQuantityWithWhiteSpace = WHITESPACE + VALID_INGREDIENT_QUANTITY_ZERO + WHITESPACE;
        IngredientQuantity zeroExpectedIngredientQuantity =
                new IngredientQuantity(Integer.parseInt(VALID_INGREDIENT_QUANTITY_ZERO));

        //zero with white space
        assertEquals(zeroExpectedIngredientQuantity,
                ParserUtil.parseIngredientQuantity((zeroIngredientQuantityWithWhiteSpace)));

        //zero without white space
        assertEquals(zeroExpectedIngredientQuantity,
                ParserUtil.parseIngredientQuantity(VALID_INGREDIENT_QUANTITY_ZERO));
    }

    @Test
    public void parseIngredientUnit_invalidNonAlphabetValue_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientUnit(INVALID_INGREDIENT_UNIT_INTEGER);
    }

    @Test
    public void parseIngredientUnit_invalidSymbolsValue_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientUnit(INVALID_INGREDIENT_UNIT_SYMBOLS);
    }

    @Test
    public void parseIngredientUnit_null_throwsException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredientUnit((String) null));
    }


    @Test
    public void parseIngredientUnit_validValue_returnsIngredientUnit() throws Exception {
        String ingredientUnitWithWhiteSpace = WHITESPACE + VALID_INGREDIENT_UNIT + WHITESPACE;
        IngredientUnit expectedIngredientUnit = new IngredientUnit(VALID_INGREDIENT_UNIT);

        //with white space
        assertEquals(expectedIngredientUnit, ParserUtil.parseIngredientUnit((ingredientUnitWithWhiteSpace)));

        //without white space
        assertEquals(expectedIngredientUnit, ParserUtil.parseIngredientUnit((VALID_INGREDIENT_UNIT)));
    }


    @Test
    public void parseIngredientWarningAmount_invalidNegativeValue_throwsExcepion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientWarningAmount(INVALID_INGREDIENT_WARNINGAMT_NEGATIVE);
    }

    @Test
    public void parseIngredientWarningAmount_invalidPlusValue_throwsExcepion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientQuantity(INVALID_INGREDIENT_WARNINGAMT_PLUS_SIGN);
    }

    @Test
    public void parseIngredientWarningAmount_invalidOutOfRange_throwsExcepion() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIngredientWarningAmount(Long.toString((long) Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIngredientWarningAmount_validValue_returnsIngredientWarning() throws Exception {
        String posIngredientWarningAmtWithWhiteSpace =
                WHITESPACE + VALID_INGREDIENT_WARNINGAMT_POSITIVEINT + WHITESPACE;
        IngredientWarningAmount posExpectedIngredientWarningAmount =
                new IngredientWarningAmount(Integer.parseInt(VALID_INGREDIENT_WARNINGAMT_POSITIVEINT));

        //positive integer with white space
        assertEquals(posExpectedIngredientWarningAmount,
                ParserUtil.parseIngredientWarningAmount((posIngredientWarningAmtWithWhiteSpace)));

        //positive integer without white space
        assertEquals(posExpectedIngredientWarningAmount,
                ParserUtil.parseIngredientWarningAmount(VALID_INGREDIENT_WARNINGAMT_POSITIVEINT));

        String zeroIngredientWarningAmtWithWhiteSpace = WHITESPACE + VALID_INGREDIENT_WARNINGAMT_ZERO + WHITESPACE;
        IngredientWarningAmount zeroExpectedIngredientWarningAmt =
                new IngredientWarningAmount(Integer.parseInt(VALID_INGREDIENT_WARNINGAMT_ZERO));

        //zero with white space
        assertEquals(zeroExpectedIngredientWarningAmt,
                ParserUtil.parseIngredientWarningAmount((zeroIngredientWarningAmtWithWhiteSpace)));

        //zero without white space
        assertEquals(zeroExpectedIngredientWarningAmt,
                ParserUtil.parseIngredientWarningAmount(VALID_INGREDIENT_WARNINGAMT_ZERO));
    }

    @Test
    public void parseBookingWindow_invalidDate_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingWindow(INVALID_FEB_29));
    }

    @Test
    public void parseBookingWindow_wrongDateFormat_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingWindow(WRONG_DATE_FORMAT));
    }

    @Test
    public void parseBookingWindow_validDate_returnsBookingWindow() throws Exception {
        BookingWindow expectedBookingWindow = new BookingWindow(LocalDateTime.of(2020, Month.FEBRUARY, 29, 12, 0));
        assertEquals(expectedBookingWindow, ParserUtil.parseBookingWindow(VALID_FEB_29));
    }

    @Test
    public void parseBookingWindow_timeNoDate_returnsBookingWindow() throws Exception {
        BookingWindow expectedBookingWindow =
                new BookingWindow(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
        assertEquals(expectedBookingWindow, ParserUtil.parseBookingWindow(TIME_ONLY_NO_DATE));
    }

    @Test
    public void parseBookingSize_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingSize("0"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingSize("-1"));
        Assert.assertThrows(
                ParseException.class, () -> ParserUtil.parseBookingSize(Integer.toString(MAX_BOOKING_SIZE + 1)));
    }

    @Test
    public void parseBookingSize_validBookingSize_returnsBookingSize() throws Exception {
        // test boundary values
        BookingSize expectedBookingSize = new BookingSize(1);
        assertEquals(expectedBookingSize, ParserUtil.parseBookingSize("1"));

        expectedBookingSize = new BookingSize(MAX_BOOKING_SIZE);
        assertEquals(expectedBookingSize, ParserUtil.parseBookingSize(Integer.toString(MAX_BOOKING_SIZE)));
    }

}
