package seedu.revision.model.answerable;

import java.util.ArrayList;
import java.util.Set;

import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.answerable.answer.SaqAnswer;
import seedu.revision.model.category.Category;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Saq extends Answerable {

    /**
     * Every field must be present and not null.
     */
    public Saq(Question question, ArrayList<Answer> correctAnswerList, Difficulty difficulty,
               Set<Category> categories) {
        super(question, correctAnswerList, new ArrayList<>(), difficulty, categories);
    }

    /**
     * Checks whether the input Saq is valid.
     * @param saq the saq to validate.
     * @return boolean to indicate whether Saq is valid or not.
     */
    public static boolean isValidSaq(Saq saq) {
        requireNonNull(saq);
        if (saq.getCorrectAnswerList().contains(SaqAnswer.emptySaqAnswer())
                || saq.getWrongAnswerList().contains(SaqAnswer.emptySaqAnswer())) {
            return false;
        }
        return true;
    }

    /**
     * Returns an entire text string of the answerable (question with all possible answers,
     * difficulty level and categories)
     * @return answerable string
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: SAQ ")
                .append("Question: ")
                .append(" Correct Answers: ")
                .append(getCorrectAnswerList())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
