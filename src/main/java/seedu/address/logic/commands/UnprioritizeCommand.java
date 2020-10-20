package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.ModuleCode;
import seedu.address.model.assignment.Name;
import seedu.address.model.assignment.Priority;
import seedu.address.model.assignment.Remind;
import seedu.address.model.assignment.Schedule;

/**
 * Removes priority for an assignment identified using it's displayed index from the address book.
 */
public class UnprioritizeCommand extends NegateCommand {

    public static final String COMMAND_WORD_SUFFIX = "prioritize";
    public static final String COMMAND_WORD = NegateCommand.COMMAND_WORD + COMMAND_WORD_SUFFIX;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the priority from the assignment identified by the index number "
            + "used in the displayed assignment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPRIORITIZE_ASSIGNMENT_SUCCESS = "Removed priority for Assignment: %1$s";
    public static final String MESSAGE_UNPRIORITIZE_ASSIGNMENT = "This assignment does not have priority set.";

    /**
     * Constructs an UnprioritiseCommand to remove priority from the specified assignment.
     * @param targetIndex index of the assignment in the filtered assignment list to remove priority.
     */
    public UnprioritizeCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (getTargetIndex().getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToUnprioritize = lastShownList.get(getTargetIndex().getZeroBased());

        if (!assignmentToUnprioritize.hasPriority() && model.hasAssignment(assignmentToUnprioritize)) {
            throw new CommandException(MESSAGE_UNPRIORITIZE_ASSIGNMENT);
        }

        assert(assignmentToUnprioritize.isReminded());
        Assignment unprioritisedAssignment = createUnprioritizedAssignment(assignmentToUnprioritize);

        model.setAssignment(assignmentToUnprioritize, unprioritisedAssignment);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENT);
        return new CommandResult(String.format(MESSAGE_UNPRIORITIZE_ASSIGNMENT_SUCCESS, unprioritisedAssignment));
    }

    /**
     * Creates and returns an {@code Assignment} with the details of {@code assignmentToUnprioritise}.
     */
    private static Assignment createUnprioritizedAssignment(Assignment assignmentToUnprioritize) {
        assert assignmentToUnprioritize != null;

        Name updatedName = assignmentToUnprioritize.getName();
        Deadline updatedDeadline = assignmentToUnprioritize.getDeadline();
        ModuleCode updatedModuleCode = assignmentToUnprioritize.getModuleCode();
        Remind updatedRemind = assignmentToUnprioritize.getRemind();
        Schedule updatedSchedule = assignmentToUnprioritize.getSchedule();
        Priority priority = new Priority();

        return new Assignment(updatedName, updatedDeadline, updatedModuleCode, updatedRemind, updatedSchedule,
                priority);
    }
}
