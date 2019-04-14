package systemtests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.MemberListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ingredient.ListIngredientsCommand;
import seedu.address.logic.commands.member.ListMembersCommand;
import seedu.address.model.Model;
import seedu.address.model.RestaurantBook;
import seedu.address.testutil.TypicalMembers;
import seedu.address.ui.CommandBox;

/**
 * A system test class for RestaurantBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class RestaurantBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected RestaurantBook getInitialData() {
        return TypicalMembers.getTypicalAddressBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public MemberListPanelHandle getMemberListPanel() {
        return mainWindowHandle.getMemberListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Displays all members in the address book.
     */
    protected void showAllMembers() {
        executeCommand(ListMembersCommand.COMMAND_WORD);
        assertEquals(getModel().getRestaurantBook().getMemberList().size(),
                getModel().getFilteredMemberList().size());
    }

    /**
     * Displays all members with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showMembersWithName(String keyword) {
        executeCommand(ListMembersCommand.COMMAND_WORD + " " + PREFIX_NAME + keyword);
        assertTrue(getModel().getFilteredMemberList().size()
                < getModel().getRestaurantBook().getMemberList().size());
    }


    /**
     * Deletes all members in the address book.
     */
    protected void deleteAllMembers() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getRestaurantBook().getMemberList().size());
    }

    /**
     * Displays all ingredients with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showIngredientsWithName(String keyword) {
        executeCommand(ListIngredientsCommand.COMMAND_WORD + " " + PREFIX_INGREDIENT_NAME + keyword);
        System.out.println(getModel().getFilteredIngredientList());
        System.out.println(getModel().getRestaurantBook().getIngredientList().size());
        assertTrue(getModel().getFilteredIngredientList().size()
                < getModel().getRestaurantBook().getIngredientList().size());
    }

    /**
     * Deletes all ingredients in the address book.
     */
    protected void deleteAllIngredients() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getRestaurantBook().getIngredientList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same member objects as {@code expectedModel}
     * and the member list panel displays the members in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new RestaurantBook(expectedModel.getRestaurantBook()), testApp.readStorageAddressBook());
        assertListMatching(getMemberListPanel(), expectedModel.getFilteredMemberList());
    }

    /**
     * Calls {@code MemberListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getMemberListPanel().rememberSelectedMemberCard();
    }

    /**
     * Asserts that the previously selected card is now deselected
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getMemberListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that only the card at {@code expectedSelectedCardIndex} is selected.
     * @see MemberListPanelHandle#isSelectedMemberCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getMemberListPanel().navigateToCard(getMemberListPanel().getSelectedCardIndex());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getMemberListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the selected card in the member list panel remain unchanged.
     * @see MemberListPanelHandle#isSelectedMemberCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getMemberListPanel().isSelectedMemberCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getMemberListPanel(), getModel().getFilteredMemberList());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
