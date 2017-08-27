package common.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class StringContainsMatcher extends BaseMatcher<String> {

    private final String expected;

    public StringContainsMatcher(String expected) {
        this.expected = expected;
    }

    public boolean matches(Object actualValue) {
        return actualValue != null && ((WebElement) actualValue).getText().contains(expected);
    }

    public void describeTo(Description description) {
        description.appendText("содержит текст ");
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).getText().contains(expected) ? "содержит текст " : "не содержит текст ";
        description.appendText(message + expected);
        description.appendValue(element);
    }

}
