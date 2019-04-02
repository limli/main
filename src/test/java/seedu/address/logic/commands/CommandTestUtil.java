package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_WARNINGAMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOYALTY_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.RestaurantBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.member.Member;
import seedu.address.model.person.staff.Staff;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.EditStaffDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //General
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_INDEX_DESC = " " + PREFIX_INDEX + "1";

    public static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX + "-1"; //negative not allowed

    //Related to persons
    public static final String PERSON_VALID_NAME_AMY = "Amy Bee";
    public static final String PERSON_VALID_NAME_BOB = "Bob Choo";
    public static final String PERSON_VALID_PHONE_AMY = "11111111";
    public static final String PERSON_VALID_PHONE_BOB = "22222222";
    public static final String PERSON_VALID_EMAIL_AMY = "amy@example.com";
    public static final String PERSON_VALID_EMAIL_BOB = "bob@example.com";

    public static final String PERSON_NAME_DESC_AMY = " " + PREFIX_NAME + PERSON_VALID_NAME_AMY;
    public static final String PERSON_NAME_DESC_BOB = " " + PREFIX_NAME + PERSON_VALID_NAME_BOB;
    public static final String PERSON_PHONE_DESC_AMY = " " + PREFIX_PHONE + PERSON_VALID_PHONE_AMY;
    public static final String PERSON_PHONE_DESC_BOB = " " + PREFIX_PHONE + PERSON_VALID_PHONE_BOB;
    public static final String PERSON_EMAIL_DESC_AMY = " " + PREFIX_EMAIL + PERSON_VALID_EMAIL_AMY;
    public static final String PERSON_EMAIL_DESC_BOB = " " + PREFIX_EMAIL + PERSON_VALID_EMAIL_BOB;

    public static final String PERSON_INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String PERSON_INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String PERSON_INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String STAFF_INVALID_APPOINTMENT_DESC = " " + PREFIX_APPOINTMENT
            + "amaz!ngC00k"; // ! not allowed
    public static final String MEMBER_INVALID_LOYALTY_POINTS_DESC = " " + PREFIX_LOYALTY_POINTS
            + "26.0"; // . not allowed

    //Related to member
    public static final int MEMBER_VALID_LOYALTY_POINTS_AMY = 11;
    public static final int MEMBER_VALID_LOYALTY_POINTS_BOB = 22;
    public static final String MEMBER_LOYALTY_POINTS_DESC_AMY = " " + PREFIX_LOYALTY_POINTS
            + MEMBER_VALID_LOYALTY_POINTS_AMY;
    public static final String MEMBER_LOYALTY_POINTS_DESC_BOB = " " + PREFIX_LOYALTY_POINTS
            + MEMBER_VALID_LOYALTY_POINTS_BOB;

    //Related to staff
    public static final String STAFF_VALID_APPOINTMENT_AMY = "Server";
    public static final String STAFF_VALID_APPOINTMENT_BOB = "Cook";
    public static final String STAFF_APPOINTMENT_DESC_AMY = " " + PREFIX_APPOINTMENT + STAFF_VALID_APPOINTMENT_AMY;
    public static final String STAFF_APPOINTMENT_DESC_BOB = " " + PREFIX_APPOINTMENT + STAFF_VALID_APPOINTMENT_BOB;

    //Related to ingredients
    public static final String INGREDIENT_VALID_NAME_CHEESE = "cheese";
    public static final String INGREDIENT_VALID_NAME_TOMATO = "tomato";
    public static final String INGREDIENT_VALID_QUANTITY_CHEESE = "4";
    public static final String INGREDIENT_VALID_QUANTITY_TOMATO = "5";
    public static final String INGREDIENT_VALID_UNIT_CHEESE = "pounds";
    public static final String INGREDIENT_VALID_UNIT_TOMATO = "pieces";
    public static final String INGREDIENT_VALID_WARNINGAMT_CHEESE = "3";
    public static final String INGREDIENT_VALID_WARNINGAMT_TOMATO = "2";


    public static final String INGREDIENT_NAME_DESC_CHEESE =
            " " + PREFIX_INGREDIENT_NAME + INGREDIENT_VALID_NAME_CHEESE;
    public static final String INGREDIENT_QUANTITY_DESC_CHEESE =
            " " + PREFIX_INGREDIENT_QUANTITY + INGREDIENT_VALID_QUANTITY_CHEESE;
    public static final String INGREDIENT_UNIT_DESC_CHEESE =
            " " + PREFIX_INGREDIENT_UNIT + INGREDIENT_VALID_UNIT_CHEESE;
    public static final String INGREDIENT_WARNINGAMT_DESC_CHEESE =
            " " + PREFIX_INGREDIENT_WARNINGAMOUNT + INGREDIENT_VALID_WARNINGAMT_CHEESE;
    public static final String INGREDIENT_NAME_DESC_TOMATO =
            " " + PREFIX_INGREDIENT_NAME + INGREDIENT_VALID_NAME_TOMATO;
    public static final String INGREDIENT_QUANTITY_DESC_TOMATO =
            " " + PREFIX_INGREDIENT_QUANTITY + INGREDIENT_VALID_QUANTITY_TOMATO;
    public static final String INGREDIENT_UNIT_DESC_TOMATO =
            " " + PREFIX_INGREDIENT_UNIT + INGREDIENT_VALID_UNIT_TOMATO;
    public static final String INGREDIENT_WARNINGAMT_DESC_TOMATO =
            " " + PREFIX_INGREDIENT_WARNINGAMOUNT + INGREDIENT_VALID_WARNINGAMT_TOMATO;


    public static final String INGREDIENT_INVALID_NAME_DESC = " " + PREFIX_INGREDIENT_NAME
            + "1"; // integers not allowed in ingredientName
    public static final String INGREDIENT_INVALID_QUANTITY_NONINTEGER_DESC = " " + PREFIX_INGREDIENT_QUANTITY
            + "potato"; // non-integers not allowed in IngredientQuantity
    public static final String INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC = " " + PREFIX_INGREDIENT_QUANTITY
            + "-1"; // negative values not allowed in IngredientQuantity
    public static final String INGREDIENT_INVALID_UNIT_DESC = " " + PREFIX_INGREDIENT_UNIT
            + "3@"; // symbols not allowed
    public static final String INGREDIENT_INVALID_WARNINGAMOUNT_DESC =
            " " + PREFIX_INGREDIENT_WARNINGAMOUNT + "3.0"; // decimals not allowed

    public static final EditMemberCommand.EditMemberDescriptor MEMBER_DESC_AMY;
    public static final EditMemberCommand.EditMemberDescriptor MEMBER_DESC_BOB;

    public static final EditStaffCommand.EditStaffDescriptor STAFF_DESC_AMY;
    public static final EditStaffCommand.EditStaffDescriptor STAFF_DESC_BOB;

    static {
        MEMBER_DESC_AMY = new EditMemberDescriptorBuilder().withName(PERSON_VALID_NAME_AMY)
                .withPhone(PERSON_VALID_PHONE_AMY).withEmail(PERSON_VALID_EMAIL_AMY)
                .withLoyaltyPoints(MEMBER_VALID_LOYALTY_POINTS_AMY).build();
        MEMBER_DESC_BOB = new EditMemberDescriptorBuilder().withName(PERSON_VALID_NAME_BOB)
                .withPhone(PERSON_VALID_PHONE_BOB).withEmail(PERSON_VALID_EMAIL_BOB)
                .withLoyaltyPoints(MEMBER_VALID_LOYALTY_POINTS_BOB).build();
    }

    static {
        STAFF_DESC_AMY = new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_AMY)
                .withPhone(PERSON_VALID_PHONE_AMY).withEmail(PERSON_VALID_EMAIL_AMY)
                .withAppointment(STAFF_VALID_APPOINTMENT_AMY).build();
        STAFF_DESC_BOB = new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_BOB)
                .withPhone(PERSON_VALID_PHONE_BOB).withEmail(PERSON_VALID_EMAIL_BOB)
                .withAppointment(STAFF_VALID_APPOINTMENT_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered member list and selected member in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RestaurantBook expectedRestaurantBook = new RestaurantBook(actualModel.getRestaurantBook());
        List<Member> expectedFilteredList = new ArrayList<>(actualModel.getFilteredMemberList());
        Member expectedSelectedMember = actualModel.getSelectedMember();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedRestaurantBook, actualModel.getRestaurantBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredMemberList());
            assertEquals(expectedSelectedMember, actualModel.getSelectedMember());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the member at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showMemberAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemberList().size());

        Member member = model.getFilteredMemberList().get(targetIndex.getZeroBased());
        final String[] splitName = member.getName().fullName.split("\\s+");
        model.updateFilteredMemberList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMemberList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the booking at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showBookingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemberList().size());

        Booking booking = model.getFilteredBookingList().get(targetIndex.getZeroBased());
        model.updateFilteredBookingList(b -> b.equals(booking));

        assertEquals(1, model.getFilteredBookingList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the ingredient at the given {@code targetIndex} in the
     * {@code model}'s restaurant book.
     */
    public static void showIngredientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIngredientList().size());

        Ingredient ingredient = model.getFilteredIngredientList().get(targetIndex.getZeroBased());
        String ingredientName = ingredient.getIngredientName().getName();
        model.updateFilteredIngredientList(p ->
                p.getIngredientName().getName().equalsIgnoreCase(ingredientName));


        assertEquals(1, model.getFilteredIngredientList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the staff at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStaffAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStaffList().size());

        Staff staff = model.getFilteredStaffList().get(targetIndex.getZeroBased());
        final String name = staff.getName().fullName;
        model.updateFilteredStaffList(p -> p.getName().fullName.equalsIgnoreCase(name));

        assertEquals(1, model.getFilteredStaffList().size());
    }

    /**
     * Deletes the first member in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstMember(Model model) {
        Member firstMember = model.getFilteredMemberList().get(0);
        model.deleteMember(firstMember);
        model.commitRestaurantBook();
    }

}
