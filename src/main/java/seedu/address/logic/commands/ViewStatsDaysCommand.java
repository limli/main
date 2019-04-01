package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.Statistics;
import seedu.address.ui.StatsWindow;

/**
 * Opens a new window containing the stats.
 */
public class ViewStatsDaysCommand extends Command {

    public static final String COMMAND_WORD = "statsdays";
    public static final String MESSAGE_SHOWING_STATS = "Opened stats window.";
    public static final String MESSAGE_SIZE_CONSTRAINTS = "Days should be an integer between 1 and "
            + Statistics.getMaxDays() + " inclusive.";

    private static final String X_AXIS_NAME = "Date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the statistics of the bookings made"
            + " for the last many days specified.\n"
            + "Parameters: DAYS (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 30 ";

    private final int days;

    public ViewStatsDaysCommand(int days) {
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new StatsWindow(new Statistics(model.getRestaurantBook().getBookingList(), days)
                .generateGraphDataDays(), X_AXIS_NAME).show();
        return new CommandResult(MESSAGE_SHOWING_STATS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewStatsDaysCommand // instanceof handles nulls
                && days == ((ViewStatsDaysCommand) other).days); // state check
    }
}
