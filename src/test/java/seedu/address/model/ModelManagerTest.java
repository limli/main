package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BENSON;
import static seedu.address.testutil.TypicalMembers.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Member;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.ItemNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.MemberBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new RestaurantBook(), new RestaurantBook(modelManager.getRestaurantBook()));
        assertEquals(null, modelManager.getSelectedMember());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setRestaurantBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setRestaurantBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setRestaurantBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setRestaurantBookFilePath(path);
        assertEquals(path, modelManager.getRestaurantBookFilePath());
    }

    @Test
    public void hasMember_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasMember(null);
    }

    @Test
    public void hasMember_memberNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasMember(ALICE));
    }

    @Test
    public void hasMember_memberInAddressBook_returnsTrue() {
        modelManager.addMember(ALICE);
        assertTrue(modelManager.hasMember(ALICE));
    }

    @Test
    public void deleteMember_memberIsSelectedAndFirstMemberInFilteredMemberList_selectionCleared() {
        modelManager.addMember(ALICE);
        modelManager.setSelectedMember(ALICE);
        modelManager.deleteMember(ALICE);
        assertEquals(null, modelManager.getSelectedMember());
    }

    @Test
    public void deleteMember_memberIsSelectedAndSecondMemberInFilteredMemberList_firstMemberSelected() {
        modelManager.addMember(ALICE);
        modelManager.addMember(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredMemberList());
        modelManager.setSelectedMember(BOB);
        modelManager.deleteMember(BOB);
        assertEquals(ALICE, modelManager.getSelectedMember());
    }

    @Test
    public void setMember_memberIsSelected_selectedMemberUpdated() {
        modelManager.addMember(ALICE);
        modelManager.setSelectedMember(ALICE);
        Member updatedAlice = new MemberBuilder(ALICE).withEmail(PERSON_VALID_EMAIL_BOB).build();
        modelManager.setMember(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedMember());
    }

    @Test
    public void getFilteredMemberList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredMemberList().remove(0);
    }

    @Test
    public void setSelectedMember_memberNotInFilteredMemberList_throwsMemberNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        modelManager.setSelectedMember(ALICE);
    }

    @Test
    public void setSelectedMember_memberInFilteredMemberList_setsSelectedMember() {
        modelManager.addMember(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredMemberList());
        modelManager.setSelectedMember(ALICE);
        assertEquals(ALICE, modelManager.getSelectedMember());
    }

    @Test
    public void equals() {
        RestaurantBook restaurantBook = new AddressBookBuilder().withMember(ALICE).withMember(BENSON).build();
        RestaurantBook differentRestaurantBook = new RestaurantBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(restaurantBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(restaurantBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different restaurantBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRestaurantBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredMemberList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(restaurantBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRestaurantBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(restaurantBook, differentUserPrefs)));
    }
}
