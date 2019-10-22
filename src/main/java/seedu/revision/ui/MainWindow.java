package seedu.revision.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.CommandResult;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends Window {

    private final Logger logger = LogsCenter.getLogger(getClass());

    private StartQuizWindow startQuizWindow;

    @FXML
    private StackPane answerableListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(primaryStage, logic);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        answerableListPanel = new AnswerableListPanel(logic.getFilteredAnswerableList());
        answerableListPanelPlaceholder.getChildren().add(answerableListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    @FXML
    public void handleStart() throws CommandException {
        logger.info(String.valueOf(logic.getFilteredAnswerableList().size()));
        if (logic.getFilteredAnswerableList().size() > 0 ) {
            startQuizWindow = new StartQuizWindow(getPrimaryStage(), getLogic());
            startQuizWindow.show();
            startQuizWindow.fillInnerParts();
        } else {
            resultDisplay.setFeedbackToUser("Cannot initialise quiz with empty test bank.");
            throw new CommandException("Cannot initialise quiz with empty test bank.");
        }
    }
    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.revision.logic.Logic#execute(String)
     */
    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isStart()) {
                handleStart();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
