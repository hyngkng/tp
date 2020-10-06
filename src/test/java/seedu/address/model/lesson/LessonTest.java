package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLessons.LECTURE;
import static seedu.address.testutil.TypicalLessons.VALID_DEADLINE;
import static seedu.address.testutil.TypicalLessons.VALID_MODULE_CODE;
import static seedu.address.testutil.TypicalLessons.VALID_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

    @Test
    public void isSameLesson() {
        // same object -> returns true
        assertTrue(LECTURE.isSameLesson(LECTURE));

        // null -> returns false
        assertFalse(LECTURE.isSameLesson(null));

        // different time and moduleCode -> returns false
        Lesson editedLecture = new LessonBuilder(LECTURE).withDeadline(VALID_DEADLINE)
                .withModuleCode(VALID_MODULE_CODE).build();
        assertFalse(LECTURE.isSameLesson(editedLecture));

        // different name -> returns false
        editedLecture = new LessonBuilder(LECTURE).withName(VALID_NAME).build();
        assertFalse(LECTURE.isSameLesson(editedLecture));
    }
}
