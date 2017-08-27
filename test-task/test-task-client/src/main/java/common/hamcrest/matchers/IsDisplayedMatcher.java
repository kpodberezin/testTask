package common.hamcrest.matchers;

import common.selenide.TaskElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class IsDisplayedMatcher extends BaseMatcher<WebElement> {

    public IsDisplayedMatcher() {
    }

    @Override
    public boolean matches(Object element) {
        return TaskElement.class.isAssignableFrom(element.getClass()) && ((TaskElement) element).isDisplayed();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("отображается");
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).isDisplayed() ? "отображается" : "не отображается";
        description.appendText(message);
    }

}
