package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.StatsWindowHandle;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.commands.stats.ViewStatsDaysCommand;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class ViewStatsDaysCommandSystemTest extends RestaurantBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHelpWindow() {
        assertStatsWindowNotOpen();

        //use accelerator
        getCommandBox().click();
        executeCommand(ViewStatsDaysCommand.COMMAND_WORD + " 30");
        assertStatiWindowOpen();

        // assert that the status bar too is updated correctly while the stats window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteMemberCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Asserts that the stats window is open, and closes it after checking.
     */
    private void assertStatiWindowOpen() {
        assertTrue(ERROR_MESSAGE, StatsWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new StatsWindowHandle(guiRobot.getStage(StatsWindowHandle.STATS_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the stats window isn't open.
     */
    private void assertStatsWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, StatsWindowHandle.isWindowPresent());
    }

}
